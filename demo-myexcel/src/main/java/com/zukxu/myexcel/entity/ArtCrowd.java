package com.zukxu.myexcel.entity;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import com.github.liaochong.myexcel.core.annotation.ExcelModel;

import java.time.LocalDateTime;

@ExcelModel(sheetName = "艺术生", useFieldNameAsTitle = true)
//自定义多级头的分隔符
//@ExcelModel(sheetName = "艺术生", useFieldNameAsTitle = true,titleSeparator = "##")
//默认单元格内自动换行
//@ExcelModel(wrapText=true)
public class ArtCrowd extends People {

    @ExcelColumn(order = 3, index = 3)
    private String paintingLevel;

    @ExcelColumn(order = 4, title = "是否会跳舞", groups = { People.class, String.class }, index = 4)
    private boolean dance;

    @ExcelColumn(order = 5, title = "考核时间", groups = { People.class, String.class }, index = 5)
    //多级表头设置
    //@ExcelColumn(title = "拓展信息->考核时间", index = 5)
    private LocalDateTime assessmentTime;

    @ExcelColumn(order = 6, defaultValue = "---")
    private String hobby;

    //@ExcelColumn(fileType = FileType.IMAGE)
    //private File image;
    //private InputStream image;

    //@ExcelColumn(linkType= LinkType.URL)
    //private String hyperlink;
    //@ExcelColumn(title="下拉列表")
    //private List<String> dropDownList;
    //@ExcelColumn(title="性别",mapping="0:男,1:女")
    //private String sex;
    //@ExcelColumn(writeConverter = MyConverter.class)
    //private String dept;
    //@ExcelColumn(prompt = @Prompt(title = "提示", text = "这是我的提示哦"))
    //Long cats;

    public String getPaintingLevel() {
        return paintingLevel;
    }

    public void setPaintingLevel(String paintingLevel) {
        this.paintingLevel = paintingLevel;
    }

    public boolean isDance() {
        return dance;
    }

    public void setDance(boolean dance) {
        this.dance = dance;
    }

    public LocalDateTime getAssessmentTime() {
        return assessmentTime;
    }

    public void setAssessmentTime(LocalDateTime assessmentTime) {
        this.assessmentTime = assessmentTime;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

}