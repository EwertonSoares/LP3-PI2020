/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.LongStringConverter;
import model.Book;
import model.UserAndBook;
import screens.AdmReleaseAndReturnBook;
import utils.QueriesDAO;
import utils.Utils;

/**
 *
 * @author ewerton
 */
public class AdmReleaseAndReturnBookController implements Initializable {

    @FXML
    private TableView<Book> tableViewUsersAndBooks;

    @FXML
    private TableColumn<Book, String> clnBookName;

    @FXML
    private TableColumn<Book, Float> clnPrice;

    @FXML
    private TableColumn<Book, String> clnUserEmail;

    @FXML
    private TableColumn<Book, Long> clnBookQuantity;

    @FXML
    private TableColumn<Book, Long> clnQtt;

    @FXML
    private TableColumn<Book, Long> clnCodBook;

    @FXML
    private Button btnReleaseBook;

    @FXML
    private Button btnClose;

    private QueriesDAO queriesDAO = new QueriesDAO();
    private Utils utils = new Utils();
    private List<Book> bookList = new ArrayList<>();
    private ObservableList<Book> observableUserAndBookList;
    private Book book = new Book();
    private String userEmail;
    private Long qtt;
    private Long codBook;
    private Long quantity;
    private String bookName;
    private Float price;

    /**
     *
     * @param event
     */
    @FXML
    private void reserveBook(ActionEvent event) {
        boolean reserved = false;
        Long codUser = queriesDAO.getCodUser(this.getUserEmail());

        this.checkQuantityBooks();
        reserved = queriesDAO.reserveBook(codUser, this.codBook, this.qtt);

        if (reserved) {
            utils.showAlert("Sucesso", "Livro reservado!",
                    this.bookName + " foi reservado para " + this.getUserEmail() + "VALOR TOTAL: "
                    + this.price * this.qtt,
                    Alert.AlertType.INFORMATION);
            
        } else {
            utils.showAlert("ERRO", "Algo inesperado ocorreu", ""
                    + "Não foi possivel reservar o livro, tente novamente!", Alert.AlertType.ERROR);
//        }
        }
    }

    @FXML
    public void getRowValue(TableColumn.CellEditEvent editcell) {
        book = tableViewUsersAndBooks.getSelectionModel().getSelectedItem();
        this.qtt = (Long) editcell.getNewValue();

        this.codBook = tableViewUsersAndBooks.getSelectionModel().getSelectedItem().getCodBook();
        this.price = tableViewUsersAndBooks.getSelectionModel().getSelectedItem().getPrice();
        this.bookName = tableViewUsersAndBooks.getSelectionModel().getSelectedItem().getBookName();
        this.quantity = tableViewUsersAndBooks.getSelectionModel().getSelectedItem().getQuantity();
    }

    @FXML
    public void setEmail(TableColumn.CellEditEvent editcell) {
        book = tableViewUsersAndBooks.getSelectionModel().getSelectedItem();
        this.setUserEmail(editcell.getNewValue().toString());

    }

    private void loadBooksTable() {

        //Preenchendo tabela
        this.clnCodBook.setCellValueFactory(new PropertyValueFactory<>("codBook"));
        this.clnBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        this.clnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.clnBookQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        this.bookList = queriesDAO.getBookList();

        this.observableUserAndBookList = FXCollections.observableArrayList(bookList);

        this.tableViewUsersAndBooks.setItems(observableUserAndBookList);

        //Tornando colunas editavel
        tableViewUsersAndBooks.setEditable(true);
        this.clnUserEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        this.clnQtt.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
    }

    public void checkQuantityBooks() {
        if (this.quantity.equals(0)) {

            utils.showAlert("ERRO", "Não foi possivel reservar esse livro",
                    this.bookName + " não esta disponivel!", Alert.AlertType.ERROR);

            this.closeActualPage();
            this.openActualPage();

        }
    }

    public void closeActualPage() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void openActualPage() {
        try {
            AdmReleaseAndReturnBook admReleaseAndReturnBook = new AdmReleaseAndReturnBook();
            admReleaseAndReturnBook.start(new Stage());
        } catch (Exception ex) {
            Logger.getLogger(AdmReleaseAndReturnBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadBooksTable();
    }
}
