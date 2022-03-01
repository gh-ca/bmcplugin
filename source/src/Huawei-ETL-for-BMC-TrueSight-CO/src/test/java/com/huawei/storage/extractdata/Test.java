package com.huawei.storage.extractdata;

import com.huawei.storage.common.extracdata.PerfStatHisFileProxy;

public class Test {

	public static void main(String[] args) throws Exception {

		//BasicConfigurator.configure();
		PerfStatHisFileProxy.queryPerfStatHisFileInfoByCompress(
				"d:\\temps\\PerfData_5500_V3_SN_zhongjunsetsn1234567_SP0_0_20160725134922.tgz");

	}

}
