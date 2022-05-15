package com.zukxu.io.buff;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * <p>
 * 缓冲流 对基础的字节流和字符流的增强
 * </p>
 *
 * @author xupu
 * @since 2022/5/15 15:49:56
 */
public class DemoBufferInput1 {

    public static void main(String[] args) throws IOException {
        buffMethod1();
    }

    private static void buffMethod1() throws IOException {
        // D:\temp\workFile\io\outputStream2.txt
        byte[] bytes = new byte[1024];
        BufferedInputStream bfis = new BufferedInputStream(new FileInputStream(("D:\\temp\\workFile\\io\\outputStream2.txt")));
        int len;
        while((len = bfis.read(bytes)) != -1) {
            System.out.println(new String(bytes, 0, len));
        }
        bfis.close();
    }

}
