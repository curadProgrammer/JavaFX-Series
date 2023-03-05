module JavaFxApplication {
    requires javafx.fxml;
    requires javafx.controls;
    requires okhttp3;
    requires com.google.gson;

    opens sample;
}