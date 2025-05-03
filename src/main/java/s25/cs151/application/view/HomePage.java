package s25.cs151.application.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import s25.cs151.application.controller.CSVHelper;
import s25.cs151.application.model.SemesterOfficeHours;
import s25.cs151.application.model.TimeSlots;

public class HomePage{

    private Button defineSemOfficeHours;
    private Button defineSemSlots;
    private Button defineNewCourse;
    private Button editBtn;
    private Button scheduleBtn;
    private Button viewAllOfficeHoursBtn;
    private Button viewAllAppointmentsBtn;
    private Button searchBtn;
    private Button viewAllCoursesBtn;
    private Button viewAllTimeSlotsBtn;
    private Stage homeStage;
    private List<SemesterOfficeHours> officeHoursList;
    private final SemOfficeHoursTablePage officeHoursTablePage;
    private List<TimeSlots> timeSlotsList;

    public HomePage(Stage homeStage){
        this.homeStage = homeStage;
        this.officeHoursList = new ArrayList<>();
        this.officeHoursTablePage = new SemOfficeHoursTablePage(homeStage);
    }
    public BorderPane getView() {
        BorderPane root = new BorderPane();

        // Title text
        Text titleText = new Text("ProfMeet");
        titleText.setStyle("-fx-font-size: 46px; -fx-font-weight: bold; -fx-font-family: 'Palatino'; -fx-fill: #2C2C2C;");

        // Sidebar (left side) - Define buttons
        VBox sidebar = new VBox(25);
        sidebar.setStyle("-fx-background-color: linear-gradient(to bottom, #5D7B6F, #EAE7D6);");
        sidebar.setPadding(new Insets(30));
        sidebar.setAlignment(Pos.TOP_CENTER);

        defineSemOfficeHours = new Button("Define Semester Office Hours");
        defineSemSlots = new Button("Define Semester Time Slots");
        defineNewCourse = new Button("Define New Course");
        editBtn = new Button("Edit Appointments");

        // Style and add buttons to sidebar
        setSideButtonStyle(defineSemOfficeHours);
        setSideButtonStyle(defineSemSlots);
        setSideButtonStyle(defineNewCourse);
        setSideButtonStyle(editBtn);
        sidebar.getChildren().addAll(titleText, defineNewCourse, defineSemSlots, defineSemOfficeHours, editBtn);

        // Header
        Text headerMsg = new Text("Welcome, Professor! Here's your office hour overview:");
        HBox header = new HBox(headerMsg);
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: #E6EAE4; -fx-font-size: 25px;");
        header.setPadding(new Insets(15, 40, 20, 40));

        // Center area
        VBox centerArea = new VBox(30);
        centerArea.setPadding(new Insets(30));
        centerArea.setAlignment(Pos.TOP_CENTER);
        centerArea.setStyle("-fx-background-color: #A4C3A2;");

        // View/Search/Schedule Buttons
        viewAllCoursesBtn = new Button("View All Courses");
        viewAllTimeSlotsBtn = new Button("View All Time Slots");
        viewAllOfficeHoursBtn = new Button("View Semester Office Hours");
        scheduleBtn = new Button("Schedule New Office Hour");
        viewAllAppointmentsBtn = new Button("View All Appointments");
        searchBtn = new Button("Search Appointments");

        setMiddleButtonStyle(viewAllCoursesBtn);
        setMiddleButtonStyle(viewAllTimeSlotsBtn);
        setMiddleButtonStyle(viewAllOfficeHoursBtn);
        setMiddleButtonStyle(scheduleBtn);
        setMiddleButtonStyle(viewAllAppointmentsBtn);
        setMiddleButtonStyle(searchBtn);

        VBox buttonsSection = new VBox(20);
        buttonsSection.setAlignment(Pos.CENTER);

// Row 1
        HBox row1 = new HBox(30);
        row1.setAlignment(Pos.CENTER);
        row1.getChildren().addAll(viewAllCoursesBtn, viewAllTimeSlotsBtn);

// Row 2
        HBox row2 = new HBox(30);
        row2.setAlignment(Pos.CENTER);
        row2.getChildren().addAll(viewAllOfficeHoursBtn, scheduleBtn);

// Row 3
        HBox row3 = new HBox(30);
        row3.setAlignment(Pos.CENTER);
        row3.getChildren().addAll(viewAllAppointmentsBtn, searchBtn);

        // Add all rows to VBox
        buttonsSection.getChildren().addAll(row1, row2, row3);

        // Quick Stats Panel
        VBox quickStats = new VBox(10);
        quickStats.setAlignment(Pos.CENTER);
        quickStats.setStyle("-fx-background-color: #E2DFDA; -fx-padding: 20px; -fx-background-radius: 10px;");

        Text quickStatsTitle = new Text("Quick Stats");
        quickStatsTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-font-family: 'Arial';");

        // Use IntegerProperty for dynamic updating
        IntegerProperty appointmentsScheduled = new SimpleIntegerProperty(CSVHelper.getScheduledAppointmentsCount());  // Fetch actual data from CSVHelper
        IntegerProperty coursesThisSemester = new SimpleIntegerProperty(CSVHelper.getCoursesCount());     // Fetch actual data from CSVHelper
        IntegerProperty officeHoursScheduled = new SimpleIntegerProperty(CSVHelper.getOfficeHoursCount());    // Fetch actual data from CSVHelper
        IntegerProperty timeSlotsAvailable = new SimpleIntegerProperty(CSVHelper.loadTimeSlots().size()); // Fetch actual time slots count

        // Create Text objects and bind them to IntegerProperties
        Text stats1 = new Text();
        Text stats2 = new Text();
        Text stats3 = new Text();
        Text stats4 = new Text();  // For Time Slots

        // Set the font size using setStyle() for each Text object
        stats1.setStyle("-fx-font-size: 18px;");  // Set font size to 18px for appointments
        stats2.setStyle("-fx-font-size: 18px;");  // Set font size to 18px for courses
        stats3.setStyle("-fx-font-size: 18px;");  // Set font size to 18px for office hours
        stats4.setStyle("-fx-font-size: 18px;");  // Set font size to 18px for time slots

        stats1.textProperty().bind(appointmentsScheduled.asString("%d Appointments scheduled"));
        stats2.textProperty().bind(coursesThisSemester.asString("%d Courses this semester"));
        stats3.textProperty().bind(officeHoursScheduled.asString("%d Office Hours scheduled"));
        stats4.textProperty().bind(timeSlotsAvailable.asString("%d Time Slots available"));  // Bind time slots count

        // Add to VBox
        quickStats.getChildren().addAll(quickStatsTitle, stats1, stats2, stats3, stats4);

        // Add everything to center area
        centerArea.getChildren().addAll(buttonsSection, quickStats);


        // Set layout positions
        root.setTop(header);
        root.setLeft(sidebar);
        root.setCenter(centerArea);

        // Button Actions
        defineSemOfficeHours.setOnAction(e -> {
            DefineSemOfficeHoursPage definePage = new DefineSemOfficeHoursPage(homeStage, officeHoursList);
            homeStage.setScene(new Scene(definePage.getView(), 1000, 800));
        });

        defineNewCourse.setOnAction(e -> {
            DefineCoursesPage defineCoursesPage = new DefineCoursesPage(homeStage);
            homeStage.setScene(new Scene(defineCoursesPage.getView(), 1000, 800));
        });

        viewAllCoursesBtn.setOnAction(e -> {
            CoursesTablePage coursesPage = new CoursesTablePage(homeStage);
            coursesPage.updateTable(CSVHelper.loadCourses());
            homeStage.getScene().setRoot(coursesPage.getView());
        });

        viewAllOfficeHoursBtn.setOnAction(e -> {
            SemOfficeHoursTablePage tablePage = new SemOfficeHoursTablePage(homeStage);
            tablePage.updateTable(CSVHelper.loadOfficeHours());
            homeStage.getScene().setRoot(tablePage.getView());
        });

        defineSemSlots.setOnAction(e -> {
            DefineSemesterTimeSlots defineSemSlots = new DefineSemesterTimeSlots(homeStage, CSVHelper.loadTimeSlots());
            homeStage.getScene().setRoot(defineSemSlots.getView());
        });

        viewAllTimeSlotsBtn.setOnAction(e -> {
            List<TimeSlots> timeSlotsList = CSVHelper.loadTimeSlots();
            TimeSlotsTablePage timeSlotsTablePage = new TimeSlotsTablePage(homeStage, timeSlotsList);
            homeStage.getScene().setRoot(timeSlotsTablePage.getView());
        });

        scheduleBtn.setOnAction(e -> {
            ScheduleOfficeHoursPage scheduleOfficeHoursPage = new ScheduleOfficeHoursPage(homeStage);
            homeStage.setScene(new Scene(scheduleOfficeHoursPage.getView(), 1000, 800));
        });

        viewAllAppointmentsBtn.setOnAction(e -> {
            ViewAppointmentsPage viewAppointmentsPage = new ViewAppointmentsPage(homeStage);
            homeStage.setScene(new Scene(viewAppointmentsPage.getView(), 1000, 800));
        });

        searchBtn.setOnAction(e -> {
            SearchAppointmentsPage searchPage = new SearchAppointmentsPage(homeStage);
            homeStage.setScene(new Scene(searchPage.getView(), 1000, 800));
        });

        editBtn.setOnAction(e -> {
            EditAppointmentsPage editAppointmentsPage = new EditAppointmentsPage(homeStage);
            homeStage.setScene(new Scene(editAppointmentsPage.getView(), 1000, 800));
        });

        return root;
    }


    // Helper function to style the buttons (middle buttons)
    private void setMiddleButtonStyle(Button button) {
        String defaultStyle = "-fx-background-color: #E2DFDA; " +
                "-fx-text-fill: #2C2C2C; " +
                "-fx-font-size: 16px; " +
                "-fx-font-weight: bold; " +
                "-fx-font-family: 'Arial'; " +
                "-fx-padding: 15px 20px; " +
                "-fx-background-radius: 5px; " +
                "-fx-border-radius: 10px;";
        button.setStyle(defaultStyle);
        button.setPrefWidth(300);  // Consistent button width

        // Hover effect (Middle buttons)
        button.setOnMouseEntered(e -> {
            button.setStyle(
                    "-fx-background-color: #DADDE2;" +  // Lighter background color
                            "-fx-text-fill: #212C2C; " +  // Darker text color
                            "-fx-font-size: 16px; " +
                            "-fx-font-family: 'Arial'; " +
                            "-fx-padding: 15px 20px; " +
                            "-fx-font-weight: bold;" +
                            "-fx-effect: dropshadow(gaussian, #000000, 10, 0, 0, 5); " + // Shadow effect
                            "-fx-scale-x: 1.05; " + // Slightly grow button on hover (X scale)
                            "-fx-scale-y: 1.05;" // Slightly grow button on hover (Y scale)
            );
        });

        button.setOnMouseExited(e -> {
            button.setStyle(defaultStyle);
        });
    }


    // Helper function to stylize a button
    private void setSideButtonStyle(Button button) {
        String defaultStyle = "-fx-background-color: #E2DFDA; " +
                "-fx-text-fill: #2C2C2C; " +
                "-fx-font-size: 16px; " +
                "-fx-font-family: 'Arial'; " +
                "-fx-padding: 15px 20px; " +
                "-fx-font-weight: bold;";
        button.setStyle(defaultStyle);
        button.setPrefWidth(270);  // Consistent button width

        //Hover over
        button.setOnMouseEntered(e -> {
            button.setStyle(
                    "-fx-background-color: #DADDE2;" +
                            "-fx-text-fill: #212C2C; " +
                            "-fx-font-size: 16px; " +
                            "-fx-font-family: 'Arial'; " +
                            "-fx-padding: 15px 20px; " +
                            "-fx-font-weight: bold;" +
                            "-fx-effect: dropshadow(gaussian, #000000, 10, 0, 0, 5);" +
                            "-fx-scale-x: 1.05; " + // Slightly grow button on hover (X scale)
                            "-fx-scale-y: 1.05;" // Slightly grow button on hover (Y scale)
                    );
        });

        button.setOnMouseExited(e -> {
            button.setStyle(defaultStyle);
        });
    }

}
