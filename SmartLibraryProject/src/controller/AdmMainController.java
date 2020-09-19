/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;
import model.QueriesDAO;
import model.Utils;

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
    private RadioButton rdbCreateAdm;

    @FXML
    private RadioButton rdbUsers;

    @FXML
    private RadioButton rdbBooks;

    QueriesDAO login = new QueriesDAO();
    Utils utils = new Utils();

    @FXML
    public void selectNextAction(ActionEvent event) {

    }

    public void checkRdbCreateAdm() {

        if (this.rdbCreateAdm.isSelected()) {
            this.rdbUsers.setDisable(true);
            this.rdbBooks.setDisable(true);
        }

        if (!this.rdbCreateAdm.isSelected()) {
            this.rdbUsers.setDisable(false);
            this.rdbBooks.setDisable(false);
        }

    }

    public void checkRdbUsers() {
        if (this.rdbUsers.isSelected()) {
            this.rdbCreateAdm.setDisable(true);
            this.rdbBooks.setDisable(true);
        }

        if (!this.rdbUsers.isSelected()) {
            this.rdbCreateAdm.setDisable(false);
            this.rdbBooks.setDisable(false);
        }

    }

    public void checkRdbBooks() {
        if (this.rdbBooks.isSelected()) {
            this.rdbUsers.setDisable(true);
            this.rdbCreateAdm.setDisable(true);
        }

        if (!this.rdbBooks.isSelected()) {
            this.rdbUsers.setDisable(false);
            this.rdbCreateAdm.setDisable(false);
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
