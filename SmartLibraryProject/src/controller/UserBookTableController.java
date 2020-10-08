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
import query.GeneralQuery;
import screens.AdmMainPage;
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
    private Button btnBack;

    private Long codUser;
    private String userType;

    private final GeneralQuery generalQuery = new GeneralQuery();
    private final Utils utils = new Utils();
    private ObservableList<UserAndBook> observableUserAndBookList;

    private void loadUserAndBookTable() {

        this.clnCodBook.setCellValueFactory(new PropertyValueFactory<>("codBook"));
        this.clnBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        this.clnReaDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        this.clnExpectedDate.setCellValueFactory(new PropertyValueFactory<>("expectedDate"));
        this.clnRetDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        this.clnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void setDataTable(Long id) {
        List<UserAndBook> userAndBookList = this.generalQuery.getUsersAndBook(id);

        this.observableUserAndBookList = FXCollections.observableArrayList(userAndBookList);
        this.tableBooksUserBooks.setItems(this.observableUserAndBookList);
    }

    @FXML
    public void closeActualPage(ActionEvent event) {
        String user = "user";
        Stage stage = (Stage) btnBack.getScene().getWindow();

        if (this.getUserType().compareTo(user) == 0) {
            stage.close();
        } else {
            try {
                stage.close();

                AdmMainPage admMainPage = new AdmMainPage();
                admMainPage.start(new Stage());
            } catch (Exception ex) {
                Logger.getLogger(UserBookTableController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public Long getCodUser() {
        return codUser;
    }

    public void setCodUser(Long codUser) {
        this.codUser = codUser;
        this.setDataTable(this.getCodUser());

    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        String user = "";
        if(userType == null) {
            user = "adm";
        }
        
        this.userType = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loadUserAndBookTable();
    }

}
