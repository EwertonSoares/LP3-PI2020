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
    private TableColumn<Book, Long> clnBookQuantity;

    @FXML
    private TableColumn<Book, Long> clnQtt;

    @FXML
    private TableColumn<Book, Long> clnCodBook;

    @FXML
    private Button btnReleaseBook;

    @FXML
    private Button btnClose;

    private final QueriesDAO queriesDAO = new QueriesDAO();
    private final Utils utils = new Utils();
    private ObservableList<Book> observableUserAndBookList;
    private final UserAndBook userAndBook = new UserAndBook();
    private List<Book> bookList = new ArrayList<>();
    private Long qttBooksToRent;

    /**
     *
     * @param event
     */
    private void reserveBook() {
        boolean reserved = false;
        Long userCod = queriesDAO.getCodUser(this.userAndBook.getEmail());

        if (userCod == 0) {
            utils.showAlert("Atenção", "Usuario invalido!", "Este usuario não esta cadastrado",
                    Alert.AlertType.ERROR);
            
            this.reloadActualPage();
        }

        userAndBook.setCodUser(userCod);

        this.checkQuantityBooks();
        this.checkQttOfBooks(this.userAndBook.getQttBookkRent());

        reserved = queriesDAO.reserveBook(userAndBook.getCodUser(),
                this.userAndBook.getCodBook(), this.userAndBook.getQttBookkRent());

        this.checkReserva(reserved);
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
            this.userAndBook.setEmail(dialogName.getResult());
            System.out.println(this.userAndBook.getEmail());
            this.quantityDialog();
        }

    }

    private void quantityDialog() {
        String empty = "";
        TextInputDialog dialogQtt = new TextInputDialog();

        dialogQtt.setTitle("Para reservar este livro");
        dialogQtt.setHeaderText("Digite quantidade!:");
        dialogQtt.setContentText("QTD: ");
        dialogQtt.showAndWait();

        if (dialogQtt.getResult().compareTo(empty) == 0) {
            this.quantityDialog();
        } else {
            this.userAndBook.setQttBookkRent(Long.parseLong(dialogQtt.getResult()));
            System.out.println(this.userAndBook.getQttBookkRent());
        }

    }

    private void checkQttOfBooks(Long qttBooksToRent) {
        if (this.userAndBook.getQttBookkRent() > 2) {
            utils.showAlert("Aviso", "Valor invalido", "Usuario não pode "
                    + "reservar mais de dois livros", Alert.AlertType.WARNING);

            this.reloadActualPage();
        }
    }

    private void checkReserva(boolean reserved) {
        if (reserved) {
            utils.showAlert("Sucesso", "Livro reservado!",
                    this.userAndBook.getBookName() + " foi reservado para " + this.userAndBook.getEmail() + "VALOR TOTAL: "
                    + this.userAndBook.getPrice() * this.userAndBook.getQttBookkRent(),
                    Alert.AlertType.INFORMATION);

        } else {
            utils.showAlert("ERRO", "Algo inesperado ocorreu", ""
                    + "Não foi possivel reservar o livro, tente novamente!", Alert.AlertType.ERROR);
        }
    }

    public void checkQuantityBooks() {
        if (this.userAndBook.getQttBookkRent().equals(0)) {

            utils.showAlert("ERRO", "Não foi possivel reservar esse livro", " Não ha"
                    + this.userAndBook.getBookName() + " em nosso estoque", Alert.AlertType.ERROR);

            this.reloadActualPage();
        }
    }

    @FXML
    private void closeActualPage() {
        Stage stage = (Stage) this.btnClose.getScene().getWindow();
        stage.close();
    }

    private void reloadActualPage() {
        try {
            Stage stage = (Stage) this.btnClose.getScene().getWindow();
            stage.close();

            AdmReleaseAndReturnBook admReleaseAndReturnBook = new AdmReleaseAndReturnBook();
            admReleaseAndReturnBook.start(new Stage());
        } catch (Exception ex) {
            Logger.getLogger(AdmReleaseAndReturnBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadBooksTable();
    }
}
