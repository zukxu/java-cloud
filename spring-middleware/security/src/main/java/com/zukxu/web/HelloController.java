package com.zukxu.web;

import com.zukxu.security.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xupu
 * @description
 * @date 2021/9/25 22:44:41
 */
@RestController
public class HelloController {
    @Autowired
    HelloService helloService;

    @GetMapping("/hello")
    public String hello() {
        return helloService.hello();
    }

 /*   @RequestMapping("/success")
    public String success() {
        return "success, welcome!";
    }

    @RequestMapping("/fail")
    public String fail() {
        return "fail, try again!";
    }*/

    @GetMapping("/admin/hello")
    public String admin(String text) {
        return "admin: "+"传递的参数为: "+text;
    }

    @GetMapping("/user/hello")
    public String user() {
        return "user";
    }

    @GetMapping("/rememberme")
    public String rememberme() {
        return "rememberme";
    }

    /**
     * 模拟csrf攻击，模拟转账
     * @param name
     * @param money
     */
    @PostMapping("/transfer")
    public void transferMoney(String name, Integer money) {
        System.out.println("name = " + name);
        System.out.println("money = " + money);
    }

    @PostMapping("/csrf")
    @ResponseBody
    public String csrfTest() {
        return "csrf";
    }
    @GetMapping("/csrf")
    public String csrf() {
        return "csrf";
    }
}
