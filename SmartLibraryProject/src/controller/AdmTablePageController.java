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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.FloatStringConverter;
import model.Book;
import model.QueriesDAO;
import model.Utils;

/**
 *
 * @author ewerton
 */
public class AdmTablePageController implements Initializable {

    @FXML
    private TableView<Book> tableViewBooks;

    @FXML
    private TableColumn<Book, Long> clnCodBook;

    @FXML
    private TableColumn<Book, Date> clnExpectedDate;

    @FXML
    private TableColumn<Book, Date> clnReaDate;

    @FXML
    private TableColumn<Book, Date> clnRetDate;

    @FXML
    private TableColumn<Book, Float> clnPrice;

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
    private void updateBook(ActionEvent event) {
        boolean updated = false;
        updated = queriesDAO.updateBook(bookSelected);
    }

//    public void closeAdminPageMain() {
//        try {
//            Login login = new Login();
//            login.start(new Stage());
//
////            Stage stage = (Stage) btnCancelar.getScene().getWindow();
////            stage.close();
//        } catch (Exception ex) {
//            Logger.getLogger(AdmTablePageController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadBooksTable();
    }

    private void loadBooksTable() {
        this.clnCodBook.setCellValueFactory(new PropertyValueFactory<>("codBook"));
        this.clnBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        this.clnGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        this.clnPub.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        this.clnAut.setCellValueFactory(new PropertyValueFactory<>("author"));
        this.clnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.clnReaDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        this.clnExpectedDate.setCellValueFactory(new PropertyValueFactory<>("expectedDate"));
        this.clnRetDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        List<Book> bookList = queriesDAO.getBookList();

        this.observableBookList = FXCollections.observableArrayList(bookList);

        this.tableViewBooks.setItems(observableBookList);

        tableViewBooks.setEditable(true);
        this.clnBookName.setCellFactory(TextFieldTableCell.forTableColumn());
        this.clnPrice.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        this.clnReaDate.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        this.clnRetDate.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
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
    public void getNewBookName(CellEditEvent editcell) {
        bookSelected = tableViewBooks.getSelectionModel().getSelectedItem();
        bookSelected.setBookName(editcell.getNewValue().toString());
    }

    @FXML
    public void getNewReleaseDate(CellEditEvent editcell) {
        bookSelected = tableViewBooks.getSelectionModel().getSelectedItem();
        bookSelected.setReleaseDate((Date) editcell.getNewValue());
    }

    @FXML
    public void getNewReturnDate(CellEditEvent editcell) {
        bookSelected = tableViewBooks.getSelectionModel().getSelectedItem();
        bookSelected.setReturnDate((Date) editcell.getNewValue());

    }

    @FXML
    public void getNewPrice(CellEditEvent editcell) {
        bookSelected = tableViewBooks.getSelectionModel().getSelectedItem();
        bookSelected.setPrice((Float) editcell.getNewValue());
    }


}
//
