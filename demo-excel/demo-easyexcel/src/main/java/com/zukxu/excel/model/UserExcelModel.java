package com.zukxu.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 用户Excel映射对象
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/2/6 0006 16:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserExcelModel implements Serializable {

    private static final long serialVersionUID = 2723059827514336242L;
    @ExcelProperty(value = "用户名", index = 0)
    private String name;
    @ExcelProperty(value = "年龄", index = 1)
    private Integer age;
    @ExcelProperty(value = "性别", index = 2)
    private String sex;
    @ExcelProperty(value = "手机号", index = 3)
    private String mobile;
}
