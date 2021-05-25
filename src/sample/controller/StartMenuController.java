package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.ProcessManager;
import sample.model.Administrator;
import sample.model.ProcessUser;

public class StartMenuController {
    @FXML
    private Button signUpButton;

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void clickSignIn(ActionEvent event) throws Exception{
        String userName = userNameField.getText();
        String password = passwordField.getText();
        ProcessUser user = Administrator.getUsers().stream().filter(it ->
                it.getName().equals(userName) && it.getPassword().equals(password)
        ).findFirst().orElse(null);
        if (user == null) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample/view/userNotFound.fxml"));
            stage.setScene(new Scene(root, 250, 200));
            stage.show();
        } else {
            ProcessManager.setCurrentUser(user);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample/view/userMenu.fxml"));
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        }
        signUpButton.getScene().getWindow().hide();
    }

    @FXML
    private void clickSignUp(ActionEvent event) throws Exception{
        signUpButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample/view/signUp.fxml"));
        stage.setScene(new Scene(root, 375, 380));
        stage.show();
    }
}