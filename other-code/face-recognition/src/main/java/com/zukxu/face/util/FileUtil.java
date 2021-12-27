package com.zukxu.face.util;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * file util
 *
 * @author zukxu
 * CreateTime: 2021/5/19 0019 22:55
 */
public class FileUtil {
    /**
     * 转换为base64
     *
     * @param multipartFile multipartFile
     * @return base64
     */
    public static String getImageBase64(MultipartFile multipartFile) {
        try {
            BASE64Encoder encoder = new BASE64Encoder();
            // 通过base64来转化图片
            return encoder.encode(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
