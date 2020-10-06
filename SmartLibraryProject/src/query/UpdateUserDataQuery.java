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
import model.User;

/**
 *
 * @author ewerton
 */
public class UpdateUserDataQuery {

    /*
    
     */
    public Boolean updateUserData(User user) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        ResultSet result;
        boolean updated = false;

        try {

            stmet = conn.prepareStatement("CALL updateUserProcedure(?,?,?,?,?);");

            stmet.setLong(1, user.getCodUser());
            stmet.setString(2, user.getUserName());
            stmet.setString(3, user.getEmail());
            stmet.setString(4, user.getPhone());
            stmet.setString(5, user.getMobilePhone());

            updated = stmet.execute();

            if (!updated) {
                updated = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UpdateUserDataQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return updated;
    }

    /*
    
     */
    public User getUserDatas(Long id) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        ResultSet result;
        User user = new User();

        try {

            stmt = con.prepareStatement("SELECT * FROM vw_users_and_phones WHERE codUser = ?");
            stmt.setLong(1, id);

            result = stmt.executeQuery();

            while (result.next()) {
                String username = result.getString("username");
                String email = result.getString("email");
                String phone = result.getString("phone");
                String mobilePhone = result.getString("mobilePhone");
                String password = result.getString("userPassword");

                user.setCodUser(id);
                user.setUserName(username);
                user.setPhone(phone);
                user.setMobilePhone(mobilePhone);
                user.setEmail(email);
                user.setPassword(password);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UpdateUserDataQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return user;
    }

}
