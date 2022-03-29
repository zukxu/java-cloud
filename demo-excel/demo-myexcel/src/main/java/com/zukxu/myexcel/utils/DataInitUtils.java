package com.zukxu.myexcel.utils;

import com.zukxu.myexcel.entity.ArtCrowd;
import com.zukxu.myexcel.entity.People;
import com.zukxu.myexcel.entity.Product;
import com.zukxu.myexcel.entity.School;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试数据 生成类
 */
public class DataInitUtils {

    public static List<ArtCrowd> getArtCrowdDataList() {
        List<ArtCrowd> dataList = new ArrayList<>(1000);
        for (int i = 0; i < 1000; i++) {
            ArtCrowd artCrowd = new ArtCrowd();
            if (i % 2 == 0) {
                artCrowd.setName("Tom");
                artCrowd.setAge(19);
                artCrowd.setGender("Man");
                artCrowd.setPaintingLevel("一级证书");
                artCrowd.setDance(false);
                artCrowd.setAssessmentTime(LocalDateTime.now());
                artCrowd.setHobby(null);
            } else {
                artCrowd.setName("Marry");
                artCrowd.setAge(18);
                artCrowd.setGender("Woman");
                artCrowd.setPaintingLevel("一级证书");
                artCrowd.setDance(true);
                artCrowd.setAssessmentTime(LocalDateTime.now());
                artCrowd.setHobby("钓鱼");
            }
            dataList.add(artCrowd);
        }
        return dataList;
    }

    public static List<People> getPeopleDataList() {
        People testDO = new People();
        testDO.setName("张三");
        People testDO1 = new People();
        testDO1.setName("李四");

        People testDO2 = new People();
        testDO2.setName("王五");
        testDO2.setAge(15);
        People testDO3 = new People();
        testDO3.setName("陈六");
        testDO3.setAge(25);

        List<People> dataList = new ArrayList<>();
        dataList.add(testDO);
        dataList.add(testDO1);
        dataList.add(testDO2);
        dataList.add(testDO3);

        return dataList;
    }

    public static Map<String, Object> getProductDataMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("sheetName", "freemarker_excel_example");

        List<String> titles = new ArrayList<>();
        titles.add("Category");
        titles.add("Product Name");
        titles.add("Count");
        dataMap.put("titles", titles);

        List<Product> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            if (i % 2 == 0) {
                product.setCategory("蔬菜");
                product.setName("小白菜");
                product.setCount(100);
            } else {
                product.setCategory("电子产品");
                product.setName("ipad");
                product.setCount(999);
            }
            data.add(product);
        }
        dataMap.put("data", data);
        return dataMap;
    }

    public static List<ArtCrowd> getArtCrowdList() {
        List<ArtCrowd> dataList = new ArrayList<>(1000);
        List<Long> list = new ArrayList<>();
        list.add(134L);
        list.add(6456L);
        list.add(54354L);
        for (int i = 0; i < 100; i++) {
            ArtCrowd artCrowd = new ArtCrowd();
            if (i % 2 == 0) {
                artCrowd.setName("Tom");
                artCrowd.setAge(19);
                artCrowd.setGender("Man");
                artCrowd.setPaintingLevel("1");
                artCrowd.setDance(false);
                artCrowd.setAssessmentTime(LocalDateTime.now());
                artCrowd.setHobby(null);
            } else {
                artCrowd.setName("Marry");
                artCrowd.setAge(18);
                artCrowd.setGender("Woman");
                artCrowd.setPaintingLevel("一级");
                artCrowd.setDance(true);
                artCrowd.setAssessmentTime(LocalDateTime.now());
                artCrowd.setHobby("钓鱼");
            }
            dataList.add(artCrowd);
        }
        return dataList;
    }

    public static List<School> getSchoolDataList() {
        List<School> schoolList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            School school = new School();
            List<String> studentNameList = new ArrayList<>();

            school.setSchoolName(i + "中");
            studentNameList.add("xiao" + i);
            studentNameList.add("da" + i);
            school.setStudentNames(studentNameList);
            schoolList.add(school);
        }

        return schoolList;
    }

}
