package com.zukxu.mybatis.inserts;

import cn.hutool.crypto.digest.DigestUtil;
import com.zukxu.mybatis.inserts.mapper.SysUserMapper;
import com.zukxu.mybatis.inserts.model.SysUser;
import com.zukxu.mybatis.inserts.service.InsertsSysUserService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StopWatch;

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

    private final int batchCount = 1000;// 每批commit的个数
    private List<SysUser> list = new ArrayList<>();
    private final String pwd = DigestUtil.md5Hex("123456");

    @Test
    void testMybatisPlusInsert() {
        LocalDateTime now = LocalDateTime.now();
        StopWatch sw = new StopWatch();
        for (int i = 1; i < 1000001; i++) {
            if (i % batchCount == 0) {
                sw.start(String.valueOf(i));
            }
            list.add(SysUser.builder()
                            .id(String.valueOf(i))
                            .deptId(String.valueOf(1000528 + i % 5))
                            .loginName("test" + i)
                            .userName("test" + i)
                            .password(pwd)
                            .sex(String.valueOf(i % 3))
                            .email("test" + i + "@163.com")
                            .phone("15815422158")
                            .status("1")
                            .createBy("admin")
                            .createTime(now)
                            .level(String.valueOf(i % 3))
                            .yd4aAccount("yd4a" + i)
                            .oaAccount("oa" + i)
                            .crmAccount("crm" + i)
                            .build());
            if (i % batchCount == 0) {
                //执行插入
                insertsService.saveBatch(list);
                list.clear();
                sw.stop();
            }
        }
        System.out.println(sw.prettyPrint());
    }

    @Test
    void testJDBC() {
        List<Object[]> list = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        StopWatch sw = new StopWatch();
        //插入上传任务数据
        String insertSql = "INSERT INTO sys_user (id, dept_id, login_name, user_name, password, sex, email, phone, status, create_by, create_time, level,yd4a_account,oa_account, crm_account) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        for (int i = 100001; i < 200001; i++) {
            if (i % batchCount == 0) {
                sw.start(String.valueOf(i));
            }
            list.add(new Object[]{
                    String.valueOf(i)
                    , 1000528 + i % 5
                    , "test" + i
                    , "test" + i
                    , pwd
                    , i % 3
                    , "test" + i + "@163.com"
                    , "15815422158"
                    , "1"
                    , "admin"
                    , now
                    , i % 3
                    , "yd4a" + i
                    , "oa" + i
                    , "crm" + i});
            if (i % batchCount == 0) {
                //执行插入
                jdbcTemplate.batchUpdate(insertSql, list);
                jdbcTemplate.execute("commit;");
                list.clear();
                sw.stop();
            }
        }
        System.out.println("总共耗时：" + sw.prettyPrint());
        //    448780ms = 448.780s = 7.4min
    }

    @Test
    void testCustomMybatis() {
        //如果非要使用 foreach 的方式来进行批量插入的话，可以考虑减少一条 insert 语句中 values 的个数，
        // 最好能达到上面曲线的最底部的值，使速度最快。
        // 一般按经验来说，一次性插20~50行数量是比较合适的，时间消耗也能接受
        //参考：https://mybatis.org/mybatis-dynamic-sql/docs/insert.html#batch-insert-support
        //基本思想是将 MyBatis session 的 executor type 设为 Batch ，然后多次执行插入语句
        List<SysUser> list = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        StopWatch sw = new StopWatch();
        try (SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false)) {
            SysUserMapper mapper = batchSqlSession.getMapper(SysUserMapper.class);
            for (int i = 200001; i < 300001; i++) {
                if (i % batchCount == 0) {
                    sw.start(String.valueOf(i));
                }
                list.add(SysUser.builder()
                                .id(String.valueOf(i))
                                .deptId(String.valueOf(1000528 + i % 5))
                                .loginName("test" + i)
                                .userName("test" + i)
                                .password(pwd)
                                .sex(String.valueOf(i % 3))
                                .email("test" + i + "@163.com")
                                .phone("15815422158")
                                .status("1")
                                .createBy("admin")
                                .createTime(now)
                                .level(String.valueOf(i % 3))
                                .yd4aAccount("yd4a" + i)
                                .oaAccount("oa" + i)
                                .crmAccount("crm" + i)
                                .build());
                if (i % batchCount == 0) {
                    mapper.insertBatch(list);
                    batchSqlSession.commit();
                    list.clear();
                    sw.stop();
                }
            }
            batchSqlSession.commit();
            System.out.println("总共耗时：" + sw.prettyPrint());
        }
        //  2164ms = 2s
    }

    @Test
    void testExcelInsert() throws IOException {
        //List<String[]> dataList = ExcelUtil.readExcel(new File(""));
        List<String> params = new ArrayList<>();
        String tableName = "";
        //insertExcelBatch(sqlSessionFactory, dataList, tableName, params);
    }

    public static void insertExcelBatch(SqlSessionFactory sqlSessionFactory, List<String[]> dataList, String tableName, List<String> paramsList) {
        int result = 1;
        try (SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false)) {
            SysUserMapper reportMapper = batchSqlSession.getMapper(SysUserMapper.class);
            StopWatch sw = new StopWatch();
            sw.start();
            int batchCount = 1000;// 每批commit的个数
            int batchLastIndex = batchCount;// 每批最后一个的下标
            for (int index = 0; index < dataList.size(); ) {
                if (batchLastIndex >= dataList.size()) {
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
