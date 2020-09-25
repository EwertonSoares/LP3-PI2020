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
public class UserAndBook {

    private Long codUser;
    private Long codBook;
    private String bookName;
    private Date releaseDate;
    private Date expectedDate;
    private Date ReturnDate;
    private Float price;
    private Long quantity;

    public UserAndBook() {
    }

    public UserAndBook(Long codUser, Long codBook, String bookName, Date releaseDate, Date expectedDate, Date ReturnDate, Float price, Long quantity) {
        this.codUser = codUser;
        this.codBook = codBook;
        this.bookName = bookName;
        this.releaseDate = releaseDate;
        this.expectedDate = expectedDate;
        this.ReturnDate = ReturnDate;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getCodUser() {
        return codUser;
    }

    public void setCodUser(Long codUser) {
        this.codUser = codUser;
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Date expectedDate) {
        this.expectedDate = expectedDate;
    }

    public Date getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(Date ReturnDate) {
        this.ReturnDate = ReturnDate;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

}
