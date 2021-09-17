package com.zukxu.common.utils.file;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author xupu
 * @Description 文件类型校验
 * @Date 2021-09-17 9:42
 */
public class FileTypeCheck {

    /**
     * 校验文件类型是否匹配
     *
     * @param fileSuffix 文件后缀名
     * @param typeArr    FileType
     * @return bool
     */
    public static boolean isMatching(String fileSuffix, String[] typeArr) {
        if (fileSuffix == null || fileSuffix.length() == 0) return false;
        if (typeArr == null || typeArr.length == 0) {
            return Arrays.asList(FileType.ALL_TYPE).contains(fileSuffix);
        }
        return Arrays.asList(typeArr).contains(fileSuffix.toLowerCase());
    }

    /**
     * 根据默认文件类型校验文件是否匹配
     *
     * @param fileSuffix 文件名
     * @return bool
     */
    public static boolean isImage(String fileSuffix) {
        if (fileSuffix == null || fileSuffix.length() == 0) return false;
        return Arrays.asList(FileType.IMG_TYPE).contains(fileSuffix);
    }

    /**
     * 校验文件是不是修改后缀名的图片
     *
     * @param file 文件
     * @return bool
     */
    public static boolean isImage(File file) {
        if (file == null) {
            return false;
        }
        try {
            Image image = ImageIO.read(file);
            return image != null;
        } catch (IOException ex) {
            return false;
        }
    }

}
