package com.zukxu.mybatis.inserts.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zukxu.mybatis.inserts.model.DemoMybatisInserts;
import org.apache.ibatis.annotations.Param;

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

}