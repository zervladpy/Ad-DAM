package com.zervladpy.utils.exceptions;

import java.sql.SQLException;

public class SqlExceptionTracer {

    public static void trace(SQLException e) {
        System.out.println("SQL State" + e.getSQLState());
        System.out.println("Error Code" + e.getErrorCode());
        System.out.println("Message" + e.getMessage());
    }

}
