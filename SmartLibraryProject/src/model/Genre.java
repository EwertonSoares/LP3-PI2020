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
public class Genre {
    
    private Long codGenre;
    private String genre;

    public Genre() {
    }

    public Genre(Long codGenre, String genre) {
        this.codGenre = codGenre;
        this.genre = genre;
    }

    public Long getCodGenre() {
        return codGenre;
    }

    public void setCodGenre(Long codGenre) {
        this.codGenre = codGenre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return getGenre();
    }
}
