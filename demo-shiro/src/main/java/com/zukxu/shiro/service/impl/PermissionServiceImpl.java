package com.zukxu.shiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zukxu.shiro.entity.Permission;
import com.zukxu.shiro.mapper.PermissionMapper;
import com.zukxu.shiro.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * ${END}
 *
 * @author zukxu
 * CreateTime: 2021/4/22 0022 17:17
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

	@Autowired
	private PermissionMapper permissionMapper;

	@Override
	public Set<String> findByRoleIds(List<Integer> roleIds) {
		return permissionMapper.findByRoleIds(roleIds);
	}
}
