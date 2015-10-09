package run.master;

import entity.Master;
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
import java.util.List;
import java.util.Scanner;

/**
 * Created by Oleg Dashkevych on 04.05.2015.
 */
public class MasterEditController {

    private Main main;

    @FXML
    public Label passportLabel;

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

    @FXML
    public ComboBox<String> orderTypeCombo;

    public MasterEditController() {
    }

    public void setMain(Main main) {
        this.main = main;
    }



    public void save() {
        java.util.Date date = null;
        java.util.Date adopt = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(birthDatePicker.getValue().toString());
            adopt = new SimpleDateFormat("yyyy-MM-dd").parse(adoptDatePicker.getValue().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String id = passportLabel.getText();
        String name = nameTextField.getText();
        String category = orderTypeCombo.getValue();
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

        if (orderTypeCombo.getValue() == "") {
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

        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(birthDatePicker.getValue().toString());
            adopt = new SimpleDateFormat("yyyy-MM-dd").parse(adoptDatePicker.getValue().toString());
        } catch (ParseException e) {
            result = false;
            e.printStackTrace();
        }
        if (result) {
            boolean resultSave = main.updateMaster(id, name, date, adopt, category, commission, salary, main.getOrderTypeByName(category));

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
            orderTypeCombo.getItems().add(name);
        }
    }

    public void fillBoxes(Master master) {
        fillTypeCombo();
        passportLabel.setText(master.getPassportId());
        nameTextField.setText(master.getName());
        birthDatePicker.setValue(master.getBirthday().toLocalDate());
        adoptDatePicker.setValue(master.getAdoptionDate().toLocalDate());
        orderTypeCombo.setValue(String.valueOf(main.getOrderTypeById(master.getCategory()).getName()));
        commissionTextField.setText(String.valueOf(master.getCommission().floatValue()));
        salaryTextField.setText(String.valueOf(master.getSalary()));
    }
}
