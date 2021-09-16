package com.zukxu.common.utils;

import cn.hutool.core.util.StrUtil;

/**
 * @author xupu
 * @Description 字符串工具类
 * @Date 2021-09-16 16:28
 */
public class StringUtils extends StrUtil {
    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }
}
