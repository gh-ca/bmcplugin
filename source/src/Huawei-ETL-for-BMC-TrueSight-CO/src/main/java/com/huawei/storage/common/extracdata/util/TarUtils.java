package com.huawei.storage.common.extracdata.util;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.GZIPInputStream;


/**
 * 对tar压缩文件进行操作,移植V2的代码
 * 
 * @author  l90005176
 * @version V100R100C00
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public final class TarUtils
{
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TarUtils.class); 
    
    /**
     * 最大解压文件限制
     * 500M
     */
    private static final int TOOBIG =
        5 * 100* 1024 * 1024; // 100MB
    

    private static final int TOOMANY = 1024; // max number of files
    
    private TarUtils()
    {
    }
    
    /**
     * 
     * extract
     * @param file 待解压文件
     * @param outputDir 解压后文件夹
     * @param gen 生成压缩包内文件名的方式
     * @return 解压后文件名
     * @throws ReportException if has error
     */
    public static String extract(File file, String outputDir, FileNameGenerater gen) throws Exception
    {
        String retName = "";
        TarArchiveInputStream in = null;
        BufferedInputStream bis = null;
        List<BufferedOutputStream> boses = new ArrayList<BufferedOutputStream>();
        FileInputStream fileInputStream = null;
        FileOutputStream outputStream = null;
        GZIPInputStream inputStream = null;
        
        try
        {
            /* begin l90005514 T11V-2522 TV1R3产品侧性能统计的数据打包方式更改，需要ISM同步修改 */
            fileInputStream = new FileInputStream(file);
            inputStream = new GZIPInputStream(fileInputStream);
            InputStream is = file.getName()
                    .toLowerCase(Locale.getDefault())
                    .endsWith(".tgz") ? inputStream
                    : new BZip2CompressorInputStream(fileInputStream);
            in = new TarArchiveInputStream(is);
            /* end l90005514 T11V-2522 TV1R3产品侧性能统计的数据打包方式更改，需要ISM同步修改 */
            //FIX FOR PERFORMANCE BY G
            byte[] tmpArray = new byte[1024 * 8];
            ArchiveEntry entry;
            int entries = 0;
            while ((entry = in.getNextEntry()) != null)
            {
                logger.debug("Extracting: " + entry);
                if (ReportUtil.validateDir(outputDir))
                {
                    throw new Exception("dir is invalid");
                }
                File tempFileFolder = new File(outputDir);
                boolean result = true;
                if (!tempFileFolder.exists())
                {
                    result = tempFileFolder.mkdirs();
                }
                if (!result)
                {
                    throw new Exception("create file dir fail.");
                }
                if (ReportUtil.validateDir(outputDir + File.separator
                        + file.getName() + System.currentTimeMillis()))
                {
                    throw new Exception("dir is invalid");
                }
                File tempFile = new File(
                    outputDir + File.separator + gen.getUnzippedFileName(entry.getName()));
                retName = checkFile(tempFile);
                
                //读写文件
                bis = new BufferedInputStream(in);
                outputStream = new FileOutputStream(tempFile);
                BufferedOutputStream bos = new BufferedOutputStream(outputStream);
                boses.add(bos);
                entries++;
                exceedMaxSize(bis, tmpArray, bos);
                exceedMaxNumber(entries);
                bos.flush();
                
            }
            return retName;
        }
        catch (Exception e)
        {
            throw new Exception(e);
        }
        finally
        {
            IOUtils.closeQuietly(bis, in, inputStream, fileInputStream);
            for (BufferedOutputStream b : boses)
            {
                IOUtils.closeQuietly(b);
            }
            IOUtils.closeQuietly(outputStream);
        }
    }

    /** 判断解压是否超出最大数
     * <功能详细描述>
     * @param entries 当前数目
     * @see [类、类#方法、类#成员]
     */
    public static void exceedMaxNumber(int entries)
    {
        if (entries > TOOMANY)
        {
            throw new IllegalStateException("Too many files to unzip.");
        }
    }
    
    /**
     * 解压缩tgz File
     * @param file 要解压的tgz文件对象
     * @param outputDir 要解压到某个指定的目录下
     * @return String
     * @throws ReportException  ReportException
     */
    public static String extract(File file, String outputDir)
        throws Exception
    {
        return extract(file, outputDir, new AppendTimeAfterOrginalName());
    }

    private static void exceedMaxSize(BufferedInputStream bis, byte[] tmpArray,
        BufferedOutputStream bos) throws IOException
    {
        int count;      
        /* Write the files to the disk, but ensure that the file is not insanely big */      
        int total = 0;  
        while ((count = bis.read(tmpArray)) != -1)
        {
            bos.write(tmpArray, 0, count);
            total += count; 
            if (total > TOOBIG) 
            {        
                throw new IllegalStateException("File being extracted is huge.");  
            }   
          
        }
    }
    
    private static String checkFile(File tempFile)
    {
        String retName = tempFile.getPath();
        if (tempFile.exists())
        {
            boolean b = tempFile.delete();
            if (!b)
            {
                throw new SecurityException();
            }
        }
        return retName;
    }
    

    /**
     * <压缩文件>
     * <功能详细描述> 
     * @param inputFilePath 需要压缩的文件夹或者文件路径
     * @param zipFilePath 保存的压缩包文件路径
     * @param baseName 保存的压缩包文件名
     * @see [类、类#方法、类#成员]
     */
    public static void zip(String inputFilePath, String zipFilePath,
            String baseName)
    {
        if (!ZipUtil.zip(inputFilePath, zipFilePath, baseName))
        {
            logger.error("zip failed." + inputFilePath + " zip:" + zipFilePath + " base:" + baseName);
        }
    }

    
}
