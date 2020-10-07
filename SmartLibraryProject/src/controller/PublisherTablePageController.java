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
import model.Publisher;
import query.AuthorQuery;
import query.PublisherQuery;
import utils.Utils;
import screens.AdmTablePage;
import screens.AuthorTablePage;
import screens.GenreTablePage;
import screens.PublisherTablePage;

/**
 *
 * @author ewerton
 */
public class PublisherTablePageController implements Initializable {

    @FXML
    private TableView<Publisher> tableViewPublisher;

    @FXML
    private TableColumn<Publisher, Long> clnCodPublisher;

    @FXML
    private TableColumn<Publisher, String> clnPublisher;

    @FXML
    private TextField txtPub;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnReload;

    private Publisher publisherSelected;
    private final PublisherQuery publisherQuery = new PublisherQuery();
    private final Utils utils = new Utils();
    private ObservableList<Publisher> observablePublisherList;

    /**
     *
     * @param event
     */
    @FXML
    private void updatePublisher(ActionEvent event) {
        boolean updated = false;
        Publisher publisher = this.tableViewPublisher.getSelectionModel().getSelectedItem();

        updated = this.publisherQuery.updatePublisher(publisher);
        if (updated) {
            utils.showAlert("Sucesso", "Editora atualizada", "A editora foi atualizado com sucesso",
                    Alert.AlertType.INFORMATION);
        } else {
            utils.showAlert("Erro", "Erro ao atualizar", "Algo inesperado ocorreu!",
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void InsertPublisher(ActionEvent event) {
        String empty = "";
        boolean inserted = false;

        if (this.txtPub.getText().compareTo(empty) == 0) {
            utils.showAlert("Atençao", "Campo obrigatório!",
                    "Editora é uma campo obrigatório!'",
                    Alert.AlertType.INFORMATION);
            
            return;
        }

        inserted = this.publisherQuery.insertPublisher(this.txtPub.getText());
        if (inserted) {
            utils.showAlert("Sucesso", "Editora inserida", "A editora foi inserida com sucesso",
                    Alert.AlertType.INFORMATION);

        } else {
            utils.showAlert("Erro", "Algo inesperado ocorreu", "Erro a o inserir o autor",
                    Alert.AlertType.ERROR);

            this.txtPub.setText("");
        }
    }

    @FXML
    private void removePublisher(ActionEvent event) {
        Publisher publisher = this.tableViewPublisher.getSelectionModel().getSelectedItem();

        boolean removed = this.publisherQuery.removePublisher(publisher.getCodPublisher());
        if (removed) {
            this.tableViewPublisher.getItems().remove(publisher);
            utils.showAlert("Sucesso", "Editora removido", "A editora foi removida com sucesso!",
                    Alert.AlertType.INFORMATION);
        } else {
            utils.showAlert("ERRO", "Tente novamente", "Algo inesperado ocorreu!",
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void getPublisher(CellEditEvent editcell) {
        this.publisherSelected = tableViewPublisher.getSelectionModel().getSelectedItem();
        this.publisherSelected.setPublisher(editcell.getNewValue().toString());
    }

    @FXML
    public void reloadPublisherTable() {
        try {
            Stage stage = (Stage) btnReload.getScene().getWindow();
            stage.close();

            PublisherTablePage publisherTablePage = new PublisherTablePage();
            publisherTablePage.start(new Stage());

        } catch (Exception ex) {
            Logger.getLogger(PublisherTablePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void closeActualPage() {
        try {
            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            Logger.getLogger(PublisherTablePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadPublisherTable() {

        //Preenchendo tabela
        this.clnCodPublisher.setCellValueFactory(new PropertyValueFactory<>("codPublisher"));
        this.clnPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));

        List<Publisher> publisherList = this.publisherQuery.getPublisherList();

        this.observablePublisherList= FXCollections.observableArrayList(publisherList);
        this.tableViewPublisher.setItems(observablePublisherList);

        //Tornando colunas editavel
        this.tableViewPublisher.setEditable(true);
        this.clnPublisher.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadPublisherTable();
    }
}
