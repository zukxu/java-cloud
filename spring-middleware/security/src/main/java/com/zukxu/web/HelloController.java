package com.zukxu.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xupu
 * @description
 * @date 2021/9/25 22:44:41
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/success")
    public String success() {
        return "success, welcome!";
    }

    @RequestMapping("/fail")
    public String fail() {
        return "fail, try again!";
    }

    @GetMapping("/admin/hello")
    public String admin() {
        return "admin";
    }

    @GetMapping("/user/hello")
    public String user() {
        return "user";
    }
}
