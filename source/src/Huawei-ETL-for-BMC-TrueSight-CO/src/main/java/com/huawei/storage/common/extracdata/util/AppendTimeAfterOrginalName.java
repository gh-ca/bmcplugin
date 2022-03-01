package com.huawei.storage.common.extracdata.util;

/**
 * 原文件名后添加时间戳
 * @author g00250185
 * @version V300R005C00
 */
public class AppendTimeAfterOrginalName implements FileNameGenerater
{

    /**
     * 生成压缩包内文件名的接口
     * getUnzippedFileName
     * @param originalFile 原始文件
     * @return 压缩包内的文件名
     */
    @Override
    public String getUnzippedFileName(String originalFile)
    {
        return originalFile + System.currentTimeMillis();
    }

}
