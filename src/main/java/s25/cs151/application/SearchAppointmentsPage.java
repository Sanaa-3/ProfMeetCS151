package s25.cs151.application;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class SearchAppointmentsPage {
    private final Stage stage;
    private final TableView<ScheduledOfficeHours> tableView;
    private FilteredList<ScheduledOfficeHours> filteredAppointments;

    public SearchAppointmentsPage(Stage stage) {
        this.stage = stage;
        this.tableView = new TableView<>();
        stage.setTitle("ProfMeet Search Appointments");
    }

    public BorderPane getView() {
        BorderPane root = new BorderPane();

        //title of the page
        Text titleText = new Text("Search Scheduled Office Hour Appointments");
        titleText.setStyle("-fx-font-size: 35px; -fx-font-weight: bold; -fx-fill: #2C2C2C; -fx-font-family: 'Arial';");
        HBox titleBox = new HBox(titleText);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(20));
        titleBox.setStyle("-fx-background-color: #A4C3A2;");

        //search portion
        Label searchIcon = new Label("\uD83D\uDD0D"); // added a magnifying glass icon
        searchIcon.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-text-fill: #808080;" +
                        "-fx-padding: 0 5px 0 0;" // right padding for spacing
        );

        //actual search field
        TextField searchField = new TextField();
        searchField.setPromptText("Search by student name...");
        searchField.setPrefWidth(300);
        searchField.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-padding: 8px 12px;" +
                        "-fx-background-color: white;" +
                        "-fx-border-color: lightgray;" +
                        "-fx-border-radius: 20px;" +
                        "-fx-background-radius: 20px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 4, 0.1, 0, 1);" +
                        "-fx-text-fill: black;"
        );


        //event listener for searching table view
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            String lowerCaseSearch = newVal.toLowerCase();
            filteredAppointments.setPredicate(appointment ->
                    appointment.getStudentName().toLowerCase().contains(lowerCaseSearch));
        });

        //decoration for the search field
        HBox searchContainer = new HBox(searchIcon, searchField);
        searchContainer.setAlignment(Pos.CENTER_LEFT);
        searchContainer.setSpacing(10);
        searchContainer.setPadding(Insets.EMPTY);
        searchContainer.setStyle("-fx-background-color: transparent;");

        VBox searchBox = new VBox(searchContainer);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setSpacing(0);
        searchBox.setPadding(new Insets(10, 0, 10, 40)); // Top, Right, Bottom, Left
        searchBox.setStyle("-fx-background-color: transparent;");

        //top of page including title and search
        VBox topBox = new VBox(titleBox, searchBox);
        topBox.setSpacing(10);
        topBox.setAlignment(Pos.TOP_LEFT);
        topBox.setStyle("-fx-background-color: #A4C3A2;");
        root.setTop(topBox);

        //load the csv for the appointments
        List<ScheduledOfficeHours> appointments = CSVHelper.loadScheduledOfficeHours();
        ObservableList<ScheduledOfficeHours> observableAppointments = FXCollections.observableArrayList(appointments);
        filteredAppointments = new FilteredList<>(observableAppointments, p -> true);
        tableView.setItems(filteredAppointments);

        //table setup
        setupTable();

        VBox centerBox = new VBox(tableView);
        centerBox.setPadding(new Insets(20));
        centerBox.setStyle("-fx-background-color: #A4C3A2;");
        root.setCenter(centerBox);

        //back button and styling
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

    //set up style for all buttons
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
