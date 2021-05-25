package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import sample.ProcessManager;

@Getter
@Setter
public class SignOutController {
    UserMenuController userMenuController;

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    @FXML
    private void clickYes(ActionEvent event) throws Exception{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("sample/view/startMenu.fxml"));
        stage.setScene(new Scene(loader.load(), 350, 350));
        stage.setTitle("ProcessManager");
        stage.show();
        ProcessManager.setCurrentUser(null);
        yesButton.getScene().getWindow().hide();
        userMenuController.hide();
    }

    @FXML
    private void clickNo(ActionEvent event) {
        noButton.getScene().getWindow().hide();
    }
}
