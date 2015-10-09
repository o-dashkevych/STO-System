package run.client;

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
 * Created by Oleg Dashkevych on 01.05.2015.
 */
public class ClientAddController {
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

    public ClientAddController() {
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void checkPassport(){
        boolean passpExist = main.checkClientPassport(passportTextField.getText());
        if(passpExist){
            passportExistAlertLabel.setVisible(true);
        }else passportExistAlertLabel.setVisible(false);
    }

    public void addClient(){
        boolean result = false;
        String id = passportTextField.getText();
        String name = nameTextField.getText();
        java.util.Date date = null;
        if(birthDatePicker.getValue() == null)
        {
            birthAlertLabel.setVisible(true);
        }
        else {
            birthAlertLabel.setVisible(false);
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(birthDatePicker.getValue().toString());
                result = main.addClient(id, name, date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

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

    public void getClientsMenu(){
        main.showClientMenu();
    }
}
