package com.zukxu.test.others;


import cn.hutool.core.util.StrUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-04-07 9:43
 */
public class TestString {

    public static void main(String[] args) throws IOException {
        String file = "E:\\Cloud\\temp.txt";
        buildFunctionalRequirementsDocument(file);
    }

    /**
     * 构建功能需求文档
     */
    private static void buildFunctionalRequirementsDocument(String file) throws IOException {
        String tblTemplate = "<table border=\"1\">\n" +
                             "    <tr>\n" +
                             "        <td bgcolor=\"#d9d9d9\">用户场景</td>\n" +
                             "        <td style=\"width:400px\">T1</td>\n" +
                             "    </tr>\n" +
                             "    <tr>\n" +
                             "        <td bgcolor=\"#d9d9d9\">功能描述</td>\n" +
                             "        <td style=\"width:400px\">T2</td>\n" +
                             "    </tr>\n" +
                             "</table>";
        String key = "^";
        String startCode = "1.";
        System.out.println("# 1. 功能特性");

        String prefix = "## ";
        int second = 0, third = 0, fourth = 0;
        StringBuilder title = new StringBuilder(startCode);

        FileReader fr = new FileReader(file);
        BufferedReader bfr = new BufferedReader(fr);
        String line;
        while((line = bfr.readLine()) != null) {
            int count = getCount(line, key);
            line = line.replace(key, "");
            switch(count) {
                case 2:
                    second++;
                    third = 0;
                    title.append(second).append(".");
                    System.out.println(prefix + title + line);
                    break;
                case 3:
                    third++;
                    fourth = 0;
                    title.append(second).append(".").append(third).append(".");
                    prefix = "### ";
                    System.out.println(prefix + title + line);
                    break;
                case 4:
                    fourth++;
                    title.append(second).append(".").append(third).append(".").append(fourth).append(".");
                    prefix = "#### ";
                    System.out.println(prefix + title + line);
                    System.out.println(tblTemplate.replace("T1", line) + "\n");
                    break;
                default:
                    title = new StringBuilder(startCode);
                    break;
            }
            title = new StringBuilder(startCode);
        }
        fr.close();
        bfr.close();
    }

    /**
     * 获取某个字符的数量
     */
    public static int getCount(String str, String key) {
        if(StrUtil.isBlank(str) || StrUtil.isBlank(key)) {
            return 0;
        }
        int count = 0;
        int index = 0;
        while((index = str.indexOf(key, index)) != -1) {
            index = index + key.length();
            count++;
        }
        return count;
    }

}