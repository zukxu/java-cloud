package com.zukxu.security.service;

import com.zukxu.security.details.MyWebAuthenticationDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-26 16:09
 */
@Service
public class HelloService {
    public String hello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) authentication.getDetails();
        System.out.println(details);
        return details.toString();
    }
}
