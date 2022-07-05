package com.zukxu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xupu
 * @description
 * @date 2021/9/25 22:44:41
 */
@RestController
@RequestMapping("/demo1")
@RequiredArgsConstructor
public class HelloController {

    //@formatter:off
    private final HelloService helloService;
    //@formatter:on

    /**
     * 标识该接口只能被有着ADMIN角色的用户访问
     */
    @GetMapping("/hello")
    @PreAuthorize("hasRole('ADMIN')")
    public String hello() {
        return helloService.hello();
    }

    @GetMapping("/success")
    public String success() {
        return helloService.success();
    }

    @GetMapping("/fail")
    public String fail() {
        return helloService.fail();
    }

    @GetMapping("/admin/hello")
    public String admin(String text) {
        return helloService.adminHello(text);
    }

    @GetMapping("/user/hello")
    public String user() {
        return helloService.userHello();
    }

    @GetMapping("/rememberme")
    public String rememberMe() {
        return helloService.rememberMe();
    }

}
