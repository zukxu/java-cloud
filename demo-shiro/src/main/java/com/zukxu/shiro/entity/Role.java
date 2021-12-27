package com.zukxu.shiro.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-23 17:47
 */
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = -1767327914553823741L;
    private Integer id;
    private String role;
    private String desc;
}