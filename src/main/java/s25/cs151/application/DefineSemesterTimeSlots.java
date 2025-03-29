package s25.cs151.application;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class DefineSemesterTimeSlots {
    private final Stage primaryStage;
    private final VBox view;
    private final List<TimeSlots> timeSlotsList = new ArrayList<>();

    public DefineSemesterTimeSlots(Stage primaryStage, List<TimeSlots> timeSlotsList) {
        this.primaryStage = primaryStage;
        this.view = view();

        //Background color
        view.setStyle("-fx-background-color: #A4C3A2");


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

        //Cancel Button
        Button cancelButton = new Button("Cancel");
        //Cancel Button action
        cancelButton.setOnAction(event ->{
            HomePage homePage = new HomePage(primaryStage);
            primaryStage.setScene(new Scene(homePage.getView(),1000,800));
        });
        //Save Button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event ->{
           //Save button action
        });

        //To show buttons
        HBox buttons = new HBox(20,saveButton,cancelButton);
        buttons.setAlignment(Pos.CENTER);
        view.getChildren().add(grid);
        view.getChildren().add(buttons);
        return view;
    }

    public Parent getView() {
        return view;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
