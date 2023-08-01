package com.zukxu.mybatis.flex.entity;

import com.mybatisflex.annotation.ColumnMask;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.mask.Masks;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * sys_user
 * </p>
 *
 * @author xupu
 * @since 2023/7/26 14:53:12
 */

@Data
@Table(value = "sys_user")//使用 @Table("tb_account") 设置实体类与表名的映射关系
public class SysUserEntity {
    /**
     * 用户ID
     */
    @Id(value = "id", keyType = KeyType.Auto)//使用 @Id(keyType = KeyType.Auto) 标识主键且为自增
    private String id;

    /**
     * 部门id
     */
    private String deptId;

    /**
     * 登录用户
     */
    private String loginName;

    /**
     * 用户账号
     */
    @ColumnMask(Masks.CHINESE_NAME)
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别(字典 1男 2女 3未知)
     */
    private String sex;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
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
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
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
    private String yd4aAccount;

    /**
     * 省OA账号
     */
    private String oaAccount;

    /**
     * 多终端账号
     */
    private String crmAccount;
}