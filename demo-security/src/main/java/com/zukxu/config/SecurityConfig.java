package com.zukxu.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zukxu.jpa.service.UserService;
import com.zukxu.security.details.MyWebAuthenticationDetailsSource;
import com.zukxu.security.provider.MyAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author xupu
 * @description
 * @date 2021/9/25 23:21:14
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig<S extends Session> extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    UserService userService;

    @Autowired
    MyWebAuthenticationDetailsSource myWebAuthenticationDetailsSource;

    @Autowired
    private FindByIndexNameSessionRepository<S> sessionRepository;

    @Bean
    PasswordEncoder passwordEncoder() {
        //不需要加密
        return NoOpPasswordEncoder.getInstance();
        //return new BCryptPasswordEncoder();
    }

    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }
    /*@Override
    @Bean
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager detailsManager = new InMemoryUserDetailsManager();
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        if(!manager.userExists("admin")) {
            manager.createUser(User.withUsername("admin").password("123456").roles("admin").build());
        }
        if(!manager.userExists("user")) {
            manager.createUser(User.withUsername("user").password("123456").roles("user").build());
        }
        return manager;
    }*/

    @Bean
    MyAuthenticationProvider myAuthenticationProvider() {
        MyAuthenticationProvider myAuthenticationProvider = new MyAuthenticationProvider();
        myAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        myAuthenticationProvider.setUserDetailsService(userService);
        return myAuthenticationProvider;
    }

    @Bean
    JdbcTokenRepositoryImpl jdbcTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.inMemoryAuthentication()
            .withUser("admin")
            .password("123456")
            .roles("admin")
            .and()
            .withUser("user1")
            .password("123456")
            .roles("user");*/
        ArrayList<Object> list1 = new ArrayList<>();
        ArrayList<Object> list2 = new ArrayList<>();
        list1.retainAll(list2);
        auth.userDetailsService(userService);
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Collections.singletonList(myAuthenticationProvider()));
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/captcha")
            .permitAll()
            .antMatchers("/rememberme/**")
            .rememberMe()//该接口只有使用了remember登录才能访问
            .antMatchers("/admin/**")
            .fullyAuthenticated()//fullyAuthenticated 不同于 authenticated，fullyAuthenticated 不包含自动登录的形式，而
            // authenticated包含自动登录的形式。
            //.hasRole("admin")
            //.antMatchers("/user/**")
            //.hasRole("user")
            .anyRequest()
            .authenticated()
            .and() //所有请求都需要认证才能访问
            .formLogin()
            .authenticationDetailsSource(myWebAuthenticationDetailsSource)
            .permitAll()
            //.loginPage("/login.html")//配置登陆页面路径，如果不配置登录接口.loginProcessingUrl()，那么登录接口也是配置的登录页
            //.loginProcessingUrl("/doLogin")//配置登录接口
            //.usernameParameter("name")//配置用户名的参数名称
            //.passwordParameter("passwd")//配置密码的参数名称
            .successHandler((req, resp, auth) -> {//auth: 当前登录成功的用户信息
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter writer = resp.getWriter();
                writer.write(new ObjectMapper().writeValueAsString(auth.getPrincipal()));
                writer.flush();
                writer.close();
            })
            .failureHandler((req, resp, exception) -> {
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter writer = resp.getWriter();
                writer.write(new ObjectMapper().writeValueAsString(exception.getMessage()));
                writer.flush();
                writer.close();
            })
            //.successForwardUrl("/success")//登录成功后跳转的接口,服务端跳转
            //.defaultSuccessUrl("/success")//配置默认的成功跳转接口，链接是客户端跳转，和successForwardUrl只需配置一个即可
            //.failureForwardUrl("/fail")//登录失败后跳转的接口，服务端跳转
            //.failureUrl("/fail")//客户端跳转
            //.permitAll()//和登录相关的页面全部都放行
            //.and()
            //.logout()
            //.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))//配置logout的接口和请求方式, 默认是/logout和get请求
            //.logoutRequestMatcher(new AntPathRequestMatcher("/logout111", "GET"))//配置logout的接口和请求方式, 默认是/logout和get请求
            //.logoutSuccessUrl("/login.html")//注销后跳转的页面
            //.deleteCookies()//清除cookie
            //.invalidateHttpSession(true)//清除session,默认true
            //.clearAuthentication(true)//清除认证, 默认true
            //.logoutSuccessHandler((req, resp, exception) -> {
            //    resp.setContentType("application/json;charset=utf-8");
            //    PrintWriter writer = resp.getWriter();
            //    writer.write(new ObjectMapper().writeValueAsString("logout"));
            //    writer.flush();
            //    writer.close();
            //})
            .and().rememberMe().key("zukxu").tokenRepository(jdbcTokenRepository()).and()//添加记住我功能
            .csrf()
            //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())//前后端分离时，Cookies中返回_csrf参数
            .disable()//关闭csrf
            .cors().and()
            .sessionManagement()
            .maximumSessions(1)//配置最大session数为1,后登录会踢掉前一个
            .maxSessionsPreventsLogin(true)//已登录后不允许再登录
            .sessionRegistry(sessionRegistry())//前后端分离配置session共享
        ;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SpringSessionBackedSessionRegistry<S> sessionRegistry() {
        return new SpringSessionBackedSessionRegistry<>(this.sessionRepository);
    }

}
