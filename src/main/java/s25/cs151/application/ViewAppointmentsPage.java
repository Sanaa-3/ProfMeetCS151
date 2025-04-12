package s25.cs151.application;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class ViewAppointmentsPage {
    private final Stage stage;
    private final TableView<ScheduledOfficeHours> tableView;

    public ViewAppointmentsPage(Stage stage) {
        this.stage = stage;
        this.tableView = new TableView<>();

        stage.setTitle("ProfMeet View All Appointments Page");
    }

    public BorderPane getView() {
        BorderPane root = new BorderPane();

        // Title
        Text titleText = new Text("Scheduled Office Hour Appointments");
        titleText.setStyle("-fx-font-size: 35px; -fx-font-weight: bold; -fx-fill: #2C2C2C; -fx-font-family: 'Arial';");

        HBox titleBox = new HBox(titleText);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(20));
        titleBox.setStyle("-fx-background-color: #A4C3A2;");
        root.setTop(titleBox);

        // Table setup
        setupTable();
        List<ScheduledOfficeHours> appointments = CSVHelper.loadScheduledOfficeHours();

        // Sort by date, then by time slot
        appointments.sort(Comparator
                .comparing(ScheduledOfficeHours::getDate)
                .thenComparing(ScheduledOfficeHours::getTimeSlot));

        ObservableList<ScheduledOfficeHours> observableAppointments = FXCollections.observableArrayList(appointments);
        tableView.setItems(observableAppointments);

        VBox centerBox = new VBox(tableView);
        centerBox.setPadding(new Insets(20));
        centerBox.setStyle("-fx-background-color: #A4C3A2;");
        root.setCenter(centerBox);

        // Back button
        Button backButton = new Button("Back");
        setButtonStyle(backButton);
        backButton.setOnAction(e -> {
            HomePage homePage = new HomePage(stage);
            stage.setScene(new Scene(homePage.getView(), 1000, 800));
        });

        HBox bottomBox = new HBox(backButton);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(20));
        bottomBox.setStyle("-fx-background-color: #A4C3A2;");
        root.setBottom(bottomBox);

        return root;
    }

    private void setupTable() {
        TableColumn<ScheduledOfficeHours, Number> indexColumn = new TableColumn<>("#");
        indexColumn.setCellValueFactory(cellData ->
                new ReadOnlyIntegerWrapper(tableView.getItems().indexOf(cellData.getValue()) + 1));
        indexColumn.setMinWidth(40);
        indexColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<ScheduledOfficeHours, String> nameCol = new TableColumn<>("Student Name");
        nameCol.setCellValueFactory(cell -> cell.getValue().studentNameProperty());
        nameCol.setMinWidth(150);

        TableColumn<ScheduledOfficeHours, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cell -> cell.getValue().dateProperty());
        dateCol.setMinWidth(100);

        TableColumn<ScheduledOfficeHours, String> timeCol = new TableColumn<>("Time Slot");
        timeCol.setCellValueFactory(cell -> cell.getValue().timeSlotProperty());
        timeCol.setMinWidth(120);

        TableColumn<ScheduledOfficeHours, String> courseCol = new TableColumn<>("Course");
        courseCol.setCellValueFactory(cell -> cell.getValue().courseProperty());
        courseCol.setMinWidth(200);

        TableColumn<ScheduledOfficeHours, String> reasonCol = new TableColumn<>("Reason");
        reasonCol.setCellValueFactory(cell -> cell.getValue().reasonProperty());
        reasonCol.setMinWidth(150);

        TableColumn<ScheduledOfficeHours, String> commentCol = new TableColumn<>("Comment");
        commentCol.setCellValueFactory(cell -> cell.getValue().commentProperty());
        commentCol.setMinWidth(200);

        tableView.getColumns().setAll(indexColumn, nameCol, dateCol, timeCol, courseCol, reasonCol, commentCol);
        tableView.setStyle("-fx-background-color: #FFFFFF;");
    }

    private void setButtonStyle(Button button) {
        button.setStyle(
                "-fx-background-color: #E2DFDA; " +
                        "-fx-text-fill: #2C2C2C; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-family: 'Arial'; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-border-color: #4A4A4A; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px;");
    }
}

