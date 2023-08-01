package com.zukxu.io.buff;

import java.io.*;

/**
 * <p>
 * write
 * </p>
 *
 * @author xupu
 * @since 2022/5/15 16:07:11
 */
public class DemoBufferWrite1 {

    public static void main(String[] args) throws IOException {
        FileWriter fw1 = new FileWriter("D:\\temp\\workFile\\io\\BufferedWriter1.txt", true);
        BufferedWriter bfw = new BufferedWriter(fw1);
        for (int i = 0; i < 100; i++) {
            bfw.write("测试阿斯顿佳酿水泥地" + i);
            bfw.newLine();//写入一个当前系统的换行符 Windows \r\n linux /n mac /r
        }
        bfw.flush();
        fw1.close();
        bfw.close();
    }

}
