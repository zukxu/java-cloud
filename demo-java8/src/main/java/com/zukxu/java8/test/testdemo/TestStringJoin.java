package com.zukxu.java8.test.testdemo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-08 9:37
 */
public class TestStringJoin {
    private static final String GZ_PROV = "851";
    private static String lineSplit = "\\|";
    private static Map<String, String> eciMap_sw = new HashMap<>();
    private static Map<String, String> phone_provMap = new HashMap<>();

    private static SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMddHHmm");
    private static SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");

    public static void main(String[] args) {
        String city = "851";
        String dataConfig = "test02|1-28863105,1-28836993,...|0|null|100,200,270" +
                            "~" +
                            "test01|1-28863105,1-28836993,...|0|851,854,852|null" +
                            "~" +
                            "test03|1-28863105,1-28836993,...|0|851,854,852|100.200.270" +
                            "~" +
                            "test04|1-28863105,1-28836993,...|0|null|null";

        String[] dataArray = dataConfig.split("~", -1);
        for (String data : dataArray) {
            String[] snw_conf_data = data.split(lineSplit, -1);

            System.out.println(data);
            String id = snw_conf_data[0];
            String lac_eci = snw_conf_data[1];
            String no_repeat_day = snw_conf_data[2];
            String snCity = snw_conf_data[3];
            System.out.println(("null").equals(snCity));
            String swCity = snw_conf_data[4];
            System.out.println(("null").equals(swCity));
            if (("null").equalsIgnoreCase(snCity) || snCity.contains(city)) {
                System.out.println("省内业务逻辑");
            } else if (("null").equalsIgnoreCase(swCity) || swCity.contains(city)) {
                System.out.println("省外业务逻辑");
            }

        }
        /*
        readSwConfigs();
        readProvText();
        mapHandler();*/
    }

    private static void mapHandler() {
        //获取用户号码
        String phoneNum = "13414071245";
        String pho_rough = "1341407";
        //lac_eci格式：1-144435
        String lacEci = "1-2863105";
        //根据pho获取value=prov+"|"+city
        String temp = phone_provMap.get(pho_rough);
        //获取前三位作为省份代码
        String prov = temp.substring(0, 3);
        //获取|后面的所有字符串为city
        String city = temp.substring(4);

        System.out.println(prov);
        System.out.println(city);

        for (String mapKey : eciMap_sw.keySet()) {
            String[] str = mapKey.split(lineSplit);
            String id = str[0];
            String[] Eci = str[1].split(",");

            String value = eciMap_sw.get(mapKey);
            String[] mapValues = value.split(lineSplit, -1);
            String no_repeat_day = mapValues[0];
            String swCity = mapValues[1];
            System.out.println(swCity.length());
            long old_date_seconds = new Date().getTime() - Long.parseLong(no_repeat_day) * 24 * 60 * 60 * 1000;
            String old_date = formatter2.format(old_date_seconds);
            System.out.println("获取省外码表信息2---" + phoneNum + "|" + id + "|" + Eci[0] + "|" + no_repeat_day + "|" + old_date + "|" + "date");

            swJudgeService();
        }

    }

    private static void readProvText() {
        String line = "1341407|200|754~1342151|851|851";
        String[] arr = line.split("~");
        for (String s : arr) {
            String[] strArr = s.split(lineSplit);
            String roughly = strArr[0];
            String prov = strArr[1];
            //获取到码表中的city列
            String city = strArr[2];
            phone_provMap.put(roughly, prov + "|" + city);
            System.out.println("phone_provMap======" + roughly + ": " + prov + "|" + city);
        }
    }

    private static void readSwConfigs() {
        //1-28863105,1-28836993,1-168703107,1-29132930,1-168744578,1-168738434,1-168738433,1-168694658,1-168694657,1-29124739,1-93727618,1-93747587,1-28861313,34266-8011,34266-8012,34193-48371,34193-1538,34193-48382,34266-28051,34266-57121,33937-61881,33937-61882,33937-61883,33937-5328,33937-5329
        String swconfigs = "test02" +
                           "|" +
                           "1-28863105,1-28836993,1-168703107" +
                           "|" +
                           "0" +
                           "|" +
                           "" +
                           "~" +
                           "test03" +
                           "|" +
                           "1-28863105,1-28836993,1-168703107,1-29132930,1-168744578,1-168738434,1-168738433,1-168694658,1-168694657,1-29124739" +
                           "|" +
                           "0" +
                           "|" +
                           "200,270,660";
        String[] swconfig = swconfigs.split("~");
        for (String swc : swconfig) {
            String[] conf_data = swc.split("\\|", -1);
            String id = conf_data[0];
            String lac_eci = conf_data[1];
            String no_repeat_day = conf_data[2];
            String swCity = conf_data[3];
            eciMap_sw.put(id + "|" + lac_eci, no_repeat_day + "|" + swCity);
            System.out.println("eciMap_sw======" + id + "|" + lac_eci + ": " + no_repeat_day + "|" + swCity);
        }

    }

    private static void swJudgeService() {
        System.out.println("逻辑处理");
    }

}
