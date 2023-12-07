package com.zervladpy.data.dao;

import java.io.File;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.zervladpy.data.model.Book;
import com.zervladpy.utils.ConnectionManager;

public class BookDao implements IDao<Book> {

    private final ConnectionManager conn;

    public BookDao(ConnectionManager conn) {
        this.conn = conn;
    }

    @Override
    public List<Integer> getAllIds() throws SQLException {

        Connection c = conn.getConn();

        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("select idBook from Book");

        List<Integer> ids = new ArrayList<Integer>();

        while (rs.next()) {
            ids.add(rs.getInt(1));
        }

        return ids;

    }

    @Override
    public void delete(Book t) throws SQLException {

        Connection c = conn.getConn();

        Statement st = c.createStatement();

        st.executeUpdate("delete from Book where idBook = " + t.getIdBook());

    }

    @Override
    public void deleteAll() throws SQLException {

        Connection c = conn.getConn();

        Statement st = c.createStatement();

        st.executeUpdate("delete * from Book");

    }

    @Override
    public void deleteById(long id) throws SQLException {

        Connection c = conn.getConn();

        Statement st = c.createStatement();

        st.executeUpdate("delete from Book where idBook = " + id);

    }

    @Override
    public Book get(long id) throws SQLException {

        Connection c = conn.getConn();

        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("select * from Book where idBook = " + id);

        Book book = new Book();

        while (rs.next()) {
            book.setIdBook(rs.getLong(1));
            book.setIsbn(rs.getString(2));
            book.setTitle(rs.getString(3));
            book.setAuthor(rs.getString(4));
            book.setYear(rs.getInt(5));
            book.setAvaliable(rs.getBoolean(6));
            book.setPortada(rs.getBytes(7));
        }

        return book;

    }

    @Override
    public List<Book> getAll() throws SQLException {
        List<Book> books = new ArrayList<Book>();

        Connection c = conn.getConn();

        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("select * from Book");

        while (rs.next()) {
            Book book = new Book();
            book.setIdBook(rs.getLong(1));
            book.setIsbn(rs.getString(2));
            book.setTitle(rs.getString(3));
            book.setAuthor(rs.getString(4));
            book.setYear(rs.getInt(5));
            book.setAvaliable(rs.getBoolean(6));
            book.setPortada(rs.getBytes(7));
            books.add(book);
        }

        return books;
    }

    @Override
    public void save(Book t) throws SQLException {

        Connection c = conn.getConn();

        Statement st = c.createStatement();

        st.executeUpdate("insert into Book (isbn, titulo, autor, anho, disponible, portada) values ('"
                + t.getIsbn() + "', '" + t.getTitle() + "', '" + t.getAuthor() + "', " + t.getYear() + ", "
                + t.getAvaliable() + ", '" + t.getPortada() + "')");

    }

    @Override
    public void update(Book t) throws SQLException {

        Connection c = conn.getConn();

        Statement st = c.createStatement();

        st.executeUpdate("update Book set isbn = '" + t.getIsbn() + "', title = '" + t.getTitle() + "', author = '"
                + t.getAuthor() + "', year = " + t.getYear() + ", avaliable = " + t.getAvaliable() + ", portada = '"
                + t.getPortada() + "' where idBook = " + t.getIdBook());

    }

    @Override
    public void updateImage(Book t, String f) throws SQLException {

        updateImageById(t.getIdBook(), f);

    }

    @Override
    public void updateImageById(long id, String f) throws SQLException {
        File image = new File(f);

        Blob portada = Blob.class.cast(image);

        Connection c = conn.getConn();

        Statement st = c.createStatement();

        st.executeUpdate("update Book set portada = '" + portada + "' where idBook = " + id);

    }

}
