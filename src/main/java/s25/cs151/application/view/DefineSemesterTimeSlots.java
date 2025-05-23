package s25.cs151.application.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import s25.cs151.application.controller.CSVHelper;
import s25.cs151.application.model.TimeSlots;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DefineSemesterTimeSlots {
    private final Stage primaryStage;
    private final VBox view;
    private final List<TimeSlots> timeSlotsList = new ArrayList<>();
    //comboboxes to track from and too time
    private final ComboBox<LocalTime> fromTime = new ComboBox<>();
    private final ComboBox<LocalTime> toTime = new ComboBox<>();

    public DefineSemesterTimeSlots(Stage primaryStage, List<TimeSlots> timeSlotsList) {
        this.primaryStage = primaryStage;
        this.view = view();

        //Background color
        view.setStyle("-fx-background-color: #A4C3A2; -fx-font-family: 'Arial'; -fx-font-size: 22px;");
       populateTimeComboBoxes();

        // Update the title of the stage when this page is active
        primaryStage.setTitle("ProfMeet Define Semester Time Slots Page");
    }

    private VBox view() {
        VBox view = new VBox(20);
        view.setAlignment(Pos.CENTER);

        //Title
        Label title = new Label("Semester Time Slots");
        //Title formatting
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 35px;");
        view.getChildren().addAll(title);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        //from and to labels
        Label fromLabel = new Label("From Hour: ");
        Label toLabel = new Label("To Hour: ");

        grid.add(fromLabel, 0, 0);
        grid.add(fromTime, 1, 0);
        grid.add(toLabel, 0, 1);
        grid.add(toTime, 1, 1);


        //Cancel Button
        Button cancelButton = new Button("Cancel");
        //Cancel Button action
        cancelButton.setOnAction(event ->{
            HomePage homePage = new HomePage(primaryStage);
            primaryStage.setTitle("ProfMeet Home Page");
            primaryStage.setScene(new Scene(homePage.getView(),1000,800));
        });
        //Save Button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            saveTimeSlot();  // Save the current time slot to the list
            CSVHelper.saveTimeSlots(timeSlotsList);  // Save the time slots to CSV
            showAlert("Time Slot successfully saved.");
        });

        HBox buttons = new HBox(20, saveButton, cancelButton);
        buttons.setAlignment(Pos.CENTER);
        view.getChildren().add(grid);
        view.getChildren().add(buttons);
        return view;


        //To show buttons
    }

    public Parent getView() {
        return view;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void populateTimeComboBoxes() {
        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m += 15) {
                LocalTime time = LocalTime.of(h, m);
                fromTime.getItems().add(time);
                toTime.getItems().add(time);
            }
        }
        fromTime.setValue(LocalTime.of(8, 0));
        toTime.setValue(LocalTime.of(8, 15));
    }

    private void saveTimeSlot() {
        LocalTime from = fromTime.getValue();
        LocalTime to = toTime.getValue();
        if (from != null && to != null && from.isBefore(to)) {
            timeSlotsList.add(new TimeSlots(from, to));
        } else {
            showAlert("Invalid Time Range! 'From' must be before 'To'.");
        }
    }
}
