package com.zervladpy.data.connection.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.zervladpy.data.connection.ConnectionManager;
import com.zervladpy.utils.constraints.Constraint;
import com.zervladpy.utils.exceptions.SqlExceptionTracer;

public class H2Connection implements ConnectionManager {

    private Connection conn;

    private static H2Connection instance;

    private H2Connection() {
        try {
            conn = DriverManager.getConnection(Constraint.URL, Constraint.USER, Constraint.PASSWORD);
        } catch (SQLException e) {
            SqlExceptionTracer.trace(e);
        }
    }

    public static H2Connection getInstance() {
        if (H2Connection.instance == null) {
            synchronized (H2Connection.class) {
                if (instance == null) {
                    H2Connection.instance = new H2Connection();
                }
            }
        }

        return H2Connection.instance;
    }

    @Override
    public Connection getConnection() {
        return getInstance().conn;
    }

    @Override
    public void closeConnection() {
        try {
            getInstance().conn.close();
        } catch (SQLException e) {
            SqlExceptionTracer.trace(e);
        }
    }

}
