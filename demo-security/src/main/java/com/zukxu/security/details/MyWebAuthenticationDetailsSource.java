package com.zukxu.security.details;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-26 15:59
 */
@Component
public class MyWebAuthenticationDetailsSource extends WebAuthenticationDetailsSource {
    @Override
    public MyWebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new MyWebAuthenticationDetails(context);
    }
}
