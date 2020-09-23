/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author ewerton
 */
public class Book {

    private Long codBook;
    private String bookName;
    private String author;
    private String genre;
    private String publisher;
    private Float price;
    private Date releaseDate;
    private Date returnDate;
    private Date extectedReurnDate;

    public Book() {
    }
    
    public Book(Long codBook, String bookName, String author, String genre, String publisher, Float price, Date releaseDate, Date returnDate, Date extectedReurnDate) {
        this.codBook = codBook;
        this.bookName = bookName;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.price = price;
        this.releaseDate = releaseDate;
        this.returnDate = returnDate;
        this.extectedReurnDate = extectedReurnDate;
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

    public Date getExtectedReurnDate() {
        return extectedReurnDate;
    }

    public void setExtectedReurnDate(Date extectedReurnDate) {
        this.extectedReurnDate = extectedReurnDate;
    }
}
