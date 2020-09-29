/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import javafx.scene.control.Button;

/**
 *
 * @author ewerton
 */
public class UserAndBook extends Book {

    private Long codUser;
    private String email;
    private Long qtt;

    public UserAndBook() {
    }

    public UserAndBook(Long codBook, String bookName, String author, String genre, 
            String publisher, Float price, Date releaseDate, Date returnDate, 
            Date expectedDate, Long quantity, Button btnGet, Button btnRet) {
        
        super(codBook, bookName, author, genre, publisher, price, 
                releaseDate, returnDate, expectedDate, quantity, btnGet, btnRet);
    }

    public Long getQtt () {
        return qtt;
    }

    public void setQtt(Long qtt) {
        this.qtt = qtt;
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
}
