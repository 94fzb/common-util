package com.hibegin.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class IOUtils {

    public static byte[] getByteByInputStream(InputStream in) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] tempByte = new byte[1024];
        try {
            int length = 0;
            while ((length = in.read(tempByte)) != -1) {
                bout.write(tempByte, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bout.toByteArray();
    }

    public static String getStringInputStream(InputStream in) {
        byte[] bytes = getByteByInputStream(in);
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new String(bytes);
    }
}
