package com.zukxu.test.postman.service;

import org.springframework.stereotype.Service;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-29 11:25
 */
@Service
public class HelloService {
    public String hello() {
        return "业务逻辑：";
    }
}
