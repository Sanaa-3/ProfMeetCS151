package s25.cs151.application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.util.*;

public class DefineOfficeHoursPage {
    private final Stage stage;
    private final VBox view;
    private final List<SemesterOfficeHours> officeHoursList;

    public DefineOfficeHoursPage(Stage stage, BorderPane officeHoursList) {
        this.stage = stage;
        this.officeHoursList = (List<s25.cs151.application.SemesterOfficeHours>) officeHoursList;
        this.view = View();

        //Background Color
        view.setStyle("-fx-background-color: #A4C3A2");
        stage.setScene(new Scene(view,700,700));
    }

    private VBox View() {
        VBox vbox = new VBox(20);
        Label title = new Label("Define Semester Office Hours");
        //Formatting for title
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold");
        GridPane form = new GridPane();

        Label semesterLabel = new Label("Semester: ");
        ComboBox<String> semesterCombo = new ComboBox<>();
        semesterCombo.getItems().addAll("Spring", "Summer", "Fall", "Winter");
        form.add(semesterLabel, 0, 0);
        form.add(semesterCombo, 1, 0);

        //Formatting the dropdown box
        semesterCombo.setStyle("-fx-background-color: #FFFFFF");

        Label yearLabel = new Label("Year: ");
        TextField yearField = new TextField();
        form.add(yearLabel, 0 ,1);
        form.add(yearField, 1, 1);

        Label daysLabel = new Label("Days: ");
        VBox daysBox = new VBox(5);
        CheckBox mon = new CheckBox("Monday");
        CheckBox tue = new CheckBox("Tuesday");
        CheckBox wed = new CheckBox("Wednesday");
        CheckBox thu = new CheckBox("Thursday");
        CheckBox fri = new CheckBox("Friday");
        daysBox.getChildren().addAll(mon, tue, wed, thu, fri);
        form.add(daysLabel, 0, 2);
        form.add(daysBox, 1, 2);


        Button saveButton = new Button("Save");
        Button backButton = new Button("Back");
        HBox buttonBox= new HBox(backButton, saveButton);

        //Formatting the buttons
        saveButton.setStyle("-fx-background-color: #E2DFDA; -fx-text-fill: grey; -fx-font-size: 14px; -fx-font-weight: bold;");
        backButton.setStyle("-fx-background-color: #E2DFDA; -fx-text-fill: grey; -fx-font-size: 14px; -fx-font-weight: bold;");


        backButton.setOnAction(e -> {
            //stage.setScene(new Scene(previousPage, 700, 700))
            HomePage homePage = new HomePage(stage);
            stage.getScene().setRoot(homePage.getView());
        });

        saveButton.setOnAction(e -> {
            String semester = semesterCombo.getValue();
            String year = yearField.getText();
            List<String> daysSelected = new ArrayList<>();

            if (semester == null || year.isEmpty()) {
                showAlert("Semester and Year are required");
                return;
            }

            if (mon.isSelected()) daysSelected.add("Monday");
            if (tue.isSelected()) daysSelected.add("Tuesday");
            if (wed.isSelected()) daysSelected.add("Wednesday");
            if (thu.isSelected()) daysSelected.add("Thursday");
            if (fri.isSelected()) daysSelected.add("Friday");
            if (daysSelected.isEmpty()) {
                showAlert("At least one day must be selected");
                return;
            }
            if (!year.matches("\\d{4}")) {
                showAlert("Year must be 4 digit integers");
                return;
            }
            //new office hour that user defines
            SemesterOfficeHours newOfficeHours = new SemesterOfficeHours(semester, Integer.parseInt(year), daysSelected);

            //add this new office hour to the stored list
            officeHoursList.add(newOfficeHours);
            //saves this data permanently to a CSV file
            CSVHelper.saveOfficeHours(newOfficeHours);

            showAlert("Office hours saved successfully!");
            yearField.clear();
            semesterCombo.setValue(null);
            mon.setSelected(false);
            tue.setSelected(false);
            wed.setSelected(false);
            thu.setSelected(false);
            fri.setSelected(false);
        });

        vbox.getChildren().addAll(title, form, buttonBox);
        return vbox;
    }

    public Parent getView() {
        return view;
    }
    private void showAlert(String alert){
        Alert message = new Alert(Alert.AlertType.WARNING);
        message.setContentText(alert);
        message.showAndWait();
    }
}
