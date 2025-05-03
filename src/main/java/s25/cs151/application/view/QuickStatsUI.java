package s25.cs151.application.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import s25.cs151.application.controller.CSVHelper;

public class QuickStatsUI {

    private VBox quickStatsVBox;
    private IntegerProperty appointmentsScheduled = new SimpleIntegerProperty(0);
    private IntegerProperty coursesThisSemester = new SimpleIntegerProperty(0);
    private IntegerProperty officeHoursScheduled = new SimpleIntegerProperty(0);

    private Text stats1;
    private Text stats2;
    private Text stats3;

    public QuickStatsUI() {
        quickStatsVBox = new VBox(10);
        quickStatsVBox.setAlignment(Pos.CENTER);
        quickStatsVBox.setStyle("-fx-background-color: #D9D9D9; -fx-padding: 20px; -fx-background-radius: 10px;");

        Text quickStatsTitle = new Text("Quick Stats");
        quickStatsTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Arial';");

        stats1 = new Text();
        stats2 = new Text();
        stats3 = new Text();

        stats1.textProperty().bind(appointmentsScheduled.asString("%d Appointments scheduled"));
        stats2.textProperty().bind(coursesThisSemester.asString("%d Courses this semester"));
        stats3.textProperty().bind(officeHoursScheduled.asString("%d Office Hours scheduled"));

        quickStatsVBox.getChildren().addAll(quickStatsTitle, stats1, stats2, stats3);

        updateStats(); // Load initial stats from CSV
    }

    // Return the VBox for embedding in your layout
    public VBox getQuickStatsVBox() {
        return quickStatsVBox;
    }

    // Updates the stats from the CSV files
    public void updateStats() {
        appointmentsScheduled.set(CSVHelper.loadScheduledOfficeHours().size());
        coursesThisSemester.set(CSVHelper.loadCourses().size());
        officeHoursScheduled.set(CSVHelper.loadOfficeHours().size());
    }

    public void forceRefresh() {
        // Rebind the text to the updated values
        stats1.textProperty().bind(appointmentsScheduled.asString("%d Appointments scheduled"));
        stats2.textProperty().bind(coursesThisSemester.asString("%d Courses this semester"));
        stats3.textProperty().bind(officeHoursScheduled.asString("%d Office Hours scheduled"));
    }

}
