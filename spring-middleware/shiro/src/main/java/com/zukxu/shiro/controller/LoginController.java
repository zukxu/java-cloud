package com.zukxu.shiro.controller;

import com.zukxu.common.result.R;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-24 10:59
 */
@RestController
@RequestMapping
public class LoginController {

    @PostMapping("/login")
    public R login(@RequestParam(value = "account") String account, @RequestParam(value = "password") String password) {
        Subject userSubject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        try {
            // 登录验证
            userSubject.login(token);
            return R.OK;
        } catch (UnknownAccountException e) {
            return R.fail(CommREnum.ACCOUNT_NOT_EXIST);
        } catch (DisabledAccountException e) {
            return R.fail(CommREnum.ACCOUNT_IS_DISABLED);
        } catch (IncorrectCredentialsException e) {
            return R.fail(CommREnum.INCORRECT_CREDENTIALS);
        } catch (Throwable e) {
            e.printStackTrace();
            return R.fail(CommREnum.FAIL);
        }
    }

    @GetMapping("/role")
    @RequiresRoles("vip")
    public String role() {
        return "测试Vip角色";
    }

    @GetMapping("/permission")
    @RequiresPermissions(value = {"add", "update"}, logical = Logical.AND)
    public String permission() {
        return "测试Add和Update权限";
    }

    @GetMapping("/logout")
    public String logOut() {
        Subject userSubject = SecurityUtils.getSubject();
        userSubject.logout();
        return "注销登陆";
    }
}