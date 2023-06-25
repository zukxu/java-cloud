package com.zukxu.mybatis.enums;

import com.zukxu.mybatis.enums.entity.SysUser;
import com.zukxu.mybatis.enums.mapper.SysUserMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/6/25 10:28:20
 */
@SpringBootTest
public class TestEnums {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    void testSelect() {
        SysUser byId = sysUserMapper.getById("200001");
        System.out.println(byId);
    }

    @Test
    void testInsert() {
        SysUser sysUser = new SysUser();
    }

    @Test
    void testUpdate() {

    }

}
