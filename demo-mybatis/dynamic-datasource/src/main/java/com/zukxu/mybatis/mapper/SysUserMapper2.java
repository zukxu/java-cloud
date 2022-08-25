package com.zukxu.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zukxu.mybatis.dynamic.annotations.DataSource;
import com.zukxu.mybatis.model.SysUser;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * ${END}
 * </p>
 *
 * @author xupu
 * @since 2022/7/7 10:28:44
 */
public interface SysUserMapper2 extends BaseMapper<SysUser> {

    @DataSource("secondary")
    @Select("select * from sys_user_t")
    List<SysUser> listUser2();

}