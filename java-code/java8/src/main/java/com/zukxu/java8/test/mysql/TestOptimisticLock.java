package com.zukxu.java8.test.mysql;


import com.zukxu.java8.test.mysql.entity.Stock;
import com.zukxu.java8.test.mysql.utils.DBTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Optimistic lock 乐观锁
 *
 * @author zukxu
 * CreateTime: 2021/5/11 0011 15:53
 */
public class TestOptimisticLock {
	public static void main(String[] args) {
		TestOptimisticLock optimisticLock = new TestOptimisticLock();
		Thread[] threads = new Thread[100];
		for (int i = 0; i < 100; i++) {
			int finalI = i;
			threads[i] = new Thread() {
				@Override
				public void run() {
					optimisticLock.service("t" + finalI);
				}
			};
		}
		for (int i = 0; i < 100; i++) {
			threads[i].start();
		}
	}

	public void service(String name) {
		try {
			Connection connection = DBTools.getConnection();
			Stock stock1 = checkStock(connection);
			updateCountByOpti(connection, stock1);
			createOrder(name, connection);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void createOrder(String name, Connection connection) throws SQLException {
		String insertSql = "insert into stock_order(sid,name) VALUES (1,'" + name + "')";
		PreparedStatement statement = connection.prepareStatement(insertSql);
		int insert = statement.executeUpdate();
		if(insert>0) System.out.println(name+"抢到了");
	}

	private void updateCountByOpti(Connection connection, Stock stock) throws SQLException {
		String sql = "update stock set count = count -1,sale=sale+1,version = version + 1 where version = " + stock.getVersion();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		int update = preparedStatement.executeUpdate();
		if (update == 0) throw new RuntimeException("没抢到");
	}

	public Stock checkStock(Connection connection) throws SQLException {
		String sql = "select * from stock where id = 1";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		Stock stock = null;
		if (resultSet.next()) {
			stock = new Stock();
			stock.setId(resultSet.getInt("id"))
				 .setName(resultSet.getString("name"))
				 .setCount(resultSet.getInt("count"))
				 .setSale(resultSet.getInt("sale"))
				 .setVersion(resultSet.getInt("version"));
		}
		if (stock.getCount() < 1) throw new RuntimeException("没有库存了");
		return stock;
	}

}
