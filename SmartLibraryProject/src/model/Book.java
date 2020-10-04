/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

/**
 *
 * @author ewerton
 */
public class Book {

    private Long codBook;
    private String bookName;
    private String genre;
    private String publisher;
    private String author;
    private Float price;
    private Date releaseDate;
    private Date returnDate;
    private Date expectedDate;
    private Long quantity;

    public Book() {
    }

    public Book(Long codBook, String bookName, String genre,
            String publisher, String author, Float price, Date releaseDate, Date returnDate,
            Date expectedDate, Long quantity) {

        this.codBook = codBook;
        this.bookName = bookName;
        this.genre = genre;
        this.publisher = publisher;
        this.author = author;
        this.price = price;
        this.releaseDate = releaseDate;
        this.returnDate = returnDate;
        this.expectedDate = expectedDate;
        this.quantity = quantity;
    }

    public Long getCodBook() {
        return codBook;
    }

    public void setCodBook(Long codBook) {
        this.codBook = codBook;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Date expectedDate) {
        this.expectedDate = expectedDate;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
