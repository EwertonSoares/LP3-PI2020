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
import model.Publisher;
import model.User;

/**
 *
 * @author ewerton
 */
public class PublisherQuery {

    /*
    
     */
    public boolean insertPublisher(String publisher) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        boolean saved = false;

        try {

            stmt = con.prepareStatement("INSERT INTO publishers (genre) VALUES(?)");
            stmt.setString(1, publisher);

            saved = stmt.execute();

            if (!saved) {
                saved = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(PublisherQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return saved;
    }

    /*
    
     */
    public boolean removePublisher(Long codPublisher) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        boolean removed = false;

        try {

            stmet = conn.prepareStatement("DELETE FROM publishers WHERE codPublisher = ?");
            stmet.setLong(1, codPublisher);

            removed = stmet.execute();

            if (!removed) {
                removed = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(PublisherQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return removed;
    }

    /*
    
     */
    public Boolean updatePublisher(Publisher publisher) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        boolean updated = false;

        try {

            stmet = conn.prepareStatement("UPDATE publishers SET publisher = ?"
                    + " WHERE codPublisher = ?;");

            stmet.setString(1, publisher.getPublisher());
            stmet.setLong(2, publisher.getCodPublisher());

            updated = stmet.execute();

            if (!updated) {
                updated = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(PublisherQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return updated;
    }

    /*
    
     */
    public List<Publisher> getPublisherList() {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        ResultSet result;
        List<Publisher> publisherList = new ArrayList();

        try {

            stmt = con.prepareStatement("SELECT * FROM publishers");

            result = stmt.executeQuery();

            while (result.next()) {
                Long codPublisher = result.getLong("codPublisher");
                String publishers = result.getString("publisher");

                Publisher publisher = new Publisher(codPublisher, publishers);

                publisherList.add(publisher);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PublisherQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return publisherList;
    }

}
