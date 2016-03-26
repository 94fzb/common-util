package com.fzb.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.configurations.Rendering;


public class ThumbUtil {

    public static byte[] jpeg(byte[] buf, float qulity) throws IOException {

        ByteArrayInputStream bain = new ByteArrayInputStream(buf);
        ByteArrayOutputStream baout = new ByteArrayOutputStream();
        //水印功能
        //Thumbnails.of(bain).outputQuality(qulity).watermark(DataMap.WM).rendering(Rendering.SPEED).outputFormat("jpeg").width(320).toOutputStream(baout);
        Thumbnails.of(bain).outputQuality(qulity).rendering(Rendering.SPEED).outputFormat("jpeg").width(320).toOutputStream(baout);
        return baout.toByteArray();
    }

    public static byte[] jpeg(byte[] buf) throws IOException {
        return jpeg(buf, 0.6F);
    }
}
