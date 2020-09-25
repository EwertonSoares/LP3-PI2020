/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import utils.QueriesDAO;

/**
 *
 * @author ewerton
 */
public class UserMyDatasController implements Initializable {

    private Long codUser;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtName;

    @FXML
    private Label txtInitText;

    @FXML
    private TextField txtMobilePhone;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancel;

    private final QueriesDAO queriesDAO = new QueriesDAO();

    @FXML
    public void closeActualPage(ActionEvent event) {
        try {

            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
//
        } catch (Exception ex) {
            Logger.getLogger(ForgetPasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Long getCodUser() {
        return codUser;
    }

    public void setCodUser(Long codUser) {
        this.codUser = codUser;
        this.loadUserDatas(codUser);
    }

    private void loadUserDatas(Long id) {
        User user = new User();
        user = queriesDAO.getUserDatas(id);
        
        this.txtInitText.setText("Perfil de " + user.getEmail());
        this.txtEmail.setText(user.getEmail());
        this.txtName.setText(user.getUserName());
        this.txtPhone.setText(user.getPhone());
        this.txtMobilePhone.setText(user.getMobilePhone());
        this.txtPassword.setText(user.getPassword());
    }
}
