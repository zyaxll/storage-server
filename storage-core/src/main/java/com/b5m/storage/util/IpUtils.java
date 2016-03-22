/*
 * Copyright 2011-2015 B5M.COM. All rights reserved
 */
package com.b5m.storage.util;

/**
 * //TODO
 * 
 * @author lucky.liu
 * @email liuwb2010@gmail.com
 * @version Nov 26, 2015 8:48:39 PM
 */
public abstract class IpUtils {
    private final static long MAX_IP = 0xffffffffL;
    
    private static final char DOT = '.';
    
    /**
     * 0～255数值校验正则
     */
    public static final String OX0_OXFF_REGEX = "([0-9]{1,2}|[0-1][0-9]{2}|2[0-4][0-9]|25[0-5])";
    
    /**
     * Ipv4地址格式校验正则
     */
    public static final String IP_REGEX = OX0_OXFF_REGEX + "(?:\\." + OX0_OXFF_REGEX + "){3}";

    private IpUtils(){}

    /**
     * 将ipv4格式的ip地址转换成long型的ip地址
     * 
     * @param ipv4
     * @return
     */
    public static long changeIp(String ipv4) {
        if (ipv4 == null) {
            throw new NullPointerException("参数不能为空");
        }
        ipv4 = ipv4.trim();
        if (!ipv4.matches(IP_REGEX)) {
            throw new IllegalArgumentException("IPv4地址格式错误");
        }
        long lip = 0L;
        int begin = 0, end = ipv4.indexOf(DOT);
        String num;
        while (end > 0) {
            num = ipv4.substring(begin, end);
            lip = lip << 8 | Long.parseLong(num);
            begin = end + 1;
            end = ipv4.indexOf(DOT, begin);
        }
        num = ipv4.substring(begin);
        lip = lip << 8 | Long.parseLong(num);
        return lip;
    }

    private final static long FIRST = 0xff000000L;
    private final static long SECOND = 0x00ff0000L;
    private final static long THIRD = 0x0000ff00L;
    private final static long FOURTH = 0x000000ffL;

    /**
     * 将long型的ip地址转换成ipv4格式的字符串
     * 
     * @param ip
     * @return
     */
    public static String changeIp(long ip) {
        if (ip < 0L || ip > MAX_IP) {
            throw new IllegalArgumentException("IPv4地址数值超出范围");
        }
        StringBuilder sb = new StringBuilder();
        sb.append((ip & FIRST) >> 24).append(DOT);
        sb.append((ip & SECOND) >> 16).append(DOT);
        sb.append((ip & THIRD) >> 8).append(DOT);
        sb.append(ip & FOURTH);
        return sb.toString();
    }

    /**
     * 根据ip地址及子网掩码获取网络地址
     * 
     * @param ip ip地址
     * @param mask 子网掩码
     * @return 网络地址ipv4格式
     */
    public static String getNetAddress(String ip, String mask) {
        return changeIp(getNetLongAddr(ip, mask));
    }

    /**
     * 根据ip地址及子网掩码获取网络地址
     * 
     * @param ip ip地址
     * @param mask 子网掩码
     * @return 网络地址的long值
     */
    public static long getNetLongAddr(String ip, String mask) {
        long ipLong = changeIp(ip);
        long maskLong = changeIp(mask);
        return ipLong & maskLong;
    }
    
    /**
     * IP地址判断，判断标准见正则表达式
     * 
     * @see #IP_REGEX
     * @param ip
     * @return
     */
    public static boolean isIpAddr(String ip) {
        return ip != null && ip.matches(IP_REGEX);
    }
    
    // 将带掩码的ip地址转成ip地址+掩码+掩码数值
    public static String[] getIpInfo(String ipAddrWithMask) {
        if (ipAddrWithMask == null)
            throw new NullPointerException("address is empty");
        if (!ipAddrWithMask.matches("\\d+\\.\\d+\\.\\d+\\.\\d+/\\d+"))
            throw new IllegalArgumentException(ipAddrWithMask + " is not match!");
        int index = ipAddrWithMask.indexOf('/');
        String ipAddr = ipAddrWithMask.substring(0, index);
        String ipMaskNum = ipAddrWithMask.substring(index + 1);
        int count = 0, num = Integer.parseInt(ipMaskNum);
        StringBuilder sb = new StringBuilder();// ipMask
        while (++count < 5) {
            if (num < 1) {
                sb.append("0.");
            } else if (num < 8) {
                int c = 0, d = 0;
                while (c < num)
                    d += (1 << (8 - ++c));
                sb.append(d).append('.');
            } else {
                sb.append("255.");
            }
            num -= 8;
        }
        sb.deleteCharAt(sb.length() - 1);
        return new String[] {ipAddr, sb.toString(), ipMaskNum};
    }
}
