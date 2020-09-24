/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ewerton
 */
public class Publisher {
    
    private Long codPublisher;
    private String publisher;

    public Publisher() {
    }

    public Publisher(Long codPublisher, String publisher) {
        this.codPublisher = codPublisher;
        this.publisher = publisher;
    }

    public Long getCodPublisher() {
        return codPublisher;
    }

    public void setCodPublisher(Long codPublisher) {
        this.codPublisher = codPublisher;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return getPublisher();
    }
}
