/*
 *  Copyright (c) Huawei Technologies Co., Ltd. 2022-2022. All rights reserved.
 */
package com.huawei.storage.common.db;

import com.neptuny.cpit.database.DatabaseConnectionManagerFactory;
import com.neptuny.cpit.database.DatabaseConnectionManagerInterface;
import com.neptuny.cpit.etl.DBConf;
import com.neptuny.cpit.etl.DefChecker;
import com.neptuny.cpit.etl.util.AuditUtil;
import com.neptuny.cpit.etl.util.CryptoUtil;

import java.sql.Connection;
import java.util.Properties;


public class BCODBManager {
    private DatabaseConnectionManagerInterface dbManager;
    private Connection connection;
    private String serviceid;
    private String sourceid;
    private String taskpsetid;
    private String schedulerid;
    private String componentid;
    private Properties prop;

    public BCODBManager(int svcid, int srcid, int tskptid, int schedid, int compid, Properties prop){
        this.dbManager = DatabaseConnectionManagerFactory.getInstance();
        this.connection = dbManager.getConnection();
        this.serviceid = String.valueOf(svcid);
        this.sourceid = String.valueOf(srcid);
        this.taskpsetid = String.valueOf(tskptid);
        this.schedulerid = String.valueOf(schedid);
        this.componentid = String.valueOf(compid);
        this.prop = prop;
    }

    public DBConf createDBConf() throws Exception {
        String driverName = dbManager.getDriverClassName();
        DBConf dbConf = new DBConf(connection, driverName, serviceid, sourceid, taskpsetid, schedulerid, componentid);
        dbConf.setCryptoUtil(new CryptoUtil());
        dbConf.loadConf(prop);
        DefChecker vDefCk = new DefChecker();
        vDefCk.setConf(dbConf);
        dbConf.setDefChecker(vDefCk);
        AuditUtil vAudUt = new AuditUtil();
        vAudUt.setConf(dbConf);
        dbConf.setAuditUtil(vAudUt);
        return dbConf;
    }

    public Connection getConnection() {
        return connection;
    }

    public void releaseConnection(){
        dbManager.releaseConnection(connection);
    }
}
