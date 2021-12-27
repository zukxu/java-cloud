package com.zukxu.shiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zukxu.shiro.entity.Permission;

import java.util.List;
import java.util.Set;

/**
  * ${END}
  *
  * @author zukxu
  * CreateTime: 2021/4/22 0022 17:17
  * 
 */
public interface PermissionService extends IService<Permission>{

	public Set<String> findByRoleIds(List<Integer> roleIds);
}
