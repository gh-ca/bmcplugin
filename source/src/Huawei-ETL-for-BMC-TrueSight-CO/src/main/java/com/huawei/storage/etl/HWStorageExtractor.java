package com.huawei.storage.etl;

import com.huawei.storage.constants.ConnectionVO;
import com.huawei.storage.domain.StorageObject;
import com.huawei.storage.exception.ETLException;
import com.huawei.storage.job.DataMapper;
import com.huawei.storage.job.RestJob;
import com.neptuny.cpit.etl.*;
import com.neptuny.cpit.etl.extractor.Extractor;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created by m00373015 on 2016/8/18.
 */
public class HWStorageExtractor  extends Extractor{

    private static final Logger logger = Logger.getLogger(HWStorageExtractor.class);
    private ExecutorService executorService;
    private ConnectionVO conn = new ConnectionVO();
    private com.neptuny.cpit.logger.Logger bcoLogger;
    private DBConf dbConf;



    public HWStorageExtractor(){
        this.bcoLogger = com.neptuny.cpit.logger.Logger.getLogger();
        executorService = Executors.newFixedThreadPool(10);
    }

    HWStorageExtractor(com.neptuny.cpit.logger.Logger bcoLogger, DBConf dbConf){
        executorService = Executors.newFixedThreadPool(10);
        this.bcoLogger = bcoLogger;
        this.dbConf = dbConf;
    }

    private void validateConfig(Properties props) {
        //validate all config

    }

    public void init(Properties props){
        bcoLogger.info("start init ETL parameter ");
        executorService = Executors.newFixedThreadPool(10);
        conn.setUsername(props.getProperty("huawei.username"));
        conn.setPassword(props.getProperty("huawei.password"));
        conn.setScope("0");
        conn.setHostIP(props.getProperty("huawei.hostname"));
        conn.setRestPort(props.getProperty("huawei.rest.port"));
        conn.setSftpPort(props.getProperty("huawei.sftp.port"));
    }

    public DataSetList extract() throws Exception {
        Log.put("start extract all data");
        // get storageObject and capacityData from rest
        Future<List<StorageObject>> restData = executorService.submit(new RestJob(conn));

        // get performanceData from sftp
        /*Future<Map<String, Map<String, String>>> performanceData = executorService.
                submit(new PerformanceDataJob(conn));*/

        List<StorageObject> storageObjects = restData.get(5, TimeUnit.MINUTES);
        bcoLogger.info("All storageObject get from rest, size is " + storageObjects.size());
        /*Map<String, Map<String, String>> perfData = performanceData.get(5, TimeUnit.MINUTES);
        bcoLogger.info("All performance data get from sftp size is :" + perfData.size());*/
        // mapping data to object
        // mapping metric data
        DataSetList list = new DataSetList();
        if(storageObjects!=null) {
            bcoLogger.info("start transform data to bco dataset, dataset size will be "
                    + storageObjects.size());

            DataMapper mapper = new DataMapper(dbConf,bcoLogger);
            //list = mapper.mappingAllData(storageObjects, perfData);
            list = mapper.mappingAllData(storageObjects);
        }else {
            throw new ETLException("Get data failed...");
        }
        executorService.shutdownNow();
        return list;
    }

    @Override
    public void disconnect() throws Exception {
        super.disconnect();
        if(executorService!=null){
            executorService.shutdownNow();
        }
    }
}
