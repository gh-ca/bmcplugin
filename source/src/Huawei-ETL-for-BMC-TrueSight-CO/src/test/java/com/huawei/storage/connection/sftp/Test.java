package com.huawei.storage.connection.sftp;



import org.apache.log4j.BasicConfigurator;

import com.huawei.storage.common.sftp.SftpAccount;
import com.huawei.storage.common.sftp.SftpTransfer;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BasicConfigurator.configure();

		SftpTransfer sftp = new SftpTransfer();
		SftpAccount account = new SftpAccount("10.158.196.210", "admin", "Admin@storage", 31004);
		sftp.transferFileFromDevice("zhongjunsetsn1234567", "10.158.196.210:/OSM/coffer_data/perf/perf_files/PerfData_5500_V3_SN_zhongjunsetsn1234567_SP0_0_20160811202105.tgz", "d:/temps", account, 31004, true);
		sftp.transferFileFromDevice("zhongjunsetsn1234567", "10.158.196.210:/OSM/coffer_data/perf/perf_files/PerfData_5500_V3_SN_zhongjunsetsn1234567_SP0_0_20160725134922.tgz", "d:/temps", account, 31004, true);
		//testContent();
				   
	}
}
