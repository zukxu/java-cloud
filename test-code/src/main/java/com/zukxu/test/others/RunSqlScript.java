package com.zukxu.test.others;

import com.zukxu.common.result.R;
import lombok.Data;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * Mybatis 的 ScriptRunner 运行sql脚本
 * </p>
 *
 * @author xupu
 * @since 2022/8/15 14:40:21
 */
public class RunSqlScript {

    private static final Logger log = LoggerFactory.getLogger(RunSqlScript.class);

    private R<?> isSQLFile(String fileName) {
        //获取锁
        Lock lock = new ReentrantLock();
        boolean lockSuccess = lock.tryLock();
        List<String> result = new ArrayList<>();
        if(lockSuccess) {
            // 建立连接
            Connection conn = null;
            Reader initReader = null;
            try {
                conn = getConnection();
                // 创建ScriptRunner，用于执行SQL脚本
                ScriptRunner runner = new ScriptRunner(conn);
                // 设置不自动提交
                runner.setAutoCommit(false);
                //遇见错误是否停止
                runner.setStopOnError(true);
                //按照哪一种方式执行 true则获取整个脚本并执行 false则按照自定义的分隔符每行执行
                runner.setSendFullScript(false);
                //设置是否输出错误日志
                runner.setErrorLogWriter(null);
                //设置是否输出日志
                runner.setLogWriter(null);
                //读取sql脚本文件
                initReader = new FileReader(new File(fileName));
                // 执行SQL脚本
                runner.runScript(initReader);

                // 若成功，打印提示信息
                System.out.println("====== SUCCESS ======");
                log.info("数据库脚本执行成功");
            } catch(Exception e) {
                try {
                    assert conn != null;
                    conn.rollback();
                    log.error("数据回滚成功");
                } catch(Exception e1) {
                    log.error("数据回滚失败");
                }
                log.error("数据库脚本执行失败");
            } finally {
                //释放锁
                lock.unlock();
                try {
                    if(initReader != null) {initReader.close();}
                    if(conn != null) {conn.close();}
                } catch(Exception e1) {
                    log.error(e1.getMessage(), e1);
                }
            }

        }
        return R.ok(result);
    }

    public Connection getConnection() throws Exception {
        JDBCProperties jdbc = new JDBCProperties();
        Connection conn;
        Class.forName(jdbc.getDriverClassName());
        conn = DriverManager.getConnection(jdbc.getUrl(), jdbc.getUsername(), jdbc.getPassword());
        return conn;
    }

    @Component
    @ConfigurationProperties(prefix = "spring.datasource")
    @Data
    public class JDBCProperties {

        private String driverClassName;

        private String url;

        private String username;

        private String password;

    }

}
