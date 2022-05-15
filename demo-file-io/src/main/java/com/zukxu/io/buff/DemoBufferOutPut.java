package com.zukxu.io.buff;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <p>
 * output
 * </p>
 *
 * @author xupu
 * @since 2022/5/15 16:00:18
 */
public class DemoBufferOutPut {

    public static void main(String[] args) throws IOException {
        output1();
    }

    private static void output1() throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("D:\\temp\\workFile\\io\\outputStream3.txt", true));
        String str = "asdas阿斯顿你家那位难难难你打开看我看你能拿";
        bos.write(str.getBytes());
        bos.flush();
        bos.close();
    }

}
