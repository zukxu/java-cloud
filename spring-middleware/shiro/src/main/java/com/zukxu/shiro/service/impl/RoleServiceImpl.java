package com.zukxu.shiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zukxu.shiro.entity.Role;
import com.zukxu.shiro.mapper.RoleMapper;
import com.zukxu.shiro.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
  * ${END}
  *
  * @author zukxu
  * CreateTime: 2021/4/22 0022 17:17
  * 
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService{
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public List<Role> findRoleByUserId(Integer id) {
		return roleMapper.findRoleByUserId(id);
	}
}
