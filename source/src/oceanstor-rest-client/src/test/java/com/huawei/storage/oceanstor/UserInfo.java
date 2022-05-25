package com.huawei.storage.oceanstor;

public class UserInfo {

    public static String hostUrl = "https://10.143.133.201:8088/deviceManager/rest";

    public static String username = "admin";

    public static String password = "Pbu4@123";

    public static String loginRelativeURL = "/xxxx/sessions";

    public static String loginRelativeUri = "/server/compositeinfo";

    public static String scope = "0";

    public static String deviceId = "2102351QLH9WK5800028";

    public static String Controller_performance_body = "{\"207:0A\":{\"method\":\"GET\",\" url\":"+hostUrl+"/"+deviceId+"/performace_statistic/cur_statistic_data?CMO_STATISTIC_UUID=207:0A&CMO_STATISTIC_DATA_ID_LIST=21,511,22,69\"},\"207:0B\":{\"method\":\"GET\",\"url\":"+hostUrl+"/"+deviceId+"/performace_statistic/cur_statistic_data?CMO_STATISTIC_UUID=207:0B&CMO_STATISTIC_DATA_ID_LIST=21,511,22,69\"}}";

}
