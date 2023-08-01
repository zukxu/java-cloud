package com.zukxu.myexcel.entity;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import com.github.liaochong.myexcel.core.annotation.MultiColumn;
import lombok.Data;

import java.util.List;

@Data
public class School {

    @ExcelColumn(title = "学校名称")
    String schoolName;

    @MultiColumn(classType = String.class)
    @ExcelColumn(title = "学生姓名")
    List<String> studentNames;

}