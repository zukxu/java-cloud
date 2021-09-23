package com.zukxu.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zukxu.shiro.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-23 17:58
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 通过用户名查询用户信息
     *
     * @param account
     * @return
     */
    User findByAccount(@Param("account") String account);
}