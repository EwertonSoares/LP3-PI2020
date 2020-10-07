/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Genre;
import model.User;

/**
 *
 * @author ewerton
 */
public class AuthorQuery {

    /*
    
     */
    public boolean insertGenre(String genre) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        ResultSet result;
        boolean saved = false;

        try {

            stmt = con.prepareStatement("INSERT INTO genres (genre) VALUES(?)");
            stmt.setString(1, genre);

            saved = stmt.execute();

            if (!saved) {
                saved = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(AuthorQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return saved;
    }

    /*
    
     */
    public boolean removeGenre(Long codGenre) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        ResultSet result;
        boolean removed = false;

        try {

            stmet = conn.prepareStatement("DELETE FROM genres WHERE codGenre = ?");
            stmet.setLong(1, codGenre);

            removed = stmet.execute();

            if (!removed) {
                removed = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(AuthorQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return removed;
    }

    /*
    
     */
    public Boolean updateGenre(Genre genre) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        ResultSet result;
        boolean updated = false;

        try {

            stmet = conn.prepareStatement("UPDATE genres SET genre = ? WHERE codGenre = ?;");

            stmet.setString(1, genre.getGenre());
            stmet.setLong(2, genre.getCodGenre());

            updated = stmet.execute();

            if (!updated) {
                updated = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdmUsersPageQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return updated;
    }

    /*
    
     */
    public List<Genre> getGenreList() {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        ResultSet result;
        List<Genre> genreList = new ArrayList();

        try {

            stmt = con.prepareStatement("SELECT * FROM genres");

            result = stmt.executeQuery();

            while (result.next()) {
                Long codGenre = result.getLong("codGenre");
                String genres = result.getString("genre");

                Genre genre = new Genre(codGenre, genres);

                genreList.add(genre);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdmUsersPageQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return genreList;
    }

}
