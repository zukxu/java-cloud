package com.zukxu.securityjwt.controller;

import com.zukxu.securityjwt.entity.LoginUser;
import com.zukxu.securityjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xupu
 * @Description
 * @Date 2021-10-13 17:11
 */
@RestController
public class JwtController {

    @Autowired
    private UserService authService;

    // 登录
    @PostMapping(value = "/authentication/login")
    public String createToken(@RequestBody LoginUser loginUser) throws AuthenticationException {
        return authService.login(loginUser.getUsername(), loginUser.getPassword());
    }
}
