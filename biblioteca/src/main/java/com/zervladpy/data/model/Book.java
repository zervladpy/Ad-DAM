package com.zervladpy.data.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import com.zervladpy.utils.constraints.BookTable;

public class Book {

    private long idBook;
    private String isbn;
    private String title;
    private String author;
    private int year;
    private boolean avaliable;
    private byte[] cover;

    public Book() {
    }

    public Book(String isbn, String title, String author, int year, boolean avaliable) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.year = year;
        this.avaliable = avaliable;
    }

    public Book(long idBook, String isbn, String title, String author, int year, boolean avaliable, byte[] cover) {
        this.idBook = idBook;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.year = year;
        this.avaliable = avaliable;
        this.cover = cover;
    }

    public long getIdBook() {
        return idBook;
    }

    public void setIdBook(long idBook) {
        this.idBook = idBook;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isAvaliable() {
        return avaliable;
    }

    public void setAvaliable(boolean avaliable) {
        this.avaliable = avaliable;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public void setCover(Path path) {
    }

    public void setCover(String route) {
    }

    public BufferedImage getImage() {

        if (cover == null) {
            return null;
        }

        ByteArrayInputStream bais = new ByteArrayInputStream(cover);

        try {
            return ImageIO.read(bais);
        } catch (IOException ex) {
            System.out.println("Error al leer la imagen");
            return null;
        }

    }

    @Override
    public String toString() {
        return this.title + " - " + this.author + " - " + this.year;
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || !(obj instanceof Book)) {
            return false;
        }

        Book book = (Book) obj;

        return hashCode() == book.hashCode();

    }

    public static Book fromResultSet(ResultSet rs) throws SQLException {

        Book book = new Book();

        book.setIdBook(rs.getInt(BookTable.ID));
        book.setIsbn(rs.getString(BookTable.ISBN));
        book.setTitle(rs.getString(BookTable.TITLE));
        book.setAuthor(rs.getString(BookTable.AUTHOR));
        book.setYear(rs.getInt(BookTable.YEAR));
        book.setCover(rs.getBytes(BookTable.COVER));

        return book;
    }

}
