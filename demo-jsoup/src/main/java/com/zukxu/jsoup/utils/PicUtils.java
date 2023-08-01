package com.zukxu.jsoup.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;

/**
 * @author xupu
 * @Description 下载图片工具
 * @Date 2021-09-22 9:51
 */
public class PicUtils {
    private static final Logger log = LoggerFactory.getLogger(PicUtils.class);

    /**
     * 根据链接url下载图片
     *
     * @param sourceUrl 爬取的url
     * @param path      下载存放路径
     * @param name      下载存放名称
     */
    public void downloadPicture(String sourceUrl, String path, String name) {
        URL url = null;
        try {
            url = new URL(sourceUrl);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            File file = new File(path);
            if (!file.exists()) {
                if (!file.mkdirs())
                    log.error("文件创建失败");
            }
            file = new File(file + "/" + name);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
