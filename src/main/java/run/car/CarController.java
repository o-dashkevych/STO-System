package run.car;

import entity.Car;
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
 * Created by Oleg Dashkevych on 09.05.2015.
 */
public class CarController {

    @FXML
    private TableColumn<Car, String> yearColumn;

    @FXML
    private TableColumn<Car, String> carIdColumn;

    @FXML
    private TableColumn<Car, String> clientColumn;

    @FXML
    private RadioButton idRadio;

    @FXML
    private TableColumn<Car, String> passportColumn;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label successDeleteLabel;

    @FXML
    private RadioButton modelRadio;

    @FXML
    private TextField searchTextField;

    @FXML
    private RadioButton passportRadio;

    @FXML
    private TableColumn<Car, String> modelColumn;

    @FXML
    private RadioButton yearRadio;

    @FXML
    private TableView<Car> carTable;

    @FXML
    private TableColumn<Car, String> brandColumn;

    @FXML
    private RadioButton brandRadio;

    @FXML
    private RadioButton clientNameRadio;

    private Main main;

    public CarController() {
    }

    public void setMain(Main main) {
        this.main = main;
        carTable.setItems(main.getAllCars());
    }

    public void searchById() {
        String id = searchTextField.getText();
        carTable.setItems(main.searchCarById(id));
    }

    public void searchByName() {
        String name = searchTextField.getText();
        carTable.setItems(main.searchCarByName(searchTextField.getText()));
    }

    public void searchByPassport() {
        String id = searchTextField.getText();
        carTable.setItems(main.searchCarByPassport(id));
    }

    public void searchByBrand() {
        String name = searchTextField.getText();
        carTable.setItems(main.searchCarByBrand(name));
    }

    public void searchByModel() {
        String name = searchTextField.getText();
        carTable.setItems(main.searchCarByModel(name));
    }

    public void searchByDate() {

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(datePicker.getValue().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        carTable.setItems(main.searchCarByYear(date));
    }

    public void resetDefault() {
        searchTextField.setText("");
        datePicker.setValue(null);
        selectPassportRadio();
        passportRadio.setSelected(true);
        search();
    }

    public void search() {
        if (searchTextField.getText().isEmpty()) {
            carTable.setItems(main.getAllCars());
        }
        if (passportRadio.isSelected()) {
            searchByPassport();
        }
        if (idRadio.isSelected()) {
            searchById();
        }
        if (brandRadio.isSelected()) {
            searchByBrand();
        }
        if (modelRadio.isSelected()) {
            searchByModel();
        }
        if (yearRadio.isSelected()) {
            searchByDate();
        }
        if (clientNameRadio.isSelected()) {
            searchByName();
        }
    }

    public void selectPassportRadio() {
        searchTextField.setDisable(false);
        idRadio.setSelected(false);
        brandRadio.setSelected(false);
        modelRadio.setSelected(false);
        yearRadio.setSelected(false);
        datePicker.setDisable(true);
        clientNameRadio.setSelected(false);
    }

    public void selectIdRadio() {
        searchTextField.setDisable(false);
        passportRadio.setSelected(false);
        brandRadio.setSelected(false);
        modelRadio.setSelected(false);
        yearRadio.setSelected(false);
        datePicker.setDisable(true);
        clientNameRadio.setSelected(false);
    }

    public void selectBrandRadio() {
        searchTextField.setDisable(false);
        idRadio.setSelected(false);
        passportRadio.setSelected(false);
        modelRadio.setSelected(false);
        yearRadio.setSelected(false);
        datePicker.setDisable(true);
        clientNameRadio.setSelected(false);
    }

    public void selectModelRadio() {
        searchTextField.setDisable(false);
        idRadio.setSelected(false);
        passportRadio.setSelected(false);
        brandRadio.setSelected(false);
        yearRadio.setSelected(false);
        datePicker.setDisable(true);
        clientNameRadio.setSelected(false);
    }

    public void selectYearRadio() {
        searchTextField.setDisable(true);
        idRadio.setSelected(false);
        passportRadio.setSelected(false);
        brandRadio.setSelected(false);
        modelRadio.setSelected(false);
        datePicker.setDisable(false);
        clientNameRadio.setSelected(false);
    }

    public void selectNameRadio() {
        searchTextField.setDisable(false);
        idRadio.setSelected(false);
        passportRadio.setSelected(false);
        brandRadio.setSelected(false);
        modelRadio.setSelected(false);
        datePicker.setDisable(false);
    }

    public void deleteCar() {
        if (carTable.getSelectionModel().getSelectedItem() == null) {

            Dialogs.create()
                    .title("Alert")
                    .masthead("No cars in data base")
                    .showWarning();

        } else {
            carTable.setItems(main.deleteCar(carTable.getSelectionModel().getSelectedItem()));
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
                        .masthead("Couldn't delete a car!")
                        .showError();
            }
        }
    }

    public void showAddCar() {
        main.showAddCar();
    }

    public void showEditCar() {

        if (carTable.getSelectionModel().getSelectedItem() == null) {

            Dialogs.create()
                    .title("Alert")
                    .masthead("No selected client")
                    .showWarning();

        } else {
            Car car = carTable.getSelectionModel().getSelectedItem();
            main.showEditCar(car);
        }
    }

    public void fillTable() {
        passportColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getClientPassportId()));
        clientColumn.setCellValueFactory(cell -> new SimpleStringProperty(main.getClientByPassport(cell.getValue().getClientPassportId()).getName()));
        carIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCarId()));
        brandColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getBrand()));
        modelColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getModel()));
        yearColumn.setCellValueFactory(cell -> new SimpleStringProperty(DateUtil.format(cell.getValue().getYear().toLocalDate())));
        passportRadio.setSelected(true);
    }
}
