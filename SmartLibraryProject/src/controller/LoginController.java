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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.QueriesDAO;
import utils.Utils;
import screens.AdmMainPage;
import screens.ForgetPassword;
import screens.RegisterUser;
import screens.UserMainPage;

/**
 *
 * @author ewerton
 */
public class LoginController implements Initializable {

    @FXML
    private Text textError;

    @FXML
    private Button btnLogin;

    @FXML
    private RadioButton rdbAdm;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    QueriesDAO login = new QueriesDAO();
    Utils utils = new Utils();

    @FXML
    private void verifyLoginAndPassword(ActionEvent event) {
        boolean checked = false;

        if (this.rdbAdm.isSelected()) {

            this.checkLoginAndPasswordField();
            checked = login.verifyLoginAndPassword(this.txtEmail.getText(),
                    this.txtPassword.getText());

            if (checked) {

                this.callMainPage(this.txtEmail.getText().split("@")[0]);

            } else {

                textError.setText("Usuario ou senha invalido!");
                this.setTextEmpty();
            }
        } else {
            this.checkLoginAndPasswordField();
            checked = login.verifyLoginAndPassword(this.txtEmail.getText(),
                    this.txtPassword.getText());

            Long id = login.getCodUser(this.txtEmail.getText());

            if (checked) {

                this.callMainPage(this.txtEmail.getText().split("@")[0], id);

            } else {
                textError.setText("Usuario ou senha invalido!");
                this.setTextEmpty();
            }
        }

    }

    @FXML
    private void callMainPage(String text) {
        try {
            AdmMainPage admMainPage = new AdmMainPage();

            admMainPage.setText(text);
            admMainPage.start(new Stage());

            closeLoginScreen();
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void callMainPage(String text, Long id) {
        try {
            UserMainPage userMainPage = new UserMainPage();

            userMainPage.setText(text);
            userMainPage.setId(id);
            userMainPage.start(new Stage());

            closeLoginScreen();
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void callRegisterScreen(ActionEvent event) {
        try {
            RegisterUser register = new RegisterUser();

            register.start(new Stage());
            closeLoginScreen();
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void callForgetPasswordScreen(ActionEvent event) {
        ForgetPassword forgetPassword = new ForgetPassword();

        try {
            forgetPassword.start(new Stage());
            closeLoginScreen();

        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void checkLoginAndPasswordField() {
        String empty = "";

        if (this.txtEmail.getText().compareTo(empty) == 0
                || this.txtPassword.getText().compareTo(empty) == 0) {

            utils.showAlert("ERRO", "Erro ao tentar logar!", "Campos não devem estar em branco!", Alert.AlertType.INFORMATION);
        }
    }

    public void closeLoginScreen() {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }

    public void setTextEmpty() {
        this.txtEmail.setText("");
        this.txtPassword.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
