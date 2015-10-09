package run.car;

/**
 * Created by Oleg Dashkevych on 09.05.2015.
 */

import entity.Car;
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
import java.util.ArrayList;
import java.util.List;

public class CarEditController {

    @FXML
    private Label modelAlertLabel;

    @FXML
    private Label passportLabel;

    @FXML
    private ComboBox<String> clientComboBox;

    @FXML
    private Label yearAlertLabel;

    @FXML
    private Label carNumLabel;

    @FXML
    private Label successAddLabel;

    @FXML
    private TextField modelTextField;

    @FXML
    private ComboBox<String> passportComboBox;

    @FXML
    private DatePicker yearDatePicker;

    @FXML
    private Button cancelButton;

    @FXML
    private Label clientLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Label brandAlertLabel;

    @FXML
    private TextField brandTextField;

    String idCarEdit;

    boolean carExist = false;

    private Main main;

    public CarEditController() {
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void save() {

        String clientName = clientComboBox.getValue();
        String passport = passportComboBox.getValue();
        String id = carNumLabel.getText();
        String brand = brandTextField.getText();
        String model = modelTextField.getText();
        java.util.Date date = null;
        if (carExist) {
            Dialogs.create()
                    .title("Error")
                    .masthead("Couldn't edit another car!")
                    .showError();
            return;
        }
        if(clientName.equals("") || main.getClientByUniqueName(clientComboBox.getValue()).size() == 0){
            clientLabel.setVisible(true);
            return;
        }
        if(passport.equals("")){
            passportLabel.setVisible(true);
        }
        if(brand.equals("")){
            brandAlertLabel.setVisible(true);
            return;
        }
        if(model.equals("")){
            modelAlertLabel.setVisible(true);
            return;
        }
        if (yearDatePicker.getValue() == null) {
            yearAlertLabel.setVisible(true);
            return;
        } else {
            clientLabel.setVisible(false);
            passportLabel.setVisible(false);
            brandAlertLabel.setVisible(false);
            modelAlertLabel.setVisible(false);
            yearAlertLabel.setVisible(false);
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(yearDatePicker.getValue().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            boolean result = main.updateCar(id,passport,brand,model,date,main.getClientByPassport(passport));
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
                        .masthead("Couldn't edit a car!")
                        .showError();
            }
        }
    }


    public void fillPassports(){
        String name = clientComboBox.getValue();
        ObservableList<Client> clients = FXCollections.observableArrayList(main.getClientByUniqueName(name));
        if(clients == null){
            clientLabel.setVisible(true);
        }else{
            clientLabel.setVisible(false);
            List<String> passports = new ArrayList<String>();
            for(Client client : clients){
                passports.add(client.getPassportId());
            }
            passportComboBox.getItems().addAll(passports);
            passportComboBox.setValue(passportComboBox.getItems().get(0));
        }
    }

    public void fillClientCombo() {
        ObservableList<Client> clients = null;
        try {
            clients = main.getAllClients();
        } catch (NullPointerException e) {
            Dialogs.create()
                    .title("Error")
                    .masthead("Couldn't edit a car! Add clients to specify a category of master.")
                    .showError();
            main.showOrderTypeMenu();
        }
        List<String> names = new ArrayList<>();
        for (Client client : clients) {
            names.add(client.getName());
        }
        for (String name : names) {
            clientComboBox.getItems().add(name);
        }
    }

    public void fillBoxes(Car car) {
        fillClientCombo();
        idCarEdit = car.getCarId();
        clientComboBox.setValue(main.getClientByPassport(car.getClientPassportId()).getName());
        passportComboBox.setValue(car.getClientPassportId());
        carNumLabel.setText(car.getCarId());
        brandTextField.setText(car.getBrand());
        modelTextField.setText(car.getModel());
        yearDatePicker.setValue(car.getYear().toLocalDate());
    }

    public void getCarsMenu() {
        main.showCarMenu();
    }
}
