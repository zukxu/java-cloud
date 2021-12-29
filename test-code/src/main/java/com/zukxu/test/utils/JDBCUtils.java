package com.zukxu.test.utils;

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

    private static String driverClassName="com.mysql.jdbc.Driver";
    private static String url="jdbc:mysql://192.168.0.21:3310/java_cloud?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true";
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

    public static void main(String[] args) {
        Connection conn = null;
        Statement stat = null;
        ResultSet res = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select * from user";
            stat = conn.createStatement();
            res = stat.executeQuery(sql);
            while (res.next()) {
                int id = res.getInt("id");
                String username = res.getString("username");
                String password1 = res.getString("password1");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(res, stat, conn);
        }
    }
}
