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
import utils.QueriesDAO;
import utils.Utils;
import screens.AdmTablePage;

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
    private TableColumn<Book, Long> clnquantity;

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

    @FXML
    private ComboBox<Genre> cbGenre;

    @FXML
    private ComboBox<Publisher> cbPub;

    @FXML
    private ComboBox<Author> cbAlt;

    @FXML
    private TextField txtBook;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQuantity;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnReload;

    private Long selectedGenre;
    private Long selectedAuthor;
    private Long selectedPublisher;

    private Book bookSelected;
    private final QueriesDAO queriesDAO = new QueriesDAO();
    private final Utils utils = new Utils();
    private ObservableList<Book> observableBookList;

    /**
     *
     * @param event
     */
    @FXML
    private void updateBook(ActionEvent event) {
        boolean updated = false;

        updated = queriesDAO.updateBook(bookSelected);
        if (updated) {
            utils.showAlert("Sucesso", "Livro atualizado", "O Livro foi atualizado com sucesso",
                    Alert.AlertType.INFORMATION);
        } else {
            utils.showAlert("Erro", "Erro ao atualizar", "Algo inesperado ocorreu!",
                    Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadBooksTable();
        this.loadGenres();
        this.loadAuthors();
        this.loadPublishers();
    }

    @FXML
    private void InsertBook(ActionEvent actionEvent) {
        String empty = "";
        boolean inserted = false;

        if (this.txtBook.getText().compareTo(empty) == 0
                || this.txtPrice.getText().compareTo(empty) == 0
                || this.txtQuantity.getText().compareTo(empty) == 0) {
            utils.showAlert("Atençao", "Campos obrigatório!",
                    "Os campos 'Nome do livro',  'preço', 'Quantidade' são obrigatórios para a inserção de um novo livro!",
                    Alert.AlertType.INFORMATION);
        }

        inserted = queriesDAO.insertBook(this.txtBook.getText(), this.selectedAuthor,
                this.selectedGenre, this.selectedPublisher, Float.parseFloat(this.txtPrice.getText()),
                Long.parseLong(this.txtQuantity.getText()));

        if (inserted) {
            utils.showAlert("Sucesso", "Livro inserido", "O livro foi inserido com sucesso",
                    Alert.AlertType.INFORMATION);

        } else {
            utils.showAlert("Erro", "Algo inesperado ocorreu", "Erro a o inserir o livro",
                    Alert.AlertType.ERROR);

            this.txtBook.setText("");
            this.selectedAuthor = null;
            this.selectedGenre = null;
            this.selectedPublisher = null;
            this.txtPrice.setText("");
        }
    }

    @FXML
    private void removeBook() {
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

    public void loadGenres() {
        List<Genre> genres = this.queriesDAO.getGenres();
        ObservableList<Genre> obsGenre;

        obsGenre = FXCollections.observableArrayList(genres);

        cbGenre.setItems(obsGenre);

        System.out.print(cbGenre.getSelectionModel().getSelectedItem());
    }

    public void loadAuthors() {
        List<Author> authors = this.queriesDAO.getAuthors();
        ObservableList<Author> obsAuthor;

        obsAuthor = FXCollections.observableArrayList(authors);

        cbAlt.setItems(obsAuthor);
    }

    public void loadPublishers() {
        List<Publisher> publisher = this.queriesDAO.getPublishers();
        ObservableList<Publisher> obsPublisher;

        obsPublisher = FXCollections.observableArrayList(publisher);

        cbPub.setItems(obsPublisher);
    }

    @FXML
    public void getCodGenre(ActionEvent actionEvent) {
        this.selectedGenre = cbGenre.getSelectionModel().getSelectedItem().getCodGenre();
    }

    @FXML
    public void getCodAuthor(ActionEvent actionEvent) {
        this.selectedAuthor = cbAlt.getSelectionModel().getSelectedItem().getCodAuthor();
    }

    @FXML
    public void getCodPublisher(ActionEvent actionEvent) {
        this.selectedPublisher = cbPub.getSelectionModel().getSelectedItem().getCodPublisher();
    }

    @FXML
    public void getNewBookName(CellEditEvent editcell) {
        bookSelected = tableViewBooks.getSelectionModel().getSelectedItem();
        bookSelected.setBookName(editcell.getNewValue().toString());
    }

    @FXML
    public void getNewPrice(CellEditEvent editcell) {
        bookSelected = tableViewBooks.getSelectionModel().getSelectedItem();
        bookSelected.setPrice((Float) editcell.getNewValue());
    }

    @FXML
    public void getNewQuantity(CellEditEvent editcell) {
        bookSelected = tableViewBooks.getSelectionModel().getSelectedItem();
        bookSelected.setQuantity((Long) editcell.getNewValue());
    }

    @FXML
    public void getReleaseDate(CellEditEvent editcell) {
        bookSelected = tableViewBooks.getSelectionModel().getSelectedItem();
        bookSelected.setReleaseDate((Date) editcell.getNewValue());
    }

    @FXML
    public void getNewReturnDate(CellEditEvent editcell) {
        bookSelected = tableViewBooks.getSelectionModel().getSelectedItem();
        bookSelected.setReturnDate((Date) editcell.getNewValue());
    }

    public void reloadBookTable() {
        try {
            Stage stage = (Stage) btnReload.getScene().getWindow();
            stage.close();

            AdmTablePage admTablePage = new AdmTablePage();
            admTablePage.start(new Stage());

        } catch (Exception ex) {
            Logger.getLogger(AdmTablePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeActualPage() {
        try {
            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            Logger.getLogger(AdmTablePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadBooksTable() {

        //Preenchendo tabela
        this.clnCodBook.setCellValueFactory(new PropertyValueFactory<>("codBook"));
        this.clnBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        this.clnGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        this.clnPub.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        this.clnAut.setCellValueFactory(new PropertyValueFactory<>("author"));
        this.clnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.clnReaDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        this.clnExpectedDate.setCellValueFactory(new PropertyValueFactory<>("expectedDate"));
        this.clnRetDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        this.clnquantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        List<Book> bookList = queriesDAO.getBookList();

        this.observableBookList = FXCollections.observableArrayList(bookList);

        this.tableViewBooks.setItems(observableBookList);

        //Tornando colunas editavel
        tableViewBooks.setEditable(true);
        this.clnBookName.setCellFactory(TextFieldTableCell.forTableColumn());
        this.clnPrice.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        this.clnquantity.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        this.clnRetDate.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        this.clnReaDate.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
    }
}
