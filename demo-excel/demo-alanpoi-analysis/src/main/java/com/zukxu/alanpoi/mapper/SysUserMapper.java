package com.zukxu.alanpoi.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zukxu.alanpoi.model.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * ${END}
 * </p>
 *
 * @author xupu
 * @since 2022/8/29 10:20:05
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select(" select id, login_name, user_name, password, phone, yd4a_account from sys_user ${ew.customSqlSegment}")
    List<SysUser> scanMapper(@Param(Constants.WRAPPER) QueryWrapper<SysUser> wrapper);

}