package com.huawei.storage.etl;

import com.huawei.storage.common.db.BCODBManager;
import com.huawei.storage.common.db.ServiceETLDatasetSaver;
import com.huawei.storage.utils.CommonUtils;
import com.neptuny.cpit.error.ErrorCode;
import com.neptuny.cpit.etl.DBConf;
import com.neptuny.cpit.etl.DSStatus;
import com.neptuny.cpit.etl.DataSetList;
import com.neptuny.cpit.etl.DefChecker;
import com.neptuny.cpit.etl.util.AuditStats;
import com.neptuny.cpit.etl.util.AuditUtil;
import com.neptuny.scheduler.task.AbstractService;

import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Huawei Technologies  all rights reserved
 */
public class HWStorageETLService extends AbstractService {

    private LinkedBlockingQueue<DataSetList> queue = new LinkedBlockingQueue<DataSetList>();
    private DefChecker defChecker;


    @Override
    public void init(Properties props) {
        super.init(props);
        this.logger.info("start init ETL service");
    }

    public void configure() {
        this.logger.info("start configure ETL service");
        //this.logger.info("start check all......" + this.props);
    }

    public void serviceImpl() throws Exception {
        this.logger.info("-----------------------------Huawei ETL Service Start-----------------------------------");
        this.logger.info("start check all props" );
        BCODBManager bcodbManager = getBCODBManager();
        DBConf dbConf = bcodbManager.createDBConf();
        DataSetList dataSetList;
        try {
            HWStorageExtractor extractor = new HWStorageExtractor(this.logger, dbConf);
            extractor.init(this.props);
            this.logger.info("start extract data from huawei storage array");
            dataSetList = extractor.extract();
            queue.put(dataSetList);
            this.logger.info("put all data into queue , queue data size is " + queue.size());
        } catch (Exception e) {
            logger.error(ErrorCode.ETL_GENERIC_ERROR, e.getMessage());
            throw e;
        } finally {
            defChecker = dbConf.getDefChecker();
            dbConf.disconnect();
            bcodbManager.releaseConnection();
            logger.info("service connection released");

        }
        this.logger.info("-----------------------------Huawei ETL Service End-----------------------------------");

    }

    public void saverImpl() throws Exception {
        this.logger.info("-----------------------------Huawei ETL Saver Start-----------------------------------");
        BCODBManager manager = getBCODBManager();
        DBConf dbConf = manager.createDBConf();
        dbConf.setDefChecker(defChecker);
        DataSetList dataSetList = null;
        try {
            if(queue.peek()==null){
                this.logger.warn("no dataset list to save, try next round");
                return;
            }
            while (queue.peek() != null) {
                this.logger.info("poll a dataset from queue , queue data size is " + queue.size());
                dataSetList = queue.poll();
                long startTime = System.currentTimeMillis();
                this.logger.info("start save dataList,the dataList size is " + dataSetList.size());
                ServiceETLDatasetSaver saver = new ServiceETLDatasetSaver(dbConf, this.logger);
                saver.saveData(dataSetList);
                this.logger.info("end save data save data consume :" + (
                        System.currentTimeMillis()-startTime) + "ms");
                this.saveLoadingStatistics(dbConf);
                DSStatus status = new DSStatus();
                status.setLastCntr("DEFAULT", CommonUtils.getCurrentTimeStamp());
                status.setMsg("DEFAULT", "Save Data OK ." + dataSetList.size() + "records saved...");
                dbConf.updateStatus(status);
            }
        } catch (Exception e) {
            logger.error(ErrorCode.ETL_GENERIC_ERROR, e.getMessage());
            throw e;
        } finally {
            if(dataSetList!=null){
                dataSetList.clear();
                dataSetList = null;
            }
            dbConf.disconnect();
            manager.releaseConnection();
            logger.info("service connection released");
        }
        this.logger.info("-----------------------------Huawei ETL Saver End-----------------------------------");
    }

    public void doStop() {

    }

    public void doKill() {

    }

    private BCODBManager getBCODBManager() {
        return new BCODBManager(this.serviceid, this.srcid, this.taskpsetid, this.schedulerid, this.componentid, this.props);
    }

    private void saveLoadingStatistics(DBConf dbConf) {
        AuditUtil auditUtil = dbConf.getAuditUtil();
        if(auditUtil != null) {
            AuditStats stats = auditUtil.getEtlLoadedStats();
            stats.setLoadElapsedTime(1);
            stats.setOverallElapsedTime(2);
            try {
                String e1 = dbConf.getPropertyForMod(2, "load.MODINDEX.module");
                if(e1 != null && !e1.contains("SeriesMessageL")) {
                    auditUtil.auditSummaryLoaderActions();
                }
            } catch (Exception e) {
                this.logger.error(e, "Error saving loader audit statistics - serviceid:"
                        + this.serviceid);
            }
        }

    }

}
