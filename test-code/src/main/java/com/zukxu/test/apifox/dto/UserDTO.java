package com.zukxu.test.apifox.dto;

import lombok.Data;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-29 11:48
 */
@Data
public class UserDTO {
    private String username;
    private String password;
    private String code;
    private String sex;
    private int age;
}
