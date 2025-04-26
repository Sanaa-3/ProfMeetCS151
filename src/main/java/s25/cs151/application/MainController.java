package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainController {

    @FXML
    private Label welcomeText;

    // VBox in FXML where the QuickStats will be displayed
    @FXML
    private VBox quickStatsContainer;

    // Instance of the QuickStatsUI class
    private static QuickStatsUI quickStatsUI;  // static

    public static QuickStatsUI getQuickStatsUI() {
        return quickStatsUI;
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    // Initialize the Quick Stats UI and bind it to the container in FXML
    @FXML
    public void initialize() {
        quickStatsUI = new QuickStatsUI();
        quickStatsContainer.getChildren().add(quickStatsUI.getQuickStatsVBox());
    }
}
