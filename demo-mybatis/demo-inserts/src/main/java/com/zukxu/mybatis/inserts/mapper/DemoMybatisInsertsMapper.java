package com.zukxu.mybatis.inserts.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zukxu.mybatis.inserts.model.DemoMybatisInserts;
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
 * @since 2022/8/25 16:05:42
 */
public interface DemoMybatisInsertsMapper extends BaseMapper<DemoMybatisInserts> {

    int insertBatch(@Param("list") List<DemoMybatisInserts> record);

    @Select("select id, username, password, _no from demo_mybatis_inserts limit #{limit}")
    //ResultSetType.FORWARD_ONLY 表示游标只向前滚动    fetchSize 每次获取量
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1000)
    Cursor<DemoMybatisInserts> scan(@Param("limit") Integer limit);

    @Select("select id, username, password, _no from demo_mybatis_inserts ${ew.customSqlSegment}")
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1000)
    @ResultType(DemoMybatisInserts.class)
    void scanHandler(@Param(Constants.WRAPPER) QueryWrapper<DemoMybatisInserts> wrapper, ResultHandler<DemoMybatisInserts> handler);

    void scanMapper(@Param(Constants.WRAPPER) QueryWrapper<DemoMybatisInserts> wrapper,ResultHandler<DemoMybatisInserts> handler);
}