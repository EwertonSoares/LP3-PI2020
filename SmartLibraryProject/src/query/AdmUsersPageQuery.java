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
import model.User;
/**
 *
 * @author ewerton
 */
public class AdmUsersPageQuery {

    /*
    
     */
    public List<User> getUsersList() {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        ResultSet result;
        List<User> userList = new ArrayList();

        try {

            stmt = con.prepareStatement("SELECT * FROM vw_users_and_phones");

            result = stmt.executeQuery();

            while (result.next()) {
                Long codUser = result.getLong("codUser");
                String username = result.getString("username");
                String userType = result.getString("userType");
                String phone = result.getString("phone");
                String mobilePhone = result.getString("mobilePhone");
                String email = result.getString("email");

                User user = new User(codUser, username, userType, phone, mobilePhone, email, null);

                userList.add(user);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdmUsersPageQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return userList;
    }

    /*
    
     */
    public boolean removeUser(Long codUser) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        ResultSet result;
        boolean removed = false;

        try {

            stmet = conn.prepareStatement("DELETE FROM users WHERE codUser = ?");
            stmet.setLong(1, codUser);

            removed = stmet.execute();

            if (!removed) {
                removed = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdmUsersPageQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return removed;
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
            Logger.getLogger(AdmUsersPageQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return saved;
    }
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
            Logger.getLogger(AdmUsersPageQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return updated;
    }

}
