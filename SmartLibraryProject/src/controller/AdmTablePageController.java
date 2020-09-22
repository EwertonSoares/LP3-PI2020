/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.fxml.Initializable;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.FloatStringConverter;
import model.Book;
import model.QueriesDAO;
import model.Utils;
import screens.Login;

/**
 *
 * @author ewerton
 */
public class AdmTablePageController implements Initializable {

    @FXML
    private TableView<Book> tableViewBooks;

    @FXML
    private TableColumn<Book, Date> clnExRetDate;

    @FXML
    private TableColumn<Book, Date> clnReaDate;

    @FXML
    private TableColumn<Book, Date> clnRetDate;

    @FXML
    private TableColumn<Book, BigDecimal> clnPrice;

    @FXML
    private TableColumn<Book, String> clnBookName;

    @FXML
    private TableColumn<Book, String> clnGenre;

    @FXML
    private TableColumn<Book, String> clnPub;

    @FXML
    private TableColumn<Book, String> clnAut;

    private Book bookSelected;

    private ObservableList<Book> observableBookList;

    private final QueriesDAO queriesDAO = new QueriesDAO();
    private final Utils utils = new Utils();

    /**
     *
     * @param event
     */
    @FXML
    public void showAdminArea(ActionEvent event) {

    }

    public void closeAdminPageMain() {
        try {
            Login login = new Login();
            login.start(new Stage());

//            Stage stage = (Stage) btnCancelar.getScene().getWindow();
//            stage.close();
        } catch (Exception ex) {
            Logger.getLogger(AdmTablePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadBooksTable();

//        tableViewBooks.getSelectionModel().selectedItemProperty().addListener(
//                (obervable, oldValue, newValue) -> selectedItem(newValue));
    }

    private void loadBooksTable() {
        this.clnBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        this.clnGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        this.clnPub.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        this.clnAut.setCellValueFactory(new PropertyValueFactory<>("author"));
        this.clnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.clnReaDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        this.clnExRetDate.setCellValueFactory(new PropertyValueFactory<>("expectedReturnDate"));
        this.clnRetDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        List<Book> bookList = queriesDAO.getBookList();

        this.observableBookList = FXCollections.observableArrayList(bookList);

        this.tableViewBooks.setItems(observableBookList);

        tableViewBooks.setEditable(true);
        this.clnBookName.setCellFactory(TextFieldTableCell.forTableColumn());
        this.clnGenre.setCellFactory(TextFieldTableCell.forTableColumn());
        this.clnPub.setCellFactory(TextFieldTableCell.forTableColumn());
        this.clnAut.setCellFactory(TextFieldTableCell.forTableColumn());
//        this.clnPrice.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    @FXML
    public void removeBook() {
        Book book = tableViewBooks.getSelectionModel().getSelectedItem();

        boolean removed = queriesDAO.removeBook(book.getCodBook());
        if (removed) {
            tableViewBooks.getItems().remove(book);
            utils.showAlert("Sucesso", "Livro removido", "O livro foi removido com sucesso!",
                    Alert.AlertType.INFORMATION);
        } else {
            utils.showAlert("ERRO", "Tente novamente", "Algo inesperado ocorreu!",
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void updateBookName(CellEditEvent editcell) {
        bookSelected = tableViewBooks.getSelectionModel().getSelectedItem();
        bookSelected.setBookName(editcell.getNewValue().toString());
    }

    @FXML
    public void updateGenre(CellEditEvent editcell) {
        bookSelected = tableViewBooks.getSelectionModel().getSelectedItem();
        bookSelected.setGenre(editcell.getNewValue().toString());
    }

    @FXML
    public void updatepublisher(CellEditEvent editcell) {
        bookSelected = tableViewBooks.getSelectionModel().getSelectedItem();
        bookSelected.setPublisher(editcell.getNewValue().toString());

    }

    @FXML
    public void updateAuthor(CellEditEvent editcell) {
        bookSelected = tableViewBooks.getSelectionModel().getSelectedItem();
        bookSelected.setAuthor(editcell.getNewValue().toString());
    }

    @FXML
    public void updatePrice(CellEditEvent editcell) {
        bookSelected = tableViewBooks.getSelectionModel().getSelectedItem();
        bookSelected.setPrice((Float) editcell.getNewValue());
    }

    
    private void updateBook() {
        
    }
}
//
