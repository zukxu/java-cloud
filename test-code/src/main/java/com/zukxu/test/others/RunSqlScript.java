package com.zukxu.test.others;

import cn.hutool.core.io.FileUtil;
import com.zukxu.common.model.JDBCProperties;
import com.zukxu.common.result.R;
import com.zukxu.common.utils.JDBCUtil;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
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
        if (lockSuccess) {
            // 建立连接
            Connection conn = null;
            Reader initReader = null;
            try {
                conn = JDBCUtil.getConnection(new JDBCProperties());
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
                initReader = new FileReader(FileUtil.getAbsolutePath(fileName));
                // 执行SQL脚本
                runner.runScript(initReader);
                // 若成功，打印提示信息
                System.out.println("====== SUCCESS ======");
                log.info("数据库脚本执行成功");
                conn.commit();
            } catch (Exception e) {
                try {
                    assert conn != null;
                    conn.rollback();
                    log.error("数据回滚成功");
                } catch (Exception e1) {
                    log.error("数据回滚失败");
                }
                log.error("数据库脚本执行失败");
            } finally {
                //释放锁
                lock.unlock();
                try {
                    if (initReader != null) {initReader.close();}
                    if (conn != null) {conn.close();}
                } catch (Exception e1) {
                    log.error(e1.getMessage(), e1);
                }
            }

        }
        return R.ok(result);
    }

}
