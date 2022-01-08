package com.zukxu.myexcel.utils;

import com.zukxu.myexcel.entity.ArtCrowd;
import com.zukxu.myexcel.entity.People;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MyExcelUtils {

    public static List<ArtCrowd> getDataList() {
        List<ArtCrowd> dataList = new ArrayList<>(1000);
        for(int i = 0; i < 1000; i++) {
            ArtCrowd artCrowd = new ArtCrowd();
            if(i % 2 == 0) {
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
    public static List<People> getData(){
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

}
