package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Calculator (JavaFX App)");
        primaryStage.setScene(new Scene(root, 435, 560));

        // load icon
        String iconPath = getClass().getResource("/resources/icon.png").getPath()
                .replaceAll("%20", " ");
        primaryStage.getIcons().add(new Image(new File(iconPath).getAbsolutePath()));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
