package com.zukxu.other.pinyin4j.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author xupu
 * @Description 工具类
 * @Date 2021-09-22 10:21
 */
public class PinYinUtils {

    /**
     * 字符串转拼音
     *
     * @param code
     * @return
     */
    public Map<String, StringBuffer> string2Pinyin(String code) {
        //全拼
        StringBuffer pinyinAllStr = new StringBuffer();
        //首字母
        StringBuffer pinyinFirstStr = new StringBuffer();
        //将字符串转换为字符数组
        char[] newChar = code.toCharArray();
        //定义转换拼音格式
        HanyuPinyinOutputFormat defaultFormat = pinyinConfig();
        //设置分隔符
        String regex = "~";
        int allLen = pinyinAllStr.length();
        for (char c : newChar) {
            if (c > 128) {
                try {
                    //获得单个汉字全部拼音（多音字）
                    String[] pyArr = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
                    if (pyArr.length == 0) {
                        pinyinAllStr = getAllPinyin(pyArr, pinyinAllStr, regex, String.valueOf(c));
                        pinyinFirstStr = getFirstPinyin(pyArr, pinyinFirstStr, regex, String.valueOf(c));
                    } else {
                        pinyinAllStr = getAllPinyin(pyArr, pinyinAllStr, regex, null);
                        pinyinFirstStr = getFirstPinyin(pyArr, pinyinFirstStr, regex, null);
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinAllStr.append(c);
                pinyinFirstStr.append(c);
            }
        }
        HashMap<String, StringBuffer> map = new HashMap<>();
        map.put("all", pinyinAllStr);
        map.put("first", pinyinFirstStr);
        return map;
    }

    /**
     * 配置拼音格式
     *
     * @return HanyuPinyinOutputFormat
     */
    private HanyuPinyinOutputFormat pinyinConfig() {
        HanyuPinyinOutputFormat configFormat = new HanyuPinyinOutputFormat();
        configFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        configFormat.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
        configFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        return configFormat;
    }

    /**
     * 获取汉字的首字母
     *
     * @param pyArr          汉字拼音
     * @param pinyinFirstStr 已经存在的首字母
     * @param regex          分隔符
     * @param c
     * @return 首字母
     */
    private StringBuffer getFirstPinyin(String[] pyArr, StringBuffer pinyinFirstStr, String regex, String c) {
        //单音字直接转换存储
        StringBuffer temp = new StringBuffer();
        String[] firstPy = new String(pinyinFirstStr).split(regex);
        if (pyArr.length == 0 && c != null) {
            for (String s : firstPy) {
                temp.append(s.trim()).append(c).append(regex);
            }
        } else if (pyArr.length == 1) {
            //单音字
            for (String s : firstPy) {
                temp.append(s).append(pyArr[0].toUpperCase(Locale.ROOT).charAt(0)).append(regex);
            }
        } else {
            //多音字
            for (String s : firstPy) {
                for (String py : pyArr) {
                    temp.append(s).append(py.toUpperCase(Locale.ROOT).charAt(0)).append(regex);
                }
            }
        }

        return temp;
    }

    /**
     * 获取多音字的拼音组合
     *
     * @param pyArr        汉字拼音
     * @param pinyinAllStr 已经存在的全拼
     * @param regex        分隔符
     * @param c
     * @return 全拼
     */
    private StringBuffer getAllPinyin(String[] pyArr, StringBuffer pinyinAllStr, String regex, String c) {
        //单音字直接转换存储
        StringBuffer temp = new StringBuffer();
        String[] allPy = new String(pinyinAllStr).split(regex);
        //字符
        if (pyArr.length == 0 && c != null) {
            for (String s : allPy) {
                temp.append(s.trim()).append(c).append(regex);
            }
        } else if (pyArr.length == 1) {
            //单音字
            for (String s : allPy) {
                temp.append(s).append(pyArr[0]).append(" ").append(regex);
            }
        } else {
            //多音字
            for (String s : allPy) {
                for (String py : pyArr) {
                    temp.append(s).append(py).append(" ").append(regex);
                }
            }
        }
        return temp;
    }

    /**
     * 获取汉字串拼音首字母，英文字符不变
     *
     * @param chinese 汉字串
     * @return 汉语拼音首字母
     */
    public static String getFirstSpell(String chinese) {
        // 用StringBuffer（字符串缓冲）来接收处理的数据
        StringBuffer sb = new StringBuffer();
        // 字符串转换为字截数组
        char[] arr = chinese.toCharArray();
        // 创建转换对象
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 转换类型（大写or小写）
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // 定义中文声调的输出格式
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            // 判断是否是汉子字符
            if (arr[i] > 128) {
                try {
                    // 提取汉字的首字母
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                    if (temp != null) {
                        sb.append(temp[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                // 如果不是汉字字符，直接拼接
                sb.append(arr[i]);
            }
        }
        return sb.toString().replaceAll("\\W", "").trim();
    }

    /**
     * 将字符串中的中文转化为拼音,其他字符不变
     *
     * @param inputString
     * @return 汉语拼音
     */
    public static String getPingYin(String inputString) {

        // 创建转换对象
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        // 转换类型（大写or小写）
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // 定义中文声调的输出格式
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        // 定义字符的输出格式
        format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);

        // 转换为字节数组
        char[] input = inputString.trim().toCharArray();
        // 用StringBuffer（字符串缓冲）来接收处理的数据
        StringBuffer output = new StringBuffer();

        try {
            for (int i = 0; i < input.length; i++) {
                // 判断是否是一个汉子字符
                if (Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                    output.append(temp[0]);
                } else {
                    // 如果不是汉字字符，直接拼接
                    output.append(input[i]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output.toString().toUpperCase();
    }

    /**
     * 获取汉字串拼音，英文字符不变 【首字母大写】
     *
     * @param chinese 汉字串
     * @return 汉语拼音
     */
    public static String getFullSpell(String chinese) {
        // 用StringBuffer（字符串缓冲）来接收处理的数据
        StringBuffer sb = new StringBuffer();
        // 字符串转换字节数组
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 转换类型（大写or小写）
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // 定义中文声调的输出格式
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        // 定义字符的输出格式
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
        for (int i = 0; i < arr.length; i++) {
            // 判断是否是汉子字符
            if (arr[i] > 128) {
                try {
                    sb.append(capitalize(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]));
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                // 如果不是汉字字符，直接拼接
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 将字符串转换成ASCII码
     *
     * @param cnStr
     * @return String
     */
    public static String getCnASCII(String cnStr) {
        StringBuilder strBuf = new StringBuilder();
        // 将字符串转换成字节序列
        byte[] bib = cnStr.getBytes();
        for (byte b : bib) {
            // 将每个字符转换成ASCII码
            strBuf.append(Integer.toHexString(b & 0xff));
        }
        return strBuf.toString();
    }

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String capitalize(String str) {
        char[] ch;
        ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        String newString = new String(ch);
        return newString;
    }
}
