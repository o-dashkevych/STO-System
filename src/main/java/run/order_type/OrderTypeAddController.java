package run.order_type;

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
public class OrderTypeAddController {

    @FXML
    public Label nameAlertLabel;

    @FXML
    public Label priceAlertLabel;

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

    public OrderTypeAddController() {
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void save() {

        String name = nameTextField.getText();
        double price = Double.valueOf(priceTextField.getText());

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
            boolean result = main.addOrderType(name, price);
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
                        .masthead("Couldn't add an order1 type!")
                        .showError();
            }
        }
    }

    public void getOrderTypeMenu() {
        main.showOrderTypeMenu();
    }
}
