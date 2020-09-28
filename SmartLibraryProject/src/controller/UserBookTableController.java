/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.UserAndBook;
import utils.QueriesDAO;
import utils.Utils;

/**
 *
 * @author ewerton
 */
public class UserBookTableController implements Initializable {

    @FXML
    private TableView<UserAndBook> tableBooksUserBooks;

    @FXML
    private TableColumn<UserAndBook, String> clnBookName;

    @FXML
    private TableColumn<UserAndBook, Float> clnPrice;

    @FXML
    private TableColumn<UserAndBook, Date> clnReaDate;

    @FXML
    private TableColumn<UserAndBook, Date> clnRetDate;

    @FXML
    private TableColumn<UserAndBook, Date> clnExpectedDate;

    @FXML
    private TableColumn<UserAndBook, Long> clnCodBook;

    @FXML
    private TableColumn<UserAndBook, Long> clnquantity;

    @FXML
    private Button btnBack;

    private Long codUser;

    private final QueriesDAO queriesDAO = new QueriesDAO();
    private final Utils utils = new Utils();
    private ObservableList<UserAndBook> observableUserAndBookList;

    private void loadUserAndBookTable() {

        this.clnCodBook.setCellValueFactory(new PropertyValueFactory<>("codBook"));
        this.clnBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        this.clnReaDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        this.clnExpectedDate.setCellValueFactory(new PropertyValueFactory<>("expectedDate"));
        this.clnRetDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        this.clnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.clnquantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    public void setDataTable(Long id) {
        List<UserAndBook> userAndBookList = queriesDAO.getUsersAndBook(id);

        this.observableUserAndBookList = FXCollections.observableArrayList(userAndBookList);
        this.tableBooksUserBooks.setItems(this.observableUserAndBookList);
    }

    @FXML
    public void closeActualPage(ActionEvent event) {
        try {

            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.close();

        } catch (Exception ex) {
            Logger.getLogger(ForgetPasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setid(Long id) {
        this.codUser = id;
        this.setDataTable(this.gettid());
    }

    public Long gettid() {
        return this.codUser;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loadUserAndBookTable();
    }

}
