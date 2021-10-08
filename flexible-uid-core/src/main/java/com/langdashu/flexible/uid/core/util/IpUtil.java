package com.langdashu.flexible.uid.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * IP工具类
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public class IpUtil {

    private static final Logger logger = LoggerFactory.getLogger(IpUtil.class);

    public static String getIp() {
        String ip;
        try {
            List<String> ipList = getHostAddress(null);
            ip = (!ipList.isEmpty()) ? ipList.get(0) : "";
        } catch (Exception e) {
            ip = "";
            logger.error("get ip error", e);
        }
        return ip;
    }

    /**
     * 获取本机网卡IP
     * @param interfaceName 可指定网卡名称,null则获取全部
     * @return
     * @throws SocketException
     */
    public static List<String> getHostAddress(String interfaceName) throws SocketException {
        List<String> ipList = new ArrayList<String>(5);
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface ni = interfaces.nextElement();
            Enumeration<InetAddress> allAddress = ni.getInetAddresses();
            while (allAddress.hasMoreElements()) {
                InetAddress address = allAddress.nextElement();
                if (address.isLoopbackAddress()) {
                    continue;
                }
                if (address instanceof Inet6Address) {
                    continue;
                }
                String hostAddress = address.getHostAddress();
                if(logger.isDebugEnabled()) {
                    logger.debug(ni.getDisplayName());
                }
                if (null == interfaceName) {
                    ipList.add(hostAddress);
                } else if (interfaceName.equals(ni.getDisplayName())) {
                    ipList.add(hostAddress);
                }
            }
        }
        return ipList;
    }

}
