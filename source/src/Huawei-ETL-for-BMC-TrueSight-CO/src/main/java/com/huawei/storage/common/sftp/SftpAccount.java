package com.huawei.storage.common.sftp;


/*
 * 文 件 名:  SftpAccount.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  d00292924
 * 修改时间:  2015年12月15日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */


/**
 * SftpAccount 用户类
 * <功能详细描述>
 * 
 * @author  d00292924
 * @version  [V300R005C00, 2015年12月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SftpAccount
{
	 private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SftpAccount.class); 
		

    
    private String _userName = ""; //sftp用户名
    
    private String _password; //sftp用户密码
    
    private String _ipAddress = ""; //sftp的ip
    
    private int port; //sftp端口号
    
    /**SFTP 私钥内容*/
    private String identity;
    
    /**SFTP 私钥 密码*/
    private String passphrase;
    
    private Boolean publicKeyAuth;
    
    public SftpAccount() {
		
	}
    
    
    /** 构造public key 方式登入
     * @param ip 方法参数：ip
     * @param name 方法参数：name
     * @param priKey 方法参数：priKey
     * @param passphrase 方法参数：passphrase
     * @param port 方法参数：port
     */
    public SftpAccount(String ip, String name, String priKey, String passphrase, int port)
    {
        if (null != ip)
        {
            this._ipAddress = ip;
        }

        if (null != passphrase)
        {
            // 获取不加密密码
            this.passphrase = passphrase;
        }
        if (null != name)
        {
            this._userName = name;
        }
        this.identity = priKey;
        this.port = port;
    }
   
    /** 
     * 方法 ： SftpAccount
     * 
     * @param ip 方法参数：ip
     * @param name 方法参数：name
     * @param password 方法参数：password
     * @param port 方法参数：port
     */
    public SftpAccount(String ip, String name, String password, int port)
    {
        if (null != ip)
        {
            this._ipAddress = ip;
        }
        /*AZ8D09501 ISM V2调出来的V1不能通过FTP进行导入导出 c00001507 begin*/

        if (null != password)
        {
            //        获取不加密密码
            this._password = password;
        }
        if (null != name)
        {
            this._userName = name;
        }
        
        this.port = port;
    }
    
    /*AZ8D09501 ISM V2调出来的V1不能通过FTP进行导入导出 c00001507 end*/
    /** 
     * 方法 ： getIpAddress
     * 
     * @return String 返回结果
     */
    public String getIpAddress()
    {
        return this._ipAddress;
    }
    
    /** 
     * 方法 ： getPassword
     * 
     * @return String 返回结果
     */
    public String getPassword()
    {
        return this._password;
    }
    
    /** 
     * 方法 ： getPort
     * 
     * @return int 返回结果
     */
    public int getPort()
    {
        return this.port;
    }
    
    /** 
     * 方法 ： getUserName
     * 
     * @return String 返回结果
     */
    public String getUserName()
    {
        return this._userName;
    }
    
    
    public String getPassphrase()
    {
        return passphrase;
    }

    public void setPassphrase(String passphrase)
    {
        this.passphrase = passphrase;
    }

    public Boolean getPublicKeyAuth()
    {
        return publicKeyAuth;
    }

    public void setPublicKeyAuth(Boolean publicKeyAuth)
    {
        this.publicKeyAuth = publicKeyAuth;
    }

    public void setPort(int port)
    {
        this.port = port;
    }
    
    public String getIdentity()
    {
        return identity;
    }

    public void setIdentity(String identity)
    {
        this.identity = identity;
    }

    
    public boolean validateSftpAccount(SftpAccount account)
    {
        if (null == account)
        {
            logger.error("the sftp account can not be null ");
            return false;
        }
        //ip地址和用户名不能为空
        if (null == account.getIpAddress() || null == account.getUserName())
        {
            logger.error("the sftp account ip and username can not be null ");
            return false;
        }
        //如果是采用publicKey方式鉴权，私钥不能为空
        if (null != account.getPublicKeyAuth() && account.getPublicKeyAuth())
        {
            if (account.getIdentity() == null)
            {
                logger.error("the sftp account Identity can not be null ");
                return false;
            }
        }
        //用过用用户名密码鉴权，用户密码不能为空
        else if (null == account.getPassword())
        {
            logger.error("the sftp account password can not be null ");
            return false;
        }
        return true;
    }
    
    
    
    /** 
     * 方法 ： toString
     * 
     * @return String 返回结果
     */
    @Override
    public String toString()
    {
        return new StringBuffer().append(super.toString())
                .append("ip:" + this._ipAddress)
                .append("user:" + this._userName)
                .append("password:" + "<null>")
                .append("port:" + this.port)
                .toString();
    }
    
    

}
