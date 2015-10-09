package run.order1;

import entity.Car;
import entity.Master;
import entity.Order1;
import entity.OrderType;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.util.Duration;
import org.controlsfx.dialog.Dialogs;
import run.Main;

import java.util.*;

public class OrderEditController {

    @FXML
    private Label successAddLabel;
    @FXML
    private ToggleButton executedToggle;

    @FXML
    private Label orderNumLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private ComboBox<String> brandComboBox;

    @FXML
    private ComboBox<String> carIdComboBox;

    @FXML
    private ComboBox<String> masterIdComboBox;

    @FXML
    private ComboBox<String> masterComboBox;

    @FXML
    private Button saveButton;

    @FXML
    private ComboBox<String> modelComboBox;

    private ObservableList<String> passportsBusy;
    private ObservableList<String> passportsFree;

    private Order1 editedOrder;

    private Main main;


    @FXML
    private void getOrderMenu() {
        main.showOrderMenu();
    }

    public void setMain(Main main) {
        this.main = main;
        passportsBusy = FXCollections.observableArrayList();
        passportsFree = FXCollections.observableArrayList();
    }

    public OrderEditController() {
    }
    public void fillTypeCombo() {
        ObservableList<OrderType> types = null;
        types = main.getAllOrderTypes();
        if (types.size() == 0) {
            Dialogs.create()
                    .title("Error")
                    .masthead("Couldn't edit an order! Add types of services to specify a service of order.")
                    .showError();
            main.showOrderMenu();
        }
        List<String> names = new ArrayList<>();
        for (OrderType type : types) {
            names.add(type.getName());
        }
        for (String name : names) {
            categoryComboBox.getItems().add(name);
        }
    }

    public void fillBrandCombo() {
        ObservableList<Car> cars = null;
        cars = main.getAllCars();
        if (cars.size() == 0) {
            Dialogs.create()
                    .title("Error")
                    .masthead("Couldn't add an order! Add cars to specify it's brand.")
                    .showError();
            main.showOrderMenu();
        }
        Set<String> names = new HashSet<>();
        for (Car car : cars) {
            names.add(car.getBrand());
        }
        for (String name : names) {
            brandComboBox.getItems().add(name);
        }
    }

    public void fillModelCombo() {
        modelComboBox.getItems().clear();
        modelComboBox.setDisable(false);
        ObservableList<Car> cars = main.searchCarByBrand(brandComboBox.getValue());
        Set<String> names = new HashSet<>();
        for (Car car : cars) {
            names.add(car.getModel());
        }
        for (String name : names) {
            modelComboBox.getItems().add(name);
        }
    }

    public void fillCarIdCombo() {
        carIdComboBox.setDisable(false);
        carIdComboBox.getItems().clear();
        ObservableList<Car> cars = main.searchCarByBrandModel(brandComboBox.getValue(), modelComboBox.getValue());
        List<String> names = new ArrayList<>();
        for (Car car : cars) {
            names.add(car.getCarId());
        }
        for (String name : names) {
            carIdComboBox.getItems().add(name);
        }

    }

    public void fillMasterCombo() {

        masterComboBox.getItems().clear();
        ObservableList<Master> allMasters = main.searchMasterByCategory(categoryComboBox.getValue());
        ObservableList<Master> freeMasters = main.getFreeMasters(categoryComboBox.getValue());
        ObservableList<Master> busyMasters = main.getBusyMasters(categoryComboBox.getValue());
        busyMasters.forEach(master1 -> passportsBusy.add(master1.getPassportId()));
        freeMasters.forEach(master1 -> passportsFree.add(master1.getPassportId()));

        Set<String> names = new HashSet<>();
        for (Master master : allMasters) {
            names.add(master.getName());
        }
        Set<String> freeNames = new HashSet<>();
        for (Master master : freeMasters) {
            freeNames.add(master.getName());
        }
        Set<String> busyNames = new HashSet<>();
        for (Master master : allMasters) {
            busyNames.add(master.getName());
        }
        for (String name : names) {
            if (freeNames.contains(name)) {
                if (busyNames.contains(name)) {
                    String str = new String("(+/-) " + name);
                    masterComboBox.getItems().add(str);
                    continue;
                } else {
                    String str = new String("(+)   " + name);
                    masterComboBox.getItems().add(str);
                    continue;
                }
            } if (busyNames.contains(name))  {
                String str = new String("(-)   " + name);
                masterComboBox.getItems().add(str);
            }

        }
    }

    public void fillMasterIdCombo() {
        masterIdComboBox.setDisable(false);
        masterIdComboBox.getItems().clear();
        String name = masterComboBox.getValue().substring(6);
        //  ObservableList<Master> masters = main.searchMasterByName(name);
        //   List<String> passports = new ArrayList<>();
        ObservableList<Master> masters = main.searchMasterByName(name);
        for (Master master:masters) {
            if (passportsFree.size() != 0) {
                if (passportsFree.contains(master.getPassportId())) {
                    String pass = new String("(+) " + master.getPassportId());
                    masterIdComboBox.getItems().add(pass);
                    continue;
                }
                if (passportsBusy.size() != 0) {
                    if (passportsBusy.contains(master.getPassportId())) {
                        String pass = new String("(-) " + master.getPassportId());
                        masterIdComboBox.getItems().add(pass);
                        continue;
                    }
                } else {
                    String pass = new String("    " + master.getPassportId());
                    masterIdComboBox.getItems().add(pass);
                }
            }
        }
    }


    public void save() {
        java.util.Date date = new Date(System.currentTimeMillis());
        String type = categoryComboBox.getValue();
        String brand = brandComboBox.getValue();
        String model = modelComboBox.getValue();
        String carId = carIdComboBox.getValue();
        String masterName = masterComboBox.getValue().substring(4);
        String masterId = masterIdComboBox.getValue().substring(4);
/*
        if (type.equals(null)) {
            serviceLabel.setVisible(true);
            return;
        }
        if (brand.equals(null)) {
            brandLabel.setVisible(true);
            return;
        }
        if (model.equals(null)) {
            modelLabel.setVisible(true);
            return;
        }
        if (carId.equals(null)) {
            carIdLabel.setVisible(true);
            return;
        }
        if (masterName.equals(null)) {
            masterLabel.setVisible(true);
            return;
        }
        if (masterId.equals(null)) {
            masterIdLabel.setVisible(true);
            return;
        } else {
            serviceLabel.setVisible(false);
            brandLabel.setVisible(false);
            modelLabel.setVisible(false);
            carIdLabel.setVisible(false);
            masterLabel.setVisible(false);
            masterIdLabel.setVisible(false);*/
            int exec = 0;
            if(executedToggle.isSelected()) exec = 1;
            boolean resultSave = main.updateOrder(editedOrder.getOrderId(),main.getOrderTypeByName(type).getPrice(), date, carId, masterId,
                    main.getOrderTypeByName(type).getTypeId(), main.getCarById(carId), main.getMasterByPassport(masterId), main.getOrderTypeByName(type), exec);
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
                        .masthead("Couldn't add an order1!")
                        .showError();
            }
        //}
    }

    public void init(Order1 order1) {
        editedOrder = order1;
        orderNumLabel.setText(String.valueOf(order1.getOrderId()));
        dateLabel.setText(String.valueOf(order1.getDateRequest()));
        fillTypeCombo();
        fillBrandCombo();
        fillModelCombo();
        brandComboBox.setValue(editedOrder.getCarByCarCarId().getBrand());
        modelComboBox.setValue(editedOrder.getCarByCarCarId().getModel());
        fillCarIdCombo();
        carIdComboBox.setValue(editedOrder.getCarCarId());

        categoryComboBox.setValue(editedOrder.getOrderTypeByOrderTypeId().getName());
        fillMasterCombo();
        ObservableList<String> masterNames = masterComboBox.getItems();
        String editedMstrName = editedOrder.getMasterByMasterMasterId().getName();
        for (String name:masterNames){
            if(name.contains(editedMstrName)){
                masterComboBox.setValue(name);
                break;
            }
        }
        fillMasterIdCombo();
        ObservableList<String> masterIds = masterIdComboBox.getItems();
        for (String pass:masterIds){
            if(pass.contains(editedOrder.getMasterByMasterMasterId().getPassportId())){
                masterIdComboBox.setValue(pass);
                break;
            }
        }
        executedToggle.setSelected((editedOrder.getExecuted() == 1));
    }

    public void changeToggle(){
        if(executedToggle.isSelected()){
            executedToggle.setText("Выполнено");
        }
         else{
            executedToggle.setText("Невыполнено");
        }
    }
}
