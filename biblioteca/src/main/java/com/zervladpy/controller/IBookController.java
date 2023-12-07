package com.zervladpy.controller;

import java.sql.SQLException;

import com.zervladpy.data.dao.IDao;
import com.zervladpy.data.model.Book;
import com.zervladpy.vista.IBookView;

public interface IBookController {

    void setView(IBookView view);

    void setDao(IDao<Book> dao) throws SQLException;

    long getFirstId();

    Book getBook(long id) throws SQLException;

    long getPreviousId(int int1);

    long getNextId(int int1);

    boolean isFirstBook(int int1);

    boolean isLastBook(int int1);

}