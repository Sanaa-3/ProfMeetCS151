package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.text.Text;

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
        VBox vbox = new VBox(20); // Spacing between elements
        vbox.setStyle("-fx-background-color: #A4C3A2; -fx-padding: 30px;");

        // Title Text
        Text titleText = new Text("Office Hours Overview");
        titleText.setStyle("-fx-font-size: 35px; -fx-font-weight: bold; -fx-fill: #2C2C2C; -fx-font-family: 'Arial';");

        //Center title
        HBox titleHBox = new HBox(titleText);
        titleHBox.setAlignment(Pos.CENTER);


        // Table columns
        TableColumn<SemesterOfficeHours, String> semesterCol = new TableColumn<>("Semester");
        semesterCol.setCellValueFactory(new PropertyValueFactory<>("semester"));
        semesterCol.setMinWidth(140); // Ensures the column starts with enough width

        TableColumn<SemesterOfficeHours, Integer> yearCol = new TableColumn<>("Year");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        yearCol.setMinWidth(100);

        TableColumn<SemesterOfficeHours, String> daysCol = new TableColumn<>("Days");
        daysCol.setCellValueFactory(data -> {
            List<String> days = data.getValue().getDays();
            return new javafx.beans.property.SimpleStringProperty(String.join(", ", days));
        });
        daysCol.setMinWidth(150);

        String headerStyle = "-fx-font-size: 16px; -fx-font-weight: bold; -fx-alignment: CENTER; -fx-font-family: 'Arial';";
        semesterCol.setStyle(headerStyle);
        yearCol.setStyle(headerStyle);
        daysCol.setStyle(headerStyle);

        // Add table columns
        table.getColumns().addAll(semesterCol, yearCol, daysCol);

        // Load stored data
        table.setItems(getOfficeHoursData());

        // Back Button
        Button backButton = new Button("Back");
        setButtonStyle(backButton);

        //Change to right side
        HBox buttonHBox = new HBox(backButton);
        buttonHBox.setAlignment(Pos.CENTER_RIGHT);

        backButton.setOnAction(e -> {
            HomePage homePage = new HomePage(stage);
            stage.getScene().setRoot(homePage.getView());
        });


        // Add content to VBox
//        vbox.getChildren().addAll(titleHBox, backButton, table);
        //Changed back button to the bottom of page
        vbox.getChildren().addAll(titleHBox, table, buttonHBox);

        return vbox;
    }


    // Helper method to style buttons
    private void setButtonStyle(Button button) {
        button.setStyle(
                "-fx-background-color: #E2DFDA; " +
                        "-fx-text-fill: #2C2C2C; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-family: 'Arial'; " +
                        "-fx-padding: 15px 20px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-border-color: #4A4A4A; " +  // Dark grey border
                        "-fx-border-width: 2px; " +       // Border thickness
                        "-fx-border-radius: 5px; " +      // Rounded corners for softer look
                        "-fx-background-radius: 5px;");   // Match rounded corners
        button.setPrefWidth(120);  // Consistent button width
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
