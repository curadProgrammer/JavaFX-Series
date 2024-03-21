package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

// N: think of the controller class the main gui class that you always do
public class Controller {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void validateLogin(ActionEvent e){
        String enteredUser = usernameField.getText();
        String enteredPass = passwordField.getText();

        if(enteredUser.equals(USERNAME) && enteredPass.equals(PASSWORD)){
            System.out.println("LOGIN SUCCESSFUL!");
        }else{
            System.out.println("LOGIN FAILED...");
        }
    }
}
