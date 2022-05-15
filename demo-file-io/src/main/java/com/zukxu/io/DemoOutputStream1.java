package com.zukxu.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * <p>
 * 字节输出流  往文件中输出内容
 * </p>
 *
 * @author xupu
 * @since 2022/5/14 18:16:14
 */
public class DemoOutputStream1 {

    public static void main(String[] args) {
        try {
            //outputToFile1();
            outputToFile2();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static void outputToFile1() throws IOException {
        //1 创建OutputStream 对象，构造方法中传递写入数据的目的地
        //2 调用write 方法，将数据写入
        //3 关闭流
        FileOutputStream fos = new FileOutputStream("D:\\temp\\workFile\\io\\outputStream1.txt");
        //fos.write(97);//字节会转换为二进制，然后根据二进制查询对应编码表存储
        //fos.write(98);
        //fos.write(99);
        String str = "中国最强！！！Hello World zukxu!!!";
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        fos.write(bytes);//将字节数组写入文件
        //fos.write(bytes,0,10);//写入字节数组的一部分
        fos.close();
    }

    private static void outputToFile2() throws IOException {
        //续写追加
        //声明时开启追加 默认false
        FileOutputStream fos = new FileOutputStream("D:\\temp\\workFile\\io\\outputStream2.txt", true);
        String str = LocalDateTime.now().toString()+"Hello";
        fos.write(str.getBytes());
        fos.write(" \r\n".getBytes());
        fos.close();
        //换行写
    }

}
