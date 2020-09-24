/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javafx.scene.control.Alert;

/**
 *
 * @author ewerton
 */
public class Utils {

    public Utils() {
    }

    public void showAlert(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public String formatDateToBr(Date date) {
        if(date == null) {
            return null;
        }
        
        SimpleDateFormat formatedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        String result = formatedDate.format(date);
        
        return result;
    }
}
