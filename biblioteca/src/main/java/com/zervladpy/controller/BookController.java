package com.zervladpy.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zervladpy.data.dao.IDao;
import com.zervladpy.data.model.Book;
import com.zervladpy.vista.IBookView;

public class BookController implements IBookController {

    private IBookView view;
    private IDao<Book> dao;

    private List<Integer> ids = new ArrayList<Integer>();

    private BookController instance;

    private BookController() {

        if (instance == null) {
            instance = new BookController();
        }

    }

    public BookController getInstance() {
        return instance;
    }

    @Override
    public void setDao(IDao<Book> dao) throws SQLException {
        this.dao = dao;
        ids = dao.getAllIds();
    }

    @Override
    public void setView(IBookView view) {
        this.view = view;
    }

    @Override
    public long getFirstId() {
        return ids.get(0);
    }

    @Override
    public Book getBook(int id) throws SQLException {

        Book book = dao.get(id);

        view.setBookTitle(book.getTitle());
        view.setAuthor(book.getAuthor());
        view.setISBN(book.getIsbn());
        view.setYear(book.getYear());
        view.setAvailable(book.getAvaliable());
        view.setCover(book.getPortada());
        view.setID(0);

    }

    @Override
    public long getPreviousId(int int1) {
        if (int1 == 0) {
            return ids.get(ids.size() - 1);
        }
        return ids.get(int1 - 1);
    }

    @Override
    public long getNextId(int int1) {

        if (int1 == ids.size() - 1) {
            return ids.get(0);
        }
        return ids.get(int1 + 1);

    }

    @Override
    public boolean isFirstBook(int int1) {

        return int1 == 0;

    }

    @Override
    public boolean isLastBook(int int1) {

        return int1 == ids.size() - 1;

    }

}
