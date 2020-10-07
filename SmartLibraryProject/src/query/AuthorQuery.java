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
import model.Author;
import model.Genre;
import model.User;

/**
 *
 * @author ewerton
 */
public class AuthorQuery {

    /*
    
     */
    public boolean insertAuthor(String author) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        boolean saved = false;

        try {

            stmt = con.prepareStatement("INSERT INTO authors (authorName) VALUES (?);");
            stmt.setString(1, author);

            saved = stmt.execute();

            if (saved == false) {
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
    public boolean removeAuthor(Long codAuthor) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        ResultSet result;
        boolean removed = false;

        try {

            stmet = conn.prepareStatement("DELETE FROM authors WHERE codAuthor = ?");
            stmet.setLong(1, codAuthor);

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
    public Boolean updateAuthor(Author author) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        ResultSet result;
        boolean updated = false;

        try {

            stmet = conn.prepareStatement("UPDATE authors SET authorName = ? WHERE codAuthor = ?;");

            stmet.setString(1, author.getAuthorName());
            stmet.setLong(2, author.getCodAuthor());

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
    public List<Author> getAuthorList() {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        ResultSet result;
        List<Author> authorList = new ArrayList();

        try {

            stmt = con.prepareStatement("SELECT * FROM authors");

            result = stmt.executeQuery();

            while (result.next()) {
                Long codAuthor = result.getLong("codAuthor");
                String authorName = result.getString("authorName");

                Author author = new Author(codAuthor, authorName);

                authorList.add(author);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdmUsersPageQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return authorList;
    }

}
