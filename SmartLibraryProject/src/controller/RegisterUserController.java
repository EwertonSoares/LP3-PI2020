/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.QueriesDAO;
import utils.Utils;
import screens.Login;
import screens.RegisterUser;

/**
 *
 * @author ewerton
 */
public class RegisterUserController {

    @FXML
    private TextField txtPhone;

    @FXML
    private Button btnEnviar;

    @FXML
    private Button btnBack;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtMobilePhone;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    QueriesDAO loginDAO = new QueriesDAO();
    Utils utils = new Utils();

    @FXML
    private void registerNewUser(ActionEvent event) {
        try {
            boolean registered = false;
            String empty = "";

            this.checkPassword();

            if (this.txtName.getText().compareTo(empty) == 0
                    || this.txtPassword.getText().compareTo(empty) == 0
                    || this.txtEmail.getText().compareTo(empty) == 0) {

                utils.showAlert("Erro", "Dados incorretos", "Campos obrigatorios vazio!", Alert.AlertType.ERROR);

                return;
            }

            registered = loginDAO.registerUser(txtName.getText(), txtPassword.getText(), "user",
                    txtEmail.getText(), txtPhone.getText(), txtMobilePhone.getText());

            utils.showAlert("Sucesso", "Dados salvos",
                    "Seu cadastro foi realizado com sucesso!", Alert.AlertType.INFORMATION);

            Login login = new Login();
            login.start(new Stage());

            this.closeRegisterUserScreen();

        } catch (Exception ex) {
            Logger.getLogger(RegisterUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void checkPassword() {
        if (this.txtPassword.getText().length() < 8) {
            utils.showAlert("Atenção", "Dados incorretos", "Senha deve ter no minimo 8 caracteries!",
                    Alert.AlertType.WARNING);

            this.reloadActualPage();
        }

        if (this.txtPassword.getText().compareTo(this.txtConfirmPassword.getText()) != 0) {
            utils.showAlert("Atenção", "Dados incorretos", "As senhas são diferentes!", Alert.AlertType.WARNING);

            this.reloadActualPage();

        }
    }

    private void closeRegisterUserScreen() {
        Stage stage = (Stage) btnEnviar.getScene().getWindow();
        stage.close();
    }

    private void reloadActualPage() {
        try {
            Stage stage = (Stage) btnEnviar.getScene().getWindow();
            stage.close();

            RegisterUser registerUser = new RegisterUser();
            registerUser.start(new Stage());
        } catch (Exception ex) {
            Logger.getLogger(RegisterUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void backToLoginPage() {
        try {
            Login login = new Login();
            login.start(new Stage());

            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            Logger.getLogger(ForgetPasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
