package s25.cs151.application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class HomePage{

    private Button defineSemOfficeHours;
    private Button defineSemSlots;
    private Button defineNewCourse;
    private Button settingsBtn;
    private Button scheduleBtn;
    private Button viewAllBtn;
    private Button searchBtn;
    private Stage homeStage;

    public HomePage(Stage homeStage){
        this.homeStage = homeStage;
    }
    public BorderPane getView(){

        //title textcommbr
        Text title = new Text("ProfMeet");
        //HBox titleBox = new HBox(title);

        //might need to change this portion so title text is not in middle
        title.setStyle("-fx-font-size: 40px; -fx-font-weight: bold");

        //sidebar of application
        VBox sidebar = new VBox(20);
        sidebar.setStyle("-fx-background-color: linear-gradient(to bottom, #5D7B6F, #EAE7D6);");
        sidebar.setAlignment(javafx.geometry.Pos.CENTER);

        //create buttons
        defineSemOfficeHours = new Button("Define Semester Office Hours");
        //go to define sem office hours page on click
        defineSemOfficeHours.setOnAction(e -> {
//            DefineOfficeHoursPage definePage = new DefineOfficeHoursPage(homeStage, this.getView());
//            homeStage.setScene(new Scene(definePage.getView(), 700, 700));
            DefineOfficeHoursPage definePage = new DefineOfficeHoursPage(homeStage);
            homeStage.getScene().setRoot(definePage.getView());
        });
        defineSemSlots = new Button("Define Semester Time Slots");
        defineNewCourse = new Button("Define New Course");
        settingsBtn = new Button("Settings");

        //stylize sidebar buttons
        defineSemOfficeHours.setStyle("-fx-background-color: #E2DFDA; -fx-text-fill: grey; -fx-font-size: 14px; -fx-font-weight: bold;");
        defineSemSlots.setStyle("-fx-background-color: #E2DFDA; -fx-text-fill: grey; -fx-font-size: 14px; -fx-font-weight: bold;");
        defineNewCourse.setStyle("-fx-background-color: #E2DFDA; -fx-text-fill: grey; -fx-font-size: 14px; -fx-font-weight: bold;");
        settingsBtn.setStyle("-fx-background-color: #E2DFDA; -fx-text-fill: grey; -fx-font-size: 14px; -fx-font-weight: bold;");


        //add all buttons to sidebar vbox
        sidebar.getChildren().addAll(title, defineNewCourse, defineSemSlots, defineSemOfficeHours, settingsBtn);

        //header and styling
        Text headerMsg = new Text("Welcome, Professor! Here's your office hour overview: ");
        HBox header = new HBox(headerMsg);
        header.setStyle("-fx-background-color: #D7F9FA; -fx-font-size: 15px; -fx-alignment: center;;");

        //main content
        VBox appointmentPg = new VBox(20);
        appointmentPg.setStyle("-fx-background-color: #A4C3A2");

        HBox actionButtons = new HBox(20);

        scheduleBtn = new Button("Schedule a new Office Hour");
        viewAllBtn = new Button("View All Appointments");
        searchBtn = new Button("Search for Appointments");

        //stylize the buttons
        scheduleBtn.setStyle("-fx-background-color: #E2DFDA; -fx-text-fill: grey; -fx-font-size: 14px; -fx-font-weight: bold;");
        viewAllBtn.setStyle("-fx-background-color: #E2DFDA; -fx-text-fill: grey; -fx-font-size: 14px; -fx-font-weight: bold;");
        searchBtn.setStyle("-fx-background-color: #E2DFDA; -fx-text-fill: grey; -fx-font-size: 14px; -fx-font-weight: bold;");

        actionButtons.getChildren().addAll(scheduleBtn, viewAllBtn, searchBtn);

        appointmentPg.getChildren().add(actionButtons);

        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setLeft(sidebar);
        root.setCenter(appointmentPg);

        return root;
    }

}
