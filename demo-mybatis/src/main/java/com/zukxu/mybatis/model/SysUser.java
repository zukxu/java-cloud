package com.zukxu.mybatis.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author xupu
 * @since 2022/7/7 10:28:44
 */

@Data
@Builder
@TableName(value = "sys_user")
public class SysUser {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 工号
     */
    @TableField(value = "user_no")
    private String userNo;

    /**
     * 用户名
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 登录账号
     */
    @TableField(value = "login_name")
    private String loginName;

    /**
     * 性别 1标示男 0标示女  2未知
     */
    @TableField(value = "sex")
    private Integer sex;

    /**
     * 登录密码
     */
    @TableField(value = "`password`")
    private String password;

    /**
     * 手机
     */
    @TableField(value = "mobile")
    private String mobile;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 传真
     */
    @TableField(value = "fax")
    private String fax;

    /**
     * 地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 公司id
     */
    @TableField(value = "company_id")
    private String companyId;

    /**
     * 部门id
     */
    @TableField(value = "dep_id")
    private String depId;

    /**
     * 逻辑删除标识 0未删除,1已删除
     */
    @TableField(value = "del_flag")
    private Boolean delFlag;

    /**
     * 账号状态 0 正常 1 失效 2 过期
     */
    @TableField(value = "`state`")
    private Boolean state;

    /**
     * 账号失效时间
     */
    @TableField(value = "fail_time")
    private LocalDateTime failTime;

    /**
     * 密码失效日期
     */
    @TableField(value = "pwd_f_time")
    private LocalDateTime pwdFTime;

    /**
     * 初始密码是否已修改 1是0否
     */
    @TableField(value = "pwd_init")
    private Boolean pwdInit;

    /**
     * 数据权限（0当前岗位权限，1当前部门权限）
     */
    @TableField(value = "data_scope")
    private Boolean dataScope;

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

}