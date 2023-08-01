package com.zukxu.mybatis.enums.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zukxu.mybatis.enums.enums.LevelEnum;
import com.zukxu.mybatis.enums.enums.SexEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/6/25 10:06:56
 */
@Data
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser {

    private String userName;

    private String email;

    /**
     * IEnum接口的枚举处理
     */
    private SexEnum sex;

    /**
     * 原生枚举： 默认使用枚举值顺序： 0：NORMAL， 1：DELETED
     */
    //    private DelEnum delFlag;

    /**
     * 原生枚举（带{@link com.baomidou.mybatisplus.annotation.EnumValue}):
     * 数据库的值对应该注解对应的属性
     */
    private LevelEnum level;

}