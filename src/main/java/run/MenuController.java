package run;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import util.ProgressControl;

/**
 * Created by Oleg Dashkevych on 26.04.2015.
 */
public class MenuController {

    @FXML
    private Button clients;

    @FXML
    private Button orderTypes;

    @FXML
    private Button orders;

    @FXML
    private Button cars;

    @FXML
    private Button masters;

    @FXML
    private ProgressIndicator progressIndicator;

    private Main main;

    private RootController rootController;

    @FXML
    private void initialize() {
        progressIndicator.setVisible(false);
    }

    public MenuController() {
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Main getMain(){
        return main;
    }

    public void getClientsMenu(){
        setProgressIndicator();
        main.showClientMenu();
    }

    public void getMastersMenu(){
        setProgressIndicator();
        main.showMasterMenu();
    }
    public void getOrderTypeMenu(){
        setProgressIndicator();
        main.showOrderTypeMenu();
    }

    public void getCarMenu(){
        setProgressIndicator();
        main.showCarMenu();
    }

    public void getOrderMenu(){
        setProgressIndicator();
        main.showOrderMenu();
    }

    public void setProgressIndicator(){
        ProgressControl progressControl = new ProgressControl();
        progressControl.setIndicator(progressIndicator);
        progressControl.start();
    }
}
