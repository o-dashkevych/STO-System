package run.master;

import entity.Master;
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

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Oleg Dashkevych on 04.05.2015.
 */
public class MasterController {

    @FXML
    private TableView<Master> mastersTable;

    @FXML
    private TableColumn<Master, String> passportColumn;

    @FXML
    private TableColumn<Master, String> birthColumn;

    @FXML
    private TableColumn<Master, String> nameColumn;

    @FXML
    private TableColumn<Master, String> acceptColumn;

    @FXML
    private TableColumn<Master, String> categoryColumn;

    @FXML
    private TableColumn<Master, String> commissionColumn;

    @FXML
    private TableColumn<Master, String> salaryColumn;

    @FXML
    private Slider salarySlider;

    @FXML
    private Slider commisionSlider;

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
    private RadioButton salaryRadio;

    @FXML
    private RadioButton commissionRadio;

    @FXML
    private RadioButton categoryRadio;

    @FXML
    private RadioButton birthRadio;

    @FXML
    private RadioButton acceptRadio;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField searchTextField;

    @FXML
    private Label successDeleteLabel;

    @FXML
    private Label maxSalaryLabel;

    @FXML
    private Label minSalaryLabel;

    @FXML
    private Label maxCommLabel;

    @FXML
    private Label minCommLabel;

    public MasterController() {
    }

    private Main main;

    public void setMain(Main main) {
        this.main = main;
        mastersTable.setItems(main.getAllMasters());
    }

    public void searchById() {
        String id = searchTextField.getText();
        mastersTable.setItems(main.searchMasterById(id));
    }

    public void searchByName() {
        String name = searchTextField.getText();
        mastersTable.setItems(main.searchMasterByName(name));
    }

    public void searchByDate() {

        Date date = null;
        if (datePicker.getValue() == null) {

            Dialogs.create()
                    .title("Alert")
                    .masthead("Enter valid date")
                    .showWarning();
        } else {
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(datePicker.getValue().toString());
            } catch (ParseException e) {
                e.printStackTrace();
                Dialogs.create()
                        .title("Alert")
                        .masthead("Enter valid date")
                        .showWarning();
            }
        }
        mastersTable.setItems(main.searchMasterByBirth(date));
    }

    public void searchByAdoption() {

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(datePicker.getValue().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mastersTable.setItems(main.searchMasterByAcception(date));
    }

    public void searchByCategory() {
        String category = searchTextField.getText();
        mastersTable.setItems(main.searchMasterByCategory(category));
    }

    public void searchBySalary() {
        Scanner scanner = new Scanner(searchTextField.getText());
        if (scanner.hasNextDouble()) {
            double salary = scanner.nextDouble();
            mastersTable.setItems(main.searchMasterBySalary(salary));
        } else {

            Dialogs.create()
                    .title("Alert")
                    .masthead("Enter valid number")
                    .showWarning();
        }
    }

    public void searchByCommission() {
        Scanner scanner = new Scanner(searchTextField.getText());
        if (scanner.hasNextBigDecimal()) {
            BigDecimal commission = scanner.nextBigDecimal();
            mastersTable.setItems(main.searchMasterByCommission(commission));
        } else {
            Dialogs.create()
                    .title("Alert")
                    .masthead("Enter valid number in format <0,??>")
                    .showWarning();
        }
    }

    public void resetDefault() {
        searchTextField.setText("");
        datePicker.setValue(null);
        selectNameRadio();
        nameRadio.setSelected(true);
        initialSet();
        search();
    }

    public void search() {
        initialSet();
        if (searchTextField.getText().isEmpty()) {
            mastersTable.setItems(main.getAllMasters());
        }
        if (passportRadio.isSelected()) {
            searchById();
        }
        if (nameRadio.isSelected()) {
            searchByName();
        }
        if (categoryRadio.isSelected()) {
            searchByCategory();
        }

    }

    public void searchByButton() {
        initialSet();
        if (searchTextField.getText().isEmpty()) {
            mastersTable.setItems(main.getAllMasters());
        }
        if (salaryRadio.isSelected()) {
            searchBySalary();
        }
        if (birthRadio.isSelected()) {
            searchByDate();
        }
        if (passportRadio.isSelected()) {
            searchById();
        }
        if (nameRadio.isSelected()) {
            searchByName();
        }
        if (acceptRadio.isSelected()) {
            searchByAdoption();
        }
        if (commissionRadio.isSelected()) {
            searchByCommission();
        }
        if (categoryRadio.isSelected()) {
            searchByCategory();
        }
    }

    public void selectPassportRadio() {
        searchTextField.setDisable(false);
        nameRadio.setSelected(false);
        birthRadio.setSelected(false);
        commissionRadio.setSelected(false);
        salaryRadio.setSelected(false);
        categoryRadio.setSelected(false);
        acceptRadio.setSelected(false);
        datePicker.setDisable(true);
    }

    public void selectNameRadio() {
        searchTextField.setDisable(false);
        passportRadio.setSelected(false);
        birthRadio.setSelected(false);
        commissionRadio.setSelected(false);
        salaryRadio.setSelected(false);
        categoryRadio.setSelected(false);
        acceptRadio.setSelected(false);
        datePicker.setDisable(true);
    }

    public void selectBirthRadio() {
        searchTextField.setDisable(true);
        passportRadio.setSelected(false);
        nameRadio.setSelected(false);
        commissionRadio.setSelected(false);
        salaryRadio.setSelected(false);
        categoryRadio.setSelected(false);
        acceptRadio.setSelected(false);
        datePicker.setDisable(false);
    }

    public void selectCommissionRadio() {
        searchTextField.setDisable(false);
        nameRadio.setSelected(false);
        birthRadio.setSelected(false);
        passportRadio.setSelected(false);
        salaryRadio.setSelected(false);
        categoryRadio.setSelected(false);
        acceptRadio.setSelected(false);
        datePicker.setDisable(true);
    }

    public void selectSalaryRadio() {
        searchTextField.setDisable(false);
        nameRadio.setSelected(false);
        birthRadio.setSelected(false);
        passportRadio.setSelected(false);
        commissionRadio.setSelected(false);
        categoryRadio.setSelected(false);
        acceptRadio.setSelected(false);
        datePicker.setDisable(true);
    }

    public void selectAcceptRadio() {
        searchTextField.setDisable(true);
        nameRadio.setSelected(false);
        birthRadio.setSelected(false);
        passportRadio.setSelected(false);
        commissionRadio.setSelected(false);
        categoryRadio.setSelected(false);
        salaryRadio.setSelected(false);
        datePicker.setDisable(false);
    }

    public void selectCategoryRadio() {
        searchTextField.setDisable(false);
        nameRadio.setSelected(false);
        birthRadio.setSelected(false);
        passportRadio.setSelected(false);
        commissionRadio.setSelected(false);
        salaryRadio.setSelected(false);
        acceptRadio.setSelected(false);
        datePicker.setDisable(true);
    }

    public void deleteMaster() {
        if (mastersTable.getSelectionModel().getSelectedItem() == null) {

            Dialogs.create()
                    .title("Alert")
                    .masthead("No selected master")
                    .showWarning();

        } else {
            mastersTable.setItems(main.deleteMaster(mastersTable.getSelectionModel().getSelectedItem()));
            if (main.getResult()) {
                initialSet();
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
                        .masthead("Couldn't delete a master!")
                        .showError();
            }
        }
    }

    public void showAddMaster() {
        main.showAddMasters();
    }

    public void showEditMaster() {

        if (mastersTable.getSelectionModel().getSelectedItem() == null) {

            Dialogs.create()
                    .title("Alert")
                    .masthead("No selected master")
                    .showWarning();

        } else {
            Master master = mastersTable.getSelectionModel().getSelectedItem();
            main.showEditMaster(master);
        }
    }

    public void fillTable() {
        passportColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPassportId()));
        nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        categoryColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getOrderTypeByCategory().getName())));
        birthColumn.setCellValueFactory(cell -> new SimpleStringProperty(DateUtil.format(cell.getValue().getBirthday().toLocalDate())));
        acceptColumn.setCellValueFactory(cell -> new SimpleStringProperty(DateUtil.format(cell.getValue().getAdoptionDate().toLocalDate())));
        salaryColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getSalary())));
        commissionColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCommission())));
        nameRadio.setSelected(true);
    }

    public void initialSet() {
        minSalaryLabel.setText(String.valueOf(main.getMinSalary()));
        maxSalaryLabel.setText(String.valueOf(main.getMaxSalary()));
        minCommLabel.setText(String.valueOf(main.getMinCommission()));
        maxCommLabel.setText(String.valueOf(main.getMaxCommission()));
        salarySlider.setValue(0);
        commisionSlider.setValue(0);
    }

    public void filterSalary() {
        minCommLabel.setText(String.valueOf(main.getMinCommission()));
        commisionSlider.setValue(0);
        double range = main.getMaxSalary() - main.getMinSalary();
        double value = main.getMinSalary() + ((salarySlider.getValue() * range) / 100);
        double finalValue = Math.round(value * 100.0) / 100.0;
        minSalaryLabel.setText(String.valueOf(finalValue));
        mastersTable.setItems(main.filterMasterBySalary(finalValue));
    }

    public void filterCommission() {
        minSalaryLabel.setText(String.valueOf(main.getMinSalary()));
        salarySlider.setValue(0);
        double range = main.getMaxCommission().doubleValue() - main.getMinCommission().doubleValue();
        double value = main.getMinCommission().doubleValue() + ((commisionSlider.getValue() * range) / 100);
        double finalValue = Math.round(value * 100.0) / 100.0;
        minCommLabel.setText(String.valueOf(finalValue));
        mastersTable.setItems(main.filterMasterByCommission(BigDecimal.valueOf(finalValue)));
    }
}
