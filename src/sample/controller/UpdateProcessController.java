package sample.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;
import lombok.Getter;
import lombok.Setter;
import sample.model.Process;
import sample.model.Status;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@Getter
@Setter
public class UpdateProcessController implements Initializable {
    private UserMenuController userMenuController;

    private Process process;

    @FXML
    private ComboBox<Status> statusComboBox;

    @FXML
    private TextField typeField;

    @FXML
    private TextField processTimeField;

    @FXML
    private TextField priorityField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statusComboBox.setItems(FXCollections.observableArrayList(Status.values()));
        TextFormatter<Integer> processTimeFormatter = new TextFormatter<>(
                new IntegerStringConverter(), 0, c -> Pattern.matches("\\d*", c.getText()) ? c : null);
        processTimeField.setTextFormatter(processTimeFormatter);
        TextFormatter<Integer> priorityFormatter = new TextFormatter<>(
                new IntegerStringConverter(), 0, c -> Pattern.matches("\\d*", c.getText()) ? c : null);
        priorityField.setTextFormatter(priorityFormatter);
    }

    @FXML
    private void click(ActionEvent event) {
        Status status = statusComboBox.getValue();
        String type = typeField.getText();
        int processTime = Integer.parseInt(processTimeField.getText());
        int priority = Integer.parseInt(priorityField.getText());
        process.getCreator().updateProcess(process, status, type, processTime, priority);
        userMenuController.fillProcessTable();
        statusComboBox.getScene().getWindow().hide();
    }
}