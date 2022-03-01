package com.huawei.storage.common.sftp;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.HostKey;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Logger;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * sftp传输类
 * 完成以下功能：从设备下载文件、上传文件到设备
 *
 * @author yKF10108
 * @version [版本号V001R010C00, 2011-12-14]
 * @see JSch
 * @since ISM Common:transfer
 */
public class SftpTransfer {

    /**
     * 服务器路径分隔符
     */
    public static final String SERVER_SPLITER = ":/";

    /**
     * 服务器默认端口
     */
    public static final int DEAFLUAT_PORT = 22; // 服务器默认端口

    /**
     * 超时时间30s 单位MS
     */
    private static final int CONNECTION_TIMEOUT = 30 * 1000;

    private static final Map<String, Session> ip2session = new ConcurrentHashMap<String, Session>();

    private static final Map<String, ChannelSftp> ip2channel = new ConcurrentHashMap<String, ChannelSftp>();

    //存放ip到lock的对应管理
    private static final Map<String, Lock> ip2lock = new HashMap<String, Lock>();

    //标识对ip2lock 这个map的访问 需要使用这个锁
    private static final Lock lockOfMap = new ReentrantLock();

    private static final ExecutorService es = Executors.newFixedThreadPool(3);

    private SftpTransferProgress sftpTansferProgress = new SftpTransferProgress(); // 监管sftp传输进度的对象

    private int ipRetryCount = 0;

    private String encoding;

    private int permission = -1;
    
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SftpTransfer.class); 
	   

   
    //获取某个IP的锁
    private static Lock getLock(SftpAccount account) {
        Lock t = null;
        lockOfMap.lock();
        try {
            t = ip2lock.get(account.getIpAddress());
            if (t == null) {
                t = new ReentrantLock();
                ip2lock.put(account.getIpAddress(), t);
            }
        } finally {
            lockOfMap.unlock();
        }
        return t;
    }

    static {
        // 设置记录日志对象到三方包
        JSch.setLogger(new SftpLogger());
    }

    /**
     * 方法 ： SftpTransfer
     */
    public SftpTransfer() {
    }

    /**
     * <默认构造函数>
     *
     * @param encoding 编码
     */
    public SftpTransfer(String encoding) {
        this.encoding = encoding;
    }

    /**
     * 方法 ： SftpTransfer
     *
     * @param serverIp 方法参数：serverIp
     * @param account     方法参数：user
     * @throws Exception 
     */
    public SftpTransfer(String serverIp, SftpAccount account) throws Exception {
        createSession(account);
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    /**
     * 关闭连接
     *
     * @see ChannelSftp#quit()
     * @see ChannelSftp#exit()
     * @see Session#disconnect()
     */
    private static void closeChannel(String ip) {
        try {
            Set<String> keys = ip2channel.keySet();
            for (String key : keys) {
                if (key.startsWith(ip + "_")) {
                    ip2channel.get(key).disconnect();
                    ip2channel.get(key).exit();
                    ip2channel.remove(key);
                    logger.info("Close channel for: " + key);
                }
            }
        } catch (Exception e) {
            logger.error("Close Sftp Error!" + e);
        }
    }

    /**
     * 获取到sftp上传或者下载的进度（把整个文件划分成一百份）
     *
     * @return int 已上传或者已下载的大小
     */
    public int getCurrentStep() {
        long fileSize = sftpTansferProgress.getFileSize();
        long finishSize = sftpTansferProgress.getFinishSize();

        // 如果文件的大小为0，就直接返回0
        if (0 == fileSize) {
            return 0;
        }

        int currentStep = (int) (finishSize * 100 / fileSize);

        long tmp = (finishSize * 100) % fileSize;
        if (tmp != 0) {
            currentStep++;
        }
        if (currentStep > 100) {
            currentStep = 100;
        }

        return currentStep;
    }

    /**
     * 从服务器（设备）的指定目录下载该目录下的指定文件/
     *
     * @param serverPath 服务器路径（IP+服务器绝对路径）比如: "127.0.0.1"+":"+"/OSM/update/ism.zip"
     * @param localPath  存放文件的本地绝对路径 比如: "c:\\temp" 或者 "c:" + File.separator + "temp"
     *
     * @param port       端口号 如果是0 使用默认端口号
     * @param arrayId    方法参数：arrayId
     * @throws Exception ISM异常，表示下载文件失败
     * @see Session#connect()
     * @see Channel#connect()
     */
    public void transferFileFromDevice(String arrayId, String serverPath,
                                       String localPath, SftpAccount account, int port, boolean brokenByException) throws Exception {
        String arrayIdTemp = arrayId;
        String serverPathTemp = serverPath;
        String localPathTemp = localPath;
        SftpAccount userTemp = account;
        logger.info("transferFromDevserverPath:" + serverPath + ";localPath" + localPath + " port " + port);

        // 把serverPath解析为serverIp和serverAbsolutePath
        //begin h90005710 同步问题单 T12R-4113 用ipv6地址发现设备后导入license以及备份已激活license数据和导入导出数据报The SFTP failed to transmit the file（s）--无用例
        int index = serverPath.indexOf(SERVER_SPLITER);
        //end h90005710 同步问题单 T12R-4113 用ipv6地址发现设备后导入license以及备份已激活license数据和导入导出数据报The SFTP failed to transmit the file（s）--无用例

        String serverIp = serverPath.substring(0, index); //获得服务器ip地址信息
        String serverRelativePath = serverPath.substring(index + 1);
        String[] ips = serverIp.split(",");
        int length = ips.length;

        int unreachAbleCount = ipRetryCount;
        int usePort = (port > 0) ? port : DEAFLUAT_PORT;

        try {
            //当IP不通就略过，此部分只有高端才会涉及
            while (ipRetryCount < 3
                    && SftpUtils.isIpUnreachAble(ips[length - 1 - ipRetryCount], arrayId)) {
                ipRetryCount++;
                unreachAbleCount++;
                if (unreachAbleCount == 3) {
                    //如果高端都不通，重新检测IP
                    recheckIps(ips, usePort, arrayId);
                    logger.error("Sftp Fail.no serverIp to download!");
                    return;
                }
            }


            // 从最后一个IP开始连接,一直到第一个
            SftpAccount account2 =
                    new SftpAccount(ips[length - 1 - ipRetryCount], account.getUserName(),account.getPassword(), port > 0 ? port : DEAFLUAT_PORT);
            transferFile(account2, serverRelativePath, localPath, true, brokenByException);
            ipRetryCount = 0;
        } catch (Exception e) {
            ipRetryCount++;
            //如果异常需要重拾且是高端下载不通，重新检测IP，并返回
            if (brokenByException && highArrayCannotConn(e, ips, usePort, arrayId)) {
                throw e;
            }
            //如果是高端的，或者是中低端，异常需要重拾，且不是文件不存在，且不是账号错误
            else if (false
                    || (!brokenByException && !isNoSuchFileError(e))) {
                if (ipRetryCount < length) {
                    transferFileFromDevice(arrayIdTemp, serverPathTemp, localPathTemp, userTemp, port,
                            brokenByException);
                }
                logger.error("Sftp Fail. serverIp:" + serverIp, e);
                if (ipRetryCount == length) {
                    ipRetryCount = 0;
                    throw e;
                }
            } else {
                //用户名或者密码错 停止重试
                ipRetryCount = 0;
                throw e;
            }
        }
        logger.debug("transferFileFromDevice over.");
    }

    private boolean isNoSuchFileError(Exception e) {
        Throwable t = e.getCause();
        if (t instanceof Exception) {
            Exception ee = (Exception) e.getCause();
            String errMsg = ee.toString();
            return errMsg != null && errMsg.contains("No such file");
        }
        return false;
    }

    private boolean highArrayCannotConn(Exception e, String[] ips, int usePort, String arrayId) {
        if (false) {
            Throwable t = e.getCause();
            if (t instanceof NoRouteToHostException) {
                recheckIps(ips, usePort, arrayId);
                return true;
            }
        }
        return false;
    }

    private void recheckIps(String[] ips, int usePort, String deviceSn) {
        if (!false) {
            return;
        }
        Set<String> unreachIps = SftpUtils.getUnReachableIps().get(deviceSn);
        if (unreachIps == null) {
            unreachIps = new HashSet<String>();
            SftpUtils.getUnReachableIps().put(deviceSn, unreachIps);
        }
        Set<String> checkedIps = new HashSet<String>();
        Set<String> unReachIpsTemp = new HashSet<String>();
        for (int i = 0; i < ips.length; i++) {
            unreachIps.remove(ips[i]);
            if (checkedIps.contains(ips[i])) {
                continue;
            }
            if (ips[i] != null && ips[i].length() > 0
                    && !SftpTransfer.isSftpCanConnected(ips[i], usePort)) {
                unReachIpsTemp.add(ips[i]);
            }
            checkedIps.add(ips[i]);
        }
        unreachIps.addAll(unReachIpsTemp);
    }


    /**
     * 上传一个本地目录下的指定文件到服务器
     *
     * @param localPath  本地路径 比如: "c:\\temp\\ism.zip" 或者 "c:" + File.separator + "temp"+File.separator+"ism.zip"
     * @param serverPath 服务器路径（IP+服务器相对路径）比如: "127.0.0.1"+":"+"/testdir/"
     *
     * @throws Exception ISM异常，表示上传文件失败
     * @see Session#connect()
     * @see Channel#connect()
     */
    public void transferFileToDevice(String localPath, String serverPath,
                                     SftpAccount account) throws Exception {
        logger.info("transferFileToDevice,serverPath:" + serverPath
                + ";localPath:" + localPath);

        // 把serverPath解析为serverIp和serverAbsolutePath
        int index = serverPath.indexOf(SERVER_SPLITER);

        //服务器对应的ip地址
        String serverIp = serverPath.substring(0, index);

        //服务器的绝对路径信息
        String serverRelativePath = serverPath.substring(index + 1);
        try {
            // 创建sftp连接,不成功抛出IsmException
            transferFile(account, serverRelativePath, localPath, false, Boolean.TRUE);
        } catch (Exception e) {
            logger.error("transferFileToDevice error:" + account);
            throw e;
        }
        logger.debug("transferFileToDevice over.");
    }

    /**
     * <查询子目录、文件>
     *
     * @param serverPath 服务器路径
     *
     * @return 是否存在
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    public List<String> ls(String ip, String serverPath, SftpAccount account) throws Exception {
        List<String> fileNames = new ArrayList<String>();
        ChannelSftp channel = null;
        try {
            createSession(account);
            channel = getChannel(ip2session.get(ip), account, encoding, Boolean.FALSE);
            List<LsEntry> list = channel.ls(serverPath);
            if (list != null && !list.isEmpty()) {
                for (LsEntry ls : list) {
                    fileNames.add(ls.getFilename());
                }
            }
        } catch (SftpException e) {
            logger.error("transferFile error when call ls.");
        }
        return fileNames;
    }

    @SuppressWarnings("unchecked")
	public String getNewDataFile(String ip, String serverPath, SftpAccount account)  {
		int first = 0;
		String filename = "";
		ChannelSftp channel = null;
		try {
			createSession(account);
			channel = getChannel(ip2session.get(account.getIpAddress()+":"+account.getPort()),
                    account, encoding, Boolean.FALSE);
			Vector<LsEntry> list = channel.ls(serverPath);
			if (list != null && !list.isEmpty()) {
				for (LsEntry ls : list) {
					if (ls.getFilename().length() >= 4&&ls.getFilename().startsWith("PerfData")
                            &&ls.getFilename().endsWith("tgz")) {
						if (first == 0)
							first = ls.getAttrs().getATime();
						if (first <= ls.getAttrs().getATime()) {
							filename = ls.getFilename();
							first = ls.getAttrs().getATime();
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("transferFile error when call ls."+e.getMessage());
		}
		return filename;
	}

	/*******************************
	 * Private Function
	 ***************************************/

	/*
	 * 建立sftp连接 1.先用user登陆信息 2.若1不成功,再从配置文件中读取信息,建立sftp连接
	 *
	 * @param serverIp 服务器IP
	 * 
	 * @param user 用户登陆信息
	 * 
	 * @see Session#connect()
	 * 
	 * @see Session#getSession(String, String, int)
	 */
	private void transferFile(SftpAccount account, String serverRelativePath, String localPath, boolean getOrPut,
			boolean brokenByException) throws Exception {

        // 1.用户登陆信息创建SFTP连接
        ChannelSftp _channelSftp = null;
        try {

            //建立SFTP连接
            createSession(account);
            _channelSftp = getChannel(ip2session.get(account.getIpAddress()+":"+account.getPort()),
                    account,
                    encoding,
                    brokenByException);
            if (getOrPut) {

                //从指定服务器路径下载指定文件到指定的本地路径，同时传入监管SMTP传输进度的对象
//                _channelSftp.get(serverRelativePath,
//                        localPath,
//                        sftpTansferProgress);
                GetSftpFile getsftpFile = new GetSftpFile(_channelSftp, serverRelativePath, localPath);
                Future future = es.submit(getsftpFile);
                System.out.println("=======");
                try {
                    future.get(1, TimeUnit.SECONDS);
                } catch (TimeoutException e) {
                    _channelSftp.quit();
                    future.cancel(true);
                    logger.error("getSftpFile is timeOut.", e);
                } catch (Exception e) {
                    logger.error("getSftpFile is error.", e);
//                    future.cancel(true);
                }

            } else {

                //从指定的本地路径上传指定文件到指定服务器路径，同时传入监管SMTP传输进度的监视对象
                _channelSftp.put(localPath,
                        serverRelativePath,
                        sftpTansferProgress);

                // 如果设置了权限，则修改服务器文件的权限
                if (permission >= 0) {
                    String sPath = serverRelativePath
                            + "/"
                            + localPath.substring(localPath.lastIndexOf(File.separator));
                    _channelSftp.chmod(permission, sPath);
                }
            }
            logger.info("SftpTransfer.transferFile over");
        } catch (Exception e) {
            logger.error("Sftp Fail :" + account, e);
            throw e;
        }
    }


    static class GetSftpFile implements Callable<Boolean> {

        public GetSftpFile(ChannelSftp channelSftp, String serverRelativePath, String localPath) {
            super();
            _channelSftp = channelSftp;
            this.serverRelativePath = serverRelativePath;
            this.localPath = localPath;
        }

        private ChannelSftp _channelSftp;

        private String serverRelativePath, localPath;


        public Boolean call() throws Exception {
            try {
                _channelSftp.get(serverRelativePath, localPath);
                return true;
            } catch (SftpException e) {
                logger.error("getSftpFile is failed:" + e);
                return false;
            }

        }

    }

    //必须使用类方法来同步多个transfer使用的对象 如果只是在在对象同步的话是多个transfer无法重用session
    private static void createSession(SftpAccount account)
            throws Exception {
        createSession0(account, 3);
    }

    /**
     * 根据SftpAccount信息,建立sftp session连接
     *
     * @param account sftpAccount信息
     * @param count   重试次数
     * @return void
     * @throws Exception 创建不成功,抛出异常
     * @see [类、类#方法、类#成员]
     */
    private static void createSession0(SftpAccount account, int count)
            throws Exception {
        // 创建JSch对象
        JSch jsch = new JSch();
        Lock lockOfSession = getLock(account);
        lockOfSession.lock();
        Session realSession = null;
        try {
            // 如果会话存在，返回
            realSession = ip2session.get(account.getIpAddress()+":"+account.getPort());
            if (isConnected(account)) {
                logger.info("Not need to reconnect for ip:" + account.getIpAddress());
                return;
            }

            // 通过JSch对象，获取对话
            realSession = createSftpSession(account, jsch);
            ip2session.put(account.getIpAddress()+":"+account.getPort(), realSession);
            // 开启此次对话的sftp信道，并连接此信道
            logger.info("new session created: ip:" + account.getIpAddress());
        } catch (JSchException e) {
            logger.error("try to connect to the sftp server failed "
                    + account.getIpAddress() + ", the " + count + "th time.", e);
            //如果是用户名密码错则不尝试。
            if (e.getMessage().equals("Auth fail")
                    || e.getMessage().equals("Auth cancel") // 用户名或者密码错误信息
                    || e.getMessage().startsWith("invalid privatekey")
                    || e.getMessage().startsWith("USERAUTH fail"))//私钥错误
            {
                throw new Exception("", e);
            }

            // 如果第一次和第二次没有连上重试
            if (count <= 1) {
                throw new Exception("SFTP_TRANSFER_FAIL", e);
            }
            createSession0(account, count - 1);

        } finally {
            lockOfSession.unlock();
        }
    }

    private static Session createSftpSession(SftpAccount account, JSch jsch) throws JSchException {
        Session realSession;
        realSession = jsch.getSession(account.getUserName(),
                account.getIpAddress(),
                account.getPort());
        if (null != account.getPublicKeyAuth() && account.getPublicKeyAuth()) {
            logger.info("use the public key to get sftp session");
            jsch.addIdentity(null, account.getIdentity().getBytes(), null, account.getPassphrase().getBytes());
        } else if (account.getPassword() != null) {
            logger.info("use the default method to get sftp session");
            realSession.setPassword(account.getPassword());

        }
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        realSession.setConfig(sshConfig);
        realSession.connect(CONNECTION_TIMEOUT);

        return realSession;
    }

    /**
     * getChannel
     *
     * @param session  sftp 会话
     * @param account  账户
     * @param encoding 编码
     * @param count    重试次数 channel 无法重用（多线程）
     * @return channle 每次需要重建。
     * @throws Exception 建立连接失败
     */
    private ChannelSftp getChannel(Session session, SftpAccount account, String encoding, int count,
                                   boolean brokenByException)
            throws Exception {
        if (session == null || !session.isConnected()) {
            createSession(account);
        }
        if (session == null) {
            throw new Exception("SFTP_TRANSFER_FAIL");
        }
        ChannelSftp channelSftp = null;
        try {
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect(CONNECTION_TIMEOUT);
            ip2channel.put(account.getIpAddress()+":"+ account.getPort()
                    + "_" + brokenByException, channelSftp);
            logger.info("New channel sftp connect for device: " + account.getIpAddress()
                    + ", param: " + brokenByException);
        } catch (JSchException e) {
            logger.error("Get sftp channel failed." + account.getIpAddress(), e);
            if (count <= 1) {
                throw new Exception("SFTP_TRANSFER_FAIL");
            }
            return getChannel(session, account, encoding, count - 1, brokenByException);
        }


        // 把普通信道转化为sftp信道
        try {
            if (encoding != null && !"".equals(encoding)) {
                channelSftp.setFilenameEncoding(encoding);
            } else {
                channelSftp.setFilenameEncoding("GBK");
            }
        } catch (SftpException e) {
            //NOT RECORD THIS EX.
            //logger.error("Set Encoding failed." + encoding, e);
            try {
                channelSftp.setFilenameEncoding("UTF-8");
            } catch (SftpException e1) {
                logger.error("file name error.", e1);
            }
        }
        return channelSftp;
    }

    private ChannelSftp getChannel(Session session, SftpAccount account, String encoding,
                                   boolean brokenByException) throws Exception {
        ChannelSftp channel = ip2channel.get(account.getIpAddress() +":"+ account.getPort()
                + "_" + brokenByException);
        if (channel != null && channel.isConnected()) {
            return channel;
        }
        return getChannel(session, account, encoding, 3, brokenByException);
    }

    private static boolean isConnected(SftpAccount account) {
        Session session = ip2session.get(account.getIpAddress()+":"+account.getPort());

        return session != null && session.isConnected();
    }

    /**
     * ip是否可以通过sftp连通,无需用户名密码
     *
     * @param ip   ip
     * @param port 端口
     * @return 是否可连通
     */
    public static boolean isSftpCanConnected(String ip, int port) {
        boolean result = false;
        if (null == ip || "".equals(ip)) {
            logger.error("the param is empty");
            return result;
        }
        Socket socket = null;
        try {
            InetSocketAddress endPoint = new InetSocketAddress(ip, port > 0 ? port : SftpTransfer.DEAFLUAT_PORT);
            socket = new Socket();
            socket.connect(endPoint, 10000);//10秒超时
            result = true;
            logger.info("the sftp ip " + ip + " is connected ");
        } catch (IOException e) {
            logger.error("the sftp ip " + ip + " can not be connected ", e);
            result = false;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    logger.warn("close socket failed." + ip + ":" + port, e);
                }
            }
        }

        return result;
    }

    /**
     * ip是否可以通过sftp连通
     *
     *
     * @return 是否可连通
     * @throws Exception 
     */
    public static boolean isSftpCanConnected(SftpAccount account) throws Exception {
        logger.info("try use the new connection to ip: " + account.getIpAddress());
        boolean result = false;
        JSch jsch = new JSch();
        Session sshSession = null;
        if (!account.validateSftpAccount(account)) {
            logger.error("failed to validateSftpAccount");
            return result;
        }
        try {
            sshSession = createSftpSession(account, jsch);
            result = true;
            logger.info("the sftp ip " + account.getIpAddress() + " connected ");
        } catch (JSchException e) {
            logger.error("the sftp Auth failed for ip " + account.getIpAddress() + " can not connected ", e);
            throw new Exception("SFTP_USER_OR_PASSWORD_ERROR", e);
        } finally {
            closeSession(sshSession);
        }
        return result;
    }


    private static void closeSession(Session sshSession) {
        try {
            if (sshSession != null) {
                sshSession.disconnect();
            }
        } catch (Exception e) {
            logger.error("Close Sftp Error!" + e);
        }

    }

    public static String getHostKey(SftpAccount account) throws Exception {
        logger.info("try to get hostKey");
        JSch jsch = new JSch();
        Session sshSession = null;
        String fingerPrint = null;
        if (!account.validateSftpAccount(account)) {
            return fingerPrint;
        } else {
            createSession(account);
            sshSession = ip2session.get(account.getIpAddress());
            HostKey hk = sshSession.getHostKey();
            fingerPrint = hk.getFingerPrint(jsch);
            logger.info("success to get hostKey");

            return fingerPrint;
        }
    }

    /**
     * 获取缓存中的可连接的session
     * <功能详细描述>
     *
     * @param ip 设备IP
     * @return 缓存中的session
     * @see [类、类#方法、类#成员]
     */
    public static Session getSftpSession(String ip) {
        Session session = ip2session.get(ip);
        if (session != null && session.isConnected()) {
            return session;
        }
        return null;
    }

    /**
     * 关闭缓存中的session,并从缓存中remove
     * <功能详细描述>
     *
     * @param ip 设备IP
     * @return 缓存中的session
     * @see [类、类#方法、类#成员]
     */
    public static void closeSftpSession(String ip) {
        Session session = ip2session.get(ip);
        closeSession(session);
        ip2session.remove(ip);
        logger.info("closeSftpSession ip :" + ip);

        closeChannel(ip);
    }
}

/**
 * begin  AZ7D00616 增加新的类SftpAccount保存sftp用户信息
 *
 * @author V1R10
 * @version [版本号V001R010C00, 2011-12-14]
 */

/* end qKF10218  增加新的类SftpAccount保存sftp用户信息 */
class SftpLogger implements Logger {
	
	 private static org.apache.log4j.Logger apachelogger = org.apache.log4j.Logger.getLogger(SftpLogger.class); 
	   


    /**
     * 判断i对应的日志级别是否支持
     * 当前都支持
     *
     * @param i 日志级别
     * @return boolean 返回结果
     */
    public boolean isEnabled(int i) {
        return true;
    }

    /**
     * 记录日志
     *
     * @param i 日志级别
     * @param s 日志信息
     */
    public void log(int i, String s) {
        //获得当前的线程的堆栈信息
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        int number = stacks.length;
        int index = 0; //三方包所在的堆栈信息

        //获得在三方包中调用日志记录的方法
        for (; index < number; index++) {
            StackTraceElement element = stacks[index];
            if (element.getClassName().equals(this.getClass().getName())
                    && element.getMethodName().equals("log")) {
                index++;
                break;
            }
        }

        String str = builderLogs(s, stacks, number, index);
        //去掉低级别的日志
        switch (i) {
            case Logger.WARN:
            	apachelogger.warn(str);
                break;
            case Logger.ERROR:
            	apachelogger.error(str);
                break;
            case Logger.FATAL:
            	apachelogger.fatal(str);
                break;
            default:
                break;
        }
    }

    /**
     * 构建日志（降低复杂度）
     *
     * @param
     * @param
     */
    private String builderLogs(String s, StackTraceElement[] stacks,
                               int number, int index) {
        StringBuffer str = new StringBuffer("jsch>>>"); //日志记录信息
        if (index < number) {
            StackTraceElement tmp = stacks[index];
            String className = tmp.getClassName();
            int lineNum = tmp.getLineNumber();
            String methodName = tmp.getMethodName();

            str.append('[' + className + "]-[" + methodName + "]- [" + lineNum
                    + ']').append("/r/n"); //增加日志记录的类名 方法名 行数的信息
        }
        str.append(s);
        return str.toString();
    }


}
