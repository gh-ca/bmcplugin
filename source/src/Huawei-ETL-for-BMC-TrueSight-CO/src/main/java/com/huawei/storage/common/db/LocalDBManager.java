package com.huawei.storage.common.db;

import com.huawei.storage.domain.StorageObject;
import com.huawei.storage.exception.ETLException;
import com.neptuny.cpit.error.ErrorCode;
import com.neptuny.cpit.etl.DataSet;
import com.neptuny.cpit.logger.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class LocalDBManager {
    private final Logger logger = Logger.getLogger(LocalDBManager.class);
    private Connection connection;

    LocalDBManager() throws Exception {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:metricList"+System.currentTimeMillis()
                    +".db");
        } catch ( Exception e ) {
            logger.error(ErrorCode.ETL_GENERIC_ERROR,"initial jdbc connection driver failed",e);
            throw e;
        }
    }



    public void saveMetricData(StorageObject obj) throws ETLException {
        Map<String, List<Map<String, String>>> metricData = obj.getMetricData();
        try {
            for(Map.Entry<String,List<Map<String,String>>> entry : metricData.entrySet()){
                String tableName = entry.getKey();
                List<Map<String, String>> metricList = entry.getValue();
                createTable(tableName,metricList.get(0));
                for(Map<String,String> metricMap : entry.getValue()){
                    insertData(tableName, metricMap);
                }
            }

        } catch (SQLException e) {
            logger.error(ErrorCode.ETL_GENERIC_ERROR,"error happened when save data ,"
                    +e.getMessage(),e);
            throw new ETLException("error happened when save data",e);
        }


    }

    private void insertData(String tableName, Map<String, String> values) throws SQLException {
        StringBuilder keyBuf = new StringBuilder("INSERT INTO  " + tableName + "(");
        StringBuilder valueBuf = new StringBuilder("VALUES (");
        for(Map.Entry<String,String> columnEntry: values.entrySet()){
            keyBuf.append(columnEntry.getKey()).append(",");
            valueBuf.append("'"+columnEntry.getValue()+"'").append(",");
        }
        keyBuf = replaceLastCommaToBracket(keyBuf);
        valueBuf = replaceLastCommaToBracket(valueBuf);
        keyBuf.append(valueBuf);
        PreparedStatement preparedStatement = connection.prepareStatement(keyBuf.toString());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    private StringBuilder replaceLastCommaToBracket(StringBuilder sf){
        return sf.deleteCharAt(sf.length()-1).append(")");
    }


    private void createTable(String tableName,Map<String,String> values) throws SQLException {
        StringBuilder sf = new StringBuilder("CREATE TABLE " + tableName +"(");
        for(String column : values.keySet()){
            sf.append(column).append(" CHAR(100)").append(",");
        }
        sf = replaceLastCommaToBracket(sf);
        PreparedStatement preparedStatement = connection.prepareStatement(sf.toString());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public DataSet getDataSet(String tableName){
        DataSet dataSet = null;
        try {
            dataSet = new DataSet(tableName);
            String[] row = dataSet.newRow();
            String querySQL = "select * from " + tableName ;
            PreparedStatement preparedStatement = connection.prepareStatement(querySQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData();
            preparedStatement.close();
        } catch (IOException e) {
           logger.error(ErrorCode.ETL_GENERIC_ERROR,"");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataSet;
    }
}
