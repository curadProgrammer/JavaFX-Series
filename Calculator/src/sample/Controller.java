package sample;

import constants.CommonConstants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private Label outputLabel;

    @FXML
    private Label calculationSequenceLabel;

    // flags to handle the logic
    // storedNum1Flag, storedNum2Flag - tell if numbers are stored into num1 and num2 vars
    private boolean pressedBinaryOperator, pressedEqual, pressedUnary;
    private boolean storedNum1, storedNum2;

    private double num1, num2;
    private String binaryOperator;

    public void handleNumberButtonClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        String numberInput = button.getText();
        String outputLabelText = outputLabel.getText();

        if (shouldReplaceZero(outputLabelText)) {
            outputLabel.setText(numberInput);

            if(shouldStoreNum2()){
                storedNum2 = true;
            }

            pressedEqual = false;
            pressedUnary = false;

        } else {
            // append to the output string
            outputLabel.setText(outputLabelText + numberInput);
        }
    }

    private boolean shouldReplaceZero(String outputLabelText){
        // replace 0 when we are about to enter value for num2
        // replace 0 after pressing equal
        // replace 0 after pressing unary button
        // replace 0 if the current value is 0
        return (storedNum1 && pressedBinaryOperator && !storedNum2)
                || pressedEqual
                || pressedUnary
                || Double.parseDouble(outputLabelText) == 0;
    }

    private boolean shouldStoreNum2(){
        // store num2 after storing num1 and pressing a binary operator
        return !storedNum2 && storedNum1 && pressedBinaryOperator;
    }

    public void handleUnaryButtonClick(ActionEvent event){
        Button button = (Button) event.getSource();
        String unaryOperator = button.getText();

        // store result
        double result = Double.parseDouble(outputLabel.getText());

        // perform unary calculation
        switch(unaryOperator){
            case CommonConstants.OPERATOR_PERCENT:
                result /= 100;
                calculationSequenceLabel.setText(Double.toString(result));
                break;
            case CommonConstants.OPERATOR_RECIPROCAL:
                result = 1/result;
                calculationSequenceLabel.setText("1/" + result);
                break;
            case CommonConstants.OPERATOR_SQUARE:
                result = result * result;
                calculationSequenceLabel.setText("sqr(" + result + ")");
                break;
            case CommonConstants.OPERATOR_SQRT:
                result = Math.sqrt(result);
                calculationSequenceLabel.setText("sqrt(" + result + ")");
                break;
            case CommonConstants.OPERATOR_NEGATE:
                result *= -1;
                break;
        }

        // store into num1 if possible
        if(!storedNum1){
            num1 = result;
            storedNum1 = true;
        }else if(shouldStoreNum2()){
            num2 = result;
            storedNum2 = true;
        }


        // output to display
        outputLabel.setText(Double.toString(result));

        // update flags
        pressedUnary = true;
        pressedEqual = false;
        pressedBinaryOperator = false;

    }

    public void handleBinaryButtonClick(ActionEvent event){
        Button button = (Button) event.getSource();
        String binaryOperator = button.getText();

        // store num1
        if(!storedNum1){
            num1 = Double.parseDouble(outputLabel.getText());
            storedNum1 = true;
        }

        // update binary operator
        if(storedNum1)
            updateBinaryOperator(binaryOperator);

        // update flags
        pressedBinaryOperator = true;
        pressedUnary = false;
        pressedEqual = false;
    }

    private void updateBinaryOperator(String binaryOperator){
        this.binaryOperator = binaryOperator;

        // update calculation sequence
        calculationSequenceLabel.setText(num1 + " " + this.binaryOperator);
    }

    public void handleDotButtonClick(){
        // add a dot if possible
        if(!outputLabel.getText().contains("."))
            outputLabel.setText(outputLabel.getText() + ".");
    }

    public void handleOtherButtonClick(ActionEvent event){
        Button button = (Button) event.getSource();
        String otherButton = button.getText();

        switch(otherButton){
            case CommonConstants.CLEAR_ENTRY_BTN: // resets current input
                outputLabel.setText("0");
                break;
            case CommonConstants.CLEAR_BTN: // resets everything
                reset();
                break;
            case CommonConstants.DEL_BTN: // deletes a number
                // only removes if value is not 0
                if(Double.parseDouble(outputLabel.getText()) != 0){
                    outputLabel.setText(outputLabel.getText().substring(0,
                            outputLabel.getText().length() - 1));
                }

                // resets to 0 if attempting to delete every number in the output label
                if(outputLabel.getText().length() <= 0){
                    outputLabel.setText("0");
                }
                break;
        }
    }

    private void reset(){
        outputLabel.setText("0");
        calculationSequenceLabel.setText("");
        storedNum1 = false;
        storedNum2 = false;
        pressedBinaryOperator = false;
        pressedEqual = false;
        pressedUnary = false;
    }

    public void handleEqualButtonClick(){
        // store num2 if possible
        if(shouldStoreNum2()){
            num2 = num1;
            storedNum2 = true;
        }

        if(shouldCalculate()){
            calculate();

            // update flags
            pressedEqual = true;
            pressedBinaryOperator = false;
            pressedUnary = false;
        }
    }

    // calculate only when there are values stored in num1 and num2
    private boolean shouldCalculate(){
        return storedNum2 && storedNum2;
    }

    private void calculate(){
        // store num2
        num2 = Double.parseDouble(outputLabel.getText());
        storedNum2 = true;

        // store result into num1
        num1 = performBinaryCalculation();
        outputLabel.setText(Double.toString(num1));
    }

    private double performBinaryCalculation(){
        double result = 0;

        switch(binaryOperator){
            case CommonConstants.OPERATOR_ADD:
                result = num1 + num2;
                break;
            case CommonConstants.OPERATOR_SUBTRACT:
                result = num1 - num2;
                break;
            case CommonConstants.OPERATOR_MULTIPLY:
                result = num1 * num2;
                break;
            case CommonConstants.OPERATOR_DIVIDE:
                if(num2 == 0){
                    outputLabel.setText("Error: Cannot divide by 0");
                    reset();
                }else{
                    result = num1/num2;
                }
                break;
        }

        // update calculation sequence
        calculationSequenceLabel.setText(num1 + " " + binaryOperator + " " + num2 + " = ");

        // reset num1 and num2
        num1 = 0;
        storedNum1 = false;

        num2 = 0;
        storedNum2 = false;

        return result;
    }
}


















