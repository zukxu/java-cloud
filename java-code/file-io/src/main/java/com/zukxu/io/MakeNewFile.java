package com.zukxu.io;

import java.io.File;
import java.io.IOException;

/**
 * @author xupu
 * @Description 创建新文件
 * @Date 2021-09-17 9:26
 */
public class MakeNewFile {
    private static String path = "";

    public static void main(String[] args) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}
