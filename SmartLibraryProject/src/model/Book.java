/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ewerton
 */
public class Book {
    
    private String bookName;
    private String author;
    private String genre;
    private String publisher;
    private Float price;
    private Date releaseDate;
    private Date returnDate;
    private Date extectedReurnDate;

    public Book () {
        
    }

    public Book(String bookName, String author, String genre, String publisher, Float price, Date releaseDate, Date returnDate, Date extectedReurnDate) {
        this.bookName = bookName;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.price = price;
        this.releaseDate = releaseDate;
        this.returnDate = returnDate;
        this.extectedReurnDate = extectedReurnDate;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setExtectedReurnDate(Date extectedReurnDate) {
        this.extectedReurnDate = extectedReurnDate;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public Float getPrice() {
        return price;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public Date getExtectedReurnDate() {
        return extectedReurnDate;
    } 
    
}
