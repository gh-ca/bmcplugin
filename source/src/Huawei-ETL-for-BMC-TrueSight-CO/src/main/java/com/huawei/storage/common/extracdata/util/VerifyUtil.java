package com.huawei.storage.common.extracdata.util;



import java.util.Collection;
import java.util.Map;



/**
 * 本类用于对常见的集合、String、Map、一维数组、二维数组等进行校验
 * 
 * @author  w90002860
 * @version  V100R001C00
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public final class VerifyUtil
{
    /** 
     * <默认构造函数>
     */
    private VerifyUtil()
    {
    }
    
    /** 
     * 判断Collection子类元素是否为空集合
     * 
     * @author lKF20890
     * @param collection 集合对象
     * @return boolean [true：空，false：非空]
     */
    public static boolean isEmpty(Collection< ? > collection)
    {
        return (null == collection) || (collection.isEmpty());
    }
    
    /**
     * 判断对象是否为空
     * 
     * @author w90002860
     * @param obj 对象
     * @return boolean [true：空，false：非空]
     */
    public static boolean isEmpty(Object obj)
    {
        return null == obj;
    }
    
    /**
     * 判断Map对象是否为空
     * 
     * @author w90002860
     * @param map Map对象
     * @return boolean [true：空，false：非空]
     */
    public static boolean isEmpty(Map< ? , ? > map)
    {
        return (null == map) || (map.isEmpty());
    }
    
    /**
     * 判断Map中取出的对象是否为空
     * 
     * @author w90002860
     * @param map Map对象
     * @param key key
     * @return boolean [true：空，false：非空]
     */
    public static boolean isEmpty(Map< ? , ? > map, Object key)
    {
        //added by z90003445 2011-2-22 
        if (isEmpty(map))
        {
            return true;
        }
        //end
        return !map.containsKey(key) || null == map.get(key)
                || "".equals(map.get(key));
    }
    
    /**
     * 判断String对象是否为null或者空字符串
     *
     * @author w90002860
     * @param string 字符串
     * @return boolean [true：空，false：非空]
     */
    public static boolean isEmpty(String string)
    {
        return (null == string) || (string.isEmpty());
    }
    
    /**
     * 
     * 判断Object数组是否为空
     * @author z90005513
     * @param objects 数组
     * @return boolean [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEmpty(Object[] objects)
    {
        return objects == null || objects.length == 0;
    }
    
    /**
     * 
     *判断二维Object数组是否为空
     * @author z90005513
     * @param objects 二维数组
     * @return boolean [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEmpty(Object[][] objects)
    {
        //objects为空，或者二维数组长度为0，比如new Object[0][anyNumber]
        if (objects == null || objects.length == 0)
        {
            return true;
        }
        
        //判断二维数组内部的数据是否为空
        for (Object[] objectArray : objects)
        {
            
            if (objectArray.length != 0)
            {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * 判断一个数组内的对象是否为空,如果一个参数都没有传入，返回false。
     * 
     * @author w90002860
     * @param obj 对象
     * @return boolean [true：空，false：非空]
     */
    public static boolean isMultiEmpty(Object... obj)
    {
        if (obj == null)
        {
            return true;
        }
        for (int i = 0; i < obj.length; i++)
        {
            if (null == obj[i])
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 
     * 检查对象是否存在，如不存在则抛出对象不存在的错误码
     *
     * @param checkedObj 被检查的对象
     * @throws Exception 
     *
     */
    public static void checkObject(Object checkedObj) throws Exception
    {
        if (null == checkedObj)
        {
            throw new Exception("OBJECT_NOT_EXIST");
        }
    }
    
    
   

}
