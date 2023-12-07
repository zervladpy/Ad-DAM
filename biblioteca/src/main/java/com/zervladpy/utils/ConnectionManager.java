package com.zervladpy.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static ConnectionManager instance = null;

    private final Connection conn;

    private ConnectionManager() throws SQLException {

        conn = DriverManager.getConnection(
                Constraints.JDBCDriver + Constraints.URL,
                Constraints.USERNAME,
                Constraints.PASSWORD);

        System.out.println("Connection established");

    }

    public static ConnectionManager getInstance() throws SQLException {
        if (instance == null) {

            synchronized (ConnectionManager.class) {
                if (instance == null) {
                    instance = new ConnectionManager();
                }
            }

        }
        return instance;
    }

    public Connection getConn() {
        return conn;
    }

    public void close() throws SQLException {
        System.out.println("Connection closed");
        conn.close();
        instance = null;
    }

}
