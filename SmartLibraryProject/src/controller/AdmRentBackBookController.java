/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
public class AdmRentBackBookController implements Initializable {

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

    @FXML
    private Button btnReload;

    private final QueriesDAO queriesDAO = new QueriesDAO();
    private final UserAndBook userAndBook = new UserAndBook();
    private final Utils utils = new Utils();
    private ObservableList<Book> observableUserAndBookList;
    private List<Book> bookList = new ArrayList<>();
    private Long qttBooksToRent;
    private boolean query;

    /**
     *
     * @param event
     */
    private void rentBook() {

        if (this.userAndBook.getQtt() > 5) {
            utils.showAlert("Aviso", "Valor invalido", "Usuario não pode "
                    + "reservar mais de cinco livros por vez!", Alert.AlertType.WARNING);
            return;
        }

        Long total = this.queriesDAO.getNumberBooksRented(this.userAndBook.getCodUser());
        if (total.compareTo(5L) >= 0) {
            this.utils.showAlert("Atenção", "Não é posivel reservar mais livros!",
                    "O usuario ja excedeu a quantidade de livros!", Alert.AlertType.WARNING);
            return;
        }

        if (this.userAndBook.getQtt() + total > 5) {
            this.utils.showAlert("Atenção", "Não é posivel fazer a reserva!",
                    "Numero de livros excedido para este usuario", Alert.AlertType.WARNING);
            return;
        }

        this.query = this.queriesDAO.reserveBook(this.userAndBook.getCodUser(),
                this.userAndBook.getCodBook(), this.userAndBook.getQtt());

        this.successRented(this.query);
    }

    private void returnBook() {
        if (this.userAndBook.getQtt() > 5) {
            utils.showAlert("Aviso", "Valor invalido", "Numero digitado maior que 5!",
                    Alert.AlertType.WARNING);
            return;
        }

        this.query = this.queriesDAO.returnBook(this.userAndBook.getCodUser(),
                this.userAndBook.getCodBook(), this.userAndBook.getQtt());

        this.successReturned(this.query);
    }

    private void loadBooksTable() {

        TableColumn clBtnGetAction = new TableColumn("Reservar");
        this.tableViewUsersAndBooks.getColumns().add(0, clBtnGetAction);

        TableColumn clBtnRetAction = new TableColumn("Devolver");
        this.tableViewUsersAndBooks.getColumns().add(1, clBtnRetAction);

        //Preenchendo tabela
        clBtnGetAction.setCellValueFactory(new PropertyValueFactory<>("btnGet"));
        clBtnRetAction.setCellValueFactory(new PropertyValueFactory<>("btnRet"));
        this.clnCodBook.setCellValueFactory(new PropertyValueFactory<>("codBook"));
        this.clnBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        this.clnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.clnBookQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        this.bookList = queriesDAO.getBookList();
        this.addBtnGetAction();
        this.addBtnRetAction();
        this.observableUserAndBookList = FXCollections.observableArrayList(bookList);

        this.tableViewUsersAndBooks.setItems(observableUserAndBookList);

        //Tornando colunas editavel
        tableViewUsersAndBooks.setEditable(true);
        this.clnQtt.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
    }

    @FXML
    private void addBtnGetAction() {
        this.bookList.forEach((b) -> {
            b.getBtnGet().setOnAction((ActionEvent event) -> {

                this.setValues(b);

                if (this.userAndBook.getQuantity() == 0) {
                    utils.showAlert("Atenção", "Não foi possivel reservar esse livro", " Não ha"
                            + this.userAndBook.getBookName() + " em nosso estoque", Alert.AlertType.WARNING);
                    return;
                }

                this.emailDialog();
                this.rentBook();
            });
        });
    }

    @FXML
    private void addBtnRetAction() {
        this.bookList.forEach((b) -> {
            b.getBtnRet().setOnAction((ActionEvent event) -> {

                this.setValues(b);
                this.emailDialog();
                this.returnBook();
            });
        });
    }

    private void emailDialog() {
        String empty = "";
        TextInputDialog dialogEmail = new TextInputDialog();

        dialogEmail.setTitle("Para reservar/Devolver este livro:");
        dialogEmail.setHeaderText("Digite email do usuario:");
        dialogEmail.setContentText("Email: ");

        dialogEmail.showAndWait();

        if (dialogEmail.getResult().compareTo(empty) == 0) {
            this.emailDialog();
        } else {
            this.userAndBook.setEmail(dialogEmail.getResult());

            this.userAndBook.setCodUser(queriesDAO.getCodUser(this.userAndBook.getEmail()));
            if (this.userAndBook.getCodUser().compareTo(0L) == 0) {
                this.utils.showAlert("Atenção", "Usuario invalido!",
                        "Este usuario não esta cadastrado", Alert.AlertType.ERROR);
                return;
            }
            this.quantityDialog();
        }

    }

    private void quantityDialog() {
        String empty = "";
        TextInputDialog dialogQtt = new TextInputDialog();

        dialogQtt.setTitle("Para reservar/devolver este livro");
        dialogQtt.setHeaderText("Digite a quantidade!:");
        dialogQtt.setContentText("QTD: ");
        dialogQtt.showAndWait();

        if (dialogQtt.getResult().compareTo(empty) == 0) {
            this.quantityDialog();
        } else {
            this.userAndBook.setQtt(Long.parseLong(dialogQtt.getResult()));
        }

    }

    private void successRented(boolean reserved) {
        if (reserved) {
            utils.showAlert("Sucesso", "Livro reservado!", "Quandade de "
                    + this.userAndBook.getQtt() + " livros: " + this.userAndBook.getBookName()
                    + " foi reservado por " + this.userAndBook.getEmail() + " VALOR TOTAL: "
                    + this.userAndBook.getPrice() * this.userAndBook.getQtt(),
                    Alert.AlertType.INFORMATION);
        } else {
            utils.showAlert("ERRO", "Algo inesperado ocorreu", ""
                    + "Não foi possivel reservar o livro, tente novamente!", Alert.AlertType.ERROR);
        }
    }

    private void successReturned(boolean reserved) {
        if (reserved) {
            utils.showAlert("Sucesso", "Livro devolvido!", "Quandade de "
                    + this.userAndBook.getQtt() + " livros " + this.userAndBook.getBookName()
                    + "foi devolvidos por: " + this.userAndBook.getEmail(),
                    Alert.AlertType.INFORMATION);
        } else {
            utils.showAlert("ERRO", "Algo inesperado ocorreu", ""
                    + "Não foi possivel devolver o livro, tente novamente!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void closeActualPage() {
        Stage stage = (Stage) this.btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void reloadActualPage() {
        try {
            Stage stage = (Stage) this.btnReload.getScene().getWindow();
            stage.close();

            AdmReleaseAndReturnBook admReleaseAndReturnBook = new AdmReleaseAndReturnBook();
            admReleaseAndReturnBook.start(new Stage());
        } catch (Exception ex) {
            Logger.getLogger(AdmRentBackBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setValues(Book b) {
        this.userAndBook.setCodBook(b.getCodBook());
        this.userAndBook.setPrice(b.getPrice());
        this.userAndBook.setBookName(b.getBookName());
        this.userAndBook.setQuantity(b.getQuantity());
        this.userAndBook.setReleaseDate(b.getReleaseDate());
    }

//    public void changeDate() {
//        String rel = this.userAndBook.getReleaseDate().toString();
//        String[] result = rel.split("-");
//        String retDate = result[2].concat("-").concat(result[1]).concat("-").concat(result[0]);
//
//        String curDate = this.userAndBook.getReleaseDate().toString();
//        String[] resultCurDate = rel.split("-");
//        String newCurDate = resultCurDate[2].concat("-")
//                .concat(resultCurDate[1]).concat("-")
//                .concat(resultCurDate[0]);
//     }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadBooksTable();
    }
}
