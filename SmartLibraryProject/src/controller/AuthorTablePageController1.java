/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.LongStringConverter;
import model.Author;
import model.Book;
import model.Genre;
import model.Publisher;
import query.AdmTablePageQuery;
import query.AuthorQuery;
import query.GenreQuery;
import utils.Utils;
import screens.AdmTablePage;
import screens.GenreTablePage;

/**
 *
 * @author ewerton
 */
public class AuthorTablePageController1 implements Initializable {

    @FXML
    private TableView<Author> tableViewAuthor;

    @FXML
    private TableColumn<Author, Long> clnCodAuthor;

    @FXML
    private TableColumn<Author, String> clnAuthor;

    @FXML
    private TextField txtAut;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnReload;

    private Author authorSelected;
    private final AuthorQuery genreQuery = new AuthorQuery();
    private final Utils utils = new Utils();
    private ObservableList<Author> observableAuthorList;

    /**
     *
     * @param event
     */
    @FXML
    private void updateGenre(ActionEvent event) {
        boolean updated = false;
        Author author = this.tableViewAuthor.getSelectionModel().getSelectedItem();

        updated = this.genreQuery.updateGenre(author);
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

        if (this.txtAut.getText().compareTo(empty) == 0) {
            utils.showAlert("Atençao", "Campos obrigatório!",
                    "Gênero é uma campo obrigatório!'",
                    Alert.AlertType.INFORMATION);
        }

        inserted = this.genreQuery.insertGenre(this.txtAut.getText());

        if (inserted) {
            utils.showAlert("Sucesso", "Gênero inserido", "O gênero foi inserido com sucesso",
                    Alert.AlertType.INFORMATION);

        } else {
            utils.showAlert("Erro", "Algo inesperado ocorreu", "Erro a o inserir o gênero",
                    Alert.AlertType.ERROR);

            this.txtAut.setText("");
        }
    }

    @FXML
    private void removeGenre(ActionEvent event) {
        Author author = this.tableViewAuthor.getSelectionModel().getSelectedItem();

        boolean removed = this.genreQuery.removeGenre(author.getCodAuthor());
        if (removed) {
            this.tableViewAuthor.getItems().remove(genre);
            utils.showAlert("Sucesso", "Gênero removido", "O gênero foi removido com sucesso!",
                    Alert.AlertType.INFORMATION);
        } else {
            utils.showAlert("ERRO", "Tente novamente", "Algo inesperado ocorreu!",
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void getGenre(CellEditEvent editcell) {
        this.authorSelected = tableViewAuthor.getSelectionModel().getSelectedItem();
        this.authorSelected.setAuthorName(editcell.getNewValue().toString());
    }

    public void reloadBookTable() {
        try {
            Stage stage = (Stage) btnReload.getScene().getWindow();
            stage.close();

            GenreTablePage genreTablePage = new GenreTablePage();
            genreTablePage.start(new Stage());

        } catch (Exception ex) {
            Logger.getLogger(AuthorTablePageController1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeActualPage() {
        try {
            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            Logger.getLogger(AuthorTablePageController1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadGenreTable() {

        //Preenchendo tabela
        this.clnCodAuthor.setCellValueFactory(new PropertyValueFactory<>("codAuthor"));
        this.clnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));

        List<Author> authorList = this.genreQuery.getGenreList();

        this.observableAuthorList = FXCollections.observableArrayList(authorList);
        this.tableViewAuthor.setItems(observableAuthorList);

        //Tornando colunas editavel
        this.tableViewAuthor.setEditable(true);
        this.clnAuthor.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadGenreTable();
    }
}
