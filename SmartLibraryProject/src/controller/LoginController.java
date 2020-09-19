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
import model.QueriesDAO;
import model.Utils;
import screens.AdmMainPage;
import screens.ForgetPassword;
import screens.RegisterUser;

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

        if (rdbAdm.isSelected()) {

            this.checkLoginAndPasswordField();

            checked = login.verifyLoginAndPassword(txtEmail.getText(), txtPassword.getText(), "adm");

            if (checked) {

                //CHAMAR TELA PRINCIPAL PARA ADM
                this.callAdmPageMain(txtEmail.getText().split("@")[0]);
                       
            } else {

                textError.setText("Usuario ou senha invalido!");
            }
        } else {
            this.checkLoginAndPasswordField();

            checked = login.verifyLoginAndPassword(txtEmail.getText(), txtPassword.getText(), "user");

            if (checked) {
                //CHAMAR TELA PRINCIPAL PARA USER
                // textError.setText("SUCESSO!!!");
            } else {
                textError.setText("Usuario ou senha invalido!");
            }
        }

    }
    
    @FXML
    private void callAdmPageMain(String text) {
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
