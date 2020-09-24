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
    private String releaseDate;
    private String returnDate;
    private String expectedDate;
    private Long quantity;

    public Book() {
    }

    public Book(Long codBook, String bookName, String author, String genre, String publisher, 
            Float price, String releaseDate, String returnDate, String expectedDate, Long quantity) {
        
        this.codBook = codBook;
        this.bookName = bookName;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
    
}
