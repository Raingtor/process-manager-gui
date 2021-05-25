package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import sample.ProcessManager;
import sample.model.Administrator;
import sample.model.ProcessUser;
import sample.model.User;

@Getter
@Setter
public class CreatingUserController {
    private UserMenuController userMenuController;

    @FXML
    private ToggleGroup role;

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField dirField;

    @FXML
    private Button createUserButton;

    @FXML
    private void click(ActionEvent event) throws Exception{
        RadioButton selectedRole = (RadioButton) role.getSelectedToggle();
        String userName = userNameField.getText();
        if (Administrator.getUsers().stream().filter(it ->
                it.getName().equals(userName)).count() != 0) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample/view/userNameIsUsed.fxml"));
            stage.setScene(new Scene(root, 375, 380));
            stage.show();
        }
        String password = passwordField.getText();
        ProcessUser user;
        if (selectedRole.getId().equals("user")) {
            String dir = dirField.getText();
            user = new User(userName, password, dir);
        } else {
            user = new Administrator(userName, password);
        }
        Administrator.addUser(user);
        if (createUserButton.getScene().getRoot().getId().equals("signUp")) {
            ProcessManager.setCurrentUser(user);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample/view/userMenu.fxml"));
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } else if (createUserButton.getScene().getRoot().getId().equals("addUser")) {
            userMenuController.fillUserTable();
        }
        createUserButton.getScene().getWindow().hide();
    }

    @FXML
    private void blockDirField(ActionEvent event) {
        dirField.setDisable(true);
    }

    @FXML
    private void unBlockDirField(ActionEvent event) {
        dirField.setDisable(false);
    }
}