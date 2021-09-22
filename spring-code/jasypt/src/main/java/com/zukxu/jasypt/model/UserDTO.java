package com.zukxu.jasypt.model;

import com.zukxu.jasypt.annotations.EncryptField;
import lombok.Data;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-22 15:35
 */
@Data
public class UserDTO {
    private Long userId;

    @EncryptField
    private String mobile;

    @EncryptField
    private String address;

    private int age;
}