package com.zukxu.other.pinyin4j;

import com.zukxu.other.pinyin4j.utils.PinYinUtils;

import java.util.Map;

/**
 * @author xupu
 * @Description pinyin4j
 * @Date 2021-09-22 10:20
 */
public class PinYin {
    public static void main(String[] args) {
        PinYinUtils pinYinUtils = new PinYinUtils();
        String str = "和平鸽";
        Map<String, StringBuffer> pinyin = pinYinUtils.string2Pinyin(str);
        System.out.println(pinyin.get("all").toString());
    }
}
