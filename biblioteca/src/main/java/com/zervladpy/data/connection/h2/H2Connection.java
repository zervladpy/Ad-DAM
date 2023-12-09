package com.zervladpy.data.connection.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.zervladpy.data.connection.ConnectionManager;
import com.zervladpy.utils.constraints.Constraint;
import com.zervladpy.utils.exceptions.SqlExceptionTracer;

public class H2Connection implements ConnectionManager {

    private static Connection conn;

    private H2Connection() {
        try {
            conn = DriverManager.getConnection(Constraint.URL, Constraint.USER, Constraint.PASSWORD);
        } catch (SQLException e) {
            SqlExceptionTracer.trace(e);
        }
    }

    public static Connection getConnection() {
        if (conn == null) {
            synchronized (H2Connection.class) {
                if (conn == null) {
                    new H2Connection();
                }
            }
        }

        return conn;
    }

    public static void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            SqlExceptionTracer.trace(e);
        }
    }

}
