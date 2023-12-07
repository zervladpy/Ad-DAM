package com.zervladpy.data.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Book {

    private int idBook;
    private String isbn;
    private String title;
    private String author;
    private int year;
    private Boolean avaliable;
    private byte[] portada;

    public Book() {
    }

    public Book(int idBook, String isbn, String title, String author, int year, Boolean avaliable, byte[] portada) {
        this.idBook = idBook;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.year = year;
        this.avaliable = avaliable;
        this.portada = portada;
    }

    public Book(String isbn, String title, String author, int year, Boolean avaliable) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.year = year;
        this.avaliable = avaliable;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
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

    public Boolean getAvaliable() {
        return avaliable;
    }

    public void setAvaliable(Boolean avaliable) {
        this.avaliable = avaliable;
    }

    public byte[] getPortada() {
        return portada;
    }

    public void setPortada(byte[] portada) {
        this.portada = portada;
    }

    public void setPortada(String portada) {

        File file = new File(portada);

        // TODO set portada from Route
        // this.portada = file.;
    }

    public void setPortada(Path portada) {

        // TODO set portada from Path

        // this.portada = portada;
    }

    public BufferedImage getImage() throws IOException {

        ByteArrayInputStream fl = new ByteArrayInputStream(this.portada);
        return ImageIO.read(fl);

    }

    @Override
    public int hashCode() {
        return this.isbn.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (((Book) obj).hashCode() == this.hashCode()) {
            return true;
        }

        return false;

    }

    @Override
    public String toString() {
        return "Book(titulo=" + this.title + ", autor=" + this.author + ", año=" + this.year + ")";
    }

}
