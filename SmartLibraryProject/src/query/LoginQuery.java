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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ewerton
 */
public class LoginQuery {
    
    /*
    
    */
    public boolean verifyLoginAndPassword(String email, String password, String userType) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        ResultSet result;
        boolean check = false;

        try {

            stmt = con.prepareStatement("SELECT * FROM users WHERE email = ? AND userPassword = ? "
                    + "AND userType = ?");

            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, userType);

            result = stmt.executeQuery();

            if (result.next()) {
                check = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return check;
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
            Logger.getLogger(LoginQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return codUser;
    }

}
