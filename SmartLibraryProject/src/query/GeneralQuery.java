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
import model.UserAndBook;

/**
 *
 * @author ewerton
 */
public class GeneralQuery {

    public boolean setNewPassword(String email, String newPassword, String userType) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        ResultSet result;
        boolean saved = false;

        try {

            stmt = con.prepareStatement("UPDATE users SET userPassword = ? WHERE email = ? AND userType = ?");

            stmt.setString(1, newPassword);
            stmt.setString(2, email);
            stmt.setString(3, email);

            saved = stmt.execute();

            if (!saved) {
                saved = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(GeneralQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return saved;
    }

    /*
    
     */
    public boolean registerUser(String userName, String password, String userType,
            String email, String phone, String mobilePhone) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        ResultSet result;
        boolean saved = false;

        try {

            stmt = con.prepareStatement("CALL insertUserProcedure(?,?,?,?,?,?)");
            stmt.setString(1, userName);
            stmt.setString(2, password);
            stmt.setString(3, userType);
            stmt.setString(4, email);
            stmt.setString(5, phone);
            stmt.setString(6, mobilePhone);

            saved = stmt.execute();

            if (!saved) {
                saved = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(GeneralQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return saved;
    }
    
    /*
    
    */
    public List<UserAndBook> getUsersAndBook(Long cod) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        ResultSet result;
        List<UserAndBook> userAndBookList = new ArrayList();

        try {

            stmet = conn.prepareStatement("SELECT * FROM vw_users_books WHERE codUser= " + cod);
            result = stmet.executeQuery();

            while (result.next()) {
                Long codUser = result.getLong("codUser");
                Long codBook = result.getLong("codBook");
                String bookName = result.getString("bookName");
                Date releaseDate = result.getDate("releaseDate");
                Date expectedDate = result.getDate("expectedDate");
                Date returnDate = result.getDate("returnDate");
                Float price = result.getFloat("price");
                Long quantity = result.getLong("quantity");
//                String email = result.getString("email");

                UserAndBook userAndBooks = new UserAndBook(null, null, null, codBook, bookName,
                        price, releaseDate, returnDate, expectedDate, quantity, null);

                userAndBookList.add(userAndBooks);
            }

        } catch (SQLException ex) {
            Logger.getLogger(GeneralQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return userAndBookList;
    }

}
