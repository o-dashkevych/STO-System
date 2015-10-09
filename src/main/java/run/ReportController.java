package run;

import entity.Client;
import entity.Master;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.controlsfx.dialog.Dialogs;

/**
 * Created by Oleg Dashkevych on 15.05.2015.
 */
public class ReportController {
    @FXML
    private Button third;

    @FXML
    private Label successAddLabel;

    @FXML
    private Button first;

    @FXML
    private Button second;

    @FXML
    private ComboBox<String> masterCombo;

    @FXML
    private ComboBox<String> idCombo;

    @FXML
    private ComboBox<String> clientCombo;

    @FXML
    private ComboBox<String> idClientCombo;

    private String name;

    private Main main;

    public void setMain(Main main) {
        this.main = main;
        fillMasters();
        fillClients();
    }

    public void fillMasters(){
        for(Master master:main.getAllMasters()){
            masterCombo.getItems().add(master.getName());
        }
    }
    public void fillIdMasters(){
        idCombo.getItems().clear();
        if(masterCombo.getValue() != null) {
            for (Master master : main.searchMasterByName(masterCombo.getValue())) {
                idCombo.getItems().add(master.getPassportId());
            }
        }else{
            Dialogs.create()
                    .title("Alert")
                    .masthead("Выберите мастера")
                    .showWarning();
        }
    }
 public void fillClients(){
        for(Client client:main.getAllClients()){
            clientCombo.getItems().add(client.getName());
        }
    }
    public void fillIdClients(){
        idClientCombo.getItems().clear();
        if(clientCombo.getValue() != null) {
            for (Client client : main.searchClientByName(clientCombo.getValue())) {
                idClientCombo.getItems().add(client.getPassportId());
            }
        }else{
            Dialogs.create()
                    .title("Alert")
                    .masthead("Выберите клиента")
                    .showWarning();
        }
    }

    public void masterInfoReport(){
        main.masterReport();
    }
    public void masterOrderReport(){
        if(masterCombo.getValue() != null) {
            if(idCombo.getValue()!= null) {
                    main.masterOrdersReport(idCombo.getValue());

            }else{
                Dialogs.create()
                        .title("Alert")
                        .masthead("Выберите паспорт мастера")
                        .showWarning();
            }
        }else{
            Dialogs.create()
                    .title("Alert")
                    .masthead("Выберите мастера")
                    .showWarning();
        }

    }
    public void clientOrderReport(){
        if(clientCombo.getValue() != null) {
            if(idClientCombo.getValue()!= null) {
                    main.clientCarReport(idClientCombo.getValue());

            }else{
                Dialogs.create()
                        .title("Alert")
                        .masthead("Выберите паспорт клиента")
                        .showWarning();
            }
        }else{
            Dialogs.create()
                    .title("Alert")
                    .masthead("Выберите клиента")
                    .showWarning();
        }

    }

    public ReportController() {
    }
}
