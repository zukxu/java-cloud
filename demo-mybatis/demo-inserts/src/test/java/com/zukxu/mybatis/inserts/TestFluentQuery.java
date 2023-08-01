package com.zukxu.mybatis.inserts;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zukxu.mybatis.inserts.handler.DemoResultHandler;
import com.zukxu.mybatis.inserts.mapper.SysUserMapper;
import com.zukxu.mybatis.inserts.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 流式查询
 * </p>
 *
 * @author xupu
 * @since 2022/7/7 10:33:35
 */
@SpringBootTest
@Slf4j
public class TestFluentQuery {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    void testFluentQuery() {
        List<SysUser> list = new ArrayList<>();
        //1、SqlSession
        try (SqlSession sqlSession = sqlSessionFactory.openSession(); Cursor<SysUser> cursor = sqlSession.getMapper(SysUserMapper.class).scan(10)
        ) {
            cursor.forEach(list::add);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        //2、TransactionManager管理事务
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(status -> {
            try (Cursor<SysUser> cursor = sysUserMapper.scan(10)) {
                cursor.forEach(list::add);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });
        //3、@Transactional
        //......
        list.forEach(System.out::println);
    }

    @Test
    void testFluentQueryHandler() {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        int pageNum = 1;
        int pageSize = 10;
        wrapper.apply("id%2=0");

        List<SysUser> list = new ArrayList<>();
        sysUserMapper.scanHandler(wrapper, resultContext -> {
            if (resultContext.getResultCount() < pageSize) {
                SysUser object = resultContext.getResultObject();
                list.add(object);
            }
        });
        list.forEach(System.out::println);
    }

    @Test
    void testFluentQueryMapper() {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        int pageNum = 1;
        int pageSize = 10;
        wrapper.apply("id%2=0");

        DemoResultHandler handler = new DemoResultHandler();
        sysUserMapper.scanMapper(wrapper, handler);
        handler.end();
        List<SysUser> list = handler.getResultList();
        list.forEach(System.out::println);
    }

}
