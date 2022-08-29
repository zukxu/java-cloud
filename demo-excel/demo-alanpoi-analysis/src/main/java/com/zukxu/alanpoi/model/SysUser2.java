package com.zukxu.alanpoi.model;

import com.alanpoi.analysis.common.enums.AlanColor;
import com.alanpoi.analysis.excel.annotation.ExcelColumn;
import com.alanpoi.analysis.excel.annotation.ExcelSheet;
import com.alanpoi.common.annotation.DateFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelSheet(name = "测试用户", backColor = AlanColor.GREEN, font = "宋体", fontSize = 25)
public class SysUser2 {

    /**
     * 用户ID
     */
    @ExcelColumn(name = "id", width = 32)
    private String id;

    /**
     * 部门id
     */
    @ExcelColumn(name = "部门id", width = 32)
    private String deptId;

    /**
     * 登录用户
     */
    @ExcelColumn(name = "登录用户", width = 32)
    private String loginName;

    /**
     * 用户账号
     */
    @ExcelColumn(name = "用户账号", width = 32)
    private String userName;

    /**
     * 密码
     */
    @ExcelColumn(name = "密码", width = 32)
    private String password;

    /**
     * 性别(字典 1男 2女 3未知)
     */
    private String sex;

    /**
     * 用户邮箱
     */
    @ExcelColumn(name = "用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @ExcelColumn(name = "手机号码")
    private String phone;

    /**
     * 状态（字典 0正常 1冻结）
     */
    private String status;

    /**
     * 逻辑删除(0存在 1删除)
     */
    private String delFlag;

    /**
     * 创建者
     */
    @ExcelColumn(name = "创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @DateFormat(value = "yyyy-MM-dd hh:mm:ss")
    @ExcelColumn(name = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @ExcelColumn(isExist = false)
    private String updateBy;

    /**
     * 更新时间
     */
    @ExcelColumn(isExist = false)
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户级别 1、省 2、市 3、区县、4、网格
     */
    private String level;

    /**
     * 移动4A账号
     */
    @ExcelColumn(name = "移动4A账号")
    private String yd4aAccount;

    /**
     * 省OA账号
     */
    @ExcelColumn(name = "省OA账号")
    private String oaAccount;

    /**
     * 多终端账号
     */
    @ExcelColumn(name = "多终端账号")
    private String crmAccount;

}

