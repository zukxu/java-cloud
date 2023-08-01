package com.zukxu.java8.test.mysql;

import com.zukxu.java8.test.mysql.entity.Stock;
import com.zukxu.java8.test.mysql.utils.DBTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Pessimistic lock悲观锁
 *
 * @author zukxu
 * CreateTime: 2021/5/11 0011 15:53
 */
public class TestPessimisticLock {
    public static void main(String[] args) {
        TestPessimisticLock pessimisticLock = new TestPessimisticLock();
        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            threads[i] = new Thread() {
                @Override
                public void run() {
                    try {
                        pessimisticLock.service("t" + finalI);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            };
        }
        for (int i = 0; i < 100; i++) {
            threads[i].start();
        }
    }

    public void service(String name) throws SQLException {
        Connection connection = null;
        try {
            connection = DBTools.getConnection();
            assert connection != null;
            connection.setAutoCommit(false);
            Stock stock1 = checkStock(connection);
            updateCountByOpti(connection, stock1);
            createOrder(name, connection);
            connection.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            connection.rollback();
        }
    }

    private void createOrder(String name, Connection connection) throws SQLException {
        String insertSql = "insert into stock_order(sid,name) VALUES (1,'" + name + "')";
        PreparedStatement statement = connection.prepareStatement(insertSql);
        int insert = statement.executeUpdate();
        if (insert > 0)
            System.out.println(name + "抢到了");
    }

    private void updateCountByOpti(Connection connection, Stock stock) throws SQLException {
        String sql = "update stock set count = count -1,sale=sale+1,version = version + 1 where version = " + stock.getVersion();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int update = preparedStatement.executeUpdate();
        if (update == 0)
            throw new RuntimeException("没抢到");
    }

    public Stock checkStock(Connection connection) throws SQLException, InterruptedException {
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
        if (stock.getCount() < 1)
            throw new RuntimeException("没有库存了");
        return stock;
    }
}