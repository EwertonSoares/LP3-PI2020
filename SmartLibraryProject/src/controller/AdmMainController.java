/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 *
 * @author ewerton
 */
public class AdmMainController implements Initializable {

    @FXML
    private Text txtStr;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void admPage(String text) {
        String welcome = "bem vindo ";
        this.txtStr.setText(welcome.concat(text).toUpperCase());
    }

}
