package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class OfficeHoursTablePage {
    private final Stage stage;
    private final VBox view;
    private final TableView<SemesterOfficeHours> table;

    public OfficeHoursTablePage(Stage stage) {
        this.stage = stage;
        this.table = new TableView<>();
        this.view = createView();

    }

    private VBox createView() {
        VBox vbox = new VBox(20);

        // Table columns
        TableColumn<SemesterOfficeHours, String> semesterCol = new TableColumn<>("Semester");
        semesterCol.setCellValueFactory(new PropertyValueFactory<>("semester"));

        TableColumn<SemesterOfficeHours, Integer> yearCol = new TableColumn<>("Year");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<SemesterOfficeHours, String> daysCol = new TableColumn<>("Days");
        daysCol.setCellValueFactory(data -> {
            List<String> days = data.getValue().getDays();
            return new javafx.beans.property.SimpleStringProperty(String.join(", ", days));
        });

        table.getColumns().addAll(semesterCol, yearCol, daysCol);

        // Load stored data
        table.setItems(getOfficeHoursData());

        vbox.getChildren().add(table);
        return vbox;
    }


    //table size updated and view updated when new data added
    private ObservableList<SemesterOfficeHours> getOfficeHoursData() {
        List<SemesterOfficeHours> loadedOfficeHours = CSVHelper.loadOfficeHours(); // Load from CSV
        return FXCollections.observableArrayList(loadedOfficeHours);
    }

    public void updateTable(List<SemesterOfficeHours> newData) {
        table.getItems().clear();
        table.getItems().addAll(newData);
    }

    public TableView<SemesterOfficeHours> getTable() {
        return table;
    }

    public Parent getView() {
        return view;
    }
}
