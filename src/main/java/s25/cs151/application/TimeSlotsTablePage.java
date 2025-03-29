package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import s25.cs151.application.TimeSlots;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class TimeSlotsTablePage {
    private final Stage primaryStage;
    private final TableView<TimeSlots> tableView;

    //constructor to set up the table and update it with the timeslots
    public TimeSlotsTablePage(Stage primaryStage, List<TimeSlots> timeSlotsList) {
        this.primaryStage = primaryStage;
        this.tableView = new TableView<>();
        setupTable();
        updateTable(timeSlotsList);
    }

    //set up the table and decorate it
    private void setupTable() {
        Text titleText = new Text("Time Slots Overview");
        titleText.setStyle("-fx-font-size: 35px; -fx-font-weight: bold; -fx-fill: #2C2C2C; -fx-font-family: 'Arial';");

        //set up the columns of the table, from and to hour
        TableColumn<TimeSlots, String> fromHourCol = new TableColumn<>("From Hour");
        fromHourCol.setCellValueFactory(new PropertyValueFactory<>("startTimeString"));
        fromHourCol.setMinWidth(140);

        TableColumn<TimeSlots, String> toHourCol = new TableColumn<>("To Hour");
        toHourCol.setCellValueFactory(new PropertyValueFactory<>("endTimeString"));
        toHourCol.setMinWidth(140);

        String headerStyle = "-fx-font-size: 16px; -fx-font-weight: bold; -fx-alignment: CENTER; -fx-font-family: 'Arial';";
        fromHourCol.setStyle(headerStyle);
        toHourCol.setStyle(headerStyle);

        tableView.getColumns().addAll(fromHourCol, toHourCol);
        tableView.setStyle("-fx-background-color: #F4F4F4; -fx-border-color: #A4C3A2; -fx-border-width: 1px;");

    }

    //update the table everytime a new slot is added
    public void updateTable(List<TimeSlots> timeSlotsList) {
        ObservableList<TimeSlots> observableList = FXCollections.observableArrayList(timeSlotsList);
        tableView.setItems(observableList);
    }

    private void setButtonStyle(Button button) {
        button.setStyle(
                "-fx-background-color: #E2DFDA; " +
                        "-fx-text-fill: #2C2C2C; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-family: 'Arial'; " +
                        "-fx-padding: 15px 20px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-border-color: #4A4A4A; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px;");
        button.setPrefWidth(120);
    }
    //decoration of how the table view page looks
    public BorderPane getView() {
        BorderPane root = new BorderPane();

        Text titleText = new Text("Time Slots Overview");
        titleText.setStyle("-fx-font-size: 35px; -fx-font-weight: bold; -fx-fill: #2C2C2C; -fx-font-family: 'Arial';");

        HBox titleHBox = new HBox(titleText);
        titleHBox.setStyle("-fx-background-color: #A4C3A2; -fx-padding: 20px;");
        titleHBox.setAlignment(Pos.CENTER);
        root.setTop(titleHBox);

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #A4C3A2;");


        layout.getChildren().add(tableView);


        //adding the back button
        Button backButton = new Button("Back");
        setButtonStyle(backButton);

        BorderPane bottomLayout = new BorderPane();
        bottomLayout.setRight(backButton);
        layout.getChildren().add(bottomLayout);

        //make sure back button goes back to homepage
        backButton.setOnAction(e -> {
            HomePage homePage = new HomePage(primaryStage);
            primaryStage.setScene(new Scene(homePage.getView(), 1000, 800));
        });

        root.setCenter(layout);

        return root;
    }


    public TableView<TimeSlots> getTableView() {
        return tableView;
    }
}

