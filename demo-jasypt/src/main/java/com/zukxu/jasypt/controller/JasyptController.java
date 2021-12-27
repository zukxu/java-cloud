package com.zukxu.jasypt.controller;

import com.alibaba.fastjson.JSON;
import com.zukxu.jasypt.annotations.EncryptField;
import com.zukxu.jasypt.annotations.EncryptMethod;
import com.zukxu.jasypt.constant.EncryptConstant;
import com.zukxu.jasypt.model.UserDTO;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-22 15:56
 */
@RestController
@RequestMapping("/jasypt")
public class JasyptController {
    @Autowired
    StringEncryptor encryptor;

    @GetMapping("encrypt")
    public String encrypt(String password) {
        System.out.println("加密：" + password);
        return encryptor.encrypt(password);
    }

    @GetMapping("decrypt")
    public String decrypt(String password) {
        System.out.println("解密：" + password);
        return encryptor.decrypt(password);
    }

    @EncryptMethod
    @PostMapping(value = "testEn")
    public Object testEncrypt(@RequestBody UserDTO user, @EncryptField String name) {
        return insertUser(user, name);
    }

    @EncryptMethod(type = EncryptConstant.DECRYPT)
    @PostMapping(value = "testDen")
    private UserDTO testDecrypt(String name) {
        UserDTO userByName = getUserByName(name);
        return userByName;
    }

    private UserDTO insertUser(UserDTO user, String name) {
        System.out.println("加密后的数据：user" + JSON.toJSONString(user));
        return user;
    }

    private UserDTO getUserByName(String name) {
        UserDTO dto = new UserDTO();
        dto.setUserId(10001L);
        dto.setMobile("bX7Zxbzv3r/N7Ky+/Wl0qNsRKxmDJGXColmczJ2WQp9ZjJZOrY3X9/zt7X3ZKQYZ");
        dto.setAddress("asd");
        dto.setAge(22);
        System.out.println("数据库获取数据：user" + JSON.toJSONString(dto));
        return dto;
    }
}
