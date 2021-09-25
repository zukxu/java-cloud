package com.zukxu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        auth.inMemoryAuthentication().withUser("admin11").password("12345").roles("admin");
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and() //所有请求都需要认证才能访问
            .formLogin().loginPage("/login.html")//配置登陆页面路径，如果不配置登录接口.loginProcessingUrl()，那么登录接口也是配置的登录页
            .loginProcessingUrl("doLogin")//配置登录接口
            //.usernameParameter("name")//配置用户名的参数名称
            //.passwordParameter("pasd")//配置密码的参数名称
            .permitAll()//和登录相关的页面全部都放行
            .and().csrf().disable()//关闭csrf
        ;
    }
}
