package com.zukxu.common.utils;



import com.zukxu.common.model.JDBCProperties;

import java.sql.*;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/8/25 14:21
 */
public class JDBCUtil {

    public static Connection getConnection(JDBCProperties jdbc) throws Exception {
        return getConnection(jdbc.getDriverClassName(), jdbc.getUrl(), jdbc.getUsername(), jdbc.getPassword());
    }

    public static Connection getConnection(String driver, String url, String user, String password) throws Exception {
        Class.forName(driver);
        return DriverManager.getConnection(url, user, password);
    }

    public static void close(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        try {
            if(resultSet != null) {
                resultSet.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if(connection != null) {
                connection.close();
            }
        } catch(SQLException e) {
            throw new RuntimeException();
        }
    }

}
