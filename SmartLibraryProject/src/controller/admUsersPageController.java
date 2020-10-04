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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.LongStringConverter;
import utils.QueriesDAO;
import model.User;
import screens.AdmTablePage;
import screens.AdmUsersPage;
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
    private TextField txtUser;

    @FXML
    private TextField txtUserType;

    @FXML
    private TextField txtPwd;

    @FXML
    private TextField txtMobPhone;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnReload;

    private User userSelected;
    private ObservableList<User> observableUserList;

    QueriesDAO queriesDAO = new QueriesDAO();
    Utils utils = new Utils();

    private void loadUsersTable() {
        this.clnUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        this.clnUserType.setCellValueFactory(new PropertyValueFactory<>("userType"));
        this.clnphone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        this.clnMobPhone.setCellValueFactory(new PropertyValueFactory<>("mobilePhone"));
        this.clnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        List<User> userList = queriesDAO.getUsersList();

        this.observableUserList = FXCollections.observableArrayList(userList);

        tableViewUsers.setItems(this.observableUserList);

        //Tornando colunas editavel
        tableViewUsers.setEditable(true);
        this.clnUserName.setCellFactory(TextFieldTableCell.forTableColumn());
        this.clnUserType.setCellFactory(TextFieldTableCell.forTableColumn());
        this.clnphone.setCellFactory(TextFieldTableCell.forTableColumn());
        this.clnMobPhone.setCellFactory(TextFieldTableCell.forTableColumn());
        this.clnEmail.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    @FXML
    private void InsertUser(ActionEvent actionEvent) {
        String empty = "";
        boolean inserted = false;

        if (this.txtUser.getText().compareTo(empty) == 0
                || this.txtPwd.getText().compareTo(empty) == 0
                || this.txtUserType.getText().compareTo(empty) == 0
                || this.txtEmail.getText().compareTo(empty) == 0) {
            utils.showAlert("Atençao", "Campos obrigatório!",
                    "Os campos 'Nome do usuario',  'Senha', 'Tipo de usuario', 'E-mail' "
                    + "são obrigatórios para a inserção de um novo livro!",
                    Alert.AlertType.INFORMATION);
        }

        inserted = queriesDAO.registerUser(this.txtUser.getText(), this.txtPwd.getText(),
                this.txtUserType.getText(), this.txtEmail.getText(), this.txtPhone.getText(),
                this.txtMobPhone.getText());

        if (inserted) {
            utils.showAlert("Sucesso", "Usuario inserido", "O usuario foi inserido com sucesso",
                    Alert.AlertType.INFORMATION);

        } else {
            utils.showAlert("Erro", "Algo inesperado ocorreu", "Erro a o inserir o usuario",
                    Alert.AlertType.ERROR);

            this.txtUser.setText("");
            this.txtPwd.setText("");
            this.txtUserType.setText("");
            this.txtEmail.setText("");
            this.txtMobPhone.setText("");
        }
    }

    @FXML
    private void updateUser(ActionEvent event) {
        boolean updated = false;

        updated = queriesDAO.updateUserData(userSelected);
        if (updated) {
            utils.showAlert("Sucesso", "Usuario atualizado", "O usuario foi atualizado com sucesso",
                    Alert.AlertType.INFORMATION);
        } else {
            utils.showAlert("Erro", "Erro ao atualizar", "Algo inesperado ocorreu!",
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void removeUser() {
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

    public void reloadBookTable() {
        try {
            Stage stage = (Stage) btnReload.getScene().getWindow();
            stage.close();

            AdmUsersPage admUsersPage = new AdmUsersPage();
            admUsersPage.start(new Stage());
        } catch (Exception ex) {
            Logger.getLogger(AdmTablePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void getNewUserName(TableColumn.CellEditEvent editcell) {
        this.userSelected = tableViewUsers.getSelectionModel().getSelectedItem();
        this.userSelected.setUserName(editcell.getNewValue().toString());
    }

    @FXML
    public void getNewUserType(TableColumn.CellEditEvent editcell) {
        this.userSelected = tableViewUsers.getSelectionModel().getSelectedItem();
        this.userSelected.setUserType(editcell.getNewValue().toString());
    }

    @FXML
    public void getNewPhone(TableColumn.CellEditEvent editcell) {
        this.userSelected = tableViewUsers.getSelectionModel().getSelectedItem();
        this.userSelected.setPhone(editcell.getNewValue().toString());
    }

    @FXML
    public void getNewMobilePhone(TableColumn.CellEditEvent editcell) {
        this.userSelected = tableViewUsers.getSelectionModel().getSelectedItem();
        this.userSelected.setMobilePhone(editcell.getNewValue().toString());
    }

    @FXML
    public void getNewEmail(TableColumn.CellEditEvent editcell) {
        this.userSelected = tableViewUsers.getSelectionModel().getSelectedItem();
        this.userSelected.setEmail(editcell.getNewValue().toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loadUsersTable();
    }
}
