package com.huawei.storage.common.extracdata.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

import com.huawei.storage.common.extracdata.constant.LegoNumberConstant;


/**
 * 
 * 压缩、解压缩公共类<br>
 * 插件打ZIP包场景： 
 * 1.选择一个报表插件目录打成ZIP压缩包 
 * 2.选择多个报表插件目录打成ZIP压缩包
 * @author  c00107026
 * @version  [UltraAPM V100R002C10, 2014-12-25]
 * @see  [相关类/方法]
 * @since  [UltraAPM V100R002C10]
 */
public final class ZipUtil
{
    /**
     * ZIP压缩，解压缩的文件分隔符，windows和linux操作系统保持一致
     */
    private static final String ZIP_FILE_SEPARATOR = "/";
    
    private static final Character ZIP_FILE_SEPARATOR_CHAR = '/';
    
    //BUF 4KB
    private static final int BUF_4KB = LegoNumberConstant.VALUE_512 * LegoNumberConstant.EIGHT;
    
    /**
     * 日志记录器
     */
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ZipUtil.class); 
    
    /**
     * <默认构造函数>
     * 防止类外创建
     */
    private ZipUtil()
    {
    
    }
    
    /**
     * 压缩文件 1.支持场景1--选择一个报表插件目录打成ZIP压缩包<br>
     * @since IReport CBB V1R1, 2010-9-19
     * @param inputFilePath 需要压缩的文件夹或者文件路径
     * @param zipFilePath 保存的压缩包文件路径
     * @param baseName 保存的压缩包文件名
     * @return suc or failed
     */
    public static boolean zip(String inputFilePath, String zipFilePath, String baseName)
    {
        logger.info("zip-->begin");
        ZipOutputStream zOut = null;
        FileOutputStream fos = null;
        boolean suc = false;
        try
        {
            logger.info("inputFilePath1:");
            logger.info("baseName:" + baseName);
            logger.info("zipFilePath:");
            // 创建文件输出对象out,提示:注意中文支持
            fos = new FileOutputStream(zipFilePath);
            zOut = new ZipOutputStream(fos, getCharset());
            zip(zOut, new File(inputFilePath), baseName);
            suc = true;
        }
        catch (Exception e)
        {
            logger.error(e);
        }
        finally
        {
            
            IOUtils.closeQuietly(zOut);
            IOUtils.closeQuietly(fos);
            
        }
        logger.info("zip-->end");
        return suc;
    }
    
    
    /**
     * 压缩文件 1.支持场景1--选择一个报表插件目录打成ZIP压缩包<br>
     * @since IReport CBB V1R1, 2010-9-19
     * @param inputFilePath 需要压缩的文件夹或者文件路径
     * @param zipFilePath 保存的压缩包文件路径
     * @param baseName 保存的压缩包文件名
     * @param filterList 需要过滤的文件后缀名列表
     */
    public static void zipWithFilter(String inputFilePath, String zipFilePath, String baseName, List<String> filterList)
    {
        ZipOutputStream zOut = null;
        FileOutputStream fis = null;
        try
        {
            logger.debug("inputFilePath1:");
            logger.debug("baseName:" + baseName);
            logger.debug("zipFilePath:");
            // 创建文件输出对象out,提示:注意中文支持
            fis = new FileOutputStream(zipFilePath);
            zOut = new ZipOutputStream(fis, getCharset());
            
            logger.debug("zip-->begin");
            zipWithFilter(zOut, new File(inputFilePath), baseName, filterList);
            logger.debug("zip-->end");
            
        }
        catch (Exception e)
        {
            logger.error(e);
        }
        finally
        {
            try
            {
                if (zOut != null)
                {
                    zOut.close();
                }
            }
            catch (Exception e)
            {
                logger.error(e);
            }
            try
            {
                if (fis != null)
                {
                    fis.close();
                }
            }
            catch (Exception e)
            {
                logger.error(e);
            }
        }
    }
    
    private static void zipWithFilter(ZipOutputStream zOut, File file, String base, List<String> filterList)
    {
        FileInputStream in = null;
        try
        {
            logger.debug("zip(ZipOutputStream zOut, File file, the String base)-> fileName=" + file.getName());
            if (file.isDirectory())
            {
                File[] listFiles = file.listFiles();
                
                String entryNamex = base + ZIP_FILE_SEPARATOR;
                logger.debug("zip(ZipOutputStream zOut, File file, String base)-> entryName:");
                zOut.putNextEntry(new ZipEntry(entryNamex));
                base = base.length() == 0 ? "" : base + ZIP_FILE_SEPARATOR;
                for (int i = 0; i < listFiles.length; i++)
                {
                    zipWithFilter(zOut, listFiles[i], base + listFiles[i].getName(), filterList);
                }
            }
            else
            {
                //对文件名进行过滤判断, 如果符合过滤条件则过滤掉
                if (!isFileNameValid(file, filterList))
                {
                    return;
                }
                if (base.equals(""))
                {
                    base = file.getName();
                }
                logger.debug("zip(ZipOutputStream zOut, File file, String base)-> entryName:");
                zOut.putNextEntry(new ZipEntry(base));
                logger.debug("zip(ZipOutputStream zOut, File file, String base) ");
                in = new FileInputStream(file);
                int len;
                while ((len = in.read()) != -1)
                {
                    zOut.write(len);
                }
            }
        }
        catch (Exception e)
        {
            logger.error(e);
        }
        finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
            }
            catch (Exception e)
            {
                logger.error(e);
            }
        }
    }
    
    private static boolean isFileNameValid(File file, List<String> filterList)
    {
        if (filterList != null && !filterList.isEmpty())
        {
            for (String item : filterList)
            {
                if (file.getName().endsWith(item))
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * 压缩文件 1.支持场景2--选择多个报表插件目录打成ZIP压缩包<br>
     * @since IReport CBB V1R1, 2010-9-19
     * @param directoryList 多个报表插件目录
     * @param zipFileName 保存的压缩包文件路径
     */
    public static void zip(List<String> directoryList, String zipFileName)
    {
        ZipOutputStream zOut = null;
        FileOutputStream fos = null;
        try
        {
            if (directoryList != null && !directoryList.isEmpty())
            {
                // 创建文件输出对象out,提示:注意中文支持
                fos = new FileOutputStream(new String(zipFileName.getBytes("gb2312")));
                zOut = new ZipOutputStream(fos, getCharset());
                for (String item : directoryList)
                {
                    logger.debug("zip-->begin");
                    zip(zOut, new File(item), getDirectoryName(item));
                    logger.debug("zip-->end");
                }
            }
        }
        catch (Exception e)
        {
            logger.error(e);
        }
        finally
        {
            try
            {
                if (zOut != null)
                {
                    zOut.close();
                }
            }
            catch (Exception eee)
            {
                logger.error(eee);
            }
            try
            {
                if (fos != null)
                {
                    fos.close();
                }
            }
            catch (Exception euu)
            {
                logger.error(euu);
            }
            
        }
    }
    
    /**
     * 压缩文件 1.支持场景2--选择多个报表插件目录打成ZIP压缩包<br>
     * @since IReport CBB V1R1, 2010-9-19
     * @param directoryList 多个报表插件目录
     * @param zipFileName 保存的压缩包文件路径
     * @param filterList 需要过滤的文件后缀名列表
     */
    public static void zipWithFilter(List<String> directoryList, String zipFileName, List<String> filterList)
    {
        ZipOutputStream zOuts = null;
        FileOutputStream fods = null;
        try
        {
            if (directoryList != null && !directoryList.isEmpty())
            {
                // 创建文件输出对象out,提示:注意中文支持
                fods = new FileOutputStream(zipFileName);
                zOuts = new ZipOutputStream(fods, getCharset());
                for (String item : directoryList)
                {
                    logger.debug("zip-->begin");
                    zipWithFilter(zOuts, new File(item), getDirectoryName(item), filterList);
                    logger.debug("zip-->end");
                }
            }
        }
        catch (Exception ev)
        {
            logger.error(ev);
        }
        finally
        {
            try
            {
                if (zOuts != null)
                {
                    zOuts.close();
                }
            }
            catch (Exception ez)
            {
                logger.error(ez);
            }
            try
            {
                if (fods != null)
                {
                    fods.close();
                }
            }
            catch (Exception ex)
            {
                logger.error(ex);
            }
            
        }
    }
    
    /**
     * 将目录名称从目录路径中取出<br>
     * @author c00107026
     * @since IReport CBB V1R1, 2010-9-19
     * @param directoryPath
     * @return
     */
    private static String getDirectoryName(String directoryPath)
    {
        if (directoryPath != null)
        {
            String[] dirs = directoryPath.replace('\\', ZIP_FILE_SEPARATOR_CHAR).split(ZIP_FILE_SEPARATOR);
            return dirs[dirs.length - 1];
        }
        else
        {
            return null;
        }
    }
    
    private static void zip(ZipOutputStream zOut, File file, String base)
    {
        FileInputStream in = null;
        BufferedInputStream bis = null;
        try
        {
            logger.debug("zip(ZipOutputStream zOut, File file, String base)-> fileName=" + file.getName());
            if (file.isDirectory())
            {
                File[] listFiles = file.listFiles();
                
                String entryName = base + ZIP_FILE_SEPARATOR;
                logger.debug("zip(ZipOutputStream zOut, File file, String base)-> entryName:");
                zOut.putNextEntry(new ZipEntry(entryName));
                base = base.length() == 0 ? "" : base + ZIP_FILE_SEPARATOR;
                for (int i = 0; i < listFiles.length; i++)
                {
                    zip(zOut, listFiles[i], base + listFiles[i].getName());
                }
            }
            else
            {
                if (base.equals(""))
                {
                    base = file.getName();
                }
                logger.debug("zip(ZipOutputStream zOut, File file, String base)-> entryName:");
                zOut.putNextEntry(new ZipEntry(base));
                logger.debug("zip(ZipOutputStream zOut, File file, String base) -> ");
                in = new FileInputStream(file);
                bis = new BufferedInputStream(in);
                int len;
                byte [] buf = new byte [BUF_4KB];
                while ((len = bis.read(buf, 0, buf.length)) != -1)
                {
                    zOut.write(buf, 0, len);
                }
            }
        }
        catch (Exception eww)
        {
            logger.error(eww);
        }
        finally
        {

            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(in);
        }
    }
    
    /**
     * 在directory目录下创建subDirectory目录<br>
     * @param directory 目录
     * @param subDirectory 子目录
     */
    private static void createDirectory(String directory, String subDirectory)
    {
        String dir[];
        File fl = new File(directory);
        if (subDirectory.equals("") && !fl.exists())
        {
            if (fl.mkdir())
            {
                logger.debug(" file create.");
            }
            else
            {
                logger.error("dir create fail:");
            }
        }
        else if (!subDirectory.equals(""))
        {
            dir = subDirectory.replace('\\', ZIP_FILE_SEPARATOR_CHAR).split(ZIP_FILE_SEPARATOR);
            StringBuffer bufDic = new StringBuffer(directory);
            for (int i = 0; i < dir.length; i++)
            {
                String newDirPath = bufDic.toString() + ZIP_FILE_SEPARATOR + dir[i];
                createDirectory(newDirPath, "");
                /*                directory += ZIP_FILE_SEPARATOR + dir[i];
                */bufDic.append(ZIP_FILE_SEPARATOR).append(dir[i]);
            }
        }
    }
    
    /**
     * 解压缩：将指定的ZIP文件解压到指定的目录<br>
     * @since IReport CBB V1R1, 2010-9-20
     * @param zipFileName 压缩文件的全文件路径
     * @param outputDirectory 需要解压缩到的文件路径
     * @return boolean success or failed.
     */
    public static boolean unZip(String zipFileName, String outputDirectory)
    {
        ZipFile zipFile = null;
        try
        {
            zipFile = new ZipFile(zipFileName);
            Enumeration< ? > e = zipFile.entries();
            ZipEntry zipEntry = null;
            createDirectory(outputDirectory, "");
            while (e.hasMoreElements())
            {
                zipEntry = (ZipEntry) e.nextElement();
                logger.debug("ziping: " + zipEntry.getName());
                String name = null;
                if (zipEntry.isDirectory())
                {
                    name = zipEntry.getName();
                    name = name.substring(0, name.length() - 1);
                    String newDirPath = outputDirectory + ZIP_FILE_SEPARATOR + name;
                    createDirectory(newDirPath, "");
                }
                else
                {
                    createFileByZip(zipEntry, zipFile, outputDirectory);
                }
            }
            return true;
        }
        catch (Exception ex)
        {
            logger.error(ex);
            return false;
        }
        finally
        {
            try
            {
                // zipFile必须关闭
                if (zipFile != null)
                {
                    zipFile.close();
                }
            }
            catch (Exception e)
            {
                logger.error(e);
            }
        }
    }
    
    private static void createFileByZip(ZipEntry zipEntry, ZipFile zipFile, String outputDirectory)
    {
        InputStream in = null;
        FileOutputStream out = null;
        String fileName = zipEntry.getName();
        //fileName = fileName.replace('\\', IReportConstants.ZIP_FILE_SEPARATOR);
        if (fileName.indexOf(ZIP_FILE_SEPARATOR) != -1)
        {
            createDirectory(outputDirectory, fileName.substring(0, fileName.lastIndexOf(ZIP_FILE_SEPARATOR)));
            fileName = fileName.substring(fileName.lastIndexOf(ZIP_FILE_SEPARATOR) + 1, fileName.length());
        }
        File f = new File(outputDirectory + ZIP_FILE_SEPARATOR + zipEntry.getName());
        try
        {
            if (f.createNewFile())
            {
                in = zipFile.getInputStream(zipEntry);
                if (null != in)
                {
                    out = new FileOutputStream(f);
                    loop(in, out);
                }
            }
        }
        catch (Exception e)
        {
            logger.error(e);
        }
        finally
        {
            close(zipFile, in, out);
        }
    }
    
    private static void loop(InputStream in, FileOutputStream out) throws IOException
    {
        byte[] by = new byte[8192];
        int c;
        while ((c = in.read(by)) != -1)
        {
            out.write(by, 0, c);
        }
        out.flush();
    }
    
    private static void close(ZipFile zipFile, InputStream in, FileOutputStream out)
    {
        try
        {
            if (out != null)
            {
                out.close();
            }
        }
        catch (IOException e)
        {
            logger.error(e);
        }
        
        try
        {
            if (in != null)
            {
                in.close();
            }
        }
        catch (IOException e)
        {
            logger.error(e);
        }
        
        if (null != zipFile)
        {
            try
            {
                zipFile.close();
            }
            catch (IOException e)
            {
                logger.error(e);
            }
        }
    }
    
    private static Charset getCharset()
    {
        try
        {
            return Charset.forName("GBK");
        }
        catch (IllegalArgumentException e)
        {
            logger.error("cannt get the gbk charset.use the default.", e);
            
            return Charset.defaultCharset();
        }
    }
    
}
