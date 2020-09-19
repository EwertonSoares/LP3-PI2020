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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.QueriesDAO;
import model.Utils;
import screens.AdmTablePage;
import screens.AdmUsersPage;

/**
 *
 * @author ewerton
 */
public class AdmMainController implements Initializable {

    @FXML
    private Text txtStr;

    @FXML
    private Button btnEnter;

    @FXML
    private RadioButton rdbConfigAdm;

    @FXML
    private RadioButton rdbUsers;

    @FXML
    private RadioButton rdbBooks;

    QueriesDAO login = new QueriesDAO();
    Utils utils = new Utils();

    @FXML
    public void selectNextAction(ActionEvent event) {
        this.changeScreen();
    }

    public void changeScreen() {
        if (rdbBooks.isSelected()) {
            try {
                AdmTablePage admTablePage = new AdmTablePage();
                admTablePage.start(new Stage());

            } catch (Exception ex) {
                Logger.getLogger(AdmMainController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (rdbUsers.isSelected()) {

            try {
                AdmUsersPage admUsersPage = new AdmUsersPage();
                admUsersPage.start(new Stage());

            } catch (Exception ex) {
                Logger.getLogger(AdmMainController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (rdbConfigAdm.isSelected()) {

        } else {
            utils.showAlert("ERRO", "Topico não selecionada",
                    "Você deve escolher um topico!", Alert.AlertType.ERROR);
        }
    }

    public void checkRdbConfigAdm() {

        if (this.rdbConfigAdm.isSelected()) {
            this.rdbUsers.setDisable(true);
            this.rdbBooks.setDisable(true);
        }

        if (!this.rdbConfigAdm.isSelected()) {
            this.rdbUsers.setDisable(false);
            this.rdbBooks.setDisable(false);
        }

    }

    public void checkRdbUsers() {
        if (this.rdbUsers.isSelected()) {
            this.rdbConfigAdm.setDisable(true);
            this.rdbBooks.setDisable(true);
        }

        if (!this.rdbUsers.isSelected()) {
            this.rdbConfigAdm.setDisable(false);
            this.rdbBooks.setDisable(false);
        }

    }

    public void checkRdbBooks() {
        if (this.rdbBooks.isSelected()) {
            this.rdbUsers.setDisable(true);
            this.rdbConfigAdm.setDisable(true);
        }

        if (!this.rdbBooks.isSelected()) {
            this.rdbUsers.setDisable(false);
            this.rdbConfigAdm.setDisable(false);
        }
    }

    public void setInitialText(String text) {
        String welcome = "bem vindo a smart library ";

        this.txtStr.setText(welcome.concat(text).toUpperCase());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
