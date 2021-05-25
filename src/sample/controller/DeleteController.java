package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.Getter;
import lombok.Setter;
import sample.model.Administrator;
import sample.model.Process;
import sample.model.ProcessUser;

@Getter
@Setter
public class DeleteController {
    UserMenuController userMenuController;

    ProcessUser processUser;

    Process process;

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    @FXML
    private void clickYes(ActionEvent event) {
        if (yesButton.getScene().getRoot().getId().equals("deleteUser")) {
            Administrator.deleteUser(processUser);
            userMenuController.fillUserTable();
        } else if (yesButton.getScene().getRoot().getId().equals("deleteProcess")) {
            processUser.deleteProcess(process);
            userMenuController.fillProcessTable();
        }

        yesButton.getScene().getWindow().hide();
    }

    @FXML
    private void clickNo(ActionEvent event) {
        noButton.getScene().getWindow().hide();
    }
}