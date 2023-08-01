package com.zukxu.shiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zukxu.shiro.entity.Role;

import java.util.List;

/**
 * ${END}
 *
 * @author zukxu
 * CreateTime: 2021/4/22 0022 17:17
 */
public interface RoleService extends IService<Role> {

    public List<Role> findRoleByUserId(Integer id);
}
