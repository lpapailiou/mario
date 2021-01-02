package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage stage) throws Exception {
        try {
            ClassLoader classLoader = Main.class.getClassLoader();
            FXMLLoader loader = new FXMLLoader(classLoader.getResource("Application.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1600, 800);
            //scene.getStylesheets().add(classLoader.getResource("applicationCss.css").toExternalForm());
            scene.setFill(Color.BLUE);
            stage.setScene(scene);
            stage.setMinWidth(1616);
            stage.setMaxWidth(1616);
            stage.setMinHeight(839);
            stage.setMaxHeight(839);
            stage.setTitle("MARIO");
            //primaryStage.getIcons().add(new Image("img.png"));
            stage.show();
            ((ApplicationController) loader.getController()).addKeyListener(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
