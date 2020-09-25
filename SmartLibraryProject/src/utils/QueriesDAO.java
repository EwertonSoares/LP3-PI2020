/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

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
import model.Author;
import model.Book;
import model.Genre;
import model.Publisher;
import model.User;
import model.UserAndBook;

/**
 *
 * @author ewerton
 */
public class QueriesDAO {
    

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
            Logger.getLogger(QueriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return user;
    }

    
    
    public boolean verifyLoginAndPassword(String email, String password, String userType) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        ResultSet result;
        boolean check = false;

        try {

            stmt = con.prepareStatement("SELECT * FROM users WHERE email = ? AND userPassword = ? AND userType = ?");
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
            Logger.getLogger(QueriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return saved;
    }

    
    
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
            Logger.getLogger(QueriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return saved;
    }

    
    
    public List<Book> getBookList() {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        Utils utils = new Utils();
        ResultSet result;
        List<Book> bookList = new ArrayList();

        try {

            stmt = con.prepareStatement("SELECT * FROM vw_all_book_information");

            result = stmt.executeQuery();

            while (result.next()) {
                Long codBook = result.getLong("codBook");
                String bookName = result.getString("bookName");
                String genre = result.getString("genre");
                String publiser = result.getString("publisher");
                String author = result.getString("authorName");
                Float price = result.getFloat("price");
                Long quantity = result.getLong("quantity");
                Date releaseDate = result.getDate("releaseDate");
                String formatedReleaseDate = utils.formatDate(releaseDate);

                Date expectedDate = result.getDate("expectedDate");
                String formatedEspectedDate = utils.formatDate(expectedDate);

                Date returnDate = result.getDate("returnDate");
                String formatedReurnDate = utils.formatDate(returnDate);

                Book book = new Book(codBook, bookName, genre, publiser, author, price,
                        formatedReleaseDate, formatedReurnDate, formatedEspectedDate, quantity);

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
            Logger.getLogger(QueriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return userList;
    }

    
    
    public boolean removeBook(Long codBook) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        ResultSet result;
        boolean removed = false;

        try {

            stmet = conn.prepareStatement("DELETE FROM books WHERE codBook = ?");
            stmet.setLong(1, codBook);

            removed = stmet.execute();

            if (!removed) {
                removed = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return removed;
    }

    
    
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
            Logger.getLogger(QueriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return removed;
    }

    
    
    public boolean updateBook(Book book) {

        Utils utils = new Utils();

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        ResultSet result;
        boolean updated = false;

        try {

            stmet = conn.prepareStatement("UPDATE books SET bookName = ?, price = ?, "
                    + "releaseDate = ?, expectedDate = ?, returnDate = ?, quantity = ? WHERE codBook = ?");

            stmet.setString(1, book.getBookName());
            stmet.setFloat(2, book.getPrice());
            stmet.setString(3, utils.formatDate(book.getReleaseDate()));
            stmet.setString(4, utils.formatDate(book.getExpectedDate()));
            stmet.setString(5, utils.formatDate(book.getReturnDate()));
            stmet.setLong(6, book.getQuantity());
            stmet.setLong(7, book.getCodBook());

            updated = stmet.execute();

            if (!updated) {
                updated = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return updated;
    }

    
    
    public List<Genre> getGenres() {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        ResultSet result;
        List<Genre> genreList = new ArrayList();

        try {

            stmet = conn.prepareStatement("SELECT * FROM genres");

            result = stmet.executeQuery();

            while (result.next()) {
                Long codGenre = result.getLong("codGenre");
                String genre = result.getString("genre");

                Genre genres = new Genre(codGenre, genre);

                genreList.add(genres);
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return genreList;
    }

    
    
    public List<Author> getAuthors() {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        ResultSet result;
        List<Author> authorList = new ArrayList();

        try {

            stmet = conn.prepareStatement("SELECT * FROM authors");

            result = stmet.executeQuery();

            while (result.next()) {
                Long codAuthor = result.getLong("codAuthor");
                String author = result.getString("authorName");

                Author authors = new Author(codAuthor, author);

                authorList.add(authors);
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return authorList;
    }

    
    
    public List<Publisher> getPublishers() {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        ResultSet result;
        List<Publisher> publisherList = new ArrayList();

        try {

            stmet = conn.prepareStatement("SELECT * FROM publishers");

            result = stmet.executeQuery();

            while (result.next()) {
                Long codPublisher = result.getLong("codPublisher");
                String publisher = result.getString("publisher");

                Publisher publishers = new Publisher(codPublisher, publisher);

                publisherList.add(publishers);
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return publisherList;
    }

    
    
    public boolean insertBook(String bookName, Long codAuthor, Long codGenre,
            Long codPublisher, Float price, Long quantity) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        ResultSet result;
        boolean saved = false;

        try {

            stmet = conn.prepareStatement("INSERT INTO books (bookName, codAuthor, codGenre, "
                    + "codPublisher, price, quantity) VALUES (?, ?, ?, ?, ?, ?);");

            stmet.setString(1, bookName);
            stmet.setLong(2, codAuthor);
            stmet.setLong(3, codGenre);
            stmet.setLong(4, codPublisher);
            stmet.setFloat(5, price);
            stmet.setFloat(6, quantity);

            saved = stmet.execute();

            if (!saved) {
                saved = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return saved;
    }

    
    
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

                UserAndBook userAndBooks = new UserAndBook(codUser, codBook, bookName,
                        releaseDate, expectedDate, returnDate, price, quantity);

                userAndBookList.add(userAndBooks);
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return userAndBookList;
    }

    
    
    public Long getCodUser(String email, String password) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmet = null;

        ResultSet result;
        Long codUser = null;

        try {

            stmet = conn.prepareStatement("SELECT codUser FROM users WHERE email = ? "
                    + "AND userPassword = ? AND userType = ?");

            stmet.setString(1, email);
            stmet.setString(2, password);
            stmet.setString(3, "user");

            result = stmet.executeQuery();

            while (result.next()) {
                codUser = result.getLong("codUser");
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return codUser;
    }
}
