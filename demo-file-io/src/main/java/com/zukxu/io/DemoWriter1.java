package com.zukxu.io;

import java.io.FileWriter;
import java.io.IOException;

/**
 * <p>
 * 字符输出流
 * </p>
 *
 * @author xupu
 * @since 2022/5/15 15:13:27
 */
public class DemoWriter1 {

    public static void main(String[] args) throws IOException {
        //D:\temp\workFile\io\outputStream2.txt
        FileWriter writer = new FileWriter("D:\\temp\\workFile\\io\\outputStream2.txt",true);
        String str = "124asdasd按你说的静安寺都不会那我们你能不能那你";
        char[] c = new char[1024];

        writer.write(str);
        writer.close();
    }

}
