package run.order_type;

import entity.OrderType;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import org.controlsfx.dialog.Dialogs;
import run.Main;

/**
 * Created by Oleg Dashkevych on 06.05.2015.
 */
public class OrderTypeController {


    @FXML
    private TableView<OrderType> typeTableView;

    @FXML
    private TableColumn<OrderType, String> idColumn;

    @FXML
    private TableColumn<OrderType, String> nameColumn;

    @FXML
    private TableColumn<OrderType, String> priceColumn;

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
    private RadioButton idRadio;

    @FXML
    private RadioButton nameRadio;

    @FXML
    private RadioButton priceRadio;

    @FXML
    private TextField searchTextField;

    @FXML
    private Label successDeleteLabel;

    @FXML
    private Label maxPriceLabel;

    @FXML
    private Label minPriceLabel;

    @FXML
    private Slider priceSlider;

    public OrderTypeController() {
    }

    private Main main;

    public void setMain(Main main) {
        this.main = main;
        typeTableView.setItems(main.getAllOrderTypes());
    }

    public void searchById() {
        try {
            int id = Integer.valueOf(searchTextField.getText());
            typeTableView.setItems(main.searchOrderTypeById(id));
            if(typeTableView.getItems().equals(null)){
                Dialogs.create()
                        .title("Alert")
                        .masthead("No order1 types in data base")
                        .showInformation();
            }
        }catch (NumberFormatException e){
            searchTextField.setText("");
            Dialogs.create()
                    .title("Warning")
                    .masthead("Invalid input")
                    .showWarning();
        }
    }

    public void searchByName() {
        String name = searchTextField.getText();
        typeTableView.setItems(main.searchOrderTypeByName(name));
        if(typeTableView.getItems().equals(null)){

            Dialogs.create()
                    .title("Alert")
                    .masthead("No OrderTypes in data base")
                    .showInformation();
        }
    }

    public void searchByPrice() {
        if (!searchTextField.getText().equals("")) {
        try {

                double price = Double.valueOf(searchTextField.getText());
                typeTableView.setItems(main.searchOrderTypeByPrice(price));
                if (typeTableView.getItems().equals(null)) {
                    Dialogs.create()
                            .title("Alert")
                            .masthead("No details in data base")
                            .showInformation();
                }
            }catch(NumberFormatException e){
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
        selectBrandRadio();
        nameRadio.setSelected(true);
        priceSlider.setValue(0);
        search();
    }

    public void search() {
        if (searchTextField.getText().isEmpty()) {
            typeTableView.setItems(main.getAllOrderTypes());
        }
        if (idRadio.isSelected()) {
            searchById();
        }
        if (nameRadio.isSelected()) {
            searchByName();
        }
        if(priceRadio.isSelected()){
            searchByPrice();
        }
    }

    public void selectIdRadio() {
        priceRadio.setSelected(false);
        nameRadio.setSelected(false);
    }

    public void selectPriceRadio() {
        idRadio.setSelected(false);
        nameRadio.setSelected(false);
    }

    public void selectBrandRadio() {
        idRadio.setSelected(false);
        priceRadio.setSelected(false);
    }

    public void deleteOrderType() {
        if (typeTableView.getSelectionModel().getSelectedItem() == null) {

            Dialogs.create()
                    .title("Alert")
                    .masthead("No details in data base")
                    .showWarning();

        } else {
            typeTableView.setItems(main.deleteOrderType(typeTableView.getSelectionModel().getSelectedItem()));
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
                        .masthead("Couldn't delete a detail!")
                        .showError();
            }
        }
    }

    public void showAddOrderType() {
        main.showAddOrderTypes();
    }

    public void showEditOrderType() {

        if (typeTableView.getSelectionModel().getSelectedItem() == null) {

            Dialogs.create()
                    .title("Alert")
                    .masthead("No selected detail")
                    .showWarning();

        } else {
            OrderType detail = typeTableView.getSelectionModel().getSelectedItem();
            main.showEditOrderType(detail);
        }
    }


    public void filterPrice() {
        maxPriceLabel.setText(String.valueOf(main.getMaxOrderTypePrice()));
        double range = main.getMaxOrderTypePrice() - main.getMinOrderTypePrice();
        double value = main.getMinOrderTypePrice() + ((priceSlider.getValue() * range) / 100);
        double finalValue = Math.round(value * 100.0) / 100.0;
        minPriceLabel.setText(String.valueOf(finalValue));
        typeTableView.setItems(main.filterOrderTypeByPrice(finalValue));
    }

    private void fillTable() {
        idColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getTypeId())));
        nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        priceColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getPrice())));
        idRadio.setSelected(true);
    }


    public void initialSet() {
        if(main.getAllOrderTypes() != null) {
            minPriceLabel.setText(String.valueOf(main.getMinOrderTypePrice()));
            maxPriceLabel.setText(String.valueOf(main.getMaxOrderTypePrice()));
            fillTable();
        }
        priceSlider.setValue(0);
    }

}
