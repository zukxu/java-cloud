package com.zukxu.shiro.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-23 17:46
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -6056125703075132981L;
    private Integer id;
    private String account;
    private String password;
    private String username;
}