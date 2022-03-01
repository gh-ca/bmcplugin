package com.huawei.storage.common.extracdata.util;



import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import com.huawei.storage.common.extracdata.PerfStatHisFileProxy;




/**
 * 
 * @author z90005513
 * 
 */
public class IOUtils
{
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(PerfStatHisFileProxy.class);
    public static void closeQuietly(Closeable closeable)
    {
        try
        {
            if (closeable != null)
            {
                closeable.close();
            }
        }
        catch (IOException ioe)
        {
        	logger.error("close io error.", ioe);
        }
    }
    
    public static void closeQuietly(Closeable ... closeable)
    {
        if (closeable != null)
        {
            for (Closeable c : closeable)
            {
                closeQuietly(c);
            }
        }
    }
    
    /**
     * 
     * deleteFile
     * @param file file
     * @return delete suc or failed
     */
    public static boolean deleteFile(String file)
    {
        try
        {
            if (null == file)
            {
                return false;
            }
            return new File(file).delete();
        }
        catch (Exception e)
        {
        	logger.error("Error when delete " + file, e);
        }
        return false;
    }
}
