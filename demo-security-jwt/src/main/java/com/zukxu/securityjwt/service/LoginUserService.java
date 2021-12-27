package com.zukxu.securityjwt.service;

import com.zukxu.securityjwt.entity.LoginUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author xupu
 * @Description
 * @Date 2021-10-13 16:01
 */
@Service
public class LoginUserService implements UserDetailsService {

    @Override
    public LoginUser loadUserByUsername(String username) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(1L);
        loginUser.setUserName("admin");
        loginUser.setPassword("123456");
        return loginUser;
    }

}
