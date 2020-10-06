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
import model.Author;
import model.Book;
import model.Genre;
import model.Publisher;
import utils.Utils;

/**
 *
 * @author ewerton
 */
public class AdmTablePageQuery {

    /*
    
     */
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
            Logger.getLogger(AdmTablePageQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return updated;
    }

    /*
    
     */
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
            Logger.getLogger(AdmTablePageQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return saved;
    }

    /*
    
     */
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
            Logger.getLogger(AdmTablePageQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return removed;
    }

    /*
    
     */
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
            Logger.getLogger(AdmTablePageQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return genreList;
    }

    /*
    
     */
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
            Logger.getLogger(AdmTablePageQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return authorList;
    }

    /*
    
     */
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
            Logger.getLogger(AdmTablePageQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmet);
        }

        return publisherList;
    }

    /*
    
     */
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
                Date expectedDate = result.getDate("expectedDate");
                Date returnDate = result.getDate("returnDate");

                Book book = new Book(codBook, bookName, genre, publiser, author, price,
                        releaseDate, returnDate, expectedDate, quantity);

                bookList.add(book);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdmTablePageQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return bookList;
    }

}
