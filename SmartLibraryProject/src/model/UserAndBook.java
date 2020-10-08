/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import javafx.scene.control.CheckBox;

/**
 *
 * @author ewerton
 */
public class UserAndBook {

    private Long codUser;
    private String email;
    private Long qtt;
    private Long codBook;
    private String bookName;
    private Float price;
    private Date releaseDate;
    private Date returnDate;
    private Date expectedDate;
    private Long quantity;
    private CheckBox checkBox;
            

    public UserAndBook() {
    }

    public UserAndBook(Long codUser, String email, Long qtt, Long codBook, String bookName, Float price, 
            Date releaseDate, Date returnDate, Date expectedDate, Long quantity, CheckBox checkBox) {
        this.codUser = codUser;
        this.email = email;
        this.qtt = qtt;
        this.codBook = codBook;
        this.bookName = bookName;
        this.price = price;
        this.releaseDate = releaseDate;
        this.returnDate = returnDate;
        this.expectedDate = expectedDate;
        this.quantity = quantity;
        this.checkBox = checkBox;
    }

    public Long getCodUser() {
        return codUser;
    }

    public void setCodUser(Long codUser) {
        this.codUser = codUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getQtt() {
        return qtt;
    }

    public void setQtt(Long qtt) {
        this.qtt = qtt;
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

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    
}
