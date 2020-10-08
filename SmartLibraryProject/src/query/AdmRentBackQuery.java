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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.CheckBox;
import model.UserAndBook;

/**
 *
 * @author ewerton
 */
public class AdmRentBackQuery {

    /*
    
     */
    public Long getNumberBooksRented(Long codUser) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        ResultSet result;
        Long numBooks = 0L;

        try {

            stmet = conn.prepareStatement("SELECT SUM(quantity) FROM users_and_books WHERE codUser = ?;");

            stmet.setLong(1, codUser);

            result = stmet.executeQuery();

            while (result.next()) {
                numBooks = result.getLong("SUM(quantity)");
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdmRentBackQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return numBooks;
    }

    /*
    
     */
    public boolean checkBookRented(Long codUser, Long codBook) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        ResultSet result;
        boolean check = false;

        try {

            stmet = conn.prepareStatement("SELECT codBook, codUser  FROM users_and_books"
                    + " WHERE codUser = ? AND codBook =?;");

            stmet.setLong(1, codUser);
            stmet.setLong(2, codBook);

            result = stmet.executeQuery();

            if (result.next()) {
                check = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdmRentBackQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return check;
    }

    /*
    
     */
    public Long getCodUserBook(Long codUser, Long codBook) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        ResultSet result;
        Long cod = 0L;

        try {

            stmet = conn.prepareStatement("SELECT codUserBook FROM users_and_books"
                    + " WHERE codUser = ? AND codBook =?;");

            stmet.setLong(1, codUser);
            stmet.setLong(2, codBook);

            result = stmet.executeQuery();

            while (result.next()) {
                cod = result.getLong("codUserBook");
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdmRentBackQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return cod;
    }

    /*
    
     */
    public boolean reserveBook(Long codUser, Long codBook, Long qtd) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        ResultSet result;
        boolean reserved = false;

        try {

            stmet = conn.prepareStatement("CALL rentBooksProcedure(?,?,?);");

            stmet.setLong(1, codUser);
            stmet.setLong(2, codBook);
            stmet.setLong(3, qtd);

            reserved = stmet.execute();

            if (reserved == false) {
                reserved = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdmRentBackQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return reserved;
    }

    /*
    
     */
    public Float getDelayValue(Long codUser, Long codBook, Long qtt) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        ResultSet result;
        Float value = 0F;

        try {

            stmet = conn.prepareStatement("SELECT calculatesDelayValue(?, ?, ?) AS 'value'");

            stmet.setLong(1, codUser);
            stmet.setLong(2, codBook);
            stmet.setLong(3, qtt);

            result = stmet.executeQuery();

            while (result.next()) {
                value = result.getFloat("value");
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdmRentBackQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return value;
    }

    /*
    
     */
    public boolean returnBook(Long codUser, Long codBook, Long qtd) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        boolean returned = false;

        try {

            stmet = conn.prepareStatement("CALL returnBooksProcedure(?,?,?);");

            stmet.setLong(1, codUser);
            stmet.setLong(2, codBook);
            stmet.setLong(3, qtd);

            returned = stmet.execute();

            if (!returned) {
                returned = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdmRentBackQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return returned;
    }

    /*
    
     */
    public List<UserAndBook> getBooksToUserAndBook() {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        ResultSet result;
        List<UserAndBook> userAndBookList = new ArrayList();

        try {

            stmt = con.prepareStatement("SELECT * FROM books");

            result = stmt.executeQuery();

            while (result.next()) {
                Long codBook = result.getLong("codBook");
                String bookName = result.getString("bookName");
                Float price = result.getFloat("price");
                Long quantity = result.getLong("quantity");
                Date releaseDate = result.getDate("releaseDate");
                Date expectedDate = result.getDate("expectedDate");
                Date returnDate = result.getDate("returnDate");

                UserAndBook userAndBooks = new UserAndBook(null, null, null, codBook, bookName,
                        price, releaseDate, returnDate, expectedDate, quantity, new CheckBox());

                userAndBookList.add(userAndBooks);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdmRentBackQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return userAndBookList;
    }

    /*
    
     */
    public Long getCodUser(String email) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        ResultSet result;
        Long codUser = 0L;

        try {

            stmet = conn.prepareStatement("SELECT codUser FROM users WHERE email = ? ");

            stmet.setString(1, email);

            result = stmet.executeQuery();

            while (result.next()) {
                codUser = result.getLong("codUser");
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdmRentBackQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return codUser;
    }

    /*
    
     */
    public Long checkIsBookReserved(Long codUser, Long codBook) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        ResultSet result;
        Long quantity = 0L;

        try {

            stmet = conn.prepareStatement("SELECT quantity FROM users_and_books WHERE codUser = ?"
                    + " AND codBook = ?;");

            stmet.setLong(1, codUser);
            stmet.setLong(2, codBook);

            result = stmet.executeQuery();

            while (result.next()) {
                quantity = result.getLong("quantity");
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdmRentBackQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return quantity;
    }

}
