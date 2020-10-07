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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import model.Author;
import query.AuthorQuery;
import utils.Utils;
import screens.AdmTablePage;
import screens.AuthorTablePage;
import screens.GenreTablePage;

/**
 *
 * @author ewerton
 */
public class AuthorTablePageController implements Initializable {

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
    private final AuthorQuery authorQuery = new AuthorQuery();
    private final Utils utils = new Utils();
    private ObservableList<Author> observableAuthorList;

    /**
     *
     * @param event
     */
    @FXML
    private void updateAuthor(ActionEvent event) {
        boolean updated = false;
        Author author = this.tableViewAuthor.getSelectionModel().getSelectedItem();

        updated = this.authorQuery.updateAuthor(author);
        if (updated) {
            utils.showAlert("Sucesso", "Autor atualizado", "O autor foi atualizado com sucesso",
                    Alert.AlertType.INFORMATION);
        } else {
            utils.showAlert("Erro", "Erro ao atualizar", "Algo inesperado ocorreu!",
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void InsertAuthor(ActionEvent event) {
        String empty = "";
        boolean inserted = false;

        if (this.txtAut.getText().compareTo(empty) == 0) {
            utils.showAlert("Atençao", "Campo obrigatório!",
                    "Autor é uma campo obrigatório!'",
                    Alert.AlertType.INFORMATION);
            
            return;
        }

        inserted = this.authorQuery.insertAuthor(this.txtAut.getText());
        if (inserted) {
            utils.showAlert("Sucesso", "Autor inserido", "O autor foi inserido com sucesso",
                    Alert.AlertType.INFORMATION);

        } else {
            utils.showAlert("Erro", "Algo inesperado ocorreu", "Erro a o inserir o autor",
                    Alert.AlertType.ERROR);

            this.txtAut.setText("");
        }
    }

    @FXML
    private void removeAuthor(ActionEvent event) {
        Author author = this.tableViewAuthor.getSelectionModel().getSelectedItem();

        boolean removed = this.authorQuery.removeAuthor(author.getCodAuthor());
        if (removed) {
            this.tableViewAuthor.getItems().remove(author);
            utils.showAlert("Sucesso", "Autor removido", "O autor foi removido com sucesso!",
                    Alert.AlertType.INFORMATION);
        } else {
            utils.showAlert("ERRO", "Tente novamente", "Algo inesperado ocorreu!",
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void getAuthor(CellEditEvent editcell) {
        this.authorSelected = tableViewAuthor.getSelectionModel().getSelectedItem();
        this.authorSelected.setAuthorName(editcell.getNewValue().toString());
    }

    @FXML
    public void reloadBookTable() {
        try {
            Stage stage = (Stage) btnReload.getScene().getWindow();
            stage.close();

            AuthorTablePage authorTablePage = new AuthorTablePage();
            authorTablePage.start(new Stage());

        } catch (Exception ex) {
            Logger.getLogger(AuthorTablePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void closeActualPage() {
        try {
            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            Logger.getLogger(AuthorTablePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadAuthorTable() {

        //Preenchendo tabela
        this.clnCodAuthor.setCellValueFactory(new PropertyValueFactory<>("codAuthor"));
        this.clnAuthor.setCellValueFactory(new PropertyValueFactory<>("authorName"));

        List<Author> authorList = this.authorQuery.getAuthorList();

        this.observableAuthorList = FXCollections.observableArrayList(authorList);
        this.tableViewAuthor.setItems(observableAuthorList);

        //Tornando colunas editavel
        this.tableViewAuthor.setEditable(true);
        this.clnAuthor.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadAuthorTable();
    }
}
