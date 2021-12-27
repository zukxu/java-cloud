package com.zukxu.shiro.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-23 18:05
 */
@Data
public class UserRole implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer roleId;

    private static final long serialVersionUID = 1L;
}