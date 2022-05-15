package com.zukxu.io.buff;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * <p>
 * write
 * </p>
 *
 * @author xupu
 * @since 2022/5/15 16:07:11
 */
public class DemoBufferRead1 {

    public static void main(String[] args) throws IOException {
        FileReader fr1 = new FileReader("D:\\temp\\workFile\\io\\BufferedWriter1.txt");
        BufferedReader bfr = new BufferedReader(fr1);
        //bfr.readLine() 通过换行符回车符 或者回车换行符作为终止符号
        String line;
        while((line = bfr.readLine()) != null) {
            System.out.println(line);
        }
        fr1.close();
        bfr.close();
    }

}
