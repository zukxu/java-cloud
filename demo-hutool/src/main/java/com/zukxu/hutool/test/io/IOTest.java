package com.zukxu.hutool.test.io;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.charset.Charset;

/**
 * <p>
 * hutool IO流相关
 * </p>
 *
 * @author xupu
 * @since 2024/10/14 10:46:13
 */
public class IOTest {
    //    #封装
    //    io包的封装主要针对流、文件的读写封装，主要以工具类为主，提供常用功能的封装
    //
    //    IoUtil 流操作工具类
    //    FileUtil 文件读写和操作的工具类。
    //    FileTypeUtil 文件类型判断工具类
    //    WatchMonitor 目录、文件监听，封装了JDK1.7中的WatchService
    //    ClassPathResource针对ClassPath中资源的访问封装
    //    FileReader 封装文件读取
    //    FileWriter 封装文件写入
    //#流扩展
    //    除了针对JDK的读写封装外，还针对特定环境和文件扩展了流实现。
    //
    //    BOMInputStream针对含有BOM头的流读取
    //    FastByteArrayOutputStream 基于快速缓冲FastByteBuffer的OutputStream，随着数据的增长自动扩充缓冲区（from blade）
    //    FastByteBuffer 快速缓冲，将数据存放在缓冲集中，取代以往的单一数组（from blade）

    /**
     * IOUtils的使用
     */
    public void ioUtilsTest() throws IOException {
        String filePath = "E:\\Cloud\\java-cloud\\demo-hutool\\src\\main\\resources\\files\\test.txt";
        String newFilePath = "E:\\Cloud\\java-cloud\\demo-hutool\\src\\main\\resources\\files\\test-new.txt";
        //拷贝
        BufferedInputStream inputStream = FileUtil.getInputStream(filePath);
        BufferedOutputStream outputStream = FileUtil.getOutputStream(newFilePath);
        BufferedReader reader = FileUtil.getReader(filePath, Charset.defaultCharset());
        BufferedWriter writer = FileUtil.getWriter(newFilePath, Charset.defaultCharset(), false);
        IoUtil.copy(inputStream,outputStream,IoUtil.DEFAULT_BUFFER_SIZE);
        //读取

        //写入
    }

    public static void main(String[] args) throws IOException {
        IOTest ioTest = new IOTest();
        ioTest.ioUtilsTest();
    }
}
