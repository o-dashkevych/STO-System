package run.order1;

import entity.Car;
import entity.Master;
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
import javafx.util.Duration;
import org.controlsfx.dialog.Dialogs;
import run.Main;

import java.util.*;

public class OrderAddController {

    @FXML
    private Label masterLabel;

    @FXML
    private Label carIdLabel;

    @FXML
    private Label modelLabel;

    @FXML
    private Label brandLabel;

    @FXML
    private Label masterIdLabel;

    @FXML
    private Label serviceLabel;

    @FXML
    private Label catrgoryLabel;

    @FXML
    private Label orderLabel;

    @FXML
    private Label orderIdLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private ComboBox<String> carIdComboBox;

    @FXML
    private ComboBox<String> brandComboBox;

    @FXML
    private ComboBox<String> modelComboBox;

    @FXML
    private Label successAddLabel;

    @FXML
    private Label adoptDateLabel;

    @FXML
    private ComboBox<String> idMasterComboBox;

    @FXML
    private Button saveButton;

    @FXML
    private Label catrgoryLabel1;

    @FXML
    private ComboBox<String> masterComboBox;

    private Main main;

    private ObservableList<String> passportsBusy = FXCollections.observableArrayList();
    private ObservableList<String> passportsFree = FXCollections.observableArrayList();

    public void setMain(Main main) {
        this.main = main;
    }

    public OrderAddController() {
    }

    @FXML
    private void getOrderMenu() {
        main.showOrderMenu();
    }

    public void fillTypeCombo() {
        orderLabel.setVisible(false);
        orderIdLabel.setVisible(false);
        ObservableList<OrderType> types = null;
        types = main.getAllOrderTypes();
        if (types.size() == 0) {
            Dialogs.create()
                    .title("Error")
                    .masthead("Couldn't add an order1! Add types of services to specify a service of order1.")
                    .showError();
            main.showOrderMenu();
        }
        List<String> names = new ArrayList<>();
        for (OrderType type : types) {
            names.add(type.getName());
        }
        for (String name : names) {
            typeComboBox.getItems().add(name);
        }
    }

    public void fillBrandCombo() {
        orderLabel.setVisible(false);
        orderIdLabel.setVisible(false);
        brandComboBox.setDisable(false);
        masterComboBox.getItems().clear();
        idMasterComboBox.getItems().clear();
        ObservableList<Car> cars = null;
        cars = main.getAllCars();
        if (cars.size() == 0) {
            Dialogs.create()
                    .title("Error")
                    .masthead("Couldn't add an order1! Add cars to specify it's brand.")
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
        modelComboBox.setValue(null);
        //  brandComboBox.setValue(brandComboBox.getItems().get(0));

    }

    public void fillModelCombo() {
        orderLabel.setVisible(false);
        orderIdLabel.setVisible(false);
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
        carIdComboBox.setValue(null);
        carIdComboBox.setDisable(true);
    }

    public void fillCarIdCombo() {
        orderLabel.setVisible(false);
        orderIdLabel.setVisible(false);
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
        orderLabel.setVisible(false);
        orderIdLabel.setVisible(false);
        masterComboBox.setDisable(false);
        masterComboBox.getItems().clear();
        ObservableList<Master> allMasters = main.searchMasterByCategory(typeComboBox.getValue());
        ObservableList<Master> freeMasters = main.getFreeMasters(typeComboBox.getValue());
        ObservableList<Master> busyMasters = main.getBusyMasters(typeComboBox.getValue());
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
        orderLabel.setVisible(false);
        orderIdLabel.setVisible(false);
        idMasterComboBox.setDisable(false);
        idMasterComboBox.getItems().clear();
        String name = masterComboBox.getValue().substring(6);
      //  ObservableList<Master> masters = main.searchMasterByName(name);
     //   List<String> passports = new ArrayList<>();
        ObservableList<Master> masters = main.searchMasterByName(name);
        for (Master master:masters) {
            if (passportsFree.size() != 0) {
                if (passportsFree.contains(master.getPassportId())) {
                    String pass = new String("(+) " + master.getPassportId());
                    idMasterComboBox.getItems().add(pass);
                    continue;
                }
                if (passportsBusy.size() != 0) {
                    if (passportsBusy.contains(master.getPassportId())) {
                        String pass = new String("(-) " + master.getPassportId());
                        idMasterComboBox.getItems().add(pass);
                        continue;
                    }
                } else {
                    String pass = new String("    " + master.getPassportId());
                    idMasterComboBox.getItems().add(pass);
                }
            }
        }
    }


    public void save() {
        java.util.Date date = new Date(System.currentTimeMillis());
        String type = typeComboBox.getValue();
        String brand = brandComboBox.getValue();
        String model = modelComboBox.getValue();
        String carId = carIdComboBox.getValue();
        String masterName = masterComboBox.getValue().substring(4);
        String masterId = idMasterComboBox.getValue().substring(4);

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
            masterIdLabel.setVisible(false);
            boolean resultSave = main.addOrder(main.getOrderTypeByName(type).getPrice(), date, carId, masterId,
                    main.getOrderTypeByName(type).getTypeId(), main.getCarById(carId), main.getMasterByPassport(masterId), main.getOrderTypeByName(type));
            if (resultSave) {
                orderIdLabel.setText(String.valueOf(main.getLastOrderIndex()));
                orderLabel.setVisible(true);
                orderIdLabel.setVisible(true);
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
        }
    }

    public void init() {
        fillTypeCombo();
    }
}
