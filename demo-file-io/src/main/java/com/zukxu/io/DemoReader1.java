package com.zukxu.io;

import java.io.FileReader;
import java.io.IOException;

/**
 * <p>
 * 字符输入流
 * </p>
 *
 * @author xupu
 * @since 2022/5/15 15:13:27
 */
public class DemoReader1 {

    public static void main(String[] args) throws IOException {

        FileReader fr = new FileReader("D:\\temp\\workFile\\io\\outputStream2.txt");
        char[] buf = new char[10240];
        int len;
        while((len = fr.read(buf)) != -1) {
            System.out.println(new String(buf, 0, len));
        }
        fr.close();
    }

}
