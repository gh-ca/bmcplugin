package com.huawei.storage.common.extracdata.util;

/**
 * byte2int
 * @author g00250185 2016年3月3日
 * @version V300R005C00
 */
public class GFCommon
{
    private static final int OXFF = 0xff;
    
    /**
     * <byte[]转换成int>
     * <功能详细描述>
     * 
     * @param b byte[]
     * @param isHighFirst 高位在前
     * @return int
     */
    public static int bytes2int(byte[] b, boolean isHighFirst)
    {
        
        return (int) bytes2long(b, isHighFirst);
    }
    
    /**
    * 字节数组转成长整形。按高位在前进行转换。
    * 
    * @param b byte[]
    * @return long
    */
    public static long bytes2long(byte[] b)
    {
        
        return bytes2long(b, true);
    }
    
    /**
    * <byte[]转换成int>
    * <功能详细描述>
    * 
    * @param b byte[]
    * @return int
    */
    public static int bytes2int(byte[] b)
    {
        
        return (int) bytes2long(b);
    }
    
    /**
    * 字节数组转成长整形
    * 
    * @param b byte[]
    * @param isHighFirst
    *            是否高位在前
    * @return long
    */
    public static long bytes2long(byte[] b, boolean isHighFirst)
    {
        long result = 0;
        
        if (b != null && b.length <= 8)
        {
            long value;
            
            if (isHighFirst)
            {
                for (int i = b.length - 1, j = 0; i >= 0; i--, j++)
                {
                    value = b[i] & OXFF;
                    result += value << (j << 3);
                }
            }
            else
            {
                for (int i = 0, j = 0; i <= b.length - 1; i++, j++)
                {
                    value = b[i] & OXFF;
                    result += value << (j << 3);
                }
            }
        }
        
        return result;
    }
    
}