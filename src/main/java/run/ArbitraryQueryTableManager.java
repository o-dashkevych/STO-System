package run;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Oleg Dashkevych on 15.05.2015.
 */
public class ArbitraryQueryTableManager {

    public void configureTableToQueryResult(TableView tableView, List<HashMap> rs) {
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        tableView.getColumns().clear();

        if (rs.size() > 0) {

            Iterator<Map.Entry<String, String>> it = rs.get(0).<String, String>entrySet().iterator();
            int i = 0;
            while (it.hasNext()) {
//We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(it.next().getKey());
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableView.getColumns().addAll(col);
                i++;
            }

            for (HashMap<String, String> map : rs) {
//Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                it = map.<String, String>entrySet().iterator();
                while (it.hasNext()) {
                    Object val = it.next().getValue();
                    row.add(val.toString());
                }
                data.add(row);
            }
        }

        tableView.setItems(data);
    }
}