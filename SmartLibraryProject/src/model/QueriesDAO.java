/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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

/**
 *
 * @author ewerton
 */
public class QueriesDAO {

    Connection con = ConnectionFactory.getConnection();
    PreparedStatement stmt = null;

    public boolean verifyLoginAndPassword(String email, String password, String userType) {
        ResultSet result;
        boolean check = false;

        try {

            stmt = con.prepareStatement("SELECT * FROM users WHERE email = ? AND pwd = ? AND userType = ?");
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, userType);

            result = stmt.executeQuery();

            if (result.next()) {
                check = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return check;
    }

    public boolean registerUser(String userName, String password, String userType,
            String email, String phone, String mobilePhone) {

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

            System.out.println("Bla");

            if (!saved) {
                saved = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return saved;
    }

    public boolean setNewPassword(String email, String newPassword, String userType) {

        ResultSet result;
        boolean saved = false;

        try {

            stmt = con.prepareStatement("UPDATE users SET pwd = ? WHERE email = ? AND userType = ?");

            stmt.setString(1, newPassword);
            stmt.setString(2, email);
            stmt.setString(3, email);

            saved = stmt.execute();

            if (!saved) {
                saved = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return saved;
    }

    public List<Book> getBookList() {

        ResultSet result;
        List<Book> bookList = new ArrayList();

        try {

            stmt = con.prepareStatement("SELECT * FROM vw_all_book_information");

            result = stmt.executeQuery();

            while (result.next()) {
                String bookName = result.getString("Nome do livro");
                String genre = result.getString("Gênero");
                String publiser = result.getString("Editora");
                String author = result.getString("Autor");
                Float price = result.getFloat("Preço");
                Date releaseDate = result.getDate("Data do emprestimo");
                Date expectedDate = result.getDate("Data esperada da devolução");
                Date returnDate = result.getDate("Data real da devolução");

                Book book = new Book(bookName, genre, publiser, author, price,
                        releaseDate, expectedDate, returnDate);

                bookList.add(book);

            }

        } catch (SQLException ex) {
            Logger.getLogger(QueriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return bookList;
    }

    public List<User> getUsersList() {

        ResultSet result;
        List<User> userList = new ArrayList();

        try {

            stmt = con.prepareStatement("SELECT * FROM vw_users_and_phones");

            result = stmt.executeQuery();

            while (result.next()) {
                String username = result.getString("username");
                String userType = result.getString("userType");
                String phone = result.getString("phone");
                String mobilePhone = result.getString("mobilePhone");
                String email = result.getString("email");

                User user = new User(username, userType, phone, mobilePhone, email);

                userList.add(user);

            }

        } catch (SQLException ex) {
            Logger.getLogger(QueriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return userList;
    }

}
