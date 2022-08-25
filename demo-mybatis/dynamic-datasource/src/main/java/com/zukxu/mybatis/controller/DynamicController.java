package com.zukxu.mybatis.controller;

import com.zukxu.mybatis.mapper.SysUserMapper;
import com.zukxu.mybatis.mapper.SysUserMapper2;
import com.zukxu.mybatis.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/7/7 10:45:47
 */
@RestController
@RequestMapping("user")
public class DynamicController {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserMapper2 sysUserMapper2;

    @GetMapping("/test1")
    public List<SysUser> listUser1() {
        return sysUserMapper.listUser1();
    }

    @GetMapping("/test2")
    public List<SysUser> listUser2() {
        return sysUserMapper2.listUser2();
    }
}
