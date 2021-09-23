package com.zukxu.shiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zukxu.shiro.entity.Permission;

import java.util.List;

/**
  * ${END}
  *
  * @author zukxu
  * CreateTime: 2021/4/22 0022 17:17
  * 
 */
public interface PermissionService extends IService<Permission>{

	public List<String> findByRoleId(List<Integer> roleIds);
}
