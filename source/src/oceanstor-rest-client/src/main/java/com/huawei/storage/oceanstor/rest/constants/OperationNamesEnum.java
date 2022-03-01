package com.huawei.storage.oceanstor.rest.constants;

public enum OperationNamesEnum {
	//FileSystem
	CREATE_FILESYSTEM("create-filesystem"),          
	DELETE_FILESYSTEM("delete-filesystem"),           
	FIND_FILESYSTEM_BY_ID("find-filesystem-by-id"),
	FIND_FILESYSTEM_BY_NAME("find-filesystem-by-name"),
	BATCH_FIND_FILESYSTEM("batch-find-filesystem"),
	UPDATE_FILESYSTEM("update-filesystem"),
	//StoragePool
	CREATE_STORAGE_POOL("create-storage-pool"),
	DELETE_STORAGE_POOL("delete-storage-pool"),
	FIND_STORAGE_POOL_BY_ID("find-storage-pool-by-id"),
	FIND_STORAGE_POOL_BY_NAME("find-storage-pool-by-name"),
	BATCH_FIND_STORAGE_POOL("batch-find-storage-pool"),
	UPDATE_STORAGE_POOL("update-storage-pool");
	//NAS SHARE
	
	
	
	//SnapShot
	
	
	
	//NAS Access
	
	
	
	
	
	
	private String operationName;
	
	OperationNamesEnum(String operationName){
		this.operationName = operationName;
	}
	
	public String getValue(){
		return this.operationName;
	}
}
