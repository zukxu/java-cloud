package com.zukxu.java8.test.mysql;


import com.zukxu.java8.test.mysql.utils.DBTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * no lock 不加锁
 *
 * @author zukxu
 * CreateTime: 2021/5/11 0011 15:53
 */
public class TestNoLock {
	public static void main(String[] args) {
		TestNoLock noLock = new TestNoLock();
		Thread[] threads = new Thread[100];
		for (int i = 0; i < 100; i++) {
			int finalI = i;
			threads[i] = new Thread() {
				@Override
				public void run() {
					try {
						noLock.service("t" + finalI);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
		}
		for (int i = 0; i < 100; i++) {
			threads[i].start();
		}

	}

	public void service(String name) throws Exception {
		String selectSql = "select count from stock where id = 1";
		Connection connection = DBTools.getConnection();
		PreparedStatement statement1 = connection.prepareStatement(selectSql);
		ResultSet resultSet = statement1.executeQuery();
		resultSet.next();
		String count = resultSet.getString("count");
		System.out.println(name + ":" + count);
		int c = Integer.parseInt(count);
		Thread.sleep(10);
		if (c < 1) throw new Exception();
		String updateSql = "update stock set count = count - 1,sale=sale+1 where count > 0";
		PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
		int update = preparedStatement.executeUpdate();
		String insertSql = "insert into stock_order (sid,name) VALUES (1, '" + name + "')";
		PreparedStatement statement = connection.prepareStatement(insertSql);
		int insert = statement.executeUpdate();
	}
}
