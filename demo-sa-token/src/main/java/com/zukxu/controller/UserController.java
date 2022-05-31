package com.zukxu.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.zukxu.common.config.aop.PostSingleParam;
import org.springframework.web.bind.annotation.*;

/**
 * @author xupu
 * @Date 2021-11-05 15:25
 */
@RestController
@RequestMapping("/user")
public class UserController {

    // 测试登录
    @PostMapping("/login")
    public String doLogin(@PostSingleParam String username, @PostSingleParam String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if("admin".equals(username) && "12345".equals(password)) {
            StpUtil.login("10001");
            return "登录成功";
        }
        return "登录失败";
    }

    // 查询登录状态
    @GetMapping("/isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }

    // 查询 Token 信息
    @GetMapping("/tokenInfo")
    public SaResult tokenInfo() {
        return SaResult.data(StpUtil.getTokenInfo());
    }

    // 测试注销
    @GetMapping("/logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }

}