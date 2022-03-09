package com.huawei.storage.common.db;

import com.neptuny.cpit.error.ErrorCode;
import com.neptuny.cpit.etl.DBConf;
import com.neptuny.cpit.etl.DataSet;
import com.neptuny.cpit.etl.DataSetList;
import com.neptuny.cpit.etl.DefChecker;
import com.neptuny.cpit.etl.loader.Loader;
import com.neptuny.cpit.etl.util.SysUtils;
import com.neptuny.cpit.logger.Logger;

/**
 * Huawei Technologies  all rights reserved
 */
public class ServiceETLDatasetSaver {

    private DBConf dbConf;
    private Logger logger;


    public ServiceETLDatasetSaver(DBConf dbConf,Logger logger){
        this.dbConf = dbConf;
        this.logger = logger;
    }


    public void saveData(DataSetList dataSetList) throws Exception {
        for (int i = 0; i < dataSetList.size(); i++) {
            DataSet dataSet = dataSetList.get(i);
            //this is very import
            dbConf.setProcessingDataset(dataSet,i);
            saveData(dataSet);
        }
    }


    public void saveData(DataSet data) throws Exception {
        String ln = dbConf.getProperty("load.module.number");
        int lnum = Integer.parseInt(ln);
        for (int i = 1; i <= lnum; i++) {
            String loaderClass = dbConf.getPropertyForMod(i, "load.MODINDEX.module");
            logger.info("start save dataSet....................dataset name " + data.getName()
                    +" dataset size is : " + data.size()
                    +" loader is " + loaderClass);
            try {
                DefChecker defc=dbConf.getDefChecker();
                defc.setConf(dbConf);

                Loader loader = (Loader) SysUtils.createInstance(loaderClass);
                loader.connect(i,dbConf);
                loader.load(data);
                loader.disconnect();
                logger.info("load data to bco database success...");
            } catch (Exception e) {
                logger.error(ErrorCode.ETL_GENERIC_ERROR,"loader data error occurred "+e.getMessage(),e);
                throw e;
            }
        }
    }
}
