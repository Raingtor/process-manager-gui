package sample.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.ProcessManager;
import sample.model.Administrator;
import sample.model.Process;
import sample.model.ProcessUser;
import sample.model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class UserMenuController implements Initializable {
    @FXML
    private Label greeting;

    @FXML
    private TableView<ProcessUser> userTable = new TableView<>();

    @FXML
    private TableView<Process> processTable = new TableView<>();

    @FXML
    private TableColumn<ProcessUser, String> userNameColumn = new TableColumn<>("Имя");

    @FXML
    private TableColumn<ProcessUser, String> passwordColumn = new TableColumn<>("Пароль");

    @FXML
    private TableColumn<ProcessUser, String> dirColumn = new TableColumn<>("Папка пользователя");

    @FXML
    private TableColumn<Process, String> pidColumn = new TableColumn<>("Пид");

    @FXML
    private TableColumn<Process, String> creatorColumn = new TableColumn<>("Создатель");

    @FXML
    private TableColumn<Process, String> statusColumn = new TableColumn<>("Статус");

    @FXML
    private TableColumn<Process, String> typeColumn = new TableColumn<>("Тип");

    @FXML
    private TableColumn<Process, String> processTimeColumn = new TableColumn<>("Процессорное время");

    @FXML
    private TableColumn<Process, String> priorityColumn = new TableColumn<>("Приоритет");

    ObservableList<ProcessUser> processUsers = FXCollections.observableArrayList();

    ObservableList<Process> processes = FXCollections.observableArrayList();

    private void initializeUserTable() {
        userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        userTable.getColumns().add(userNameColumn);
        userTable.getColumns().add(passwordColumn);
        userTable.getColumns().add(dirColumn);
    }

    private void initializeProcessTable() {
        processTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        processTable.getColumns().add(pidColumn);
        processTable.getColumns().add(creatorColumn);
        processTable.getColumns().add(statusColumn);
        processTable.getColumns().add(typeColumn);
        processTable.getColumns().add(processTimeColumn);
        processTable.getColumns().add(priorityColumn);
    }

    public void fillUserTable() {
        processUsers.clear();
        ProcessUser currentUser = ProcessManager.getCurrentUser();
        if (currentUser instanceof User) {
            processUsers.add(ProcessManager.getCurrentUser());
        } else {
            processUsers.addAll(Administrator.getUsers());
        }
        userTable.setItems(processUsers);
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        dirColumn.setCellValueFactory(new PropertyValueFactory<>("homeDir"));
    }

    public void fillProcessTable() {
        processes.clear();
        ProcessUser currentUser = ProcessManager.getCurrentUser();
        if (currentUser instanceof User) {
            processes.addAll(currentUser.getProcesses());
        } else {
            processes.addAll(Administrator.getAllProcesses());
        }
        processTable.setItems(processes);
        pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
        creatorColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getCreator().getName()));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        processTimeColumn.setCellValueFactory(new PropertyValueFactory<>("processTime"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        greeting.setText(String.format("Добро пожаловать, %s!", ProcessManager.getCurrentUser().getName()));
        initializeUserTable();
        fillUserTable();
        initializeProcessTable();
        fillProcessTable();
    }

    @FXML
    private void handleAddUser(ActionEvent event) throws Exception{
        Stage stage = new Stage();
        stage.setTitle("ProcessManager");
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("sample/view/addUser.fxml"));
        stage.setScene(new Scene(loader.load(), 375, 380));
        CreatingUserController controller = loader.getController();
        controller.setUserMenuController(this);
        stage.show();
    }

    @FXML
    private void handleDeleteUser(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("ProcessManager");
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("sample/view/deleteUser.fxml"));
        stage.setScene(new Scene(loader.load(), 260, 210));
        DeleteController controller = loader.getController();
        controller.setUserMenuController(this);
        controller.setProcessUser(userTable.getSelectionModel().getSelectedItem());
        stage.show();
    }

    @FXML
    private void handleUpdateUser(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("ProcessManager");
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("sample/view/updateUser.fxml"));
        stage.setScene(new Scene(loader.load(), 375, 380));
        UpdateUserController controller = loader.getController();
        controller.setUserMenuController(this);
        controller.setProcessUser(userTable.getSelectionModel().getSelectedItem());
        if (userTable.getSelectionModel().getSelectedItem() instanceof Administrator) {
            Node dir = stage.getScene().lookup("#dirField");
            dir.setDisable(true);
        }
        stage.show();
    }

    @FXML
    private void handleAddProcess(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("ProcessManager");
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("sample/view/addProcess.fxml"));
        stage.setScene(new Scene(loader.load(), 375, 380));
        CreateProcessController controller = loader.getController();
        controller.setUserMenuController(this);
        stage.show();
    }

    @FXML
    private void handleUpdateProcess(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("ProcessManager");
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("sample/view/updateProcess.fxml"));
        stage.setScene(new Scene(loader.load(), 375, 380));
        UpdateProcessController controller = loader.getController();
        controller.setUserMenuController(this);
        controller.setProcess(processTable.getSelectionModel().getSelectedItem());
        stage.show();
    }

    @FXML
    private void handleStartProcess(ActionEvent event) throws Exception {
        Process process = processTable.getSelectionModel().getSelectedItem();
        process.getCreator().startProcess(process);
        fillProcessTable();
    }

    @FXML
    private void handleStopProcess(ActionEvent event) throws Exception {
        Process process = processTable.getSelectionModel().getSelectedItem();
        process.getCreator().stopProcess(process);
        fillProcessTable();
    }

    @FXML
    public void handleDeleteProcess(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("ProcessManager");
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("sample/view/deleteProcess.fxml"));
        stage.setScene(new Scene(loader.load(), 260, 210));
        DeleteController controller = loader.getController();
        controller.setUserMenuController(this);
        controller.setProcess(processTable.getSelectionModel().getSelectedItem());
        controller.setProcessUser(processTable.getSelectionModel().getSelectedItem().getCreator());
        stage.show();
    }

    @FXML
    private void handleSignOut(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("ProcessManager");
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("sample/view/signOut.fxml"));
        stage.setScene(new Scene(loader.load(), 260, 210));
        SignOutController controller = loader.getController();
        controller.setUserMenuController(this);
        stage.show();
    }

    public void hide() {
        greeting.getScene().getWindow().hide();
    }
}