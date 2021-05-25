package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PopUpController {
    @FXML
    private Button okButton;

    @FXML
    private void click(ActionEvent event) {
        okButton.getScene().getWindow().hide();
    }
}