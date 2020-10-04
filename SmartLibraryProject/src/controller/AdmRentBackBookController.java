/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
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
    private TableView<UserAndBook> tableViewUsersAndBooks;

    @FXML
    private TableColumn<UserAndBook, String> clnBookName;

    @FXML
    private TableColumn<UserAndBook, Float> clnPrice;

    @FXML
    private TableColumn<UserAndBook, Long> clnBookQuantity;

    @FXML
    private TableColumn<UserAndBook, Long> clnCodBook;

    @FXML
    private Button btnReleaseBook;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnReload;

    @FXML
    private Button btnReturn;

    @FXML
    private Button btnRent;

    private final QueriesDAO queriesDAO = new QueriesDAO();
    private final UserAndBook userAndBook = new UserAndBook();
    private final Utils utils = new Utils();
    private ObservableList<UserAndBook> observableUserAndBookList;
    private List<UserAndBook> bookList = new ArrayList<>();
    private Long qttBooksToRent;
    private boolean query;
    private int count = 0;

    /**
     *
     * @param event
     */
    @FXML
    private void rentBook() {
        if (this.userAndBook.getQuantity() == 0) {
            utils.showAlert("Atenção", "Não é possivel reservar esse livro",
                    " Não ha o livro " + this.userAndBook.getBookName()
                    + " em nosso estoque",
                    Alert.AlertType.WARNING);

            return;
        }

        this.emailDialog();

        if (this.userAndBook.getQtt() > 5) {
            utils.showAlert("Aviso", "Valor invalido", "Usuario não pode "
                    + "reservar mais de cinco livros por vez!", Alert.AlertType.WARNING);
            return;
        }

        Long total = this.queriesDAO.getNumberBooksRented(this.userAndBook.getCodUser());
        if (total.compareTo(5L) == 0) {
            this.utils.showAlert("Atenção", "Não é posivel reservar mais livros!",
                    "O usuario ja excedeu a quantidade de livros!", Alert.AlertType.WARNING);
            return;
        }

        if (this.userAndBook.getQtt() + total > 5) {
            this.utils.showAlert("Atenção", "Não é posivel fazer a reserva!",
                    "Numero de livros excedido para este usuario", Alert.AlertType.WARNING);
            return;
        }

        boolean checkUserRentedBook = this.queriesDAO.checkBookRented(
                this.userAndBook.getCodUser(), this.userAndBook.getCodBook());
        if (checkUserRentedBook) {
            if ((total + this.userAndBook.getQtt()) <= 5) {
                Long codUserBook = this.queriesDAO.getCodUserBook(this.userAndBook.getCodUser(),
                        this.userAndBook.getCodBook());

                this.query = this.queriesDAO.updateReserveBook(codUserBook,
                        this.userAndBook.getCodBook(), this.userAndBook.getQtt());

                this.successRented(this.query);

                return;
            }
        }

        this.query = this.queriesDAO.reserveBook(this.userAndBook.getCodUser(),
                this.userAndBook.getCodBook(), this.userAndBook.getQtt());

        this.successRented(this.query);
    }

    @FXML
    private void returnBook() {
        this.emailDialog();

        if (this.userAndBook.getQtt() > 5) {
            utils.showAlert("Aviso", "Valor invalido", "Numero digitado maior que 5!",
                    Alert.AlertType.WARNING);
            return;
        }

        if (this.delayTime() > 10) {
            Float value = this.queriesDAO.getDelayValue(this.userAndBook.getCodBook(),
                    this.userAndBook.getCodUser(), this.userAndBook.getQtt());

            utils.showAlert("Atenção", this.delayTime() + " dias de atraso!",
                    "Será cobrado uma multa de 10% por dia atrasado. Valor total: " + value + "0R$",
                    Alert.AlertType.INFORMATION);
        }

        this.query = this.queriesDAO.returnBook(this.userAndBook.getCodUser(),
                this.userAndBook.getCodBook(), this.userAndBook.getQtt());

        this.successReturned(this.query);
    }

    private void loadBooksTable() {

        TableColumn clnCheck = new TableColumn("Selecione um livro");
        this.tableViewUsersAndBooks.getColumns().add(0, clnCheck);

        //Preenchendo tabela
        clnCheck.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        clnCheck.setStyle("-fx-alignment: CENTER;");

        this.clnCodBook.setCellValueFactory(new PropertyValueFactory<>("codBook"));
        this.clnBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        this.clnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.clnBookQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        this.bookList = queriesDAO.getBooksToUserAndBook();
        this.addCheckBoxAction();
        this.observableUserAndBookList = FXCollections.observableArrayList(bookList);

        this.tableViewUsersAndBooks.setItems(observableUserAndBookList);
    }

    @FXML
    private void addCheckBoxAction() {
        this.bookList.forEach((c) -> {
            c.getCheckBox().setOnAction((ActionEvent event) -> {

                this.qttCheckBoxSelected(c);

                this.setValues(c);

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

        if (!dialogEmail.isShowing()) {
            System.out.println("Bla, bla, bla");
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

    private void successReturned(boolean rented) {
        if (rented) {
            utils.showAlert("Sucesso", "Livro devolvido!", "Quandade de "
                    + this.userAndBook.getQtt() + " livros " + this.userAndBook.getBookName()
                    + "foi devolvidos por: " + this.userAndBook.getEmail(),
                    Alert.AlertType.INFORMATION);
        } else {
            utils.showAlert("ERRO", "Algo inesperado ocorreu", ""
                    + "Não foi possivel devolver o livro, tente novamente!", Alert.AlertType.ERROR);
        }
    }

    private void setValues(UserAndBook b) {
        this.userAndBook.setCodBook(b.getCodBook());
        this.userAndBook.setPrice(b.getPrice());
        this.userAndBook.setBookName(b.getBookName());
        this.userAndBook.setQuantity(b.getQuantity());
        this.userAndBook.setReleaseDate(b.getReleaseDate());
    }

    private void qttCheckBoxSelected(UserAndBook c) {
        if (c.getCheckBox().selectedProperty().getValue()) {
            this.count = this.count + 1;

            if (this.count > 1) {
                utils.showAlert("Atenção", "Valor excedido!",
                        " Selecione um livro por vez!",
                        Alert.AlertType.WARNING);

                c.getCheckBox().setSelected(false);
            }
        } else {
            this.count = 0;
        }
    }

    private int delayTime() {
        Date curDate = new Date(System.currentTimeMillis());
        String strCurrentData = utils.formatDate(curDate);
        String[] resCurDate = strCurrentData.split("-");
        LocalDate currentDate = LocalDate.of(
                Integer.parseInt(resCurDate[2]),
                Integer.parseInt(resCurDate[1]),
                Integer.parseInt(resCurDate[0]));

        Date relDate = this.userAndBook.getReleaseDate();
        String strReleaseDate = utils.formatDate(relDate);
        String[] resRelesDate = strReleaseDate.split("-");
        LocalDate releaseDate = LocalDate.of(
                Integer.parseInt(resRelesDate[2]),
                Integer.parseInt(resRelesDate[1]),
                Integer.parseInt(resRelesDate[0]));

        Period period = Period.between(releaseDate, currentDate);
        int time = period.getDays() + period.getMonths() + period.getYears();

        return time;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadBooksTable();
    }
}
