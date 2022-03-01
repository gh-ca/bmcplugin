package com.huawei.storage.common.sftp;



import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <功能详细描述>
 * 
 * @author d00292924
 * @version [V300R005C00, 2015年12月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SftpUtils {

	/**
	 * 下载IP检测结果，不通的IP
	 */
	private static Map<String, Set<String>> unReachableIps = new ConcurrentHashMap<String, Set<String>>();

	/**
	 * 上次检测IP的时间
	 */
	private static Map<String, Long> lastCheckTime = new ConcurrentHashMap<String, Long>();

	public static SftpAccount sftpAdapter(String ip, String uname, String upass, int port) {
		SftpAccount account = null;
		account = new SftpAccount(ip, uname, upass, port);
		return account;
	}

	public static Map<String, Set<String>> getUnReachableIps() {
		return unReachableIps;
	}

	public static void setUnReachableIps(Map<String, Set<String>> unReachableIps) {
		SftpUtils.unReachableIps = unReachableIps;
	}

	public static Map<String, Long> getLastCheckTime() {
		return lastCheckTime;
	}

	public static void setLastCheckTime(Map<String, Long> lastCheckTime) {
		SftpUtils.lastCheckTime = lastCheckTime;
	}

	/**
	 * 判断IP是不是通
	 * 
	 * @param ip
	 *            ip
	 * @param deviceId
	 *            deviceId
	 * @return boolean
	 */
	public static boolean isIpUnreachAble(String ip, String deviceSn) {
		if (ip == null || "".equals(ip.trim())) {
			return Boolean.TRUE;
		}
		Set<String> unReacheabeIps = getUnReachableIps().get(deviceSn);
		return unReacheabeIps != null && unReacheabeIps.contains(ip);
	}

}
