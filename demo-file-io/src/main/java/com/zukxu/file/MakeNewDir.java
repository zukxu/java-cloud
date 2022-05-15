package com.zukxu.file;

import java.io.File;

/**
 * @author xupu
 * @Description 创建新文件夹
 * @Date 2021-09-17 9:27
 */
public class MakeNewDir {
    static String path = "E:/temp/test\\admin\\temp\\toux.jpg";

    public static void main(String[] args) {
        System.out.println(path.indexOf("\\"));
        System.out.println(path.indexOf("/"));
        System.out.println(path.indexOf(File.separator));
        String substring = path.substring(0, path.indexOf(File.separator));
        System.out.println(substring);
        File file = new File(substring);
        //如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            System.out.println("//不存在");
            file.mkdir();
        } else {
            System.out.println("//目录存在");
        }

    }
}
