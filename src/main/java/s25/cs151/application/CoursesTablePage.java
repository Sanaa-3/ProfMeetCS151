package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

public class CoursesTablePage {
    private final Stage stage;
    private final VBox view;
    private final TableView<Course> table;

    public CoursesTablePage(Stage stage) {
        this.stage = stage;
        this.table = new TableView<>();
        this.view = createView();

        stage.setTitle("ProfMeet Courses Overview Page");
    }

    private VBox createView() {
        VBox vbox = new VBox(20);
        vbox.setStyle("-fx-background-color: #A4C3A2; -fx-padding: 30px;");

        // Title
        Text titleText = new Text("Courses Overview");
        titleText.setStyle("-fx-font-size: 35px; -fx-font-weight: bold; -fx-fill: #2C2C2C; -fx-font-family: 'Arial';");
        HBox titleHBox = new HBox(titleText);
        titleHBox.setAlignment(Pos.CENTER);

        // Table columns
        TableColumn<Course, String> codeCol = new TableColumn<>("Course Code");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        codeCol.setMinWidth(140);

        TableColumn<Course, String> nameCol = new TableColumn<>("Course Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        nameCol.setMinWidth(350);

        TableColumn<Course, String> sectionCol = new TableColumn<>("Section Number");
        sectionCol.setCellValueFactory(new PropertyValueFactory<>("sectionNumber"));
        sectionCol.setMinWidth(100);

        String headerStyle = "-fx-font-size: 16px; -fx-font-weight: bold; -fx-alignment: CENTER; -fx-font-family: 'Arial';";
        codeCol.setStyle(headerStyle);
        nameCol.setStyle(headerStyle);
        sectionCol.setStyle(headerStyle);

        // Add columns to table
        table.getColumns().addAll(codeCol, nameCol, sectionCol);

        // Load and sort data
        table.setItems(getCoursesData());
        table.getSortOrder().add(codeCol); // Sort by Course Code
        codeCol.setSortType(TableColumn.SortType.DESCENDING); // Descending order
        table.sort();

        // Back Button
        Button backButton = new Button("Back");
        setButtonStyle(backButton);
        HBox buttonHBox = new HBox(backButton);
        buttonHBox.setAlignment(Pos.CENTER_RIGHT);

        backButton.setOnAction(e -> {
            HomePage homePage = new HomePage(stage);
            stage.setTitle("ProfMeet Home Page");
            stage.getScene().setRoot(homePage.getView());
        });

        // Add content to VBox
        vbox.getChildren().addAll(titleHBox, table, buttonHBox);

        return vbox;
    }

    // Helper method to style buttons
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

    // Load sorted courses data
    private ObservableList<Course> getCoursesData() {
        List<Course> loadedCourses = CSVHelper.loadCourses();
        ObservableList<Course> courses = FXCollections.observableArrayList(loadedCourses);
        return courses;
    }

    // Update table
    public void updateTable( List<Course> courses) {
        table.getItems().clear();
        table.getItems().addAll(courses);
        table.sort();
    }

    public TableView<Course> getTable() {
        return table;
    }

    public Parent getView() {
        return view;
    }
}
