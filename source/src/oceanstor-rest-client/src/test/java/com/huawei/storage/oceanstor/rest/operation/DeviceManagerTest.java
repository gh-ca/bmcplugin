package com.huawei.storage.oceanstor.rest.operation;

import com.huawei.storage.oceanstor.UserInfo;
import com.huawei.storage.oceanstor.rest.constants.OperationNames;
import com.huawei.storage.oceanstor.rest.constants.OperationNamesEnum;
import com.huawei.storage.oceanstor.rest.domain.ConnectionData;
import com.huawei.storage.oceanstor.rest.exception.RestException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DeviceManagerTest {
	private transient final static Logger logger = Logger.getLogger(DeviceManagerTest.class);
	private DeviceManager deviceManager;

	@Before
	public void setUp() throws RestException, IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("log4j.properties"));
        PropertyConfigurator.configure(properties);
		ConnectionData connectionData = new ConnectionData();
		connectionData.setHostURL(UserInfo.hostUrl);
		connectionData.setUsername(UserInfo.username);
		connectionData.setPassword(UserInfo.password);
		deviceManager = new DeviceManager(connectionData);
		deviceManager.login();
	}



	@Test
	public void testCreateFileSystem() throws Exception {
		OceanStorOperation operation = new OceanStorOperation();
		operation.setOperationName(OperationNames.FileSystem.CREATE_FILESYSTEM);
		Map<String, String> operationData = new HashMap<String, String>();
		operation.putOperationData("NAME", "restManager999");
		operation.putOperationData("ALLOCTYPE", "0");
		operation.putOperationData("PARENTID", "2");
		operation.putOperationData("capacity", "20480000");
		
		OperationResult operationResult = deviceManager.performAction(operation);
		printResult(operationResult);
	}
	
	@Test
	public void testCreateFileSysteUseMO() throws Exception {
		//TODO  Use MO  make people use predefined key Bean
		
	}
	
	@Test
	public void testUpdateFileSystem() throws Exception {
		OceanStorOperation operation = new OceanStorOperation();
		operation.setOperationName(OperationNames.FileSystem.UPDATE_FILESYSTEM);
		Map<String, String> operationData = new HashMap<String, String>();
		operation.putOperationData("ID", "45");
		operation.putOperationData("NAME", "restManager888");
		OperationResult operationResult = deviceManager.performAction(operation);
		printResult(operationResult);
	}
	
	@Test
	public void testDeleteFileSystem() throws Exception {
		OceanStorOperation operation = new OceanStorOperation();
		operation.setOperationName(OperationNames.FileSystem.DELETE_FILESYSTEM);
		Map<String, String> operationData = new HashMap<String, String>();
		operation.putOperationData("id", "43");
		
		OperationResult operationResult = deviceManager.performAction(operation);
		printResult(operationResult);
	}
	
	
	@Test
	public void testFindFileSystem() throws Exception {
		OceanStorOperation operation = new OceanStorOperation();
		operation.setOperationName(OperationNames.FileSystem.FIND_FILESYSTEM_BY_ID);
		Map<String, String> operationData = new HashMap<String, String>();
		operation.putOperationData("id", "45");
		
		OperationResult operationResult = deviceManager.performAction(operation);
		printResult(operationResult);
	}

	@Test
	public void testFindFileSystemByName() throws Exception {
		OceanStorOperation operation = new OceanStorOperation();
		operation.setOperationName(OperationNames.FileSystem.FIND_FILESYSTEM_BY_NAME);
		Map<String, String> operationData = new HashMap<String, String>();
		operation.putOperationData("name", "restManager888");

		OperationResult operationResult = deviceManager.performAction(operation);
		printResult(operationResult);
	}
	
	@Test
	public void testFindFileSystemBatch() throws Exception {
		OceanStorOperation operation = new OceanStorOperation();
		operation.setOperationName(OperationNames.FileSystem.BATCH_FIND_FILESYSTEM);
		Map<String, String> operationData = new HashMap<String, String>();
		operation.putOperationData("range", "[0-100]");
		operation.putOperationData("filter", "ALLOCTYPE::0");
		
		OperationResult operationResult = deviceManager.performAction(operation);
		printResult(operationResult);
	}
	

	private void printResult(OperationResult operationResult) {
		System.out.println("======================Operation Result Start===========================");
		System.out.println("operationErrorCode:"+operationResult.getErrorCode());
		System.out.println("operationErrorDesc:"+operationResult.getErrorDescription());
        System.out.println("operationErrorSuggestion: " + operationResult.getErrorSuggestion());
        System.out.println("operationResultSize:"+operationResult.getResultData().size());
		System.out.println("operationResult:"+operationResult.getResultData());
		System.out.println("======================Operation Result End===========================");
	}
	
	
	
	@Test
	public void testCreateStoragePool() throws Exception {
		OceanStorOperation operation = new OceanStorOperation();
		operation.setOperationName(OperationNames.StoragePool.CREATE_STORAGE_POOL);
		
		operation.putOperationData("NAME", "testStoragePool222");
		operation.putOperationData("DESCRIPTION", "testStoragePool1");
		operation.putOperationData("PARENTID", "0");
		operation.putOperationData("PARENTTYPE", "266");
		operation.putOperationData("REPCAPACITYTHRESHOLD", "100");
		operation.putOperationData("EXTENTSIZE", "8192");
		operation.putOperationData("USAGETYPE", "2");
		operation.putOperationData("USERCONSUMEDCAPACITYTHRESHOLD", "60");
		operation.putOperationData("TIER1CAPACITY", "2097152");
		operation.putOperationData("TIER1DISKTYPE", "1");
		operation.putOperationData("USERCONSUMEDCAPACITYTHRESHOLD", "80");
		operation.putOperationData("TIER1RAIDDISKNUM", "5");
		operation.putOperationData("TIER1RAIDLV", "2");
		OperationResult operationResult = deviceManager.performAction(operation);
		printResult(operationResult);
	}
	
	@Test
	public void testDeleteStoragePool() throws Exception {
		OceanStorOperation operation = new OceanStorOperation();
		operation.setOperationName(OperationNames.StoragePool.DELETE_STORAGE_POOL);
		operation.putOperationData("ID","10");
		OperationResult operationResult = deviceManager.performAction(operation);
		printResult(operationResult);
	}
	
	@Test
	public void testUpdateStorage() throws Exception {
		OceanStorOperation operation = new OceanStorOperation();
		operation.setOperationName(OperationNames.StoragePool.UPDATE_STORAGE_POOL);
		operation.putOperationData("ID","9");
		operation.putOperationData("name", "restManager");
		OperationResult operationResult = deviceManager.performAction(operation);
		printResult(operationResult);
	}
	
	@Test
	public void testFindStorage() throws Exception {
		OceanStorOperation operation = new OceanStorOperation();
		operation.setOperationName(OperationNamesEnum.FIND_STORAGE_POOL_BY_ID);
		operation.putOperationData("ID","9");
		OperationResult operationResult = deviceManager.performAction(operation);
		printResult(operationResult);
	}


	
	
	@Test
	public void testFindStoragePoolByName() throws Exception {
		OceanStorOperation operation = new OceanStorOperation();
		operation.setOperationName(OperationNamesEnum.FIND_STORAGE_POOL_BY_NAME);
		operation.putOperationData("NAME","FileStoragePool001");
		OperationResult operationResult = deviceManager.performAction(operation);
		printResult(operationResult);
	}

	@Test
	public void testCreateNFSshare() throws Exception {
		OceanStorOperation operation = new OceanStorOperation();
		operation.setOperationName("create-NFS-share");
		operation.putOperationData("sharePath","/Test_filesystem05/");
		OperationResult operationResult = deviceManager.performAction(operation);
		printResult(operationResult);
	}

	@Test
	public void testCreateCIFSshare() throws Exception {
        for (int i = 0; i < 200; i++) {
            OceanStorOperation operation = new OceanStorOperation();
            operation.setOperationName("create-CIFS-share");
            operation.putOperationData("sharePath","/restManager999/");
            operation.putOperationData("name","restManager999"+i);
            OperationResult operationResult = deviceManager.performAction(operation);
            printResult(operationResult);
        }


	}

    @Test
    public void testGetCIFSSharebyName() throws Exception {
        OceanStorOperation operation = new OceanStorOperation();
        operation.setOperationName("find-CIFS-share-by-name");
        operation.putOperationData("name","share_123");
        OperationResult operationResult = deviceManager.performAction(operation);
        printResult(operationResult);
    }

    @Test
    public void testGetCIFSfuzzy() throws Exception {
        OceanStorOperation operation = new OceanStorOperation();
        operation.setOperationName("find-CIFS-share-by-name-fuzzy-single");
        operation.putOperationData("name","TestFileSystem21");
        OperationResult operationResult = deviceManager.performAction(operation);
        printResult(operationResult);
    }

    @Test
    public void testGetCIFSfuzzy_but_return_one() throws Exception {
        OceanStorOperation operation = new OceanStorOperation();
        operation.setOperationName("find-CIFS-share-by-name-fuzzy-single");
        operation.putOperationData("name","restManager999199");
        OperationExecutor executor = new OperationExecutor();
        executor.setDMConnection("admin","Pbu4@123",
                "https://10.143.133.201:8088/deviceManager/rest","0");
        OperationResult operationResult = executor.execute(operation);
        printResult(operationResult);

    }

    @Test
    public void testGetSystem() throws Exception {
        OceanStorOperation operation = new OceanStorOperation();
        operation.setOperationName("find-all-system");
        OperationResult result = deviceManager.performAction(operation);
        printResult(result);
    }

    @Test
    public void testGetAllDisk() throws Exception {
        OceanStorOperation operation = new OceanStorOperation();
        operation.setOperationName("find-all-disk");
        OperationResult result = deviceManager.performAction(operation);
        printResult(result);
    }

    @Test
    public void testGetDiskPool() throws Exception {
        OceanStorOperation operation = new OceanStorOperation();
        operation.setOperationName("find-all-diskpool");
        OperationResult result = deviceManager.performAction(operation);
        printResult(result);
    }




    @Test
    public void testGetStoragePool() throws Exception {
        OceanStorOperation operation = new OceanStorOperation();
        operation.setOperationName("find-all-storage-pool");
        OperationResult result = deviceManager.performAction(operation);
        printResult(result);
    }

    @Test
    public void testGetlun() throws Exception {
        OceanStorOperation operation = new OceanStorOperation();
        operation.setOperationName("find-all-lun");
        OperationResult result = deviceManager.performAction(operation);
        printResult(result);
    }

    @Test
    public void testGetfilesystem() throws Exception {
        OceanStorOperation operation = new OceanStorOperation();
        operation.setOperationName("find-all-filesystem");
        OperationResult result = deviceManager.performAction(operation);
        printResult(result);
    }

    @Test
    public void testGethost() throws Exception {
        OceanStorOperation operation = new OceanStorOperation();
        operation.setOperationName("find-all-host");
        OperationResult result = deviceManager.performAction(operation);
        printResult(result);
    }

    @Test
    public void testGetController() throws Exception {
        OceanStorOperation operation = new OceanStorOperation();
        operation.setOperationName("find-all-controller");
        OperationResult result = deviceManager.performAction(operation);
        printResult(result);
    }


    @Test
    public void testGetAllController() throws Exception {
        OceanStorOperation operation = new OceanStorOperation();
        operation.setOperationName("batch-find-eth_port");
        //get all management ip, selectType==2
        operation.putOperationData("filter","selectType::2");
        operation.putOperationData("range","[0-100]");
        OperationResult result = deviceManager.performAction(operation);
        printResult(result);

    }

    @Test
    public void testAssociateGetLun() throws Exception {
        OceanStorOperation operation = new OceanStorOperation();
        operation.setOperationName("get-associate-lun");
        operation.putOperationData("ASSOCIATEOBJTYPE","21");
        operation.putOperationData("ASSOCIATEOBJID","1");
        OperationResult result = deviceManager.performAction(operation);
        printResult(result);
    }


	@Test
	public void testGetPerformanceData() throws Exception {
        OceanStorOperation operation = new OceanStorOperation();
        operation.setOperationName("find-all-lun");
        OperationResult result = deviceManager.performAction(operation);
        printResult(result);
        operation.getOperationData().clear();
        List<Map<String, String>> resultData = result.getResultData();
        for(int i=0;i<10;i++){
            for(Map<String,String> data : resultData){
                String id = data.get("ID");
                String type = data.get("TYPE");
                String name = data.get("NAME");
                operation.setOperationName("get-current-performance-data");
                operation.putOperationData("CMO_STATISTIC_UUID",type+":"+id);
                operation.putOperationData("CMO_STATISTIC_DATA_ID_LIST","21,22");
                OperationResult perfResult = deviceManager.performAction(operation);
                printResult(perfResult);
            }
        }


    }

	@Test
	public void testGetBatchPerformanceData() throws Exception {
		OceanStorOperation operation = new OceanStorOperation();
		operation.setOperationName("get-current-performance-data");
		operation.putOperationData("CMO_STATISTIC_UUID", "207:0A");
		operation.putOperationData("CMO_STATISTIC_DATA_ID_LIST", "21,22");
		OperationResult perfResult = deviceManager.performAction(operation);
		printResult(perfResult);
	}



	@Test
	public void testLog() throws Exception {
		logger.debug("test");
	}
	
	
	@After
	public void tearDown() throws Exception {
		deviceManager.logout();
	}

}
