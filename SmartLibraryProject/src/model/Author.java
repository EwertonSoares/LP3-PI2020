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
public class Author {
    
    private Long codAuthor;
    private String authorName;

    public Author() {
    }

    public Author(Long codAuthor, String authorName) {
        this.codAuthor = codAuthor;
        this.authorName = authorName;
    }

    public Long getCodAuthor() {
        return codAuthor;
    }

    public void setCodAuthor(Long codAuthor) {
        this.codAuthor = codAuthor;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return getAuthorName();
    }
   
}
