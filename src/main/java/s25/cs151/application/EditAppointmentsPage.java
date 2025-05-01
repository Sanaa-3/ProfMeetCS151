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
import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class EditAppointmentsPage {
    private final Stage stage;
    private final TableView<ScheduledOfficeHours> tableView;
    private FilteredList<ScheduledOfficeHours> filteredAppointments;
    private ObservableList<ScheduledOfficeHours> observableAppointments;

    public EditAppointmentsPage(Stage stage) {
        this.stage = stage;
        this.tableView = new TableView<>();
        stage.setTitle("ProfMeet Edit Appointments Page");
    }

    public BorderPane getView() {
        BorderPane root = new BorderPane();

        //title of the page
        Text titleText = new Text("Edit Scheduled Office Hour Appointments");
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
        searchField.setPromptText("Edit by student name...");
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
            if (filteredAppointments != null) {
                String lowerCaseSearch = newVal.toLowerCase();
                filteredAppointments.setPredicate(appointment ->
                        appointment.getStudentName().toLowerCase().contains(lowerCaseSearch));
            }
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
        try {
            List<ScheduledOfficeHours> appointments = CSVHelper.loadScheduledOfficeHours();
            System.out.println("Loaded " + appointments.size() + " appointments");

            //Sort appointments in descending order
            appointments.sort((a1, a2) -> {
                int dateCompare = a2.getDate().compareTo(a1.getDate());
                if (dateCompare != 0) {
                    return dateCompare;
                }
                return a2.getTimeSlot().compareTo(a1.getTimeSlot());
            });


            observableAppointments = FXCollections.observableArrayList(appointments);
            filteredAppointments = new FilteredList<>(observableAppointments, p -> true);
            tableView.setItems(filteredAppointments);
        } catch (Exception e) {
            System.err.println("Error loading appointments: " + e.getMessage());
            e.printStackTrace();
            observableAppointments = FXCollections.observableArrayList();
            filteredAppointments = new FilteredList<>(observableAppointments, p -> true);
            tableView.setItems(filteredAppointments);
            showAlert("Failed to load appointments: " + e.getMessage());
        }

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
        try {
            TableColumn<ScheduledOfficeHours, Number> indexColumn = new TableColumn<>("#");
            indexColumn.setCellValueFactory(cellData ->
                    new ReadOnlyIntegerWrapper(tableView.getItems().indexOf(cellData.getValue()) + 1));
            indexColumn.setMinWidth(40);
            indexColumn.setStyle("-fx-alignment: CENTER;");

            TableColumn<ScheduledOfficeHours, String> nameCol = new TableColumn<>("Student Name");
            nameCol.setCellValueFactory(cell -> cell.getValue().studentNameProperty());
            nameCol.setMinWidth(100);

            TableColumn<ScheduledOfficeHours, String> dateCol = new TableColumn<>("Date");
            dateCol.setCellValueFactory(cell -> cell.getValue().dateProperty());
            dateCol.setMinWidth(100);

            TableColumn<ScheduledOfficeHours, String> timeCol = new TableColumn<>("Time Slot");
            timeCol.setCellValueFactory(cell -> cell.getValue().timeSlotProperty());
            timeCol.setMinWidth(120);

            TableColumn<ScheduledOfficeHours, String> courseCol = new TableColumn<>("Course");
            courseCol.setCellValueFactory(cell -> cell.getValue().courseProperty());
            courseCol.setMinWidth(350);

            TableColumn<ScheduledOfficeHours, String> reasonCol = new TableColumn<>("Reason");
            reasonCol.setCellValueFactory(cell -> cell.getValue().reasonProperty());
            reasonCol.setMinWidth(150);

            TableColumn<ScheduledOfficeHours, String> commentCol = new TableColumn<>("Comment");
            commentCol.setCellValueFactory(cell -> cell.getValue().commentProperty());
            commentCol.setMinWidth(200);

            TableColumn<ScheduledOfficeHours, Void> actionCol = new TableColumn<>("Action");
            actionCol.setMinWidth(100);
            actionCol.setCellFactory(col -> new TableCell<ScheduledOfficeHours, Void>() {
                private final Button editButton = new Button("Edit");

                {
                    setButtonStyle(editButton);
                    editButton.setOnAction(e -> {
                        ScheduledOfficeHours appointment = getTableView().getItems().get(getIndex());
                        showEditDialog(appointment);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : editButton);
                }
            });

            tableView.getColumns().setAll(actionCol, indexColumn, nameCol, dateCol, timeCol, courseCol, reasonCol, commentCol);
            tableView.setStyle("-fx-background-color: #FFFFFF;");
        } catch (Exception e) {
            System.err.println("Error setting up table: " + e.getMessage());
            e.printStackTrace();
        }
    }


    //set up style for all buttons
    private void setButtonStyle(Button button) {
        button.setStyle(
                "-fx-background-color: #E2DFDA; " +
                        "-fx-text-fill: #2C2C2C; " +
                        "-fx-font-size: 12px; " +
                        "-fx-font-family: 'Arial'; " +
                        "-fx-padding: 5px 20px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-border-color: #4A4A4A; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px;");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void showEditDialog(ScheduledOfficeHours appointment) {
        // Load the full list of appointments from CSV
        List<ScheduledOfficeHours> appointmentList = CSVHelper.loadScheduledOfficeHours();

        // Create the dialog and pre-fill it with the current appointment details
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Appointment");
        dialog.setHeaderText("Edit details for " + appointment.getStudentName());

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create form fields pre-filled with current values
        TextField nameField = new TextField(appointment.getStudentName());
        TextField dateField = new TextField(appointment.getDate());
        TextField timeSlotField = new TextField(appointment.getTimeSlot());
        TextField courseField = new TextField(appointment.getCourse());
        TextField reasonField = new TextField(appointment.getReason());
        TextField commentField = new TextField(appointment.getComment());

        // Layout for the form
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Student Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Date:"), 0, 1);
        grid.add(dateField, 1, 1);
        grid.add(new Label("Time Slot:"), 0, 2);
        grid.add(timeSlotField, 1, 2);
        grid.add(new Label("Course:"), 0, 3);
        grid.add(courseField, 1, 3);
        grid.add(new Label("Reason:"), 0, 4);
        grid.add(reasonField, 1, 4);
        grid.add(new Label("Comment:"), 0, 5);
        grid.add(commentField, 1, 5);

        dialog.getDialogPane().setContent(grid);

        // Handle the result of the dialog
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                // Update the appointment with new values from the dialog
                appointment.setStudentName(nameField.getText());
                appointment.setDate(dateField.getText());
                appointment.setTimeSlot(timeSlotField.getText());
                appointment.setCourse(courseField.getText());
                appointment.setReason(reasonField.getText());
                appointment.setComment(commentField.getText());

                // Find the appointment in the list and update it
                for (int i = 0; i < appointmentList.size(); i++) {
                    if (appointmentList.get(i).equals(appointment)) {
                        appointmentList.set(i, appointment);  // Update the appointment in the list
                        break;
                    }
                }

                //Something goes wrong here. I think the above for loop works because it's updated on the page. Just not in csv.
                // Save the updated list of appointments to the CSV file
                CSVHelper.saveAppointments(appointmentList, "src/main/java/s25/cs151/application/DataFiles/scheduled_office_hours.csv");

                // Refresh the table view to reflect the changes
                tableView.refresh();
                showAlert("Appointment updated successfully!");
            }
            return null;
        });

        dialog.showAndWait();
    }

}
