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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.LongStringConverter;
import model.Book;
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
    private Long qttBooksToRent;
    private Long codBook;
    private Long qttBooksDb;
    private String bookName;
    private Float price;

    /**
     *
     * @param event
     */

    private void reserveBook() {
        boolean reserved = false;
        Long codUser = queriesDAO.getCodUser(this.getUserEmail());

        this.checkQuantityBooks();
        this.checkQttOfBooks(this.getQttBooksToRent());
        reserved = queriesDAO.reserveBook(codUser, this.getCodBook(), this.getQttBooksToRent());

        if (reserved) {
            utils.showAlert("Sucesso", "Livro reservado!",
                    this.getBookName() + " foi reservado para " + this.getUserEmail() + "VALOR TOTAL: "
                    + this.getPrice() * this.getQttBooksToRent(),
                    Alert.AlertType.INFORMATION);

        } else {
            utils.showAlert("ERRO", "Algo inesperado ocorreu", ""
                    + "Não foi possivel reservar o livro, tente novamente!", Alert.AlertType.ERROR);
//        }
        }
    }


    private void loadBooksTable() {

        TableColumn clActionButon = new TableColumn("Reserva");
        this.tableViewUsersAndBooks.getColumns().add(0, clActionButon);

        //Preenchendo tabela
        clActionButon.setCellValueFactory(new PropertyValueFactory<>("btnGet"));
        this.clnCodBook.setCellValueFactory(new PropertyValueFactory<>("codBook"));
        this.clnBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        this.clnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.clnBookQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        this.bookList = queriesDAO.getBookList();
        this.addButtonAction();
        this.observableUserAndBookList = FXCollections.observableArrayList(bookList);

        this.tableViewUsersAndBooks.setItems(observableUserAndBookList);

        //Tornando colunas editavel
        tableViewUsersAndBooks.setEditable(true);
        this.clnQtt.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
    }

    public void checkQuantityBooks() {
        if (this.getQttBooksDb().equals(0)) {

            utils.showAlert("ERRO", "Não foi possivel reservar esse livro",
                    this.getBookName() + " não esta disponivel!", Alert.AlertType.ERROR);

            this.closeActualPage();
            this.openActualPage();

        }
    }

    public void closeActualPage() {
        Stage stage = (Stage) this.btnClose.getScene().getWindow();
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

    @FXML
    private void addButtonAction() {
        this.bookList.forEach((b) -> {
            b.getBtnGet().setOnAction((ActionEvent event) -> {

                this.emailDialog();
                this.reserveBook();
            });
        });
    }

    private void emailDialog() {
        String empty = "";
        TextInputDialog dialogName = new TextInputDialog();

        dialogName.setTitle("Para reservar este livro");
        dialogName.setHeaderText("Digite email do usuario:");
        dialogName.setContentText("Email: ");

        // se o usuário fornecer um valor, assignamos ao nome
        dialogName.showAndWait();

        if (dialogName.getResult().compareTo(empty) == 0) {
            this.emailDialog();
        } else {
            this.setUserEmail(dialogName.getResult());
            this.quantityDialog();
        }

    }

    private void quantityDialog() {
        String empty = "";
        TextInputDialog dialogQtt = new TextInputDialog();

        dialogQtt.setTitle("Para reservar este livro");
        dialogQtt.setHeaderText("Digite quantidade!:");
        dialogQtt.setContentText("QTD: ");

        // se o usuário fornecer um valor, assignamos ao nome
        dialogQtt.showAndWait();

        if (dialogQtt.getResult().compareTo(empty) == 0) {
            this.quantityDialog();
        } else {
            this.setQttBooksToRent(Long.parseLong(dialogQtt.getResult()));
            System.out.println(this.getQttBooksToRent());
        }

    }
    
    private void checkQttOfBooks(Long qttBooksToRent) {
        if(this.getQttBooksToRent() > 2) {
            utils.showAlert("Aviso", "Valor invalido", "Usuario não pode "
                    + "reservar mais de dois livros", Alert.AlertType.WARNING);
            
            this.closeActualPage();
            this.openActualPage();
        }
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getCodBook() {
        return codBook;
    }

    public void setCodBook(Long codBook) {
        this.codBook = codBook;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Long getQttBooksDb() {
        return qttBooksDb;
    }

    public void setQttBooksDb(Long qttBooksDb) {
        this.qttBooksDb = qttBooksDb;
    }

    public Long getQttBooksToRent() {
        return qttBooksToRent;
    }

    public void setQttBooksToRent(Long qttBooksToRent) {
        this.qttBooksToRent = qttBooksToRent;
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadBooksTable();
    }
}