package com.fzb.common;

import java.io.File;

/**
 * 提供给一些路径供程序更方便的调用
 *
 * @author Chun
 */
public class PathKit {

    public static String getRootPath() {
        String path;
        if (PathKit.class.getResource("/") != null) {
            path = new File(PathKit.class.getClass().getResource("/").getPath())
                    .getParentFile().toString();

        } else {
            String thisPath = PathKit.class.getProtectionDomain()
                    .getCodeSource().getLocation().getPath()
                    .replace("\\", "/");
            path = thisPath.substring(0, thisPath.lastIndexOf('/'));
        }
        return path;
    }
}
