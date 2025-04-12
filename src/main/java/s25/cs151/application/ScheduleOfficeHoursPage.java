package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ScheduleOfficeHoursPage {
    private Parent view;
    private final Stage stage;

    public ScheduleOfficeHoursPage(Stage stage) {
        this.stage = stage;
        this.view = createView();

        stage.setTitle("ProfMeet Schedule a new Office Hour Page");
    }

    private VBox createView() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Schedule Office Hours");
        title.setStyle("-fx-font-size: 42px; -fx-font-weight: bold");
        root.setStyle("-fx-background-color: #A4C3A2");

        GridPane form = new GridPane();
        form.setAlignment(Pos.CENTER);
        form.setStyle("-fx-font-size: 22px;");
        form.setHgap(10);
        form.setVgap(10);

        // student information
        Label nameLabel = new Label("Student Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("John Doe");

        // allow user to pick the date
        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker(LocalDate.now());

        // allow user to pick time slot from loaded time slots
        Label timeSlotLabel = new Label("Time Slot:");
        ComboBox<String> timeSlotCombo = new ComboBox<>();
        List<TimeSlots> timeSlots = CSVHelper.loadTimeSlots();
        for (TimeSlots slot : timeSlots) {
            timeSlotCombo.getItems().add(slot.getStartTime() + " – " + slot.getEndTime());
        }
        if (!timeSlotCombo.getItems().isEmpty()) {
            timeSlotCombo.getSelectionModel().selectFirst();
        }

        // allow user to pick course from loaded course slots
        Label courseLabel = new Label("Course:");
        ComboBox<String> courseCombo = new ComboBox<>();
        List<Course> courseList = CSVHelper.loadCourses();
        for (Course c : courseList) {
            String label = c.getCourseCode() + " - " + c.getCourseName() + " (Sec " + c.getSectionNumber() + ")";
            courseCombo.getItems().add(label);
        }
        if (!courseCombo.getItems().isEmpty()) {
            courseCombo.getSelectionModel().selectFirst();
        }

        // allow user to give reason
        Label reasonLabel = new Label("Reason:");
        TextField reasonField = new TextField();

        // allow user to add a comment
        Label commentLabel = new Label("Comment:");
        TextField commentField = new TextField();

        // use grid logic to add sections
        form.add(nameLabel, 0, 0);
        form.add(nameField, 1, 0);

        form.add(dateLabel, 0, 1);
        form.add(datePicker, 1, 1);

        form.add(timeSlotLabel, 0, 2);
        form.add(timeSlotCombo, 1, 2);

        form.add(courseLabel, 0, 3);
        form.add(courseCombo, 1, 3);

        form.add(reasonLabel, 0, 4);
        form.add(reasonField, 1, 4);

        form.add(commentLabel, 0, 5);
        form.add(commentField, 1, 5);

        // save and back buttons
        Button saveButton = new Button("Save");
        Button backButton = new Button("Back");

        HBox buttonBox = new HBox(60, backButton, saveButton);
        buttonBox.setStyle("-fx-font-size: 32px;");
        buttonBox.setAlignment(Pos.CENTER);

        //save button logic when pressed
        saveButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            LocalDate date = datePicker.getValue();
            String timeSlot = timeSlotCombo.getValue();
            String course = courseCombo.getValue();
            String reason = reasonField.getText().trim();
            String comment = commentField.getText().trim();

            //alert shown if certain field not added
            if (name.isEmpty() || timeSlot == null || course == null) {
                showAlert("Missing required fields.");
                return;
            }

            // save the appointment
            System.out.println("Saved appointment for " + name + " on " + date);

            //saveOfficeHour(name, date, timeSlot, course, reason, comment);
            //CSVHelper.saveTimeSlots(saveOfficeHour);  // Save the time slots to CSV
            CSVHelper.saveScheduledOfficeHour(name, date, timeSlot, course, reason, comment);


            showAlert("Office hour scheduled successfully.");
        });

        //back button goes back to home page
        backButton.setOnAction(e -> {
            HomePage homePage = new HomePage(stage);
            stage.setTitle("ProfMeet Home Page");
            stage.getScene().setRoot(homePage.getView());
        });

        root.getChildren().addAll(title, form, buttonBox);
        return root;
    }



    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public Parent getView() {
        return view;
    }
}
