/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.fxml.Initializable;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Book;
import model.QueriesDAO;
import model.Utils;
import screens.Login;

/**
 *
 * @author ewerton
 */
public class AdmTablePageController implements Initializable {

    @FXML
    private TableView<Book> tableViewBooks;

    @FXML
    private TableColumn<Book, Date> clnExRetDate;

    @FXML
    private TableColumn<Book, Date> clnReaDate;

    @FXML
    private TableColumn<Book, Date> clnRetDate;

    @FXML
    private TableColumn<Book, BigDecimal> clnPrice;

    @FXML
    private TableColumn<Book, String> clnBookName;

    @FXML
    private TableColumn<Book, String> clnGenre;

    @FXML
    private TableColumn<Book, String> clnPub;

    @FXML
    private TableColumn<Book, String> clnAut;

    private ObservableList<Book> observableBookList;

    QueriesDAO queriesDAO = new QueriesDAO();
    Utils utils = new Utils();

    /**
     *
     * @param event
     */
    @FXML
    public void showAdminArea(ActionEvent event) {

    }

    public void closeAdminPageMain() {
        try {
            Login login = new Login();
            login.start(new Stage());

//            Stage stage = (Stage) btnCancelar.getScene().getWindow();
//            stage.close();
        } catch (Exception ex) {
            Logger.getLogger(AdmTablePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.clnBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        this.clnGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        this.clnPub.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        this.clnAut.setCellValueFactory(new PropertyValueFactory<>("author"));
        this.clnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.clnReaDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        this.clnExRetDate.setCellValueFactory(new PropertyValueFactory<>("expectedReturnDate"));
        this.clnRetDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        List<Book> bookList = queriesDAO.getBookList();

        this.observableBookList = FXCollections.observableArrayList(bookList);

        tableViewBooks.setItems(observableBookList);
    }

}
