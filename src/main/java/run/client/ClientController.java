package run.client;

import entity.Client;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import org.controlsfx.dialog.Dialogs;
import run.Main;
import util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oleg Dashkevych on 26.04.2015.
 */
public class ClientController {
    @FXML
    private TableView<Client> clientsTable;

    @FXML
    private TableColumn<Client, String> passportColumn;

    @FXML
    private TableColumn<Client, String> nameColumn;

    @FXML
    private TableColumn<Client, String> birthColumn;

    @FXML
    private Button searchButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button addButton;

    @FXML
    private Button resetButton;

    @FXML
    private RadioButton nameRadio;

    @FXML
    private RadioButton passportRadio;

    @FXML
    private RadioButton birthRadio;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField searchTextField;

    @FXML
    private Label successDeleteLabel;

    public ClientController() {
    }

    private Main main;


    public void setMain(Main main) {
        this.main = main;
        clientsTable.setItems(main.getAllClients());
    }

    public void searchById() {
        String id = searchTextField.getText();
        clientsTable.setItems(main.searchClientById(id));
    }

    public void searchByName() {
        String name = searchTextField.getText();
        clientsTable.setItems(main.searchClientByName(name));
    }

    public void searchByDate() {

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(datePicker.getValue().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        clientsTable.setItems(main.searchClientByBirth(date));
    }

    public void resetDefault(){
        searchTextField.setText("");
        datePicker.setValue(null);
        selectNameRadio();
        nameRadio.setSelected(true);
        search();
    }

    public void search() {
        if (searchTextField.getText().isEmpty()) {
            clientsTable.setItems(main.getAllClients());
        }
        if (birthRadio.isSelected()) {
            searchByDate();
        } else {
            if (passportRadio.isSelected()) {
                searchById();
            } else searchByName();
        }
    }

    public void selectPassportRadio() {
        searchTextField.setDisable(false);
        nameRadio.setSelected(false);
        birthRadio.setSelected(false);
        datePicker.setDisable(true);
    }

    public void selectNameRadio() {
        searchTextField.setDisable(false);
        passportRadio.setSelected(false);
        birthRadio.setSelected(false);
        datePicker.setDisable(true);
    }

    public void selectBirthRadio() {
        searchTextField.setDisable(true);
        passportRadio.setSelected(false);
        nameRadio.setSelected(false);
        datePicker.setDisable(false);
    }

    public void deleteClient() {
        if (clientsTable.getSelectionModel().getSelectedItem() == null) {

            Dialogs.create()
                    .title("Alert")
                    .masthead("No users in data base")
                    .showWarning();

        } else {
            clientsTable.setItems(main.deleteClient(clientsTable.getSelectionModel().getSelectedItem()));
            if (main.getResult()) {
                final Timeline timeline = new Timeline();
                timeline.setCycleCount(2);
                timeline.setAutoReverse(true);
                final KeyValue kv = new KeyValue(successDeleteLabel.opacityProperty(), 1);
                final KeyFrame kf = new KeyFrame(Duration.millis(1500), kv);
                timeline.getKeyFrames().add(kf);
                timeline.play();
            } else {
                Dialogs.create()
                        .title("Error")
                        .masthead("Couldn't delete a client!")
                        .showError();
            }
        }
    }

    public void showAddClient() {
        main.showAddClients();
    }

    public void showEditClient(){

        if (clientsTable.getSelectionModel().getSelectedItem() == null) {

            Dialogs.create()
                    .title("Alert")
                    .masthead("No selected client")
                    .showWarning();

        } else {
            Client client = clientsTable.getSelectionModel().getSelectedItem();
            main.showEditClient(client);
        }
    }

    public void fillTable() {

        passportColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPassportId()));
        nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        birthColumn.setCellValueFactory(cell -> new SimpleStringProperty(DateUtil.format(cell.getValue().getBirthday().toLocalDate())));
        nameRadio.setSelected(true);
    }
}
