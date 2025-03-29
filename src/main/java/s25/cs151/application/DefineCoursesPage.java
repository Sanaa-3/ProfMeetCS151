package s25.cs151.application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class DefineCoursesPage {
    private final Stage stage;
    private final VBox view;
    private final CoursesDAO dao;

    public DefineCoursesPage(Stage stage) {
        this.stage = stage;
        this.dao = new CoursesDAO();
        this.view = createView();
        this.view.setStyle("-fx-background-color: #A4C3A2");
    }

    private VBox createView() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        Label title = new Label("Define Courses");
        title.setStyle("-fx-font-size: 42px; -fx-font-weight: bold");

        GridPane form = new GridPane();
        form.setAlignment(Pos.CENTER);
        form.setStyle("-fx-font-size: 32px;");
        form.setHgap(10);
        form.setVgap(10);

        Label codeLabel = new Label("Course Code:");
        TextField codeField = new TextField();
        codeField.setPromptText("e.g., CS151");
        form.add(codeLabel, 0, 0);
        form.add(codeField, 1, 0);

        Label nameLabel = new Label("Course Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("e.g., Object-Oriented Design");
        form.add(nameLabel, 0, 1);
        form.add(nameField, 1, 1);

        Label sectionLabel = new Label("Section Number:");
        TextField sectionField = new TextField();
        sectionField.setPromptText("e.g., 01");
        form.add(sectionLabel, 0, 2);
        form.add(sectionField, 1, 2);

        Button saveButton = new Button("Save");
        Button backButton = new Button("Back");
        HBox buttonBox = new HBox(60, backButton, saveButton);
        buttonBox.setStyle("-fx-font-size: 32px;");
        buttonBox.setAlignment(Pos.CENTER);

        saveButton.setStyle("-fx-background-color: #E2DFDA; -fx-text-fill: grey; -fx-font-size: 14px; -fx-font-weight: bold;");
        backButton.setStyle("-fx-background-color: #E2DFDA; -fx-text-fill: grey; -fx-font-size: 14px; -fx-font-weight: bold;");

        saveButton.setOnAction(e -> {
            String courseCode = codeField.getText().trim();
            String courseName = nameField.getText().trim();
            String sectionNumber = sectionField.getText().trim();

            if (courseCode.isEmpty() || courseName.isEmpty() || sectionNumber.isEmpty()) {
                showAlert("All fields are required");
                return;
            }

            Course course = new Course(courseCode, courseName, sectionNumber);
            try {
                dao.save(course);
                showAlert("Course saved successfully!");
                codeField.clear();
                nameField.clear();
                sectionField.clear();
            } catch (IllegalStateException ex) {
                showAlert(ex.getMessage());
            }
        });

        backButton.setOnAction(e -> {
            HomePage homePage = new HomePage(stage);
            stage.getScene().setRoot(homePage.getView());
        });

        vbox.getChildren().addAll(title, form, buttonBox);
        return vbox;
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
