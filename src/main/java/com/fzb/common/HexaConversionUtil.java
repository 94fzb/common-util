package com.fzb.common;

import java.io.IOException;

/**
 * 字节，字节数组(合并)，二，十，十六进制的相互
 *
 * @author xiaochun
 */
public class HexaConversionUtil {

    public static String bytesToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString().toUpperCase();
    }

    public static String bytes2IPStr(byte[] b) {
        String bytesStr = Long.toBinaryString(
                Long.valueOf(bytesToHexString(b), 16)).toString();
        int length = bytesStr.length();
        int need = 32 - length;
        String str = "";
        while (need > 0) {
            str += "0";
            need--;
        }
        str += bytesStr;
        String ipStr = "";
        for (int i = 0; i < 4; i++) {
            String temp = str.substring(i * 8, (i + 1) * 8);
            int in = Integer.valueOf(temp, 2);
            ipStr += in + ".";
        }
        return ipStr.substring(0, ipStr.length() - 1);
    }


    public static byte[] HexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    public static byte[] HexString2Bytes(String hexstr, int size) {
        byte[] b = new byte[size];
        int j = 0;
        for (int i = 0; i < hexstr.length() / 2; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    private static int parse(char c) {
        if (c >= 'a')
            return (c - 'a' + 10) & 0x0F;
        if (c >= 'A')
            return (c - 'A' + 10) & 0x0F;
        return (c - '0') & 0x0F;
    }

    /**
     * int到byte[]
     *
     * @param i
     * @return
     */
    public static byte[] intToByteArray(int number) {
        byte[] targets = new byte[4];
        targets[0] = (byte) (number & 0xff);// 最低位
        targets[1] = (byte) ((number >> 8) & 0xff);// 次低位
        targets[2] = (byte) ((number >> 16) & 0xff);// 次高位
        targets[3] = (byte) (number >>> 24);// 最高位,无符号右移。
        return targets;
    }

    public static byte[] intToByteArrayH(int number) {
        byte[] targets = new byte[4];
        targets[3] = (byte) (number & 0xff);// 最低位
        targets[2] = (byte) ((number >> 8) & 0xff);// 次低位
        targets[1] = (byte) ((number >> 16) & 0xff);// 次高位
        targets[0] = (byte) (number >>> 24);// 最高位,无符号右移。
        return targets;
    }

    public static int byteArrayToIntH(byte[] bytes) {
        int value;
        value = (int) (((bytes[0] & 0xFF) << 24)
                | ((bytes[1] & 0xFF) << 16)
                | ((bytes[2] & 0xFF) << 8)
                | (bytes[3] & 0xFF));
        return value;
    }

    /**
     * byte[]转int
     *
     * @param bytes
     * @return
     */
    public static int byteArrayToInt(byte[] b) {
        int res = 0;
        int bLen = b.length;

        if (bLen < 5) {// int 最大到4个字节
            for (int i = 0; i < bLen; i++) {
                res += (b[i] & 0xFF) << (8 * i);
            }
        }
        return res;
    }

    /**
     * short到字节数组的转换！
     *
     * @param s
     * @return
     */
    public static byte[] shortToByte(short number) {
        int temp = number;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xFF).byteValue();// 将最低位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    public static short byteToShort(byte[] b) {
        short s = 0;
        short s0 = (short) (b[0] & 0xFF);// 最低位
        short s1 = (short) (b[1] & 0xFF);
        s1 <<= 8;
        s = (short) (s0 | s1);
        return s;
    }


    public static byte[] mergeBytes(byte[]... bytes) {
        int nbytesSize = 0;

        for (byte[] bs : bytes) {
            nbytesSize += bs.length;
        }
        byte nBytes[] = new byte[nbytesSize];
        int size = 0;
        for (byte[] bs : bytes) {
            System.arraycopy(bs, 0, nBytes, size, bs.length);
            size += bs.length;
        }
        return nBytes;
    }

    public static byte[] subByts(byte[] b, int start, int length) {
        byte rbytes[] = new byte[length];
        System.arraycopy(b, start, rbytes, 0, length);
        return rbytes;
    }

    public static String byteToBit(byte b) {
        return ""
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
    }

    public static void main(String[] args) throws IOException {
        byte bs[] = intToByteArrayH(111177);
        int i = byteArrayToIntH(bs);
        System.out.println("" + bs[0] + "" + bs[1] + "" + bs[2] + "" + bs[3]);
        System.out.println(i);
    }

}
