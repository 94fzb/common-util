package com.fzb.common;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.MessageDigest;


public class CodecUtil {

    //用于加密的字符
    private static final char md5String[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};

    private static String dealIn(byte[] btInput, boolean isUpper, String mdType) {
        if (btInput != null) {
            try {
                MessageDigest mdInst = MessageDigest.getInstance(mdType);
                mdInst.update(btInput);
                byte[] md = mdInst.digest();
                int j = md.length;
                char str[] = new char[j * 2];
                int k = 0;
                for (int i = 0; i < j; i++) {   //  i = 0
                    byte byte0 = md[i];  //95
                    str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5
                    str[k++] = md5String[byte0 & 0xf];   //   F
                }
                if (isUpper) {
                    return new String(str).toUpperCase();
                }
                return new String(str);

            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static String MD5(String str) {
        return dealIn(str.getBytes(), true, "MD5");
    }

    public static String SHA(String str) {
        return dealIn(str.getBytes(), true, "SHA-1");
    }

    public static String SHA(File file) throws IOException {
        return dealIn(IOUtil.getByteByInputStream(new FileInputStream(file)), true, "SHA");
    }

    public static byte[] getHmacMD5(String privateKey, String input) throws Exception {
        String algorithm = "HmacSHA1";
        byte[] keyBytes = Base64.encodeBase64(privateKey.getBytes());
        Key key = new SecretKeySpec(keyBytes, 0, keyBytes.length, algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(key);
        return Base64.decodeBase64(mac.doFinal(input.getBytes()));
    }

    public static void main(String[] args) {
        try {
            String begin = new String(getHmacMD5("5b926153aa64bbfb5742792402616830", "134123414"));
            System.out.println(CodecUtil.SHA(new File("e:/qiyi/一次性爱上[高清].qsv")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
