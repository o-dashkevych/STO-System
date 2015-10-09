package run.car;

import entity.Client;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import org.controlsfx.dialog.Dialogs;
import run.Main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Oleg Dashkevych on 09.05.2015.
 */
public class CarAddController {

    @FXML
    private TextField idTextField;

    @FXML
    private Label nameAlertLabel;

    @FXML
    private Label successAddLabel;

    @FXML
    private Label passportAlertLabel;

    @FXML
    private Label modelAlertLabel;

    @FXML
    private Label yearAlertLabel;

    @FXML
    private TextField modelTextField;

    @FXML
    private ComboBox<String> passportComboBox;

    @FXML
    private DatePicker yearDatePicker;

    @FXML
    private Label idExistAlertLabel;

    @FXML
    private Label idAlertLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<String> nameComboBox;

    @FXML
    private Label brandAlertLabel;

    @FXML
    private Button saveButton;

    @FXML
    private TextField brandTextField;

    private Main main;

    public CarAddController() {
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void checkId() {
        boolean idExist = main.checkCarId(idTextField.getText());
        if (idExist) {
            idExistAlertLabel.setVisible(true);
        } else idExistAlertLabel.setVisible(false);
    }

    public void addCar() {
        boolean result = false;
        String id = idTextField.getText();
        String idClient = passportComboBox.getValue();
        String brand = brandTextField.getText();
        String model = modelTextField.getText();
        Date date = null;
        if(nameComboBox.getValue().equals("")){
            nameAlertLabel.setVisible(true);
            return;
        }
        if(passportComboBox.getValue().equals("")){
            passportAlertLabel.setVisible(true);
            return;
        }
        if (id.equals("")) {
            idAlertLabel.setVisible(true);
            return;
        }
        if (yearDatePicker.getValue() == null) {
            yearAlertLabel.setVisible(true);
            return;
        }
        if (brandTextField.getText().equals("")) {
            brandAlertLabel.setVisible(true);
            return;
        }
        if (modelAlertLabel.getText().equals("")) {
            modelAlertLabel.setVisible(true);
            return;
        } else {
            nameAlertLabel.setVisible(false);
            passportAlertLabel.setVisible(false);
            idAlertLabel.setVisible(false);
            yearAlertLabel.setVisible(false);
            brandAlertLabel.setVisible(false);
            modelAlertLabel.setVisible(false);
            passportComboBox.setDisable(true);
            try {
                date = new SimpleDateFormat("yyyy-mm-dd").parse(yearDatePicker.getValue().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            result = main.addCar(id, idClient, brand, model, date, main.getClientByPassport(idClient));

            if (result) {
                final Timeline timeline = new Timeline();
                timeline.setCycleCount(2);
                timeline.setAutoReverse(true);
                final KeyValue kv = new KeyValue(successAddLabel.opacityProperty(), 1);
                final KeyFrame kf = new KeyFrame(Duration.millis(1500), kv);
                timeline.getKeyFrames().add(kf);
                timeline.play();
            } else {
                Dialogs.create()
                        .title("Error")
                        .masthead("Couldn't add a client!")
                        .showError();
            }
        }
    }

    public void getCarsMenu() {
        main.showCarMenu();
    }

    public void fillPassportCombo(){
        passportComboBox.setDisable(false);
        ObservableList<String> passports = main.getPassportsByName(nameComboBox.getValue());
        passportComboBox.setItems(passports);
    }

    public void init(){
    /*
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("u");
        yearDatePicker.setChronology(formatter.getChronology());*/
        ObservableList<Client> clients = main.getAllClients();
        if(clients == null){
            Dialogs.create()
                    .title("Error")
                    .masthead("Cannot add car! Add client first.")
                    .showError();
            main.showCarMenu();
        }
        else {
            Set<String> uniqueNames = new HashSet<>();
            for (Client client : clients) {
                uniqueNames.add(client.getName());
            }
            ObservableList<String> names = FXCollections.observableArrayList();
            for(String name:uniqueNames){
                names.add(name);
            }
            nameComboBox.setItems(names);
        }
    }
}
