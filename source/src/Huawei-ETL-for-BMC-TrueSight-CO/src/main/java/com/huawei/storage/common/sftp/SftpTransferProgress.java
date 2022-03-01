package com.huawei.storage.common.sftp;

import com.jcraft.jsch.SftpProgressMonitor;

/** 
 * 监管sftp传输进度
 * 
 * @author   yKF10108
 * @version [版本号V001R010C00, 2011-12-14]
 * @see  SftpTansfer
 * @since  ISM Common：SftpTransferProgress
 */
public class SftpTransferProgress implements SftpProgressMonitor
{
    private long fileSize; // 整个文件的大小

    private long finishSize; // 已上传或者已下载的文件大小

    /** 
     * 初始化sftp传输
     * 
     * @param tansferState 传输状态：0为上传，1为下载
     * @param sourcePath 资源路径
     * @param destinationPath 目的路径
     * @param size 整个文件的大小
     */
    public synchronized void init(int tansferState, String sourcePath,
            String destinationPath, long size)
    {
        this.fileSize = size;
    }

    /** 
     * 查询已上传或者已下载的大小
     * 
     * @param mount 已下载或者已上传的大小
     * @return boolean true为查询上传进度成功
     */
    public synchronized boolean count(long mount)
    {
        this.finishSize += mount;
        return true;
    }

    /** 
     * 结束sftp传输后调用此方法
     */
    public synchronized void end()
    {
        this.finishSize = this.fileSize;
    }

    /** 
     * 获取整个文件的大小
     * 
     * @return long 整个文件的大小
     */
    public synchronized long getFileSize()
    {
        return this.fileSize;
    }

    /** 
     * 获取已上传或者已下载的大小
     * 
     * @return long 已上传或者已下载的大小
     */
    public synchronized long getFinishSize()
    {
        return this.finishSize;
    }

}
