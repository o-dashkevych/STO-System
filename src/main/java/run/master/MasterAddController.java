package run.master;

import entity.OrderType;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import org.controlsfx.dialog.Dialogs;
import run.Main;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Oleg Dashkevych on 04.05.2015.
 */
public class MasterAddController {


    private Main main;

    @FXML
    public ComboBox<String> typeComboBox;

    @FXML
    public Label passportExistAlertLabel;

    @FXML
    public Label passportAlertLabel;

    @FXML
    public TextField passportTextField;

    @FXML
    public TextField categoryTextField;

    @FXML
    public TextField commissionTextField;

    @FXML
    public TextField salaryTextField;

    @FXML
    public TextField nameTextField;

    @FXML
    public DatePicker birthDatePicker;

    @FXML
    public DatePicker adoptDatePicker;

    @FXML
    public Button saveButton;

    @FXML
    public Button cancelButton;

    @FXML
    public Label successAddLabel;

    @FXML
    public Label birthAlertLabel;

    @FXML
    public Label nameAlertLabel;

    @FXML
    public Label adoptAlertLabel;

    @FXML
    public Label categoryAlertLabel;

    @FXML
    public Label commissionAlertLabel;

    @FXML
    public Label salaryAlertLabel;

    private ObservableList<String> typeNames;

    public MasterAddController() {
    }

    public void fillTypeCombo() {
        ObservableList<OrderType> types = null;
        try {
            types = main.getAllOrderTypes();
        } catch (NullPointerException e) {
            Dialogs.create()
                    .title("Error")
                    .masthead("Couldn't add a master! Add types of services to specify a category of master.")
                    .showError();
            main.showOrderTypeMenu();
        }
        List<String> names = new ArrayList<>();
        for (OrderType type : types) {
            names.add(type.getName());
        }
        for (String name : names) {
            typeComboBox.getItems().add(name);
        }
    }



    public void setMain(Main main) {
        this.main = main;
    }

    public void checkId() {
        String passport = passportTextField.getText();
        boolean passExist = main.checkMasterPassport(passport);
        if (passExist) {
            passportExistAlertLabel.setVisible(true);
            nameTextField.setText("");
            birthDatePicker.setValue(null);
            adoptDatePicker.setValue(null);
            categoryTextField.setText("");
            commissionTextField.setText("");
            salaryTextField.setText("");
        } else {
            passportExistAlertLabel.setVisible(false);
        }
    }

    public void save() {
        java.util.Date date = null;
        java.util.Date adopt = null;
        String id = passportTextField.getText();
        String name = "";
        String category = typeComboBox.getValue();
        double salary = 0;
        Scanner salScan = new Scanner(salaryTextField.getText());
        boolean result = true;
        BigDecimal commission = new BigDecimal(0);
        Scanner scanner = new Scanner(commissionTextField.getText());
        if (scanner.useDelimiter("\\.").hasNextBigDecimal()) {
            commission = new BigDecimal(commissionTextField.getText());
            commissionAlertLabel.setVisible(false);
        } else {
            commissionAlertLabel.setVisible(true);
            result = false;
        }
        if (salScan.useDelimiter("\\.").hasNextDouble()) {
            salary = salScan.nextDouble();
            salaryAlertLabel.setVisible(false);
        } else {
            salaryAlertLabel.setVisible(true);
            result = false;
        }

        if (birthDatePicker.getValue() == null) {
            birthAlertLabel.setVisible(true);
            result = false;
        } else {
            birthAlertLabel.setVisible(false);
        }

        if (adoptDatePicker.getValue() == null) {
            adoptAlertLabel.setVisible(true);
            result = false;
        } else {
            adoptAlertLabel.setVisible(false);
        }

        if (nameTextField.getText() == "") {
            nameAlertLabel.setVisible(true);
            result = false;
        } else {
            nameAlertLabel.setVisible(false);
            name = nameTextField.getText();
        }

        if (category.equals("")) {
            categoryAlertLabel.setVisible(true);
            result = false;
        } else {
            categoryAlertLabel.setVisible(false);
        }

        if (commissionTextField.getText() == "") {
            commissionAlertLabel.setVisible(true);
            result = false;
        } else {
            commissionAlertLabel.setVisible(false);
        }

        if (salaryTextField.getText() == "") {
            salaryAlertLabel.setVisible(true);
            result = false;
        } else {
            salaryAlertLabel.setVisible(false);
        }
        if(!adoptDatePicker.getValue().isAfter(birthDatePicker.getValue())){
            Dialogs.create()
                    .title("Error")
                    .masthead("Дата принятия на работу не должна быть меньше даты рождения!")
                    .showError();
            return;
        }

        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(birthDatePicker.getValue().toString());
            if(date.after(new Date(System.currentTimeMillis()))){
                Dialogs.create()
                        .title("Error")
                        .masthead("Дата рождения не должна быть в будущем!")
                        .showError();
                return;
            }
            adopt = new SimpleDateFormat("yyyy-MM-dd").parse(adoptDatePicker.getValue().toString());
            if(adopt.after(new Date(System.currentTimeMillis()))){
                Dialogs.create()
                        .title("Error")
                        .masthead("Дата принятия на работу не должна быть в будущем!")
                        .showError();
                return;
            }  if(date.after(adopt)){
                Dialogs.create()
                        .title("Error")
                        .masthead("Дата принятия на работу не должна быть раньше даты рождения!")
                        .showError();
                return;
            }
        } catch (ParseException e) {
            result = false;
            e.printStackTrace();
        }
        if (result) {
            boolean resultSave = main.addMaster(id, name, date, adopt, category, commission, salary);

            if (resultSave) {
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
                        .masthead("Couldn't add a master!")
                        .showError();
            }
        } else {
            Dialogs.create()
                    .title("Error")
                    .masthead("Check fields!")
                    .showError();
        }
    }


    public void showMastersMenu() {
        main.showMasterMenu();
    }

    public void init() {
        fillTypeCombo();
    }
}
