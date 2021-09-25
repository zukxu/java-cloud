package com.zukxu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author xupu
 * @description
 * @date 2021/9/25 23:21:14
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder passwordEncoder() {
        //不需要加密
        return NoOpPasswordEncoder.getInstance();
        //return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("123456").roles("admin");
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and() //所有请求都需要认证才能访问
            .formLogin().loginPage("/login.html")//配置登陆页面路径，如果不配置登录接口.loginProcessingUrl()，那么登录接口也是配置的登录页
            .loginProcessingUrl("/doLogin")//配置登录接口
            .usernameParameter("name")//配置用户名的参数名称
            .passwordParameter("passwd")//配置密码的参数名称
            //.successForwardUrl("/success")//登录成功后跳转的接口,服务端跳转
            .defaultSuccessUrl("/success")//配置默认的成功跳转接口，链接是客户端跳转，和successForwardUrl只需配置一个即可
            //.failureForwardUrl("/fail")//登录失败后跳转的接口，服务端跳转
            .failureUrl("/fail")//客户端跳转
            .permitAll()//和登录相关的页面全部都放行
            .and()
            .logout()
            //.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))//配置logout的接口和请求方式, 默认是/logout和get请求
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout111", "GET"))//配置logout的接口和请求方式, 默认是/logout和get请求
            .logoutSuccessUrl("/login.html")//注销后跳转的页面
            .deleteCookies()//清除cookie
            .invalidateHttpSession(true)//清除session,默认true
            .clearAuthentication(true)//清除认证, 默认true
            .and()
            .csrf().disable()//关闭csrf
        ;
    }
}
