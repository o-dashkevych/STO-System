package run.order1;

import entity.Order1;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import org.controlsfx.dialog.Dialogs;
import run.Main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderController {


    @FXML
    private TableView<Order1> ordersTable;

    @FXML
    private TableColumn<Order1, String> orderIdColumn;

    @FXML
    private TableColumn<Order1, String> dateColumn;

    @FXML
    private TableColumn<Order1, String> carIdColumn;

    @FXML
    private TableColumn<Order1, String> carNameColumn;

    @FXML
    private TableColumn<Order1, String> masterIdColumn;

    @FXML
    private TableColumn<Order1, String> masterColumn;

    @FXML
    private TableColumn<Order1, String> serviceColumn;

    @FXML
    private TableColumn<Order1, String> priceColumn;

    @FXML
    private TableColumn<Order1, String> executedColumn;

    @FXML
    private RadioButton masterIdRadio;

    @FXML
    private Button searchButton;

    @FXML
    private Button deleteButton;

    @FXML
    private RadioButton dateRadio;

    @FXML
    private RadioButton notExecRadio;

    @FXML
    private RadioButton execRadio;

    @FXML
    private Label successDeleteLabel;

    @FXML
    private Slider priceSlider;

    @FXML
    private TextField searchTextField;

    @FXML
    private RadioButton carRadio;

    @FXML
    private Label minSalaryLabel;

    @FXML
    private Button resetButton;

    @FXML
    private Label maxSalaryLabel;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button addButton;

    @FXML
    private Label testLab;

    @FXML
    private RadioButton masterRadio;

    @FXML
    private RadioButton serviceRadio;

    @FXML
    private Button editButton;

    @FXML
    private RadioButton carIdRadio;

    @FXML
    private RadioButton priceRadio;

    private Main main;

    public OrderController() {
    }

    public void getEditOrder() {

        if (ordersTable.getSelectionModel().getSelectedItem() == null) {

            Dialogs.create()
                    .title("Alert")
                    .masthead("No selected order")
                    .showWarning();

        } else {
            Order1 item = ordersTable.getSelectionModel().getSelectedItem();
            main.showEditOrder(item);
        }
    }

    public void setMain(Main main) {
        this.main = main;
        ordersTable.setItems(main.getAllOrders());
    }

    public void searchByDate() {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(datePicker.getValue().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ordersTable.setItems(main.searchOrderByYear(date));
    }

    public void searchByCarId() {
        try {
            String id = searchTextField.getText();
            ordersTable.setItems(main.searchOrderByCarId(id));
            if (ordersTable.getItems().equals(null)) {
                Dialogs.create()
                        .title("Alert")
                        .masthead("No order1 types in data base")
                        .showInformation();
            }
        } catch (NumberFormatException e) {
            searchTextField.setText("");
            Dialogs.create()
                    .title("Warning")
                    .masthead("Invalid input")
                    .showWarning();
        }
    }

    public void searchByCarName() {
        String name = searchTextField.getText();
        ordersTable.setItems(main.getOrderByCarName(name));
        if (ordersTable.getItems().equals(null)) {

            Dialogs.create()
                    .title("Alert")
                    .masthead("No orders in data base")
                    .showInformation();
        }
    }

    public void searchByMasterId() {
        try {
            String id = searchTextField.getText();
            ordersTable.setItems(main.searchOrderByMasterId(id));
            if (ordersTable.getItems().equals(null)) {
                Dialogs.create()
                        .title("Alert")
                        .masthead("No order1 types in data base")
                        .showInformation();
            }
        } catch (NumberFormatException e) {
            searchTextField.setText("");
            Dialogs.create()
                    .title("Warning")
                    .masthead("Invalid input")
                    .showWarning();
        }
    }

    public void searchByMasterName() {
        String name = searchTextField.getText();
        ordersTable.setItems(main.getOrderByMasterName(name));
        if (ordersTable.getItems().equals(null)) {
            Dialogs.create()
                    .title("Alert")
                    .masthead("No orders in data base")
                    .showInformation();
        }
    }

    public void searchByType() {
        String name = searchTextField.getText();
        ordersTable.setItems(main.getOrderByTypeName(name));
        if (ordersTable.getItems().size() == 0) {
            Dialogs.create()
                    .title("Alert")
                    .masthead("No orders in data base")
                    .showInformation();
        }
    }

    public void searchByPrice() {
        if(!searchTextField.getText().equals("")) {
            try {
                double price = Double.valueOf(searchTextField.getText());
                ordersTable.setItems(main.searchOrderByPrice(price));
                if (ordersTable.getItems().equals(null)) {
                    Dialogs.create()
                            .title("Alert")
                            .masthead("No orders in data base")
                            .showInformation();
                }
            } catch (NumberFormatException e) {
                searchTextField.setText("");
                Dialogs.create()
                        .title("Warning")
                        .masthead("Invalid input")
                        .showWarning();
            }
        }
    }

    public void resetDefault() {
        searchTextField.setText("");
        selectDateRadio();
        dateRadio.setSelected(true);
        priceSlider.setValue(0);
        searchButton.setDisable(false);
        search();
    }

    public void search() {
        if (searchTextField.getText().isEmpty()) {
            ordersTable.setItems(main.getAllOrders());
        }
        if (dateRadio.isSelected()) {
            searchByDate();
        }
        if (carIdRadio.isSelected()) {
            searchByCarId();
        }
        if (carRadio.isSelected()) {
            searchByCarName();
        }
        if (masterIdRadio.isSelected()) {
            searchByMasterId();
        }
        if (masterRadio.isSelected()) {
            searchByMasterName();
        }
        if (serviceRadio.isSelected()) {
            searchByType();
        }
        if (priceRadio.isSelected()) {
            searchByPrice();
        }
    }

    public void selectDateRadio() {
        carIdRadio.setSelected(false);
        carRadio.setSelected(false);
        masterIdRadio.setSelected(false);
        masterRadio.setSelected(false);
        serviceRadio.setSelected(false);
        priceRadio.setSelected(false);
        searchTextField.setDisable(true);
        searchButton.setDisable(false);
        notExecRadio.setSelected(false);
        execRadio.setSelected(false);
        datePicker.setDisable(false);
    }

    public void selectExecRadio() {
        carIdRadio.setSelected(false);
        notExecRadio.setSelected(false);
        dateRadio.setSelected(false);
        carRadio.setSelected(false);
        masterIdRadio.setSelected(false);
        masterRadio.setSelected(false);
        serviceRadio.setSelected(false);
        priceRadio.setSelected(false);
        searchTextField.setDisable(true);
        searchButton.setDisable(true);
        ordersTable.setItems(main.getExecuted());
        datePicker.setDisable(true);
        if (ordersTable.getItems().equals(null)) {

            Dialogs.create()
                    .title("Alert")
                    .masthead("No executed orders in data base")
                    .showInformation();
        }
    }

    public void selectNotExecRadio() {
        carIdRadio.setSelected(false);
        execRadio.setSelected(false);
        dateRadio.setSelected(false);
        carRadio.setSelected(false);
        masterIdRadio.setSelected(false);
        masterRadio.setSelected(false);
        serviceRadio.setSelected(false);
        priceRadio.setSelected(false);
        searchTextField.setDisable(true);
        searchButton.setDisable(true);
        ordersTable.setItems(main.getNotExecuted());
        datePicker.setDisable(true);
        if (ordersTable.getItems().equals(null)) {

            Dialogs.create()
                    .title("Alert")
                    .masthead("There are only executed orders in data base")
                    .showInformation();
        }
    }

    public void selectCarIdRadio() {
        dateRadio.setSelected(false);
        masterIdRadio.setSelected(false);
        masterRadio.setSelected(false);
        serviceRadio.setSelected(false);
        priceRadio.setSelected(false);
        searchTextField.setDisable(false);
        searchButton.setDisable(false);
        notExecRadio.setSelected(false);
        execRadio.setSelected(false);
        datePicker.setDisable(true);
    }

    public void selectCarRadio() {
        dateRadio.setSelected(false);
        carIdRadio.setSelected(false);
        masterIdRadio.setSelected(false);
        masterRadio.setSelected(false);
        serviceRadio.setSelected(false);
        priceRadio.setSelected(false);
        searchTextField.setDisable(false);
        searchButton.setDisable(false);
        notExecRadio.setSelected(false);
        execRadio.setSelected(false);
        datePicker.setDisable(true);
    }

    public void selectMasterIdRadio() {
        dateRadio.setSelected(false);
        carIdRadio.setSelected(false);
        carRadio.setSelected(false);
        masterRadio.setSelected(false);
        serviceRadio.setSelected(false);
        priceRadio.setSelected(false);
        searchTextField.setDisable(false);
        searchButton.setDisable(false);
        notExecRadio.setSelected(false);
        execRadio.setSelected(false);
        datePicker.setDisable(true);
    }

    public void selectMasterRadio() {
        dateRadio.setSelected(false);
        carIdRadio.setSelected(false);
        carRadio.setSelected(false);
        masterIdRadio.setSelected(false);
        serviceRadio.setSelected(false);
        priceRadio.setSelected(false);
        searchTextField.setDisable(false);
        searchButton.setDisable(false);
        notExecRadio.setSelected(false);
        execRadio.setSelected(false);
        datePicker.setDisable(true);
    }

    public void selectServiceRadio() {
        dateRadio.setSelected(false);
        carIdRadio.setSelected(false);
        carRadio.setSelected(false);
        masterIdRadio.setSelected(false);
        masterRadio.setSelected(false);
        priceRadio.setSelected(false);
        searchTextField.setDisable(false);
        searchButton.setDisable(false);
        notExecRadio.setSelected(false);
        execRadio.setSelected(false);
        datePicker.setDisable(true);
    }

    public void selectPriceRadio() {
        dateRadio.setSelected(false);
        carIdRadio.setSelected(false);
        carRadio.setSelected(false);
        masterIdRadio.setSelected(false);
        masterRadio.setSelected(false);
        serviceRadio.setSelected(false);
        searchTextField.setDisable(false);
        searchButton.setDisable(false);
        notExecRadio.setSelected(false);
        execRadio.setSelected(false);
        datePicker.setDisable(true);
    }

    public void deleteOrder() {
        if (ordersTable.getSelectionModel().getSelectedItem() == null) {

            Dialogs.create()
                    .title("Alert")
                    .masthead("No details in data base")
                    .showWarning();

        } else {
            ordersTable.setItems(main.deleteOrder(ordersTable.getSelectionModel().getSelectedItem()));
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
                        .masthead("Couldn't delete an order1!")
                        .showError();
            }
        }
    }

    public void showAddOrderType() {
        main.showAddOrder();
    }

    public void filterPrice() {
        maxSalaryLabel.setText(String.valueOf(main.getMaxOrderPrice()));
        double range = main.getMaxOrderPrice() - main.getMinOrderPrice();
        double value = main.getMinOrderPrice() + ((priceSlider.getValue() * range) / 100);
        double finalValue = Math.round(value * 100.0) / 100.0;
        minSalaryLabel.setText(String.valueOf(finalValue));
        ordersTable.setItems(main.filterOrderByPrice(finalValue));

    }

    private void fillTable() {/*
        dateColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getDateRequest())));
        carIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCarCarId()));
        carNameColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(main.getCarById(cell.getValue().getCarByCarCarId().getBrand()
                + " " + cell.getValue().getCarByCarCarId().getModel()))));
        masterIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMasterMasterId()));
        masterColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMasterByMasterMasterId().getName()));
        serviceColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getOrderTypeByOrderTypeId().getName()));
        priceColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getOrderTypeByOrderTypeId().getPrice())));
        executedColumn.setCellValueFactory(cell -> new SimpleStringProperty((cell.getValue().getExecuted() == 0) ? "да" : "нет"));
        priceRadio.setSelected(true);
*/
        orderIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getOrderId())));
        dateColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDateRequest().toString()));
        carIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCarCarId()));
        carNameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCarByCarCarId().getBrand()+" " + cell.getValue().getCarByCarCarId().getModel()));
        masterIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMasterMasterId()));
        masterColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMasterByMasterMasterId().getName()));
        serviceColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getOrderTypeByOrderTypeId().getName()));
        priceColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getPrice())));
        executedColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getExecuted())));
    }

    public void initialSet() {
        if (main.getAllOrders().size() != 0) {
            minSalaryLabel.setText(String.valueOf(main.getMinOrderPrice()));
            maxSalaryLabel.setText(String.valueOf(main.getMaxOrderPrice()));
            fillTable();
        }
        priceSlider.setValue(0);
        selectDateRadio();
        dateRadio.setSelected(true);


    }
}