package com.huawei.storage.common.extracdata.constant;

/**
 * Copyright (c) Huawei Technologies Co., Ltd. 2022-2022. All rights reserved.
 */
public class IsmConstant
{
    //begin　  2008-8-18 AZ7D01197　增加告警导出功能的常量分割符
    /**告警文件中属性分割符号*/
    public static final String ALARM_ATTRIBUTE_SEPEARATOR = "\t";
    
    /** --- */
    public static final String ALARM_PARAMETER_PLACEHOLDER = "#???";//告警参数解释字符串中的占位字符串

    /** --- */
    public static final String ALARM_PARAMETER_SEPARATOR = ",";//告警参数分隔符
    
    /**空内容*/
    public static final String BLANK_CONTENT = "--";
    
    /**CSV分割符号*/
    public static final String CSV_SEPEARATOR = ",";
    
    /** 日期格式，年月日 时分秒  */
    public static final String DATA_FORMAT_YEAR_SECEND = "yyyy-MM-dd hh:mm:ss";
    
    /**按devSN作为数据库查询条件*/
    public static final String DB_DEVSN_PROPERTY_NAME = "devSN";
    
    /** 半角空格*/
    public static final String DBC_CASE_BLANK = " ";
    
    /**系统默认root用户名*/
    public static final String DEFAULT_ROOT_NAME_STRING = "admin";

    /** --- */
    public static final String ELEMENT_SEPARATOR = "@@@@@@"; // qKF10218 定一个比较通用的分割符

    /** --- */
    public static final String EMAIL_SEPARATOR = ";";//电子邮件分隔符号
    

    /** 国际化资源KEY错误码前缀*/
    public static final String ERROR_CODE_PREFIX = "lego.err.";
    
    /**xls分割符号*/
    public static final String EXCEL_SEPEARATOR = "\t";
    
    /** 文件分隔符    */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    /** --- */
    public static final String HYPER_MANAGER_NAME = "HyperManager";
    
    /**系统中表示实物的名字不能使无效的字符串*/
    public static final String INVALID_NAME_STRING = "--";

    /** --- */
    public static final boolean IS_WINDOWS;
    
    //end　  2008-8-18 AZ7D01197　增加告警导出功能的常量分割符
    
    /** 国际化资源KEY错误码前缀 */
    public static final String ISM_MENU_PREFIX = "ism.menu.";
    
    /**
     * 所有的MO都可以用 离线状态
     */
    public static final int MO_OFF_LINE = 80;

    /** --- */
    public static final String MOBILE_PHONE_SEPARATOR = ";";//手机号码分隔符号
    
    /**回车换行*/
    public static final String NEW_LINE = System.getProperty("line.separator");
    
    /**日志级别:危险*/
    public static final int OPERATELOG_LEVEL_DANGER = 4;
    
    //begin　 cKF17701  日志相关
    /**日志级别:提示*/
    public static final int OPERATELOG_LEVEL_INFO = 1;
    
    /**日志级别:一般*/
    public static final int OPERATELOG_LEVEL_NORMAL = 3;
    
    /**日志级别:警告*/
    public static final int OPERATELOG_LEVEL_WARNING = 2;
    
    /**操作结果:失败*/
    public static final int OPERATELOG_RESULT_FAIL = 0;
    
    /**操作结果:成功*/
    public static final int OPERATELOG_RESULT_SUCCESS = 1; 

    /** --- */
    public static final String PERFORMANCE_DATA_SEPARATOR = ",";//性能数据分隔符 
    
    /**当告警中有分割符时，使用替代符号*/
    public static final String REPLEASE_SEPEARATOR = " "; 

    /** --- */
    public static final String SEMICOLON = ";";//分号分隔符 
 
    /** --- */
    public static final String COMMA = ",";//逗号号分隔符 
    /**  连接线 */
    public static final String SHORT_CROSS_LINE = "-"; 
    
    /**平台新增交换机设备类型**/
    public static final String SWITCH_PRODUCT = "Switch"; 
    
    /**电源温度状态门限*/
    public static final int TEMPERATURE_THRESHOLD = 50;
    
    /**系统中表示无法明确的数值*/
    public static final int UNDEFINE_VALUE = Integer.MIN_VALUE;
    /**
     *硬盘域对应字段，主要用于resource_properties表
     */
    /**  连接线 */
    public static final String DASH = "_"; 
    /**  diskDomainId */
    public static final String DISK_DOMAIN_ID = "diskDomainId";
    
    /**
     *lun组所对应的lun id,主要用于lun组的resource_properties表
     */
    public static final String LUN_ID = "lunResId";
    
    /**
     *主机组映射主机ID,主机ID逗号分隔
     */
    public static final String HOST_ID = "hostResId";
    
    /**
     * V100R001C01LNJD01
     */
    public static final String ISM_N_SPECIAL_DEVVERSION = "V100R001C01LNJD01";
    /**
     * SSO_ADMIN
     */
    public static final String USER_TYPE_SSO_ADMIN = "sso_admin";
    
    /**
     * SSO_READONLY
     */
    public static final String USER_TYPE_SSO_READONLY = "sso_readonly";
    
    /**
     * admin
     */
    public static final String USER_TYPE_ADMIN = "admin";
    
    /**
     * publicKey
     */
    public static final String PUBLIC_KEY = "publicKey";

    /**
     * USER CREATED 用户自己创建的
     */
    public static final String USER_TYPE_USER_CREATED = "user_created";
    
    /**
     * Default template
     */
    public static final String DEFAULT_TEMPLATE_NAME = "Default template";
    
    /**
     * 自定义显示
     */
    public static final String CUSTOMIZE = "customized";
    
    /**
     * top
     */
    public static final String TOP = "top";
    /**
     * bottom
     */
    public static final String BOTTOM = "bottom";
    
    /**空白*/
    public static final String EMPTY_STR = "";
    
    /** 指标前缀 */
    public static final String METRIC_PREFIX = "ism.report.metric.name.";
    /** 指标后缀 */
    public static final String TITLE_SUFIX = ".title";
    /**离线设备的IP */
    public static final String OFFLINE_DEVICE_IP = "1.0.0.1";
    /**deviceName*/
    public static final String DEVICE_NAME = "deviceName";
    /**resourceid*/
    public static final String RESOURCE_ID = "resourceId";
    static
    {
        String os = System.getProperty("os.name");
        IS_WINDOWS = os != null && os.contains("Windows");
    }

    
}