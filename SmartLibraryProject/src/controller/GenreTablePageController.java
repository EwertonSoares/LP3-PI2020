/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import model.Genre;
import query.GenreQuery;
import utils.Utils;
import screens.GenreTablePage;

/**
 *
 * @author ewerton
 */
public class GenreTablePageController implements Initializable {

    @FXML
    private TableView<Genre> tableViewGenre;

    @FXML
    private TableColumn<Genre, Long> clnCodGenre;

    @FXML
    private TableColumn<Genre, String> clnGenre;

    @FXML
    private TextField txtGen;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnReload;

    private Genre genreSelected;
    private final GenreQuery genreQuery = new GenreQuery();
    private final Utils utils = new Utils();
    private ObservableList<Genre> observableGenreList;

    /**
     *
     * @param event
     */
    @FXML
    private void updateGenre(ActionEvent event) {
        boolean updated = false;
        Genre genre = this.tableViewGenre.getSelectionModel().getSelectedItem();

        updated = this.genreQuery.updateGenre(genre);
        if (updated) {
            utils.showAlert("Sucesso", "Gênero atualizado", "O gênero foi atualizado com sucesso",
                    Alert.AlertType.INFORMATION);
        } else {
            utils.showAlert("Erro", "Erro ao atualizar", "Algo inesperado ocorreu!",
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void InsertGenre(ActionEvent event) {
        String empty = "";
        boolean inserted = false;

        if (this.txtGen.getText().compareTo(empty) == 0) {
            utils.showAlert("Atençao", "Campos obrigatório!",
                    "Gênero é uma campo obrigatório!'",
                    Alert.AlertType.INFORMATION);
        }

        inserted = this.genreQuery.insertGenre(this.txtGen.getText());

        if (inserted) {
            utils.showAlert("Sucesso", "Gênero inserido", "O gênero foi inserido com sucesso",
                    Alert.AlertType.INFORMATION);

        } else {
            utils.showAlert("Erro", "Algo inesperado ocorreu", "Erro a o inserir o gênero",
                    Alert.AlertType.ERROR);

            this.txtGen.setText("");
        }
    }

    @FXML
    private void removeGenre(ActionEvent event) {
        Genre genre = this.tableViewGenre.getSelectionModel().getSelectedItem();

        boolean removed = this.genreQuery.removeGenre(genre.getCodGenre());
        if (removed) {
            this.tableViewGenre.getItems().remove(genre);
            utils.showAlert("Sucesso", "Gênero removido", "O gênero foi removido com sucesso!",
                    Alert.AlertType.INFORMATION);
        } else {
            utils.showAlert("ERRO", "Tente novamente", "Algo inesperado ocorreu!",
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void getGenre(CellEditEvent editcell) {
        this.genreSelected = tableViewGenre.getSelectionModel().getSelectedItem();
        this.genreSelected.setGenre(editcell.getNewValue().toString());
    }

    public void reloadBookTable() {
        try {
            Stage stage = (Stage) btnReload.getScene().getWindow();
            stage.close();

            GenreTablePage genreTablePage = new GenreTablePage();
            genreTablePage.start(new Stage());

        } catch (Exception ex) {
            Logger.getLogger(GenreTablePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeActualPage() {
        try {
            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            Logger.getLogger(GenreTablePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadGenreTable() {

        //Preenchendo tabela
        this.clnCodGenre.setCellValueFactory(new PropertyValueFactory<>("codGenre"));
        this.clnGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));

        List<Genre> genreList = this.genreQuery.getGenreList();

        this.observableGenreList = FXCollections.observableArrayList(genreList);
        this.tableViewGenre.setItems(observableGenreList);

        //Tornando colunas editavel
        this.tableViewGenre.setEditable(true);
        this.clnGenre.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadGenreTable();
    }
}
