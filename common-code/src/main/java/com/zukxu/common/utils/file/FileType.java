package com.zukxu.common.utils.file;

import com.zukxu.common.utils.array.ArrayUtils;

/**
 * @author xupu
 * @Description 文件类型
 * @Date 2021-09-17 9:51
 */
public class FileType {
    public static final String[] IMG_TYPE = {"jpg", "jpeg", "png", "webp", "bmp", "gif"};
    public static final String[] TXT_TYPE = {"text", "json", "xml", "yaml", "yml", "html", "properties"};
    public static final String[] AUDIO_TYPE = {"mp3"};
    public static final String[] VIDEO_TYPE = {"mp4"};
    public static final String[] FORBID_TYPE = {"exe", "bin"};
    public static final String[] ALL_TYPE = ArrayUtils.addAll(IMG_TYPE, TXT_TYPE, AUDIO_TYPE, VIDEO_TYPE);

}
