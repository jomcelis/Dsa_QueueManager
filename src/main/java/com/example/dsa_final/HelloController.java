package com.example.dsa_final;

import javafx.animation.FillTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;

public class HelloController {
    @FXML
    public TextField SignIn_Username;

    @FXML
    public TextField SignIn_Password;

    @FXML
    public Button SignIn_Button;

    // Define colors for normal and clicked states
    private final Color normalColor = Color.WHITE;
    private final Color clickedColor = Color.LIGHTGRAY;

    public void SignInBtn_Click(ActionEvent event) throws IOException {
        // Change button background color on click
        FillTransition fillTransition = new FillTransition(Duration.millis(200), SignIn_Button.getShape(), normalColor, clickedColor);
        fillTransition.play();

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), SignIn_Button);
        scaleTransition.setByX(0.1);
        scaleTransition.setByY(0.1);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();

        Main m = new Main();
        m.changeScene("MainQueue.fxml");
    }

    @FXML
    public void OnMouseEnter(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), SignIn_Button);
        scaleTransition.setToX(1.1); // Scale X by 10%
        scaleTransition.setToY(1.1); // Scale Y by 10%
        scaleTransition.play();
    }

    @FXML
    public void OnMouseExit(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), SignIn_Button);
        scaleTransition.setToX(1.0); // Restore X to its original size
        scaleTransition.setToY(1.0); // Restore Y to its original size
        scaleTransition.play();
    }
}
