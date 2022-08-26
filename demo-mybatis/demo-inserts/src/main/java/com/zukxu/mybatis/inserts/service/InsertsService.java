package com.zukxu.mybatis.inserts.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zukxu.mybatis.inserts.model.DemoMybatisInserts;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/8/25 10:30:58
 */
public interface InsertsService extends IService<DemoMybatisInserts> {

    List<DemoMybatisInserts> list(Integer limit) throws IOException;
    void export(DemoMybatisInserts demoMybatisInserts) throws IOException;
}
