package com.huawei.storage.connection.sftp;



import com.huawei.storage.UserInfo;
import org.apache.log4j.BasicConfigurator;

import com.huawei.storage.common.sftp.SftpAccount;
import com.huawei.storage.common.sftp.SftpTransfer;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BasicConfigurator.configure();

		SftpTransfer sftp = new SftpTransfer();
		SftpAccount account = new SftpAccount(UserInfo.hostIp, UserInfo.username, UserInfo.password, Integer.valueOf(UserInfo.port));
		sftp.transferFileFromDevice(UserInfo.arrayId, UserInfo.serverPath1, UserInfo.localPath, account, 31004, true);
		sftp.transferFileFromDevice(UserInfo.arrayId, UserInfo.serverPath2, UserInfo.localPath, account, 31004, true);
		//testContent();
				   
	}
}
