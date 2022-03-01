package com.huawei.storage.common.extracdata.util;


/**
 * 生成压缩包类文件名的接口
 * @author g00250185
 * @version V300R005C00
 */
interface  FileNameGenerater
{
    /**
     * 生成压缩包内文件名的接口
     * getUnzippedFileName
     * @param originalFile 原始文件
     * @return 压缩包内的文件名
     */
    String getUnzippedFileName(String originalFile);
}
