package com.huawei.storage.common.extracdata.util;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.huawei.storage.common.extracdata.constant.LegoNumberConstant;



/**
 * Ip地址工具�?
 * <功能详细描述>
 *
 * @author  s90004407
 * @version  [Lego V100R002C10, 2014-12-18]
 * @see  [相关�?/方法]
 * @since  [产品/模块版本]
 */
public final class AddressUtil
{
    private static final int VALUE_0X00FFFFFF = 0x00FFFFFF;
    
    private static final int VALUE_0X0000FFFF = 0x0000FFFF;
    
    private static final int VALUE_0X000000FF = 0x000000FF;
    
    private static final String REREX_IPV6_ADDRESS =
        "^\\s*((([0-9A-Fa-f]{1,4}:){7}(([0-9A-Fa-f]{1,4})|:))|(([0-9A-Fa-f]{1,4}:){6}"
            + "(:|((25[0-5]|2[0-4]\\d|[01]?\\d{1,2})"
            + "(\\.(25[0-5]|2[0-4]\\d|[01]?\\d{1,2})){3})|(:[0-9A-Fa-f]{1,4})))|(([0-9A-Fa-f]{1,4}:){5}"
            + "((:((25[0-5]|2[0-4]\\d|[01]?\\d{1,2})(\\.(25[0-5]|2[0-4]\\d|[01]?\\d{1,2})){3})?)|"
            + "((:[0-9A-Fa-f]{1,4}){1,2})))|(([0-9A-Fa-f]{1,4}:){4}(:[0-9A-Fa-f]{1,4}){0,1}"
            + "((:((25[0-5]|2[0-4]\\d|[01]?\\d{1,2})(\\.(25[0-5]|2[0-4]\\d|[01]?\\d{1,2})){3})?)|"
            + "((:[0-9A-Fa-f]{1,4}){1,2})))|(([0-9A-Fa-f]{1,4}:){3}(:[0-9A-Fa-f]{1,4}){0,2}"
            + "((:((25[0-5]|2[0-4]\\d|[01]?\\d{1,2})(\\.(25[0-5]|2[0-4]\\d|[01]?\\d{1,2})){3})?)|"
            + "((:[0-9A-Fa-f]{1,4}){1,2})))|(([0-9A-Fa-f]{1,4}:){2}(:[0-9A-Fa-f]{1,4}){0,3}"
            + "((:((25[0-5]|2[0-4]\\d|[01]?\\d{1,2})(\\.(25[0-5]|2[0-4]\\d|[01]?\\d{1,2})){3})?)|"
            + "((:[0-9A-Fa-f]{1,4}){1,2})))|(([0-9A-Fa-f]{1,4}:)(:[0-9A-Fa-f]{1,4}){0,4}"
            + "((:((25[0-5]|2[0-4]\\d|[01]?\\d{1,2})(\\.(25[0-5]|2[0-4]\\d|[01]?\\d{1,2})){3})?)|"
            + "((:[0-9A-Fa-f]{1,4}){1,2})))|(:(:[0-9A-Fa-f]{1,4}){0,5}((:((25[0-5]|2[0-4]\\d|[01]?\\d{1,2})"
            + "(\\.(25[0-5]|2[0-4]\\d|[01]?\\d{1,2})){3})?)|((:[0-9A-Fa-f]{1,4}){1,2})))"
            + "|(((25[0-5]|2[0-4]\\d|[01]?\\d{1,2})(\\.(25[0-5]|2[0-4]\\d|[01]?\\d{1,2})){3})))(%.+)?\\s*$";
            
    private static final BigInteger INVALID = BigInteger.ZERO;
    
    private AddressUtil()
    {
    }
    
    /**
     * �?查IP是否是本机IP
     *
     * @param ip ip
     * @return boolean
     * @throws Exception 
     */
    public static boolean checkLocalIP(String ip) throws Exception
    {
        if ((null == ip) || (ip.length() <= 0))
        {
            return false;
        }
        
        if ("127.0.0.1".equals(ip))
        {
            return true;
        }
        
        if ("localhost".equalsIgnoreCase(ip))
        {
            return true;
        }
        
        InetAddress addrd;
        
        try
        {
            addrd = InetAddress.getLocalHost();
        }
        catch (UnknownHostException ee)
        {
            throw new Exception(ee);
        }
        
        String hostName = addrd.getHostName();
        InetAddress[] ipsAddr = null;
        
        try
        {
            ipsAddr = InetAddress.getAllByName(hostName);
        }
        catch (UnknownHostException ex)
        {
            throw new Exception(ex);
        }
        
        String[] localServers = new String[ipsAddr.length];
        
        for (int i = 0; i < ipsAddr.length; i++)
        {
            if (ipsAddr[i] != null)
            {
                localServers[i] = ipsAddr[i].getHostAddress();
                
                if (ip.equals(localServers[i]))
                {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * 得到本机ip
     * <功能详细描述>
     *
     *<参数说明及返回类型说�?>
     * @return String 本机ip
     * @throws Exception 
     * @see [类�?�类#方法、类#成员]
     */
    public static String getLocalIP() throws Exception
    {
        InetAddress addr;
        
        try
        {
            addr = InetAddress.getLocalHost();
        }
        catch (UnknownHostException e)
        {
            throw new Exception(e);
        }
        
        String hostName = addr.getHostName();
        InetAddress[] ipsAddr = null;
        
        try
        {
            ipsAddr = InetAddress.getAllByName(hostName);
        }
        catch (UnknownHostException e)
        {
            throw new Exception(e);
        }
        
        if ((null != ipsAddr) && (ipsAddr.length > 0))
        {
            return ipsAddr[0].getHostAddress();
        }
        
        return "";
    }
    
    /**
     * �?127.0.0.1形式的ip地址 转换�? 十进制整数形�?
     * <功能详细描述>
     *
     * @param ipaddress ipaddress
     * @return long [返回类型说明]
     * @see [类�?�类#方法、类#成员]
     */
    public static long ipToLong(String ipaddress)
    {
        long[] ip = new long[LegoNumberConstant.FOUR];
        
        //先找到IP地址字符串中.的位�?  
        int position1 = ipaddress.indexOf(".");
        int position2 = ipaddress.indexOf(".", position1 + 1);
        int position3 = ipaddress.indexOf(".", position2 + 1);
        //将每�?.之间的字符串转换成整�?  
        ip[0] = Long.parseLong(ipaddress.substring(0, position1));
        ip[1] = Long.parseLong(ipaddress.substring(position1 + 1, position2));
        ip[LegoNumberConstant.TWO] = Long.parseLong(ipaddress.substring(position2 + 1, position3));
        ip[LegoNumberConstant.THREE] = Long.parseLong(ipaddress.substring(position3 + 1));
        
        return (ip[0] << LegoNumberConstant.TWENTY_FOUR) + (ip[1] << LegoNumberConstant.SIXTEEN)
            + (ip[LegoNumberConstant.TWO] << LegoNumberConstant.EIGHT) + ip[LegoNumberConstant.THREE];
    }
    
    /**
     * 将十进制整数形式转换�?127.0.0.1形式的ip地址
     * 将整数�?�进行右移位操作�?>>>），右移24位，右移时高位补0，得到的数字即为第一段IP�?
     * 通过与操作符�?&）将整数值的�?8位设�?0，再右移16位，得到的数字即为第二段IP�?
     * 通过与操作符把整数�?�的�?16位设�?0，再右移8位，得到的数字即为第三段IP�?
     * 通过与操作符把整数�?�的�?24位设�?0，得到的数字即为第四段IP�?
     *
     * @param ipaddress ipaddress
     * @return String String
     */
    public static String longToIP(long ipaddress)
    {
        StringBuffer sb = new StringBuffer("");
        //直接右移24�?  
        sb.append(String.valueOf(ipaddress >>> LegoNumberConstant.TWENTY_FOUR));
        sb.append('.');
        //将高8位置0，然后右�?16�?  
        sb.append(String.valueOf((ipaddress & VALUE_0X00FFFFFF) >>> LegoNumberConstant.SIXTEEN));
        sb.append('.');
        //将高16位置0，然后右�?8�?  
        sb.append(String.valueOf((ipaddress & VALUE_0X0000FFFF) >>> LegoNumberConstant.EIGHT));
        sb.append('.');
        //将高24位置0  
        sb.append(String.valueOf(ipaddress & VALUE_0X000000FF));
        
        return sb.toString();
    }
    
    /**
     * �?查所给的是否为一个合法的IP地址 下边三种都是特殊地址，不能用于IP用户 0.* 127.* 255.255.255.255
     * 224.*以上的，都是组播或保留地�?，也不能用于IP用户�?
     * 综合�?下，就是�?0.*�?127.*以及�?224�?255�?.*都不能用于网络上的IP用户�?
     *
     * @param ip ip
     * @return true: 合法的IP地址 false: 非法的IP地址
     */
    public static boolean ipValid(String ip)
    {
        if (null == ip)
        {
            return false;
        }
        
        String regex = "^([1-9]|[1-9]\\d|1\\d{2}|2[0-1]\\d|22[0-3]).(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])."
            + "(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5]).(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(ip);
        boolean isIp = m.matches();
        
        if (isIp)
        {
            String[] iPArray = ip.split("\\.");
            
            if (iPArray.length != LegoNumberConstant.FOUR)
            {
                return false;
            }
            
            int val = Integer.parseInt(iPArray[0]);
            
            if (val == LegoNumberConstant.ONE_HUNDRED_TWENTY_SEVEN)
            {
                return false;
            }
            
            return true;
        }
        
        return false;
    }
    
    
    /**
     * 根据子网掩码的位数得�?*.*.*.*格式的子网掩�?
     * <功能详细描述>
     *
     * @param mask mask
     * @return String [返回类型说明]
     * @see [类�?�类#方法、类#成员]
     */
    public static String maskToIp(int mask)
    {
        int x;
        int y;
        String part = "";
        
        x = mask / LegoNumberConstant.EIGHT;
        y = mask % LegoNumberConstant.EIGHT;
        
        for (int i = 0; i < x; i++)
        {
            if (x == LegoNumberConstant.FOUR)
            {
                part = "255.255.255.255";
                
                break;
            }
            
            part = part + "255.";
        }
        
        int sum = 0;
        int constant = LegoNumberConstant.TWO_HUNDRED_FIFTY_SIX;
        
        for (int i = 0; i < y; i++)
        {
            constant = constant >> 1;
            sum = sum + constant;
        }
        
        /*String temp = "";*/
        StringBuffer buf = new StringBuffer();
        
        if (x != LegoNumberConstant.FOUR)
        {
            for (int i = 0; i < (LegoNumberConstant.THREE - x); i++)
            {
                buf = buf.append(".0");
            }
            
            part = part + sum + buf.toString();
        }
        
        return part;
    }
    
    /**
     * 添加关于IPV6的方法
     * g00250185
     * @param addr ipv6格式的字符串 可选压缩
     * @return ip代表的数字
     */
    public static BigInteger ipv6ToBigInteger(String addr)
    {
        if (addr == null)
        {
            return INVALID;
        }
        int startIndex = addr.indexOf("::");
        
        if (startIndex != -1)
        {
            
            String firstStr = addr.substring(0, startIndex);
            String secondStr = addr.substring(startIndex + LegoNumberConstant.TWO, addr.length());
            
            BigInteger first = ipv6ToBigInteger(firstStr);
            //获取前半部分有多少个：
            int x = countChar(firstStr, ':');
            BigInteger two = ipv6ToBigInteger(secondStr);
            //总共最多7个：
            first = first.shiftLeft(LegoNumberConstant.SIXTEEN * (LegoNumberConstant.SEVEN - x)).add(two);
            
            return first;
        }
        
        String[] strArr = addr.split(":");
        
        BigInteger retValue = BigInteger.valueOf(0);
        for (int i = 0; i < strArr.length; i++)
        {
            if (strArr[i].isEmpty())
            {
                strArr[i] = "0";
            }
            BigInteger bi = new BigInteger(strArr[i], LegoNumberConstant.SIXTEEN);
            retValue = retValue.shiftLeft(LegoNumberConstant.SIXTEEN).add(bi);
        }
        return retValue;
    }
    
    /**
     * 添加关于IPV6的方法
     * @param ipNumber 大数字
     * @return 字符串 带压缩
     */
    public static String bigIntegerToipv6(BigInteger ipNumber)
    {
        String ipString = "";
        BigInteger a = new BigInteger("FFFF", LegoNumberConstant.SIXTEEN);
        for (int i = 0; i < LegoNumberConstant.EIGHT; i++)
        {
            ipString = ipNumber.and(a).toString(LegoNumberConstant.SIXTEEN) + ":" + ipString;
            
            ipNumber = ipNumber.shiftRight(LegoNumberConstant.SIXTEEN);
        }
        //压缩
        return ipString.substring(0, ipString.length() - 1).replaceFirst("(^|:)(0+(:|$)){2,8}", "::");
    }
    
    /** 
     * 检查IPV6地址的合法性，包括检查是否是正确的IPV6地址，不能是组播地址，不能是还回地址，不能是多播地址
     * @param ipv6 方法参数：ipv6
     * 
     * @return boolean [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isValidIPV6(String ipv6)
    {
        if (null == ipv6)
        {
            return false;
        }
        ipv6 = Normalizer.normalize(ipv6, Form.NFKC);
        Pattern pattern = Pattern.compile(REREX_IPV6_ADDRESS);
        Matcher mat = pattern.matcher(ipv6);
        if (mat.matches())
        {
            try
            {
                InetAddress address = InetAddress.getByName(ipv6);
                if (address == null)
                {
                    return false;
                }
                if (address instanceof Inet4Address && ipv6.equals(address.getHostAddress()))
                {
                    return false;
                }
                return true;
            }
            catch (UnknownHostException e)
            {
                return false;
            }
            //可能是IPv4地址
            catch (Exception e1)
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    
    /**
     * 是否是合法的IPV4地址 
     * @param ip ip
     * @return 是否合法
     */
    public static boolean isValidIPV4(String ip)
    {
        return ipValid(ip);
    }
    
    private static int countChar(String str, char reg)
    {
        char[] ch = str.toCharArray();
        int count = 0;
        for (int i = 0; i < ch.length; ++i)
        {
            if (ch[i] == reg)
            {
                ++count;
            }
        }
        return count;
    }
}
