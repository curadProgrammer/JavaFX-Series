package sample;

import javafx.application.Application;
import javafx.application.Platform;
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
        primaryStage.setTitle("Currency Converter (JavaFX App)");
        primaryStage.setScene(new Scene(root, 540, 760));
        primaryStage.setResizable(false);

        // load icon
        String iconPath = getClass().getResource("/resources/logoIcon.png").getPath()
                .replaceAll("%20", " ");
        primaryStage.getIcons().add(new Image(new File(iconPath).getAbsolutePath()));

        // ends the program when window is closed
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
