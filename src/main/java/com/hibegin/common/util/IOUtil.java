package com.hibegin.common.util;

import org.apache.log4j.Logger;

import java.io.*;

public class IOUtil {

    private static final Logger LOGGER = Logger.getLogger(IOUtil.class);

    public static byte[] getByteByInputStream(InputStream in) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] tempByte = new byte[1024];
        try {
            int length;
            while ((length = in.read(tempByte)) != -1) {
                bout.write(tempByte, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        }
        return bout.toByteArray();
    }

    public static String getStringInputStream(InputStream in) {
        byte[] bytes = getByteByInputStream(in);
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("", e);
        }
        return new String(bytes);
    }


    public static void writeStrToFile(String str, File file) {
        writeBytesToFile(str.getBytes(), file);
    }

    public static void writeBytesToFile(byte[] bytes, File file) {
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(bytes);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
