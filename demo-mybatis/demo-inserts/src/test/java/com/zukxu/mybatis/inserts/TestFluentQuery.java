package com.zukxu.mybatis.inserts;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zukxu.mybatis.inserts.handler.DemoResultHandler;
import com.zukxu.mybatis.inserts.handler.ExcelResultHandler;
import com.zukxu.mybatis.inserts.mapper.DemoMybatisInsertsMapper;
import com.zukxu.mybatis.inserts.model.DemoMybatisInserts;
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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
    private DemoMybatisInsertsMapper demoMybatisInsertsMapper;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    void testFluentQuery() {
        List<DemoMybatisInserts> list = new ArrayList<>();
        //1、SqlSession
        try(
                SqlSession sqlSession = sqlSessionFactory.openSession();
                Cursor<DemoMybatisInserts> cursor = sqlSession.getMapper(DemoMybatisInsertsMapper.class).scan(10)
        ) {
            cursor.forEach(list::add);
        } catch(Exception e) {
            log.error(e.getMessage());
        }
        //2、TransactionManager管理事务
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(status -> {
            try(Cursor<DemoMybatisInserts> cursor = demoMybatisInsertsMapper.scan(10)) {
                cursor.forEach(list::add);
            } catch(IOException e) {
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
        QueryWrapper<DemoMybatisInserts> wrapper = new QueryWrapper<>();
        int pageNum = 1;
        int pageSize = 10;
        wrapper.apply("id%2=0");

        List<DemoMybatisInserts> list = new ArrayList<>();
        demoMybatisInsertsMapper.scanHandler(wrapper, resultContext -> {
            if(resultContext.getResultCount() < pageSize) {
                DemoMybatisInserts object = resultContext.getResultObject();
                list.add(object);
            }
        });
        list.forEach(System.out::println);
    }

    @Test
    void testFluentQueryMapper() {
        QueryWrapper<DemoMybatisInserts> wrapper = new QueryWrapper<>();
        int pageNum = 1;
        int pageSize = 10;
        wrapper.apply("id%2=0");

        DemoResultHandler handler = new DemoResultHandler();
        demoMybatisInsertsMapper.scanMapper(wrapper,handler);
        handler.end();
        List<DemoMybatisInserts> list = handler.getResultList();
        list.forEach(System.out::println);
    }

}
