package com.zervladpy.data.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zervladpy.data.model.Book;
import com.zervladpy.utils.constraints.BookTable;
import com.zervladpy.utils.exceptions.SqlExceptionTracer;

public class BookDAO implements DAO<Book> {

    private Connection conn;

    public BookDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Book get(long id) {

        String query = "select * from " + BookTable.TABLE_NAME + " where " + BookTable.ID + " = ";

        try (var st = conn.createStatement()) {

            ResultSet rs = st.executeQuery(query + id);
            Book book = null;
            while (rs.next()) {
                book = Book.fromResultSet(rs);
            }
            return book;
        } catch (SQLException e) {
            SqlExceptionTracer.trace(e);
        }

        return null;

    }

    @Override
    public List<Book> getAll() {

        String query = "select * from " + BookTable.TABLE_NAME;

        try (var st = conn.createStatement()) {

            ResultSet rs = st.executeQuery(query);

            List<Book> books = new ArrayList<Book>();

            while (rs.next()) {
                books.add(Book.fromResultSet(rs));
            }

            return books;

        } catch (SQLException e) {
            SqlExceptionTracer.trace(e);
        }

        return null;

    }

    @Override
    public Book save(Book book) {

        if (book == null) {
            return null;
        }

        String query = "insert into "
                + BookTable.TABLE_NAME
                + " (" + BookTable.ID + ", " + BookTable.ISBN + ", " + BookTable.TITLE
                + ", " + BookTable.AUTHOR + ", " + BookTable.YEAR
                + ", " + BookTable.AVALIABLE + ", " + BookTable.COVER
                + ") values (?,?,?,?,?,?,?)";

        try (var pst = conn.prepareStatement(query)) {

            pst.setLong(1, book.getIdBook());
            pst.setString(2, book.getIsbn());
            pst.setString(3, book.getTitle());
            pst.setString(4, book.getAuthor());
            pst.setInt(5, book.getYear());
            pst.setBoolean(6, book.isAvaliable());
            pst.setBytes(7, book.getCover());

            pst.executeUpdate();

            return book;

        } catch (SQLException e) {
            SqlExceptionTracer.trace(e);
        }

        return null;

    }

    @Override
    public void update(Book book) {

        String query = "select * from "
                + BookTable.TABLE_NAME + " where "
                + BookTable.ID + " = " + book.getIdBook();

        try (var st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                String oldIsb = rs.getString(BookTable.ISBN);
                String newIsbn = book.getIsbn();
                if (!newIsbn.equals(oldIsb)) {
                    rs.updateString(BookTable.ISBN, newIsbn);

                }

                String oldTitle = rs.getString(BookTable.TITLE);
                String newTitle = book.getTitle();
                if (!oldTitle.equals(newTitle)) {
                    rs.updateString(BookTable.TITLE, newTitle);
                }

                String oldAuthor = rs.getString(BookTable.AUTHOR);
                String newAuthor = book.getAuthor();
                if (!oldAuthor.equals(newAuthor)) {
                    rs.updateString(BookTable.AUTHOR, newAuthor);
                }

                int oldYear = rs.getInt(BookTable.YEAR);
                int newYear = book.getYear();
                if (oldYear != newYear) {
                    rs.updateInt(BookTable.YEAR, newYear);
                }

                boolean oldAvaliable = rs.getBoolean(BookTable.AVALIABLE);
                boolean newAvaliable = book.isAvaliable();
                if (oldAvaliable != newAvaliable) {
                    rs.updateBoolean(BookTable.AVALIABLE, newAvaliable);
                }

                byte[] oldCover = rs.getBytes(BookTable.COVER);
                byte[] newCover = book.getCover();
                if (oldCover != newCover) {
                    rs.updateBytes(BookTable.COVER, newCover);
                }

                rs.updateRow();
            }

        } catch (SQLException e) {
            SqlExceptionTracer.trace(e);
        }

    }

    @Override
    public void delete(Book book) {

        deleteById(book.getIdBook());

    }

    @Override
    public void deleteById(long id) {
        String query = "delete from " + BookTable.TABLE_NAME + " where " + BookTable.ID + " = " + id;

        try (var st = conn.createStatement()) {

            st.executeUpdate(query);

        } catch (SQLException e) {

            SqlExceptionTracer.trace(e);

        }
    }

    @Override
    public void updateImage(Book book, String imageRoute) {

        updateImageById(book.getIdBook(), imageRoute);

    }

    @Override
    public void updateImageById(long id, String imageRoute) {

        File imageFile = new File(imageRoute);

        if (!imageFile.exists()) {
            return;
        }

        Path image = imageFile.toPath();

        String query = "select "
                + BookTable.COVER + " from "
                + BookTable.TABLE_NAME + " where "
                + BookTable.ID + " = " + id;

        try (var st = conn.createStatement()) {

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                rs.updateBytes(BookTable.COVER, Files.readAllBytes(image));

            }

        } catch (SQLException | IOException e) {

            if (e instanceof SQLException) {
                SqlExceptionTracer.trace((SQLException) e);
            } else {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void deleteAll() {

        String query = "delete from " + BookTable.TABLE_NAME;

        try (var st = conn.createStatement()) {

            st.executeUpdate(query);

        } catch (SQLException e) {

            SqlExceptionTracer.trace(e);
        }
    }

}
