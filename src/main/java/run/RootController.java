package run;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;

/**
 * Created by Oleg Dashkevych on 04.05.2015.
 */
public class RootController {


    @FXML
    private ToggleButton menuToggle;

    @FXML
    private ToggleButton clientToggle;

    @FXML
    private ToggleButton carToggle;

    @FXML
    private ToggleButton masterToggle;

    @FXML
    private ToggleButton orderToggle;

    @FXML
    private ToggleButton reportToggle;

    @FXML
    private ToggleButton queryToggle;

    @FXML
    private ToggleButton serviceToggle;

    public Main main;

    public void setMain(Main main){
        this.main = main;
    }

    public void handleMasterMenu(){
        queryToggle.setSelected(false);
        menuToggle.setSelected(false);
        reportToggle.setSelected(false);
        clientToggle.setSelected(false);
        carToggle.setSelected(false);
        serviceToggle.setSelected(false);
        orderToggle.setSelected(false);
        main.showMasterMenu();
    }
    public void handleClientMenu(){
        queryToggle.setSelected(false);
        menuToggle.setSelected(false);
        reportToggle.setSelected(false);
        masterToggle.setSelected(false);
        carToggle.setSelected(false);
        orderToggle.setSelected(false);
        serviceToggle.setSelected(false);
        main.showClientMenu();

    }
    public void handleMainMenu(){

        queryToggle.setSelected(false);
        masterToggle.setSelected(false);
        reportToggle.setSelected(false);
        clientToggle.setSelected(false);
        carToggle.setSelected(false);
        orderToggle.setSelected(false);
        serviceToggle.setSelected(false);
        main.showMainMenu();
    }
    public void handleOrderTypeMenu(){
        orderToggle.setSelected(false);
        queryToggle.setSelected(false);
        menuToggle.setSelected(false);
        reportToggle.setSelected(false);
        clientToggle.setSelected(false);
        carToggle.setSelected(false);
        masterToggle.setSelected(false);
        main.showOrderTypeMenu();
    }
    public void handleOrderMenu(){
        queryToggle.setSelected(false);
        menuToggle.setSelected(false);
        reportToggle.setSelected(false);
        clientToggle.setSelected(false);
        carToggle.setSelected(false);
        masterToggle.setSelected(false);
        serviceToggle.setSelected(false);
        main.showOrderMenu();
    }
    public void handleCarMenu(){
        queryToggle.setSelected(false);
        menuToggle.setSelected(false);
        reportToggle.setSelected(false);
        clientToggle.setSelected(false);
        masterToggle.setSelected(false);
        orderToggle.setSelected(false);
        serviceToggle.setSelected(false);
        main.showCarMenu();
    }
    public void handleReportMenu(){
        queryToggle.setSelected(false);
        menuToggle.setSelected(false);
        carToggle.setSelected(false);
        clientToggle.setSelected(false);
        masterToggle.setSelected(false);
        orderToggle.setSelected(false);
        serviceToggle.setSelected(false);
        main.showReportMenu();
    }
    public void handleQueryMenu(){
        reportToggle.setSelected(false);
        menuToggle.setSelected(false);
        carToggle.setSelected(false);
        clientToggle.setSelected(false);
        masterToggle.setSelected(false);
        orderToggle.setSelected(false);
        serviceToggle.setSelected(false);
        main.showQueryMenu();
    }
}
