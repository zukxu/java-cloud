package com.zukxu.shiro.config;

import com.zukxu.shiro.config.realm.UserRealm;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-23 17:45
 */
@Configuration
public class ShiroConfig {
    //1、配置realm Bean生成
    @Bean("userRealm")
    public UserRealm userRealm() {
        return new UserRealm();
    }

    //2、配置mgt
    @Bean("defaultSecurityManager")
    public DefaultSecurityManager defaultSecurityManager() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(userRealm());
        return securityManager;
    }

    //3、配置过滤链
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultSecurityManager defaultSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultSecurityManager);
        //配置登录
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/");

        //配置权限
        Map<String, String> map = new HashMap<>();
        // 有先后顺序
        map.put("/login", "anon"); // 允许匿名访问
        map.put("/**", "authc"); // 进行身份认证后才能访问
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    //4、可选，配置开启shiro注解模式，可在controller中开启注解模式
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("defaultSecurityManager") DefaultSecurityManager defaultSecurityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(defaultSecurityManager);
        return advisor;
    }

}
