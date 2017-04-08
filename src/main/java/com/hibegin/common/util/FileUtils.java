package com.hibegin.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class FileUtils {

    public static void getAllFiles(String path, List<File> files) {
        getAllFilesByProfix(path, null, files);
    }

    public static void getAllFilesByProfix(String path, String suffix, List<File> files) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] fs = file.listFiles();
            if (fs != null) {
                for (File file2 : fs) {
                    if (file2.isDirectory()) {
                        getAllFilesByProfix(file2.getPath(), suffix, files);
                    } else {
                        if (suffix != null) {
                            if (file2.toString().endsWith(suffix)) {
                                files.add(file2);
                            }
                        } else {
                            files.add(file2);
                        }
                    }
                }
            }

        } else {
            if (suffix != null) {
                if (file.toString().endsWith(suffix)) {
                    files.add(file);
                }
            } else {
                files.add(file);
            }
        }
    }

    public static void moveOrCopy(String src, String target, boolean isMove) {
        File f = new File(src);
        if (f.isDirectory()) {
            File fs[] = new File(src).listFiles();
            target = target + "/" + f.getName();
            new File(target).mkdirs();
            for (File fl : fs) {
                if (fl.isDirectory()) {
                    moveOrCopy(fl.toString(), target, isMove);
                } else {
                    moveOrCopyFile(fl.toString(), target + "/" + fl.getName(), isMove);
                }
            }
        } else {
            moveOrCopyFile(f.toString(), target + "/" + f.getName(), isMove);
        }
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

    public static void moveOrCopyFile(String src, String target, boolean isMove) {
        try {
            File f = new File(src);
            FileInputStream in = new FileInputStream(f);
            new File(target).getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(target);
            // 小于1M(大小根据自己的情况而定)的文件直接一次性写入。
            byte[] b = new byte[1024];
            int length = 0; // 出来cnt次后 文件 跳出循环
            while ((length = in.read(b)) != -1) {
                out.write(b, 0, length);
            }
            // 一定要记得关闭流额。 不然其他程序那个文件无法进行操作
            in.close();
            out.close();
            if (isMove) {
                f.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteFile(String file) {
        File f = new File(file);
        if (f.isDirectory()) {
            deleteDir(file);
            return !f.exists();
        } else {
            return new File(file).delete();
        }
    }

    private static void deleteDir(String filer) {
        File f = new File(filer);
        if (f.isDirectory()) {
            File fs[] = new File(filer).listFiles();
            if (fs != null && fs.length > 0) {
                for (File fl : fs) {
                    if (fl.isDirectory()) {
                        deleteDir(fl.toString());
                    } else {
                        fl.delete();
                    }
                }
            }
        }
        f.delete();
    }
}
