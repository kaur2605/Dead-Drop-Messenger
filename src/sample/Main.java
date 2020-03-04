package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Startup Screen");

        // Create the registration form grid pane
        GridPane startupPane = FormWindow.createDefaultFormPane();
        // Add UI controls to the registration form grid pane
        FormWindow.loginSceneElements(startupPane);
        // Create a scene with registration form grid pane as the root node
        Scene sceneStartup = new Scene(startupPane, 800, 500);

        GridPane messPane = FormWindow.createDefaultFormPane();
        // Add UI controls to the registration form grid pane
        FormWindow.messageSceneElements(messPane);
        // Create a scene with registration form grid pane as the root node
        Scene messStartup = new Scene(messPane, 800, 500);


        // Set the scene in primary stage
        primaryStage.setScene(messStartup);

        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}