package com.zukxu.excel.model.satisfaction;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zukxu.excel.annotations.Excel;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2021-12-29 19:38
 */
@TableName("satisfaction_sn")
@Data
public class Sn5g {
    @Excel(name = "编码")
    @ExcelProperty(index = 1)
    private String var1;
    @Excel(name = "地州名称")
    @ExcelProperty(index = 2)
    private String var2;
    @Excel(name = "样本量")
    @ExcelProperty(index = 3)
    private String var3;
    @Excel(name = "手机客户满意度")
    @ExcelProperty(index = 4)
    private String var4;
    @Excel(name = "手机网络质量")
    @ExcelProperty(index = 5)
    private String var5;
    @Excel(name = "语音通话质量")
    @ExcelProperty(index = 6)
    private String var6;
    @Excel(name = "手机上网质量")
    @ExcelProperty(index = 7)
    private String var7;
    @Excel(name = "上网体验-视频业务")
    @ExcelProperty(index = 8)
    private String var8;
    @Excel(name = "上网体验-游戏业务")
    @ExcelProperty(index = 9)
    private String var9;
    @Excel(name = "手机资费套餐")
    @ExcelProperty(index = 10)
    private String var10;
    @Excel(name = "资费规则清晰度")
    @ExcelProperty(index = 11)
    private String var11;
    @Excel(name = "套餐设计合理性")
    @ExcelProperty(index = 12)
    private String var12;
    @Excel(name = "套餐适配度")
    @ExcelProperty(index = 13)
    private String var13;
    @Excel(name = "套餐办理便捷性")
    @ExcelProperty(index = 14)
    private String var14;
    @Excel(name = "套餐办理规范性")
    @ExcelProperty(index = 15)
    private String var15;
    @Excel(name = "账单服务")
    @ExcelProperty(index = 16)
    private String var16;
    @Excel(name = "触点服务质量")
    @ExcelProperty(index = 17)
    private String var17;
    @Excel(name = "整体感知")
    @ExcelProperty(index = 18)
    private String var18;
    @Excel(name = "推荐意愿")
    @ExcelProperty(index = 19)
    private String var19;
}
