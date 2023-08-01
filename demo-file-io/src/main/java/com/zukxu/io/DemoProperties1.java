package com.zukxu.io;

import lombok.SneakyThrows;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import static jdk.nashorn.internal.codegen.OptimisticTypesPersistence.load;

/**
 * <p>
 * Properties
 * </p>
 *
 * @author xupu
 * @since 2022/5/15 15:34:51
 */
public class DemoProperties1 {

    public static void main(String[] args) throws IOException {
        //storeMethod();
        loadMethod();
    }

    private static void loadMethod() throws IOException {
        //D:\temp\workFile\io\outputStream2.txt
        Properties pt1 = new Properties();
        pt1.load(new FileReader("D:\\temp\\workFile\\io\\config.properties"));
        Set<String> name = pt1.stringPropertyNames();//返回key名集合

        for (String n : name) {
            System.out.println(n + "||" + pt1.getProperty(n));////通过key获取value 可以设置默认值
        }
    }

    @SneakyThrows
    private static void storeMethod() {
        Properties pt1 = new Properties();
        //pt1.load();//输入流
        pt1.setProperty("name", "刘亦菲");//设置key value
        pt1.setProperty("age", "18");
        pt1.setProperty("sex", "女");
        Set<String> name = pt1.stringPropertyNames();//返回key名集合

        for (String n : name) {
            System.out.println(n + "||" + pt1.getProperty(n));////通过key获取value 可以设置默认值
        }
        //D:\\temp\\workFile\\io\\config.properties
        FileWriter fw = new FileWriter("D:\\temp\\workFile\\io\\config.properties");
        pt1.store(fw, "config");//输出流
        fw.close();
    }

}
