package com.zukxu.mybatis.inserts.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zukxu.mybatis.inserts.model.SysUser;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;

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

    int insertBatch(@Param("list") List<SysUser> record);

    @Select("select id, login_name,user_name, password, phone,yd4a_account from sys_user limit #{limit}")
    //ResultSetType.FORWARD_ONLY 表示游标只向前滚动    fetchSize 每次获取量
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1000)
    Cursor<SysUser> scan(@Param("limit") Integer limit);

    @Select("select id, login_name,user_name, password, phone,yd4a_account from sys_user ${ew.customSqlSegment}")
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1000)
    @ResultType(SysUser.class)
    void scanHandler(@Param(Constants.WRAPPER) QueryWrapper<SysUser> wrapper, ResultHandler<SysUser> handler);

    void scanMapper(@Param(Constants.WRAPPER) QueryWrapper<SysUser> wrapper, ResultHandler<SysUser> handler);

}