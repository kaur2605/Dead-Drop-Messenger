package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Model();
        Controller controller = new Controller(model);
        IOLocalController ioLocalController = new IOLocalController(model);
        View view = new View(controller, model);

        primaryStage.setTitle("Startup Screen");


        // Create a scene with registration form grid pane as the root node
      //  Scene sceneStartup = new Scene(view.startupPane, 800, 500);

        // Create a scene with registration form grid pane as the root node
        Scene messStartup = new Scene(view.asParent(), 800, 500);


        // Set the scene in primary stage
        primaryStage.setScene(messStartup);

        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}