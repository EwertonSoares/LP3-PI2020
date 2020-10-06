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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import query.UpdateUserDataQuery;
import utils.Utils;

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
    private Button btnSalvar;

    @FXML
    private Button btnCancel;

    private final UpdateUserDataQuery updateUserDataQuery = new UpdateUserDataQuery();
    private final Utils utils = new Utils();

    @FXML
    public void updateUserData() {
        Boolean updated = false;
        User user = new User();

        user = this.fillUser();
        updated = this.updateUserDataQuery.updateUserData(user);

        if (updated) {
            utils.showAlert("Sucesso!", "Dados alterados", "Seus dados foram alterados com sucesso",
                    Alert.AlertType.INFORMATION);
        } else {
            utils.showAlert("Erro!", "Algo deu errado", "Algo insperado ocorreu, Tente novamente!",
                    Alert.AlertType.ERROR);
        }

    }

    @FXML
    public void closeActualPage(ActionEvent event) {
        try {

            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();

        } catch (Exception ex) {
            Logger.getLogger(ForgetPasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadUserDatas(Long id) {
        User user = new User();
        user = this.updateUserDataQuery.getUserDatas(id);

        this.txtInitText.setText("Perfil de " + user.getEmail());
        this.txtEmail.setText(user.getEmail());
        this.txtName.setText(user.getUserName());
        this.txtPhone.setText(user.getPhone());
        this.txtMobilePhone.setText(user.getMobilePhone());
    }

    public User fillUser() {
        User user = new User();

        user.setCodUser(this.getCodUser());
        user.setEmail(this.txtEmail.getText());
        user.setUserName(this.txtName.getText());
        user.setPhone(this.txtPhone.getText());
        user.setMobilePhone(this.txtMobilePhone.getText());

        return user;
    }

    public Long getCodUser() {
        return codUser;
    }

    public void setCodUser(Long codUser) {
        this.codUser = codUser;
        this.loadUserDatas(codUser);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
