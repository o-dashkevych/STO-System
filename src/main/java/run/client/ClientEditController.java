package run.client;

import entity.Client;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.controlsfx.dialog.Dialogs;
import run.Main;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Oleg Dashkevych on 03.05.2015.
 */
public class ClientEditController {
    @FXML
    public Label passportExistAlertLabel;

    @FXML
    public Label passportAlertLabel;

    @FXML
    public TextField passportTextField;

    @FXML
    public TextField nameTextField;

    @FXML
    public DatePicker birthDatePicker;

    @FXML
    public Button saveButton;

    @FXML
    public Button cancelButton;

    @FXML
    public Label successAddLabel;

    @FXML
    public Label birthAlertLabel;

    private Main main;

    public ClientEditController() {
    }

    public void setMain(Main main){
        this.main=main;
    }

    public void checkId(){
        String passport = passportTextField.getText();
        if(main.checkClientPassport(passport)){
            Client client = main.getClientByPassport(passport);
            if(client!=null){
                nameTextField.setText(client.getName());
                birthDatePicker.setValue(client.getBirthday().toLocalDate());
            }
        }else {
            nameTextField.setText("");
            birthDatePicker.setValue(null);
        }
    }

    public void save(){
        String id = passportTextField.getText();
        String name = nameTextField.getText();
        java.util.Date date = null;
        if(!main.checkClientPassport(id)){
            Dialogs.create()
                    .title("Error")
                    .masthead("Couldn't edit a client!")
                    .showError();
            return;
        }
        if(birthDatePicker.getValue() == null)
        {
            birthAlertLabel.setVisible(true);
        }
        else {
            birthAlertLabel.setVisible(false);
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(birthDatePicker.getValue().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            boolean result = main.updateClient(id, name, date);
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
                        .masthead("Couldn't edit a client!")
                        .showError();
            }
        }
    }

    public void fillBoxes(Client client){
        passportTextField.setText(client.getPassportId());
        nameTextField.setText(client.getName());
        birthDatePicker.setValue(client.getBirthday().toLocalDate());
    }

    public void getClientsMenu(){
        main.showClientMenu();
    }
}
