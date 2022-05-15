package com.zukxu.test.others;

import com.alibaba.fastjson.JSON;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-04-07 9:43
 */
public class TestString {
    public static void main(String[] args) {
        String str = "process_research_audit:1:busType";
        String defKey = "process_research_audit";
        System.out.println(str.substring(str.lastIndexOf(":")+1));
        System.out.println(JSON.toJSONString(str.split(":")));
    }
}
