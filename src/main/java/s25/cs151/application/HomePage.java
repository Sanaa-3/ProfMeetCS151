package s25.cs151.application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomePage{

    private Button defineSemOfficeHours;
    private Button defineSemSlots;
    private Button defineNewCourse;
    private Button settingsBtn;
    private Button scheduleBtn;
    private Button viewAllOfficeHoursBtn;
    private Button viewAllBtn;
    private Button searchBtn;

    private Button viewAllTimeSlotsBtn;
    private Stage homeStage;
    private List<SemesterOfficeHours> officeHoursList;
    private final OfficeHoursTablePage officeHoursTablePage;
    private List<TimeSlots> timeSlotsList;

    public HomePage(Stage homeStage){
        this.homeStage = homeStage;
        this.officeHoursList = new ArrayList<>();
        this.officeHoursTablePage = new OfficeHoursTablePage(homeStage);
    }
    public BorderPane getView(){
        BorderPane root = new BorderPane(); // Ensure this is used for the layout

        //title text
        Text titleText = new Text("ProfMeet");
        titleText.setStyle("-fx-font-size: 42px; -fx-font-weight: bold; -fx-font-family: 'Palatino'; -fx-fill: #2C2C2C;");

        //sidebar of application
        VBox sidebar = new VBox(20);
        sidebar.setStyle("-fx-background-color: linear-gradient(to bottom, #5D7B6F, #EAE7D6);");
        sidebar.setPadding(new Insets(20));
        sidebar.setAlignment(Pos.TOP_CENTER);

        //create buttons
        defineSemOfficeHours = new Button("Define Semester Office Hours");
        defineSemSlots = new Button("Define Semester Time Slots");
        defineNewCourse = new Button("Define New Course");
        viewAllOfficeHoursBtn = new Button("View Office Hours");
        settingsBtn = new Button("Settings");
        viewAllTimeSlotsBtn = new Button("View All Time Slots");


        //Stylize sidebar buttons using the helper function
        setSideButtonStyle(defineSemOfficeHours);
        setSideButtonStyle(defineSemSlots);
        setSideButtonStyle(defineNewCourse);
        setSideButtonStyle(settingsBtn);
        setSideButtonStyle(viewAllOfficeHoursBtn);
        setSideButtonStyle(viewAllTimeSlotsBtn);


        //go to define sem office hours page on click
        defineSemOfficeHours.setOnAction(e -> {
            DefineOfficeHoursPage definePage = new DefineOfficeHoursPage(homeStage, officeHoursList);
            //System.out.println("Define Semester Office Hours button clicked.");
            //homeStage.setScene(new Scene(definePage.getView(), 700, 700));
            homeStage.setScene(new Scene(definePage.getView(),1000,800));
        });


        defineNewCourse.setOnAction(e -> {
            DefineCoursesPage defineCoursesPage = new DefineCoursesPage(homeStage);
            homeStage.setScene(new Scene(defineCoursesPage.getView(),1000,800));
        });


        //view all office hours, loads it upon clicking of button and sorts data
        viewAllOfficeHoursBtn.setOnAction(e -> {
            OfficeHoursTablePage tablePage = new OfficeHoursTablePage(homeStage);
            tablePage.updateTable(CSVHelper.loadOfficeHours());
            homeStage.getScene().setRoot(tablePage.getView());
        });

        //To navigate to define semester time slots page
        defineSemSlots.setOnAction(e -> {
            DefineSemesterTimeSlots defineSemSlots = new DefineSemesterTimeSlots(homeStage,timeSlotsList);
            homeStage.getScene().setRoot(defineSemSlots.getView());
        });

        //view all time slots, loads upon clicking of button and sorts data
        viewAllTimeSlotsBtn.setOnAction(e -> {
            List<TimeSlots> timeSlotsList = CSVHelper.loadTimeSlots();
            TimeSlotsTablePage timeSlotsTablePage = new TimeSlotsTablePage(homeStage, timeSlotsList);
            homeStage.getScene().setRoot(timeSlotsTablePage.getView());
        });




        //add all buttons to sidebar vbox
        sidebar.getChildren().addAll(titleText, defineNewCourse, defineSemSlots, defineSemOfficeHours, viewAllOfficeHoursBtn, viewAllTimeSlotsBtn, settingsBtn);

        //header and styling
        Text headerMsg = new Text("Welcome, Professor! Here's your office hour overview: ");
        HBox header = new HBox(headerMsg);
        header.setStyle("-fx-background-color: #D7F9FA; -fx-font-size: 25px; -fx-alignment: center;;");
        header.setPadding(new Insets(15, 40, 20, 40));

        //main content
        VBox appointmentPg = new VBox(20);
        appointmentPg.setStyle("-fx-background-color: #A4C3A2");

        HBox actionButtons = new HBox(20);
        actionButtons.setPadding(new Insets(30, 40, 20, 40));

        scheduleBtn = new Button("Schedule a new \n   Office Hour");
        viewAllBtn = new Button("   View All \nAppointments");
        searchBtn = new Button("   Search for \nAppointments");

        //stylize the buttons
        setMiddleButtonStyle(scheduleBtn);
        setMiddleButtonStyle(viewAllBtn);
        setMiddleButtonStyle(searchBtn);

        // Add buttons to action panel
        actionButtons.getChildren().addAll(scheduleBtn, viewAllBtn, searchBtn);

        // Adding action buttons to the appointment page
        appointmentPg.getChildren().add(actionButtons);

        root.setTop(header);
        root.setLeft(sidebar);
        root.setCenter(appointmentPg);

        return root;
    }

    // Helper function to style the buttons
    private void setMiddleButtonStyle(Button button) {
        button.setStyle(
                "-fx-background-color: #E2DFDA; " +
                        "-fx-text-fill: #2C2C2C; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-family: 'Arial'; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-border-radius: 10px;");
        button.setPrefWidth(250);  // Consistent button width
    }

    // Helper function to stylize a button
    private void setSideButtonStyle(Button button) {
        button.setStyle(
                "-fx-background-color: #E2DFDA; " +
                        "-fx-text-fill: #2C2C2C; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-family: 'Arial'; " +
                        "-fx-padding: 15px 20px; " +
                        "-fx-font-weight: bold;");
        button.setPrefWidth(270);  // Consistent button width
    }

}
