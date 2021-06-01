package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * javafx application
 */
public class GameApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        primaryStage.setTitle("Chess Game");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/color.css");
        primaryStage.setScene(scene);
        primaryStage.setOnHidden(e -> controller.onExit());
        primaryStage.show();
    }
}
