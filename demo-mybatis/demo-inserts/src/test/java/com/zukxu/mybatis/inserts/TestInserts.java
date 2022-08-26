package com.zukxu.mybatis.inserts;

import com.zukxu.mybatis.inserts.mapper.DemoMybatisInsertsMapper;
import com.zukxu.mybatis.inserts.model.DemoMybatisInserts;
import com.zukxu.mybatis.inserts.service.InsertsService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/7/7 10:33:35
 */
@SpringBootTest
public class TestInserts {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private InsertsService insertsService;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    void testMybatis() {
        List<DemoMybatisInserts> list = new ArrayList<>();
        for(int i = 0; i < 10000; i++) {
            list.add(DemoMybatisInserts.builder()
                                       .username("test" + i)
                                       .password("123456")
                                       .No("0119" + i)
                                       .build());
        }
        StopWatch sw = new StopWatch();
        sw.start();
        insertsService.saveBatch(list);
        sw.stop();
        System.out.println("总共耗时：" + sw.getTotalTimeMillis() + "ms");
        //    384671ms = 384.671s = 6.4min
    }

    @Test
    void testJDBC() {
        List<Object[]> records = new ArrayList<>();
        Object[] ob1;
        for(int i = 0; i < 10000; i++) {
            ob1 = new Object[]{ "name" + i, "password", 151 + i };
            records.add(ob1);
        }
        StopWatch sw = new StopWatch();
        sw.start();
        //插入上传任务数据
        String insertSql = "insert into demo_mybatis_inserts(username, password, _no) values (?,?,?)";
        int result = 1;
        int batchCount = 1000;// 每批commit的个数
        int batchLastIndex = batchCount;// 每批最后一个的下标
        for(int index = 0; index < records.size(); ) {
            if(batchLastIndex >= records.size()) {
                batchLastIndex = records.size();
                int[] row = jdbcTemplate.batchUpdate(insertSql, records.subList(index, batchLastIndex));
                result = result * row.length;
                jdbcTemplate.execute("commit;");
                System.out.println("index:" + index + " batchLastIndex:" + batchLastIndex);
                break;// 数据插入完毕，退出循环
            } else {
                result = result * jdbcTemplate.batchUpdate(insertSql, records.subList(index, batchLastIndex)).length;
                jdbcTemplate.execute("commit;");
                System.out.println("index:" + index + " batchLastIndex:" + batchLastIndex);
                index = batchLastIndex;// 设置下一批下标
                batchLastIndex = index + (batchCount - 1);
            }

        }
        sw.stop();
        System.out.println("总共耗时：" + sw.getTotalTimeMillis() + "ms");
        //    448780ms = 448.780s = 7.4min
    }

    @Test
    void testCustomMybatis() {
        //如果非要使用 foreach 的方式来进行批量插入的话，可以考虑减少一条 insert 语句中 values 的个数，
        // 最好能达到上面曲线的最底部的值，使速度最快。
        // 一般按经验来说，一次性插20~50行数量是比较合适的，时间消耗也能接受
        //参考：https://mybatis.org/mybatis-dynamic-sql/docs/insert.html#batch-insert-support
        //基本思想是将 MyBatis session 的 executor type 设为 Batch ，然后多次执行插入语句
        int result = 1;
        try(SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false)) {
            List<DemoMybatisInserts> records = new ArrayList<>();
            for(int i = 0; i < 1000000; i++) {
                records.add(DemoMybatisInserts.builder()
                                              .username("test" + i)
                                              .password("123456")
                                              .No("0119" + i)
                                              .build());
            }
            DemoMybatisInsertsMapper mapper = batchSqlSession.getMapper(DemoMybatisInsertsMapper.class);
            StopWatch sw = new StopWatch();
            sw.start();
            int batchCount = 1000;// 每批commit的个数
            int batchLastIndex = batchCount;// 每批最后一个的下标
            for(int index = 0; index < records.size(); ) {
                if(batchLastIndex >= records.size()) {
                    batchLastIndex = records.size();
                    result = result * mapper.insertBatch(records.subList(index, batchLastIndex));
                    batchSqlSession.commit();
                    System.out.println("index:" + index + " batchLastIndex:" + batchLastIndex);
                    break;// 数据插入完毕，退出循环
                } else {
                    result = result * mapper.insertBatch(records.subList(index, batchLastIndex));
                    batchSqlSession.commit();
                    System.out.println("index:" + index + " batchLastIndex:" + batchLastIndex);
                    index = batchLastIndex;// 设置下一批下标
                    batchLastIndex = index + (batchCount - 1);
                }
            }
            batchSqlSession.commit();

            sw.stop();
            System.out.println("总共耗时：" + sw.getTotalTimeMillis() + "ms");
        }
        //  2164ms = 2s
    }

}
