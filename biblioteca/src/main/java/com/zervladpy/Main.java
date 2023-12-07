package com.zervladpy;

import java.sql.*;

import com.zervladpy.data.dao.BookDao;
import com.zervladpy.data.model.Book;
import com.zervladpy.utils.ConnectionManager;

public class Main {
    public static void main(String[] args) throws SQLException {

        ConnectionManager conn = ConnectionManager.getInstance();

        BookDao bookDao = new BookDao(conn);

        Book book = new Book("1234567890123", "El Quijote", "Miguel de Cervantes", 1605, true);

        bookDao.save(book);

        bookDao.getAll().forEach(System.out::println);

        conn.close();
    }
}
