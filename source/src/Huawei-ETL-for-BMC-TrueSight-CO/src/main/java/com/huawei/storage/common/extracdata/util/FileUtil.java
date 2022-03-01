package com.huawei.storage.common.extracdata.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.huawei.storage.common.extracdata.constant.IsmConstant;
import com.huawei.storage.common.extracdata.constant.IsmNumberConstant;
import com.huawei.storage.common.extracdata.constant.LegoNumberConstant;

/**
 * 文件工具类
 * @author f00278226
 * @version V300R005C10
 */
public class FileUtil
{
    static final int BUFFER = 8192;
    
    /**
     * 日志记录器
     */
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(FileUtil.class); 
    
    /**
     * 执行压缩操作
     * @param srcPathName 被压缩的文件/文件夹
     * @param destZipFile 生成的ZIP文件
     * @return boolean suc or failed
     */
    public static boolean compressFile(String srcPathName, String destZipFile)
    {
        if (destZipFile == null || srcPathName == null)
        {
            return false;
        }
        File file = new File(srcPathName);
        if (!file.exists())
        {
            logger.warn(srcPathName + "not exits");
            return false;
        }
        try
        {
            //fixed by g 使用ZipUtil可以解决中文文件 文件夹的乱码问题
            //不要使用ant里面的
            return ZipUtil.zip(srcPathName, destZipFile, "");
        }
        catch (Exception e)
        {
            logger.error("zip compress file error", e);
            return false;
        }
    }
    
    /**
     * 拷贝文件
     * @param sourcefile sourcefile
     * @param targetFile targetFile
     * @return boolean suc or failed
     */
    public static boolean copyFile(File sourcefile, File targetFile)
    {
        //记录日志太多
        logger.debug("copy src:" + sourcefile + "to:" + targetFile);
        //新建文件输入流并对它进行缓冲
        FileInputStream input = null;
        FileOutputStream out = null;
        try
        {
            input = new FileInputStream(sourcefile.getCanonicalPath());
            //新建文件输出流并对它进行缓冲
            out = new FileOutputStream(targetFile.getCanonicalPath());
            //缓冲数组
            byte[] b = new byte[IsmNumberConstant.NUM_1024 * IsmNumberConstant.FIVE];
            int len = 0;
            while ((len = input.read(b)) != -1)
            {
                out.write(b, 0, len);
            }
            //刷新此缓冲的输出流
            out.flush();
            return true;
        }
        catch (FileNotFoundException e)
        {
            logger.error("copy file error，not exists", e);
            return false;
        }
        catch (IOException e)
        {
            logger.error("copy file error", e);
            return false;
        }
        finally
        {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(input);
        }
    }
    
  
    /**
     * 授权文件
     * @param temporyFilePath temporyFilePath
     * @param permission permission
     */
    public static void chmodFile(String temporyFilePath, String permission)
    {
        if (ReportUtil.isLinux())
        {
            try
            {
                Process p = Runtime.getRuntime().exec("chmod -R " + permission + temporyFilePath);
                Thread.sleep(LegoNumberConstant.ONE_HUNDRED_TWENTY_EIGHT);
                if (logger.isDebugEnabled())
                {
                    logger.debug("Process checked." + p);
                }
            }
            catch (Exception e)
            {
                logger.error("exec chmod faild:" + temporyFilePath, e);
            }
        }
    }
    
    /**
     * 授权文件
     * @param temporyFilePath temporyFilePath
     */
    public static void chmodFile(String temporyFilePath)
    {
        chmodFile(temporyFilePath, "700");
    }
    
    /**
     * 获取文件的后缀 
     * @param fileName 文件
     * @return 文件后缀
     */
    public static String getPrefix(String fileName)
    {
        if (VerifyUtil.isEmpty(fileName))
        {
            throw new IllegalArgumentException("Invalid file:" + fileName);
        }
        int index = fileName.lastIndexOf(".");
        if (index >= 0)
        {
            return fileName.substring(0, index);
        }
        throw new IllegalArgumentException("Invalid file:" + fileName);
    }
    
    /**
     * 获取文件的后缀 
     * @param fileName 文件
     * @return 文件后缀
     */
    public static String getSuffix(String fileName)
    {
        if (VerifyUtil.isEmpty(fileName))
        {
            throw new IllegalArgumentException("Invalid file:" + fileName);
        }
        int index = fileName.lastIndexOf(".");
        if (index >= 0)
        {
            return fileName.substring(index + 1, fileName.length());
        }
        throw new IllegalArgumentException("Invalid file:" + fileName);
    }
    
    /**
     * 清除某个文件夹
     * clearFloder
     * @param folder 文件夹
     */
    public static void clearFloder(String folder)
    {
        clearFloder(folder, true);
    }
    
    /**
     * 清除某个文件夹 会递归删除该文件夹下所有文件
     * @param folder 文件夹
     * @param deleteSelf 是否删除自身这个文件夹
     */
    public static void clearFloder(String folder, boolean deleteSelf)
    {
        if (folder == null)
        {
            return;
        }
        try
        {
            File dir = new File(folder);
            File[] fs = dir.listFiles();
            if (fs != null)
            {
                processNotEmpty(fs);
            }
            fs = dir.listFiles();
            if (VerifyUtil.isEmpty(fs) && deleteSelf)
            {
                logger.info("delete dir:" + dir.getAbsolutePath() + " result: " + dir.delete());
            }
        }
        catch (Exception e)
        {
            logger.error("DELETE FAILED." + folder, e);
        }
    }

    private static void processNotEmpty(File[] fs)
    {
        for (File f : fs)
        {
            if (f.isDirectory())
            {
                clearFloder(f.getAbsolutePath(), true);
            }
            else
            {
                if (!f.delete())
                {
                    logger.info("DELETE failed." + f.getAbsolutePath());
                }
            }
        }
    }
    
    /**
     * 删除文件
     * @param f 文件待删除
     */
    public static void deleteFile(String f)
    {
        if (f != null)
        {
            try
            {
                File file = new File(f);
                boolean suc = false;
                if (file.exists())
                {
                    suc = FileUtils.deleteQuietly(file);
                }
                if (!suc)
                {
                    logger.warn("delete failed." + f);
                }
            }
            catch (Exception e)
            {
                logger.error("error when delete " + f, e);
            }
        }
    }
    
    /**
     * 创建路径
     * @param path 路径
     * @return 是否创建成功
     */
    public static boolean mkdirs(String path)
    {
        if (path == null)
        {
            return false;
        }
        try
        {
            File f = new File(path);
            if (f.exists())
            {
                return f.isDirectory();
            }
            else
            {
                return f.mkdirs();
            }
        }
        catch (Exception e)
        {
            logger.error("error when create dir " + path, e);
            return false;
        }

    }
    
    /** 
     * 删除多余上传public key文件 
     * @param   name   被删除文件的文件名 
     * @return 单个文件删除成功返回true，否则返回false 
     */
    public static boolean deletePublicKeyFile(String name)
    {
        boolean flag = false;
        if (name == null)
        {
            return false;
        }
        File file = FileUtils.getFile(name);
        if (null == file)
        {
            logger.info("fileUtil getFile return null, file=" + name);
            return false;
        }
        // 路径为文件且不为空则进行删除  
        if (file.isFile() && file.exists())
        {
            boolean result = file.delete();
            if (result)
            {
                logger.info("this public key file is deleted :" + name);
            }
            else
            {
                logger.info("this public key file delete fail :" + name);
            }
            flag = true;
        }
        else
        {
            logger.info("this public key file delete fail:" + name);
        }
        return flag;
        
    }

    /** 
     * 删除ip相同的publicKey文件 
     * @param   ip   删除publickey对应ip
     */
    public static void deleteFileByIp(String ip)
    {
        String ipStr = "";
        if (AddressUtil.isValidIPV4(ip))
        {
            ipStr = ip.replaceAll("\\.", "_");
        }
        if (AddressUtil.isValidIPV6(ip))
        {
            ipStr = ip.replaceAll(":", "_");
        }
        String publicDir = System.getProperty("user.dir") + File.separator + IsmConstant.PUBLIC_KEY;
        try
        {
            File file = FileUtils.getFile(publicDir);
            if (null == file)
            {
                logger.info("fileUtil getFile return null, file=" + publicDir);
                return;
            }
            File[] files = file.listFiles();
            if (null == files)
            {
                logger.info("this public key dir not exit :" + publicDir);
                return;
            }
            for (File f : files)
            {
                String fn = f.getName();
                int index = fn.lastIndexOf("_");
                if (index < 0)
                {
                    continue;
                }
                String ipString = fn.substring(0, index);
                if (ipString.equals(ipStr) && f.isFile() && f.exists())
                {
                    FileUtil.deleteFile(f.getAbsolutePath());
                }
            }
            
        }
        catch (Exception e)
        {
            logger.info("this public key file delete fail:");
        }
    }
    
    /** 
     * 删除ip相同的publicKey文件 
     * @param   fileName   文件
     * @return 文件
     */
    public static String readFile(String fileName)
    {
        String str = "";
        if (null == fileName || fileName.isEmpty())
        {
            logger.error("Param error: null or empty");
            return str;
        }
        
        BufferedReader bf = null;
        try
        {
            bf = new BufferedReader(new FileReader(fileName));
            
            String content = "";
            StringBuilder sb = new StringBuilder();
            while (null != content)
            {
                /*
                 * FORTIFY.Denial_of_Service
                 * 问题：如果一行的内容很多，容易造成DoS
                 */
                content = bf.readLine();
                if (content == null)
                {
                    break;
                }
                
                sb.append(content).append('\n');
            }
            
            str = sb.toString();
        }
        catch (FileNotFoundException e)
        {
            logger.error("readFile error. file not found", e);
        }
        catch (IOException e)
        {
            logger.error("IOException:", e);
        }
        finally
        {
            IOUtils.closeQuietly(bf);
        }
        return str;
    }
    
    /**
     * 写文件
     * @param fileName 文件名
     * @param content 文件内容
     * @return 
     */
    public static void writeFile(String fileName, String content)
    {
        if (null == fileName || fileName.isEmpty() || null == content)
        {
            logger.error("Param error: null or empty");
            return;
        }
        
        BufferedWriter bw = null;
        try
        {
            bw = new BufferedWriter(new FileWriter(fileName));
            bw.write(content);
        }
        catch (FileNotFoundException e)
        {
            logger.error("writeFile error. file not found", e);
        }
        catch (IOException e)
        {
            logger.error("IOException:", e);
        }
        finally
        {
            closeBw(bw);
        }
    }

    private static void closeBw(BufferedWriter bw)
    {
        if (null != bw)
        {
            try
            {
                bw.close();
            }
            catch (IOException e)
            {
                /**
                 * FORTIFY.System_Information_Leak
                 * 不整改的原因：不会导致敏感信息泄露
                 */
                logger.error("IOException: while close the buffer write", e);
            }
        }
    }
}
