package com.zukxu.shiro.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-23 17:53
 */
@Data
public class Permission implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String permission;
    private String desc;
}