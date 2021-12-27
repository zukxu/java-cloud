package com.zukxu.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zukxu.shiro.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-23 17:57
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 通过用户id查询角色
     *
     * @param userId
     * @return
     */
    List<Role> findRoleByUserId(@Param("userId") Integer userId);
}