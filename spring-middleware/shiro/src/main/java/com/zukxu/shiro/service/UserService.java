package com.zukxu.shiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zukxu.shiro.entity.User;

/**
 * ${END}
 *
 * @author zukxu
 * CreateTime: 2021/4/22 0022 17:17
 */
public interface UserService extends IService<User> {

	public User findByAccount(String account);
}
