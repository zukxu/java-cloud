package com.zukxu.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zukxu.shiro.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-23 17:48
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Role> {
    List<String> findByRoleIds(@Param("roleIds") List<Integer> roleIds);
}