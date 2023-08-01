package com.zukxu.mybatis.enums.mapper;

import com.zukxu.mybatis.enums.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * ${END}
 * </p>
 *
 * @author xupu
 * @since 2022/8/29 10:20:05
 */
@Mapper
public interface SysUserMapper {

    @Select("select * from sys_user where id=#{id}")
    SysUser getById(String id);
}