package com.zukxu.shiro.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-23 18:04
 */
@Data
public class RolePermission implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer roleId;
    private Integer permissionId;
}