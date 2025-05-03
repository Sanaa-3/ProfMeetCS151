package s25.cs151.application.controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import s25.cs151.application.view.HomePage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // homePage scene setup
        HomePage home = new HomePage(primaryStage);
        Scene scene = new Scene(home.getView(), 1000, 800);

        // testing define page
//        DefineSemOfficeHoursPage definePage = new DefineSemOfficeHoursPage(primaryStage, home.getView());
//        Scene scene2 = new Scene(definePage.getView(), 500, 500);

        //set Homepage title, and set the scene for it
        primaryStage.setTitle("ProfMeet Home Page");
        primaryStage.setScene(scene);


        primaryStage.show();

    }


    public static void main(String[] args) {
        launch();

    }


}