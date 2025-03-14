package s25.cs151.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;


import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // homePage scene setup
        HomePage home = new HomePage(primaryStage);
        Scene scene = new Scene(home.getView(), 700, 700);

        // testing define page
       // DefineOfficeHoursPage definePage = new DefineOfficeHoursPage(primaryStage);
        //Scene scene2 = new Scene(definePage.getView(), 500, 500);

        //set Homepage title, and set the scene for it
        primaryStage.setTitle("ProfMeet HomePage");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch();

    }


}