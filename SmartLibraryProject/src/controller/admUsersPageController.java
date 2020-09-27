/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utils.QueriesDAO;
import model.User;
import screens.Login;
import utils.Utils;

/**
 *
 * @author ewerton
 */
public class admUsersPageController implements Initializable {

    @FXML
    private TableView<User> tableViewUsers;

    @FXML
    private TableColumn<User, String> clnUserName;

    @FXML
    private TableColumn<User, String> clnUserType;

    @FXML
    private TableColumn<User, String> clnphone;

    @FXML
    private TableColumn<User, String> clnMobPhone;

    @FXML
    private TableColumn<User, String> clnEmail;

    @FXML
    private Button btnClose;

    private ObservableList<User> observableUserList;

    QueriesDAO queriesDAO = new QueriesDAO();
    Utils utils = new Utils();

    @FXML
    public void selectNextAction(ActionEvent event) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loadUsersTable();
    }

    public void loadUsersTable() {
        this.clnUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        this.clnUserType.setCellValueFactory(new PropertyValueFactory<>("userType"));
        this.clnphone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        this.clnMobPhone.setCellValueFactory(new PropertyValueFactory<>("mobilePhone"));
        this.clnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        List<User> userList = queriesDAO.getUsersList();

        this.observableUserList = FXCollections.observableArrayList(userList);

        tableViewUsers.setItems(this.observableUserList);
    }

    @FXML
    public void removeUser() {
        User user = tableViewUsers.getSelectionModel().getSelectedItem();

        boolean removed = queriesDAO.removeUser(user.getCodUser());
        if (removed) {
            tableViewUsers.getItems().remove(user);
            utils.showAlert("Sucesso", "Usuario removido", "O usuario foi removido com sucesso!",
                    Alert.AlertType.INFORMATION);
        } else {
            utils.showAlert("ERRO", "Tente novamente", "Algo inesperado ocorreu!",
                    Alert.AlertType.ERROR);
        }
    }

    public void closeActualPage() {
        try {
            Login login = new Login();
            login.start(new Stage());
            
            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            Logger.getLogger(AdmTablePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
