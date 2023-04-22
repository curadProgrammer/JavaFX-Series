package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Random;

public class Controller {
    private static final int GUESS_RANGE = 101;
    private static final int NUM_CHANCES = 10;

    private Random random;

    // numberGuess stores the randomly generated number that the user has to guess
    // chanceCounter counts the number of remaining chances the user has before the game is over
    private int numberGuess, chanceCounter;

    // xOffset and yOffset are used to calculate the frame's position relative to mouse movement (used for titleBar)
    private double xOffset, yOffset;

    @FXML
    private Button minBtn;

    @FXML
    private Button closeBtn;

    @FXML
    private GridPane titleBar;

    @FXML
    private GridPane gameDisplay;

    @FXML
    private GridPane mainMenu;

    @FXML
    private Label chanceCounterLabel;

    @FXML
    private TextField userInputField;

    @FXML
    private ImageView backgroundImg;

    @FXML
    private Button tryAgainBtn;

    @FXML
    private Label hintLabel;

    public void initialize(){
        random = new Random();

        // generate a random number between 0 and GUESS_RANGE - 1
        numberGuess = random.nextInt(GUESS_RANGE);

        // set the initial number of chances
        chanceCounter = NUM_CHANCES;

        // update the count label to show the initial # of chances
        chanceCounterLabel.setText("You have " + chanceCounter + " guesses!");
    }

    public void handleSubmitAction(){
        int userGuess;

        try{
            // stores number from input field
            userGuess = Integer.parseInt(userInputField.getText().replace(" ", ""));

            if(userGuess == numberGuess){
                // display success screen
                backgroundImg.setImage(new Image(getClass().getResourceAsStream("/resources/success.png")));

                // hide game display
                gameDisplay.setVisible(false);

                // show try again button
                tryAgainBtn.setVisible(true);
            }else{
                // decrement chance counter and update label
                chanceCounter--;
                chanceCounterLabel.setText("You have " + chanceCounter + " guess" + (chanceCounter == 1 ? "" : "es") + " left");

                if(chanceCounter <= 0){
                    // display game over screen
                    backgroundImg.setImage(new Image(getClass().getResourceAsStream("/resources/game_over.png")));

                    // hide game display
                    gameDisplay.setVisible(false);

                    // show try again button
                    tryAgainBtn.setVisible(true);
                }
            }

            // update hint message
            if(userGuess < numberGuess){
                hintLabel.setText("Alien: Too Low");
            }else{
                hintLabel.setText("Alien: Too High");
            }

        }catch(Exception e){
            hintLabel.setText("Alien: That isn't a number!");
        }
    }

    public void handleTryAgainAction(){
        // display game background
        backgroundImg.setImage(new Image(getClass().getResourceAsStream("/resources/background.png")));

        // display game layout
        gameDisplay.setVisible(true);

        // generate new number to guess
        numberGuess = random.nextInt(GUESS_RANGE);

        // reset chances
        chanceCounter = NUM_CHANCES;

        // hide try again button
        tryAgainBtn.setVisible(false);

        // reset hint message
        hintLabel.setText("");

        // reset input field
        userInputField.setText("");
    }

    // launches the game
    public void handleStartAction(){
        // hide the menu
        mainMenu.setVisible(false);

        // display game
        gameDisplay.setVisible(true);
    }

    // exit application
    public void handleExitAction(){
        Platform.exit();
    }

    // close the window
    public void handleCloseAction(){
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    // minimizes the window
    public void handleMinimizedAction(){
        Stage stage = (Stage) minBtn.getScene().getWindow();
        stage.setIconified(true);
    }

    // stores an x and y offset of the mouse click on the titl bar, used to calculate the frame's position relative to mouse movement
    public void handleTitleBarClickAction(MouseEvent event){
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
        event.consume();
    }

    // updates the position of the frame based on the mouse drag on the title bar, using the offsets from the click action
    public void handleTitleBarMovementAction(MouseEvent event){
        Scene scene = titleBar.getScene();
        Window window = scene.getWindow();

        // set new window position based on mouse drag
        window.setX(event.getScreenX() - xOffset);
        window.setY(event.getScreenY() - yOffset);
        event.consume();
    }
}



















