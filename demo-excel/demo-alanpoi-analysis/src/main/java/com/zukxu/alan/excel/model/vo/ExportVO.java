package com.zukxu.alan.excel.model.vo;

import com.alanpoi.analysis.common.enums.AlanColor;
import com.alanpoi.analysis.excel.annotation.ExcelColumn;
import com.alanpoi.analysis.excel.annotation.ExcelSheet;
import com.alanpoi.common.annotation.DateFormat;
import com.alanpoi.common.annotation.NumFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/7/18 17:18:52
 */
@ExcelSheet(name = "测试", backColor = AlanColor.GREEN, font = "宋体", fontSize = 25)
@Data
public class ExportVO {

    @ExcelColumn(name = "名称", width = 32, link = "${url}")
    private String name;

    @ExcelColumn(name = "值")
    private String value;

    @ExcelColumn(name = "金额")
    @NumFormat(value = "0000.00##")
    private BigDecimal amount;

    @ExcelColumn(name = "时间格式化")
    @DateFormat(value = "yyyy-MM-dd hh:mm:ss")
    private Date dateTime;

    @DateFormat
    @ExcelColumn(name = "日期格式化")
    private java.sql.Date date;

    @ExcelColumn(isExist = false)
    private String url;

}