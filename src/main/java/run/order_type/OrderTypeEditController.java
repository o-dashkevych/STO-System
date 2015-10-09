package run.order_type;

import entity.OrderType;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.controlsfx.dialog.Dialogs;
import run.Main;

/**
 * Created by Oleg Dashkevych on 06.05.2015.
 */
public class OrderTypeEditController {

    @FXML
    public Label idAlertLabel;

    @FXML
    public Label nameAlertLabel;

    @FXML
    public Label idExistAlertLabel;

    @FXML
    public Label priceAlertLabel;

    @FXML
    public TextField idTextField;

    @FXML
    public TextField priceTextField;

    @FXML
    public TextField nameTextField;

    @FXML
    public Button saveButton;

    @FXML
    public Button cancelButton;

    @FXML
    public Label successAddLabel;

    private Main main;

    public OrderTypeEditController() {
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void checkId() {
        try {
            int id = Integer.valueOf(idTextField.getText());
            if (main.checkOrderTypeId(id)) {
                OrderType OrderType = main.getOrderTypeById(id);
                nameTextField.setText(OrderType.getName());
                priceTextField.setText(Double.valueOf(OrderType.getPrice()).toString());
            } else {
                nameTextField.setText("");
                priceTextField.setText("");
            }
        } catch (NumberFormatException e) {
            Dialogs.create()
                    .title("Warning")
                    .masthead("Invalid input")
                    .showWarning();
        }
    }

    public void save() {
        int id = Integer.valueOf(idTextField.getText());
        String name = nameTextField.getText();
        double price = Double.valueOf(priceTextField.getText());
        if (!main.checkOrderTypeId(id)) {
            Dialogs.create()
                    .title("Error")
                    .masthead("Couldn't edit a OrderType!")
                    .showError();
            return;
        }
        if (name == "") {
            nameAlertLabel.setVisible(true);
            return;
        }
        if (price == 0) {
            priceAlertLabel.setVisible(true);
            return;
        } else {
            nameAlertLabel.setVisible(false);
            priceAlertLabel.setVisible(false);
            boolean result = main.updateOrderType(id, name, price);
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
                        .masthead("Couldn't edit a OrderType!")
                        .showError();
            }
        }
    }


    public void fillBoxes(OrderType orderType) {
        idTextField.setText(String.valueOf(orderType.getTypeId()));
        nameTextField.setText(orderType.getName());
        priceTextField.setText(String.valueOf(orderType.getPrice()));
    }

    public void getOrderTypeMenu() {
        main.showOrderTypeMenu();
    }
}


