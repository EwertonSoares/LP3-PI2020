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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.QueriesDAO;
import utils.Utils;
import screens.Login;

/**
 *
 * @author ewerton
 */
public class ForgetPasswordController {
    
    @FXML
    private Button btnEnviar;
    
    @FXML
    private PasswordField txtConfirmPassword;
    
    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private Button btnCancelar;
    
    @FXML
    private RadioButton rdbAdm;
    
    QueriesDAO setPassword = new QueriesDAO();
    Utils utils = new Utils();
    /**
     *
     * @param event
     */
    @FXML
    public void updatePassword(ActionEvent event) {
        boolean done = false;
        String empty = "";
        
        if (txtEmail.getText().compareTo(empty) == 0 || txtPassword.getText().compareTo(empty) == 0) {
            
           utils.showAlert("ATENÇÃO!", "Erro ao tentar logar!", "Campos não devem estar em branco!", Alert.AlertType.ERROR);

            return;
        }
        
        if (rdbAdm.isSelected()) {
            done = setPassword.setNewPassword(txtEmail.getText(), txtPassword.getText(), "adm");
            
        } else {
            done = setPassword.setNewPassword(txtEmail.getText(), txtPassword.getText(), "user");
        }
        
    }
    
    public void closeForgetPasswordScreen() {
        try {
            Login login = new Login();
            login.start(new Stage());
            
            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            Logger.getLogger(ForgetPasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
