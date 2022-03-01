package com.huawei.storage.common.extracdata.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.huawei.storage.common.extracdata.constant.LegoNumberConstant;

/**
 * <功能描述>
 * 
 * @author  l90005176
 * @version V100R100C00
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ReportUtil
{
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ReportUtil.class); 
            
    /**
     * 错误
     */
    public static final String ERROR = "error";
    
    /**
     * 错误编码
     */
    public static final String ERROR_CODE = "errorCode";
    
    /**
     * 错误信息
     */
    public static final String DESCRIPTION = "description";
    
    /**
     * 模板ID
     */
    public static final String TEMPLATE_ID = "templateId";
    
    /**
     * 错误信息前缀
     */
    public static final String ERROR_PREFIX = "lego.err.";
    
    /**
     * 注册IP
     */
    public static final String DEV_IP = "accessIp";
    
    /**
     * 注册IP
     */
    public static final String STATUS = "status";
    
   
    /**
     * <获取凌晨时间>
     * <功能详细描述>
     * 
     * @param time 参考时间
     * @return Long <Long>
     */
    public static Long getZeroClockTimeOfDay(Long time)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(time));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Long dayTime = c.getTime().getTime();
        return dayTime;
    }
    
    /**
     * 获取当前时间前 n 天 时间和当前时间的字串
     * @param time 需要返回的天数
     * @return 返回   开始时间和结束时间的数组
     * @see [类、类#方法、类#成员]
     */
    public static long[] getPeriodOfTime(int time)
    {
        long[] returnTime = new long[LegoNumberConstant.TWO];
        Date todayTime = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(todayTime);
        c.add(Calendar.DAY_OF_YEAR, -time);
        returnTime[0] = c.getTime().getTime();
        returnTime[1] = todayTime.getTime();
        return returnTime;
    }
    
    /**
     * <分钟区间转换为毫秒>
     * <功能详细描述> 
     * @param minute 分钟
     * @return long 毫秒
     * @see [类、类#方法、类#成员]
     */
    public static long minuteToMillis(int minute)
    {
        return (long) minute * LegoNumberConstant.SIX * LegoNumberConstant.TEN
                * LegoNumberConstant.THOUSAND;
    }
    
    /**
     * <小时区间转换为毫秒>
     * <功能详细描述> 
     * @param hour 小时int
     * @return long 毫秒
     * @see [类、类#方法、类#成员]
     */
    public static long hourToMillis(int hour)
    {
        return (long) hour * LegoNumberConstant.SIX * LegoNumberConstant.TEN
                * LegoNumberConstant.SIX * LegoNumberConstant.TEN * LegoNumberConstant.THOUSAND;
    }
    
    /**
     * <把英文字符串转换为符合Title要求>
     * <功能详细描述> 
     * @param inStr 英文String
     * @param local 语言
     * @return 符合Title要求的String
     * @see [类、类#方法、类#成员]
     */
    public static String changeToEnglishTitle(String inStr, Locale local)
    {
        String resultStr = "";
        String[] sb = inStr.split(" ");
        List<String> words = new ArrayList<String>();
        words.add("of");
        words.add("and");
        words.add("in");
        words.add("on");
        words.add("at");
        words.add("to");
        words.add("a");
        words.add("an");
        words.add("the");
        for (int i = 0; i < sb.length; i++)
        {
            if (i == 0 || !words.contains(sb[i]))
            {
                String tmpStr = sb[i].substring(0, 1);
                sb[i] = tmpStr.toUpperCase(local).concat(sb[i].substring(1));
            }
            resultStr = resultStr.concat(sb[i]);
            resultStr = resultStr.concat(" ");
        }
        resultStr = resultStr.substring(0, resultStr.length() - 1);
        return resultStr;
    }
    
    /**
     * <Fortify检查路径安全性>
     * <功能详细描述> 
     * @param srcpath 检查路径
     * @return 安全性 true:安全
     * @see [类、类#方法、类#成员]
     */
    public static boolean validateDir(String srcpath)
    {
        boolean result1 = null != srcpath && !"".equals(srcpath);
        boolean result2 = null != srcpath && srcpath.indexOf("\"") < 0 && srcpath.indexOf("|") < 0
                && srcpath.indexOf("?") < 0;
        boolean result3 = null != srcpath && srcpath.indexOf("*") < 0 && srcpath.indexOf("<") < 0
                && srcpath.indexOf(">") < 0;
        return !(result1 && result2 && result3);
    }
    
    /**
     * 是否是Linux操作系统
     * @return true or false
     */
    public static boolean isLinux()
    {
        String n = System.getProperty("os.name");
        if (n == null)
        {
            return false;
        }
        else
        {
            return n.toLowerCase(Locale.US).indexOf("linux") >= 0;
        }
        
    }
    
    /**
     * 是否是windows操作系统
     * @return true or false
     */
    public static boolean isWindows()
    {
        String n = System.getProperty("os.name");
        if (n == null)
        {
            return false;
        }
        else
        {
            return n.toLowerCase(Locale.US).indexOf("windows") >= 0;
        }
    }
    
   
    /**
     * 获取JSON的值，如果没有返回默认值
     * @param obj obj
     * @param defaultVal defaultVal
     * @param paramName paramName
     * @return String
     */
    public static String getJSONStrVal(JSONObject obj, String defaultVal, String paramName)
    {
        try
        {
            return obj.getString(paramName);
        }
        catch (JSONException e)
        {
            return defaultVal;
        }
    }
    
    /**
     * 获取JSON的值，如果没有返回默认值
     * @param obj obj
     * @param paramName paramName
     * @return String
     */
    public static String getJSONStrVal(JSONObject obj, String paramName)
    {
        return getJSONStrVal(obj, null, paramName);
    }
    
    /**
     * 获取JSON的值，如果没有返回默认值
     * @param obj obj
     * @param defaultVal defaultVal
     * @param paramName paramName
     * @return String
     */
    public static int getJSONIntVal(JSONObject obj, int defaultVal, String paramName)
    {
        try
        {
            return obj.getInt(paramName);
        }
        catch (JSONException e)
        {
            return defaultVal;
        }
    }
    
    /**
     * 获取JSON的值，如果没有返回默认值
     * @param obj obj
     * @param paramName paramName
     * @return int
     */
    public static int getJSONIntVal(JSONObject obj, String paramName)
    {
        return getJSONIntVal(obj, LegoNumberConstant.ZERO, paramName);
    }
    
    /**
     * 获取JSON的值，如果没有返回默认值
     * @param obj obj
     * @param defaultVal defaultVal
     * @param paramName paramName
     * @return String
     */
    public static long getJSONLongVal(JSONObject obj, long defaultVal, String paramName)
    {
        try
        {
            return obj.getLong(paramName);
        }
        catch (JSONException e)
        {
            return defaultVal;
        }
    }
    
    /**
     * 获取JSON的值，如果没有返回默认值
     * @param obj obj
     * @param paramName paramName
     * @return int
     */
    public static long getJSONLongVal(JSONObject obj, String paramName)
    {
        return getJSONLongVal(obj, LegoNumberConstant.ZERO, paramName);
    }
    
    /**
     * 检测资源类型合法性
     * @param resourceType resourceType
     * @return boolean
     */
    public static boolean checkResourceType(int resourceType)
    {
        if (resourceType < LegoNumberConstant.ONE || resourceType > LegoNumberConstant.THIRTY_SIX)
        {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
    
    /**
     * 获取JSON的值，如果没有返回默认值
     * @param obj obj
     * @param paramName paramName
     * @return boolean boolean
     */
    public static boolean getJSONBoolVal(JSONObject obj, String paramName)
    {
        try
        {
            return obj.getBoolean(paramName);
        }
        catch (JSONException e)
        {
            return Boolean.FALSE;
        }
    }
    
   
    /** 
     * <putJsonVal>
     * @param <T> the type parameter
     * @param json json
     * @param paramName paramName
     * @param val val
     */
    public static <T> void putJsonVal(JSONObject json, String paramName, T val)
    {
        if (json == null || paramName == null)
        {
            throw new IllegalArgumentException("json || paramName is null");
        }
        try
        {
            json.put(paramName, val);
        }
        catch (JSONException e)
        {
            throw new IllegalArgumentException("json error!", e);
        }
    }
    
    /** 
     * 设置JSON参数
     * @param <T> the type parameter
     * @param json json
     * @param paramName paramName
     * @param val val
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T getJsonVal(JSONObject json, String paramName, T val)
    {
        if (json == null || paramName == null)
        {
            throw new IllegalArgumentException("json || paramName is null");
        }
        try
        {
            T value = (T) json.get(paramName);
            return value;
        }
        catch (JSONException e)
        {
            return val;
        }
    }
    
    /**
     * 检测并确保路径存在
     * @param loc loc
     */
    public static void checkDirectory(String loc)
    {
        if (VerifyUtil.isEmpty(loc))
        {
            return;
        }
        File file = new File(loc);
        if (!file.exists())
        {
            if (!file.mkdir())
            {
                logger.warn("mkdir error");
            }
        }
    }
    
    /** 
     * 转化数字
     * @param numStr numStr
     * @param defaultVal defaultVal
     * @return int
     */
    public static int parseInt(String numStr, int defaultVal)
    {
        int result = defaultVal;
        try
        {
            result = Integer.valueOf(numStr);
        }
        catch (NumberFormatException e)
        {
            return result;
        }
        return result;
    }
    
    /**
     * 转化数字
     * @param numStr numStr
     * @return int int value
     */
    public static int parseInt(String numStr)
    {
        int result = LegoNumberConstant.ZERO;
        try
        {
            result = Integer.valueOf(numStr.trim());
        }
        catch (NumberFormatException e)
        {
            return result;
        }
        return result;
    }
    
    /** 
     * <转化数字>
     *
     * @param numStr numStr
     * @param defaultVal defaultVal
     * @return long
     */
    public static long parseLong(String numStr, long defaultVal)
    {
        long result = defaultVal;
        try
        {
            result = Long.valueOf(numStr);
        }
        catch (NumberFormatException e)
        {
            return result;
        }
        return result;
    }
    
    /**
     * 转化数字
     * @param numStr numStr
     * @return long
     */
    public static long parseLong(String numStr)
    {
        long result = LegoNumberConstant.ZERO;
        try
        {
            result = Long.valueOf(numStr);
        }
        catch (NumberFormatException e)
        {
            return result;
        }
        return result;
    }
    
    /**
     * 拷贝目录
     * @param sourceDir sourceDir
     * @param targetDir targetDir
     */
    public static void copyDirectiory(String sourceDir, String targetDir)
    {
        
        //新建目标目录
        checkDirectory(targetDir);
        //获取源文件夹当下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        if (file == null)
        {
            return;
        }
        for (int i = 0; i < file.length; i++)
        {
            if (file[i].isFile())
            {
                //源文件
                File sourceFile = file[i];
                //目标文件
                File targetFile = new File(
                        new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                FileUtil.copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory())
            {
                //准备复制的源文件夹
                String dir1 = sourceDir + File.separator + file[i].getName();
                //准备复制的目标文件夹
                String dir2 = targetDir + File.separator + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }
    
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     * @param fileName fileName
     * @return String
     */
    public static String readFileByLines(String fileName)
    {
        if (fileName == null)
        {
            return null;
        }
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuilder result = new StringBuilder();
        InputStreamReader isr = null;
        FileInputStream fis = null;
        try
        {
            //注意从UTF8 读取 by g
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis, "utf-8");
            reader = new BufferedReader(isr);
            String line = null;
            // 一次读入一行，直到读入null为文件结束
            while ((line = reader.readLine()) != null)
            {
                // 显示行号
                result.append(line).append("\r\n");
            }
        }
        catch (IOException e)
        {
            logger.error("read file error", e);
        }
        finally
        {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(isr);
            IOUtils.closeQuietly(reader);

        }
        return result.toString();
    }
    /** 
     * 删除临时文件路径及文件
     * 
     * @param file void
     * @see [类、类#方法、类#成员]
     */
    public static void deleteFile(File file)
    {
        if (file.exists())
        {
            // 删除文件
            if (file.isFile())
            {
                Boolean result = file.delete();
                if (!result)
                {
                    logger.error("delete file error");
                }
            }
            else if (file.isDirectory())
            {
                // 删除文件夹下的文件
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++)
                {
                    deleteFile(files[i]);
                }
                
                // 删除文件夹
                Boolean result = file.delete();
                if (!result)
                {
                    logger.error("delete file error");
                }
            }
        }
        else
        {
            logger.error("file is not exist");
        }
    }
    
}
