package com.zhunongyun.toalibaba.commoncode.utils;

import java.io.File;

public class PictureNameHandler {

    private final static String FILE_PATH_URL = "D:\\picture";

    private static Long fileName = System.currentTimeMillis();

    public static void main(String[] args) {
        PictureNameHandler pictureNameHandler = new PictureNameHandler();
        File file = new File(FILE_PATH_URL);
        System.out.println("重命名文件,fileName start:" + fileName);
        pictureNameHandler.handlerPictureFileName(file);
        System.out.println("重命名文件,fileName end:" + fileName);
    }

    private void handlerPictureFileName(File file) {
        for (File tempFile : file.listFiles()) {
            if (tempFile.isDirectory()) {
                handlerPictureFileName(file);
            } else {
                tempFile.renameTo(new File(FILE_PATH_URL + File.separator + fileName + ".png"));
                fileName++;
            }
        }
    }
}
