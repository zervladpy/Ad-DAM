package com.zervladpy;

import com.zervladpy.data.connection.ConnectionManager;
import com.zervladpy.data.connection.h2.H2Connection;
import com.zervladpy.data.dao.BookDAO;

public class Main {
    public static void main(String[] args) {

        ConnectionManager connectionManager = H2Connection.getInstance();

        BookDAO bookDAO = new BookDAO(connectionManager.getConnection());

        System.out.println(bookDAO.get(1));

        connectionManager.closeConnection();

    }
}