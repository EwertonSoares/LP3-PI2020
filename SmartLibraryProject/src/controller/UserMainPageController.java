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
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import screens.Login;
import screens.UserTablePage;

/**
 *
 * @author ewerton
 */
public class UserMainPageController implements Initializable {

    @FXML
    private Text txtStr;

    @FXML
    private Button btnEnter;

    @FXML
    private RadioButton rdbBooks;

    @FXML
    private RadioButton rdbAltEmailPwd;

    private Long codUser;

    @FXML
    private Button btnClose;

    @FXML
    public void selectNextAction(ActionEvent event) {
        this.changeScreen();
    }

    public void changeScreen() {
        if (rdbBooks.isSelected()) {
            try {
                Long cod = this.getCodUser();

                this.callUserTablePage(cod);

            } catch (Exception ex) {
                Logger.getLogger(UserMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (rdbAltEmailPwd.isSelected()) {

            try {
//                AdmUsersPage admUsersPage = new AdmUsersPage();
//                admUsersPage.start(new Stage());

            } catch (Exception ex) {
                Logger.getLogger(UserMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void callUserTablePage(Long id) {
        try {

            UserTablePage userTablePage = new UserTablePage();
            userTablePage.setId(id);
            userTablePage.start(new Stage());

        } catch (Exception ex) {
            Logger.getLogger(UserMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setInitialText(String text) {
        String welcome = "bem vindo a smart library ";

        this.txtStr.setText(welcome.concat(text).toUpperCase());
    }

    public void setCodUser(Long id) {
        this.codUser = id;
    }

    public Long getCodUser() {
        return this.codUser;
    }

    @FXML
    public void checkRdbBooks() {
        if (this.rdbBooks.isSelected()) {
            this.rdbAltEmailPwd.setDisable(true);
        }

        if (!this.rdbBooks.isSelected()) {
            this.rdbAltEmailPwd.setDisable(false);
        }
    }

    @FXML
    public void checkRdbEmailPwd() {
        if (this.rdbAltEmailPwd.isSelected()) {
            this.rdbBooks.setDisable(true);
        }

        if (!this.rdbAltEmailPwd.isSelected()) {
            this.rdbBooks.setDisable(false);
        }

    }

    @FXML
    public void closeActualPage(ActionEvent event) {
        try {
            Login login = new Login();
            login.start(new Stage());

            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();

        } catch (Exception ex) {
            Logger.getLogger(ForgetPasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
