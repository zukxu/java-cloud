package com.zukxu.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/5/14 18:16:14
 */
public class DemoInputStream1 {

    public static void main(String[] args) {
        try {
            inputFromFile1();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void inputFromFile1() throws IOException {
        //    获取对象
        //    调用read方法读取
        //    关闭流"C:\Users\zukxu\Pictures\Saved Pictures\GZ.jpg"
        FileInputStream fis = new FileInputStream("C:\\Users\\zukxu\\Pictures\\Saved Pictures\\GZ.jpg");
        FileOutputStream fos = new FileOutputStream("D:\\temp\\workFile\\io\\GZcopy.jpg");
        byte[] bytes = new byte[1024];
        int len;
        while ((len = fis.read(bytes)) != -1) {
            fos.write(bytes, 0, len);
        }
        fis.close();
        fos.close();
    }

}
