package run;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import org.controlsfx.dialog.Dialogs;
import org.hibernate.metamodel.ValidationException;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Oleg Dashkevych on 15.05.2015.
 */
public class QueryController {
    @FXML
    private Button query1But;

    @FXML
    private TextArea queryField;

    @FXML
    private Button execButton;

    @FXML
    private Label successDeleteLabel;

    @FXML
    private TableView<Object> queryTable;

    @FXML
    private Button query2But;

    @FXML
    private Button query3But;

    private Main main;

    private ObservableList<Object> data;

    public void setMain(Main main) {
        this.main = main;
    }

    public QueryController() {
    }

    public void buildTable() throws Exception {

        try {
            List<HashMap> rs = main.executeQuery(queryField.getText());
            new ArbitraryQueryTableManager().configureTableToQueryResult(queryTable, rs);
        } catch (ValidationException e) {
            Dialogs.create()
                    .title("Alert")
                    .masthead("Wrong.")
                    .showWarning();
        }
    }

    public void query1(){
      String query = "select car.car_id, car.brand, car.model from car where car.car_id not in (select order1.car_car_id from order1);";
        try {
            List<HashMap> rs = main.executeQuery(query);
            new ArbitraryQueryTableManager().configureTableToQueryResult(queryTable, rs);
        } catch (ValidationException e) {
            Dialogs.create()
                    .title("Alert")
                    .masthead("Wrong.")
                    .showWarning();
        }
    }
    public void query2(){
       String query = "select master.name, master.passport_id, count(order1.order_id) from master, order1 where order1.master_master_id = master.passport_id group by master.name, master.passport_id;";
        try {
            List<HashMap> rs = main.executeQuery(query);
            new ArbitraryQueryTableManager().configureTableToQueryResult(queryTable, rs);
        } catch (ValidationException e) {
            Dialogs.create()
                    .title("Alert")
                    .masthead("Wrong.")
                    .showWarning();
        }
    }
    public void query3(){
       String query = "select distinct car.brand from car;";
        try {
            List<HashMap> rs = main.executeQuery(query);
            new ArbitraryQueryTableManager().configureTableToQueryResult(queryTable, rs);
        } catch (ValidationException e) {
            Dialogs.create()
                    .title("Alert")
                    .masthead("Wrong.")
                    .showWarning();
        }
    }
}
