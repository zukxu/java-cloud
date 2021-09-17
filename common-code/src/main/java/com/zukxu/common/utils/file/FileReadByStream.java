package com.zukxu.common.utils.file;

import java.io.*;

/**
 * @author xupu
 * @Description 文件工具类-字节流
 * @Date 2021-09-17 9:30
 */
public class FileReadByStream {
    /**
     * 字节输入流
     *
     * @param path 文件路径
     */
    public void readByFileInputStream(String path) {
        try (FileInputStream fis = new FileInputStream(path)) {
            byte[] bytes = new byte[1024];
            while (fis.read(bytes) != -1) {
                String str = new String(bytes);
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 字节缓冲输入流
     *
     * @param path 文件路径
     */
    public void readByBufferedInputStream(String path) {
        try (BufferedInputStream fis = new BufferedInputStream(new FileInputStream(path))) {
            byte[] bytes = new byte[1024];
            while (fis.read(bytes) != -1) {
                String str = new String(bytes);
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 字符输入流
     *
     * @param path 文件路径
     */
    public void readByFileReader(String path) {
        try (FileReader reader = new FileReader(path)) {
            char[] chars = new char[1024];
            while (reader.read(chars) != -1) {
                System.out.println(chars);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 字符缓冲输入流
     *
     * @param path 文件路径
     */
    public void readByBufferedReader(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String str;
            while ((str = reader.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
