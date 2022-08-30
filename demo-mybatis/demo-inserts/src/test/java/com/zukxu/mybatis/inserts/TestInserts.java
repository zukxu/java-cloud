package com.zukxu.mybatis.inserts;

import com.zukxu.mybatis.inserts.mapper.SysUserMapper;
import com.zukxu.mybatis.inserts.model.SysUser;
import com.zukxu.mybatis.inserts.service.InsertsSysUserService;
import com.zukxu.mybatis.inserts.utils.ExcelUtil;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StopWatch;

import java.io.File;
import java.io.IOException;
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
    private InsertsSysUserService insertsService;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    void testMybatis() {
        List<SysUser> list = new ArrayList<>();
        for(int i = 0; i < 10000; i++) {
            list.add(SysUser.builder()
                            .id("test" + i)
                            .deptId("1")
                            .loginName("test" + i)
                            .userName("test" + i)
                            .password("123456")
                            .sex("" + (i % 3))
                            .email("test" + i + "@163.com")
                            .phone("15815422158")
                            .status("1")
                            .createBy("admin")
                            .createTime(LocalDateTime.now())
                            .yd4aAccount("yd4a" + i)
                            .oaAccount("oa" + i)
                            .crmAccount("crm" + i)
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
            ob1 = new Object[]{ "name" + i, "1", "name" + i, "name" + i, "123456", i % 3, "name" + i + "@163.com", "151215875412", "1", "admin", LocalDateTime.now(), "yd4a" + i, "oa" + i, "crm" + i };
            records.add(ob1);
        }
        StopWatch sw = new StopWatch();
        sw.start();
        //插入上传任务数据
        String insertSql = "INSERT INTO sys_user (id, dept_id, login_name, user_name, password, sex, email, phone, status, create_by, create_time, yd4a_account,oa_account, crm_account) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
            List<SysUser> records = new ArrayList<>();
            for(int i = 0; i < 1000000; i++) {
                records.add(
                        SysUser.builder()
                               .id("test" + i)
                               .deptId("1")
                               .loginName("test" + i)
                               .userName("test" + i)
                               .password("123456")
                               .sex("" + (i % 3))
                               .email("test" + i + "@163.com")
                               .phone("15815422158")
                               .status("1")
                               .createBy("admin")
                               .createTime(LocalDateTime.now())
                               .yd4aAccount("yd4a" + i)
                               .oaAccount("oa" + i)
                               .crmAccount("crm" + i)
                               .build()
                           );
            }
            SysUserMapper mapper = batchSqlSession.getMapper(SysUserMapper.class);
            StopWatch sw = new StopWatch();
            sw.start();
            int batchCount = 1000;// 每批commit的个数
            int batchLastIndex = batchCount;// 每批最后一个的下标
            for(int index = 0; index < records.size(); ) {
                if(batchLastIndex >= records.size()) {
                    batchLastIndex = records.size();
                    result = result * mapper.insertBatch(records.subList(index, batchLastIndex));
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

    @Test
    void testExcelInsert() throws IOException {
        List<String[]> dataList = ExcelUtil.readExcel(new File(""));
        List<String> params = new ArrayList<>();
        String tableName = "";
        insertExcelBatch(sqlSessionFactory, dataList,tableName,params);
    }

    public static void insertExcelBatch(SqlSessionFactory sqlSessionFactory, List<String[]> dataList, String tableName, List<String> paramsList) {
        int result = 1;
        try(SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false)) {
            SysUserMapper reportMapper = batchSqlSession.getMapper(SysUserMapper.class);
            StopWatch sw = new StopWatch();
            sw.start();
            int batchCount = 1000;// 每批commit的个数
            int batchLastIndex = batchCount;// 每批最后一个的下标
            for(int index = 0; index < dataList.size(); ) {
                if(batchLastIndex >= dataList.size()) {
                    batchLastIndex = dataList.size();
                    result = result * reportMapper.insertExcelBatch(dataList.subList(index, batchLastIndex), tableName, paramsList);
                    break;// 数据插入完毕，退出循环
                } else {
                    result = result * reportMapper.insertExcelBatch(dataList.subList(index, batchLastIndex), tableName, paramsList);
                    batchSqlSession.commit();
                    index = batchLastIndex;// 设置下一批下标
                    batchLastIndex = index + (batchCount - 1);
                }
            }
            batchSqlSession.commit();

            sw.stop();
            System.out.println("总共耗时：" + sw.getTotalTimeMillis() + "ms");
        }
    }

}
