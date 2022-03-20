package com.zukxu.test.refn;

import java.sql.*;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2021-12-28 15:48
 */
public class JDBCUtils {

    private static String driverClassName="com.mysql.cj.jdbc.Driver";
    private static String url="jdbc:mysql://127.0.0.1:3306/java_cloud?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true";
    private static String user = "root";
    private static String password = "123456";



    /**
     * 获取连接对象的方法
     *
     * @return连接对象
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(driverClassName);
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * 释放资源
     *
     * @param stat
     * @param conn
     */
    public static void close(Statement stat, Connection conn) {
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet res, Statement stat, Connection conn) {
        if (res != null) {
            try {
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
