package com.zukxu.mybatis.inserts.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author xupu
 * @since 2022/8/29 10:20:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "sys_user")
public class SysUser {

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 部门id
     */
    @TableField(value = "dept_id")
    private String deptId;

    /**
     * 登录用户
     */
    @TableField(value = "login_name")
    private String loginName;

    /**
     * 用户账号
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 密码
     */
    @TableField(value = "`password`")
    private String password;

    /**
     * 性别(字典 1男 2女 3未知)
     */
    @TableField(value = "sex")
    private String sex;

    /**
     * 用户邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 手机号码
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 状态（字典 0正常 1冻结）
     */
    @TableField(value = "`status`")
    private String status;

    /**
     * 逻辑删除(0存在 1删除)
     */
    @TableField(value = "del_flag")
    private String delFlag;

    /**
     * 创建者
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 用户级别 1、省 2、市 3、区县、4、网格
     */
    @TableField(value = "`level`")
    private String level;

    /**
     * 移动4A账号
     */
    @TableField(value = "yd4a_account")
    private String yd4aAccount;

    /**
     * 省OA账号
     */
    @TableField(value = "oa_account")
    private String oaAccount;

    /**
     * 多终端账号
     */
    @TableField(value = "crm_account")
    private String crmAccount;

}