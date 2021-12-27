package com.zukxu.java8.test.mysql.utils;

import java.sql.*;

/**
 * @author zukxu
 * CreateTime: 2021/5/11 0011 16:14
 */
public class DBTools {

	//定义静态变量
	private static String driverName = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/little-tools?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&useSSL=false";
	private static String user = "root";
	private static String password = "123456";
	private static String tablePrefix = "test-mysql-";
	//工具类 1、不能直接创建对象，即私有化构造函数
	// 2、所有的方法都是静态的方法,即private static
	private DBTools() {}

	//    定义一个静态的返回数据库连接对象的方法
	public static Connection getConnection() {
		try {
			Class.forName(driverName);

			Connection conn = DriverManager.getConnection(url, user, password);

			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		return null;
	}

	//    关闭数据库连接
	public static void closeDb(Connection conn, Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//    关闭数据库连接，方法重载
	public static void closeDb(Connection conn, Statement stmt, ResultSet rs) {

		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//        注意关闭的顺序
		closeDb(conn, stmt);
	}

}
