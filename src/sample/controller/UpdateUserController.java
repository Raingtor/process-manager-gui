package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import sample.model.Administrator;
import sample.model.ProcessUser;
import sample.model.User;

@Getter
@Setter
public class UpdateUserController {
    private UserMenuController userMenuController;

    private ProcessUser processUser;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField dirField;

    @FXML
    private void click(ActionEvent event) throws Exception{
        String userName = userNameField.getText();
        if (Administrator.getUsers().stream().filter(it ->
                it.getName().equals(userName)).count() != 0) {
            Stage stage = new Stage();
            stage.setTitle("ProcessManager");
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample/view/userNameIsUsed.fxml"));
            stage.setScene(new Scene(root, 375, 380));
            stage.show();
        }
        String password = passwordField.getText();
        if (processUser instanceof Administrator) {
            ((Administrator) processUser).updateUser(userName, password);
        } else {
            String dir = dirField.getText();
            ((User) processUser).updateUser(userName, password, dir);
        }
        userNameField.getScene().getWindow().hide();
        userMenuController.fillUserTable();
    }
}
