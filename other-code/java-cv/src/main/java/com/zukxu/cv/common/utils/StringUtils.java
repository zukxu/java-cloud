package com.zukxu.cv.common.utils;

import cn.hutool.core.util.StrUtil;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-27 15:34
 */
public class StringUtils extends StrUtil {
    public static String capitalize(final String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }

        final char firstChar = str.charAt(0);
        final char newChar = Character.toTitleCase(firstChar);
        if (firstChar == newChar) {
            return str;
        }

        char[] newChars = new char[strLen];
        newChars[0] = newChar;
        str.getChars(1, strLen, newChars, 1);
        return String.valueOf(newChars);
    }
}
