package com.huawei.storage;

public class UserInfo {

    public static String hostUrl = "https://10.143.133.201:8088/deviceManager/rest";

    public static String hostIp = "10.143.133.201";

    public static String port = "8088";

    public static String sftpPort = "22";

    public static String username = "admin";

    public static String password = "Pbu4@123";

    public static String loginRelativeURL = "/xxxx/sessions";

    public static String loginRelativeUri = "/server/compositeinfo";

    public static String scope = "0";

    public static String deviceId = "2102351QLH9WK5800028";

    public static String arrayId = "zhongjunsetsn1234567";

    public static String serverPath1 = hostIp+":/OSM/coffer_data/perf/perf_files/PerfData_5500_V3_SN_zhongjunsetsn1234567_SP0_0_20160811202105.tgz";

    public static String serverPath2 = hostIp+":/OSM/coffer_data/perf/perf_files/PerfData_5500_V3_SN_zhongjunsetsn1234567_SP0_0_20160725134922.tgz";

    public static String localPath = "d:/temps";

    public static String Controller_performance_body = "{\"207:0A\":{\"method\":\"GET\",\" url\":"+hostUrl+"/"+deviceId+"/performace_statistic/cur_statistic_data?CMO_STATISTIC_UUID=207:0A&CMO_STATISTIC_DATA_ID_LIST=21,511,22,69\"},\"207:0B\":{\"method\":\"GET\",\"url\":"+hostUrl+"/"+deviceId+"/performace_statistic/cur_statistic_data?CMO_STATISTIC_UUID=207:0B&CMO_STATISTIC_DATA_ID_LIST=21,511,22,69\"}}";

}
