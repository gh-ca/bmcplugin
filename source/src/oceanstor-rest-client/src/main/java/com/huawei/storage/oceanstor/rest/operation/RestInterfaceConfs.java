package com.huawei.storage.oceanstor.rest.operation;

import com.huawei.storage.oceanstor.rest.constants.OperationError;
import com.huawei.storage.oceanstor.rest.exception.RestException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;


/**
 *
 * Huawei Technologies  all rights reserved
 */
public class RestInterfaceConfs {
	private static Logger logger = Logger.getLogger(RestInterfaceConfs.class);
	//operationName , [restMethod,restRelativePath]
	public static Map<String, List<String>> conf = new HashMap<String, List<String>>();
	
	//when the class is loading , load the properties once
	static{
		Properties properties = new Properties();
		try {
			properties.load(RestInterfaceConfs.class.getResourceAsStream("/config/restOperations.properties"));
			for(Object key : properties.keySet()){
				String value = (String) properties.get(key);
				String[] attrs = value.split(";");
				List<String> list = new ArrayList<String>();
				list.add(attrs[0]);
				list.add(attrs[1]);
				conf.put((String)key, list);
			}
		} catch (IOException e) {
			logger.error("The config file loading error : "+e.getMessage(),e);
		}
	}
	
	
	public static boolean isValidOperation(String operationName) throws RestException{
		doCheck();
		return conf.get(operationName)==null? false : true ;
	}
	
	
	private static void doCheck() throws RestException {
		if(conf.size()==0){
			throw OperationError.INTERNAL.OPERATION_CONFIG_FILE_LOADING_ERROR
			.newException(RestInterfaceConfs.class.getResource("")+ "config/restOperations.properties");
		}
	}


	public static String getRestMethod(String operationName) throws RestException{
		doCheck();
		return conf.get(operationName).get(0);
	}
	
	public static String getRestRelativePath(String operationName) throws RestException {
		doCheck();
		return conf.get(operationName).get(1);
	}
}
