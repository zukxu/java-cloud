package com.zukxu.controller;

import com.zukxu.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/7/5 15:47:33
 */
@Service
@Slf4j
public class HelloService {

    /**
     * 只有ADMIN角色才能访问 严格区分大小写
     */
    @PreAuthorize("hasRole('ADMIN')")
    public String hello() {
        log.info("Hello");
        return "Hello";
    }

    /**
     * 访问者名称必须是 zukxu，而且还需要同时具备 ADMIN 角色，才可以访问该方法
     */
    @PreAuthorize("hasRole('ADMIN') and authentication.name=='zukxu'")
    public String hello2() {
        log.info("Hello zukxu");
        return "Hello zukxu";
    }

    /**
     * 表示请求者的用户名必须等于方法参数 name 的值，方法才可以被执行
     */
    @PreAuthorize("authentication.name==#name")
    public String hello3(String name) {
        log.info("Hello {}", name);
        return "hello:" + name;
    }

    /**
     * 对参数进行过滤，排除不满足条件的
     */
    @PreFilter(value = "filterObject.id%2!=0", filterTarget = "users")
    public void addUsers(List<SysUser> users, Integer other) {
        log.info("users {}", users);
        log.info("other {}", other);
    }

    /**
     * 对返回结果进行校验
     */
    @PostAuthorize("returnObject.id==1")
    public SysUser getUserById(Integer id) {
        log.info("id {}", id);
        return new SysUser(id, "zukxu");
    }

    /**
     * 对返回结果及逆行过滤
     */
    @PostFilter("filterObject.id%2==0")
    public List<SysUser> listUser() {
        List<SysUser> users = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            users.add(new SysUser(i, "zukxu:" + i));
        }
        return users;
    }

    /**
     * 满足ADMIN 和 USER 角色的才能访问
     */
    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    public SysUser getUserByUsername(String username) {
        log.info("username {}", username);
        return new SysUser(99, username);
    }

    public String success() {
        log.info("success");
        return "success";
    }

    public String fail() {
        log.info("fail");
        return "fail";
    }

    public String adminHello(String text) {
        log.info("admin welcome, param is {}", text);
        return "admin welcome, param is " + text;
    }

    public String userHello() {
        log.info("welcome user");
        return "welcome user";
    }

    public String rememberMe() {
        log.info("remember me");
        return "remember me success!";
    }

    private SysUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SysUser details = (SysUser) authentication.getDetails();
        log.info(String.valueOf(details));
        return details;
    }

}