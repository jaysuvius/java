/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import plannerapp.bl.UserBo;
import plannerapp.exception.PlannerLog;
import plannerapp.model.User;
import plannerapp.validation.Conditions;

/**
 * FXML Controller class
 *
 * @author Dad
 */
public class ManageUsersController implements Initializable {
    @FXML
    private TableView<User> UserTable;
    @FXML
    private TableColumn<User, String> UserNameColumn;
    @FXML
    private TableColumn<User, String> ActiveColumn;
    
    @FXML
    private TextField FindUserTextField;
    @FXML
    private Label selectUserLabel;
    @FXML
    private Label searchLabel;
    @FXML
    private Button searchButton;
    @FXML
    private Button selectButton;
    @FXML
    private Button editButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    public TableColumn<User, String> getUserNameColumn(){
        return this.UserNameColumn;
    }
    public TableColumn<User, String> getActiveColumn(){
        return this.ActiveColumn;
    }
    public Label getSelectUserLabel(){
        return this.selectUserLabel;
    }
    public Label getSearchLabel(){
        return this.searchLabel;
    }
    public Button getSearchButton(){
        return this.searchButton;
    }
    public Button getSelectButton(){
        return this.selectButton;
    }
    public Button getEditButton(){
        return this.editButton;
    }
    public Button getAddButton(){
        return this.addButton;
    }
    public Button getDeleteButton(){
        return this.deleteButton;
    }
    
    private Planner mainApp;
    private Stage dialogStage;
    private final boolean okClicked = false;
    private ObservableList<User> userList;
    private final UserBo userBo = new UserBo();
    private boolean isEdit = false;
    private User user;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUserName()));
        ActiveColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().isActiveString()));
        initUserList();
    }
    
    public void initUserList(){
        userList = userBo.getObservableList();
        UserTable.setItems(userList);
        UserTable.refresh();
    }
    
    public void findUser(){
        String searchText = FindUserTextField.getText();
        FilteredList<User> searchCountryResults = searchUsers(searchText);
        SortedList<User> sortedData = new SortedList<>(searchCountryResults);
        sortedData.comparatorProperty().bind(UserTable.comparatorProperty());
        UserTable.setItems(sortedData);
    }
    
    public FilteredList<User> searchUsers(String s){
        return userList.filtered(p -> p.getUserName().toLowerCase().contains(s.toLowerCase()));
    }
    
    public void select(){
        
    }
    
    public boolean add(){
        if (user == null){
            user = new User();
        }
        isEdit = false;
        return spawnUserController();
    }
    
    public void delete(){
        if(userSelected()){
            Alert a = new Alert(AlertType.CONFIRMATION);
            a.setTitle(mainApp.getResourceBundle().getString("confirm"));
            a.setContentText(mainApp.getResourceBundle().getString("confirmDelete"));
            a.showAndWait();
            if(a.getResult() == ButtonType.OK){
                userBo.delete(UserTable.getSelectionModel().getSelectedItem().getId());
                initUserList();
            }
        }
    }
    
    public boolean edit(){
        if(userSelected()){
            user = UserTable.getSelectionModel().getSelectedItem();
            isEdit = true;
            return spawnUserController();
        }
        return false;
    }
    
    public boolean spawnUserController(){
        try{
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Planner.class.getResource("UserView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            UserController controller = loader.getController();
            //controller.setMainApp(this);
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(mainApp.getResourceBundle().getString("addEditUser"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            UserController controller1 = loader.getController();
            controller1.setDialogStage(dialogStage);
            //controller.setAnimal(animal);
            controller1.setMainApp(this.mainApp);
            if (isEdit){
                dialogStage.addEventHandler(WindowEvent.WINDOW_SHOWING, (WindowEvent window) -> {
                    controller1.getUserNameTextField().setText(user.getUserName());
                    controller1.getPasswordTextField().setText(user.getPassword());
                    if (user.isActive()){
                        controller1.getActiveCheckbox().selectedProperty().set(true);
                    }
                    else{
                        controller1.getActiveCheckbox().selectedProperty().set(false);
                    }
                    
                    controller1.setUser(user);
                    controller1.setUserList(userList);
                    controller1.setUserTable(UserTable);
                    controller1.setIsEdit(isEdit);
                }); 
            }
            else{
                controller1.setUser(user);
                controller1.setUserList(userList);
                controller1.setUserTable(UserTable);
                controller1.setIsEdit(isEdit);
            }
            // Show the dialog and wait until the user closes it
            controller1.setUserList(userList);
            controller1.getAddUserLabel().setText(mainApp.getResourceBundle().getString("addUser"));
            controller1.getuserNameLabel().setText(mainApp.getResourceBundle().getString("userName"));
            controller1.getPasswordLabel().setText(mainApp.getResourceBundle().getString("password"));
            controller1.getActiveLabel().setText(mainApp.getResourceBundle().getString("active"));
            controller1.getSaveButtpm().setText(mainApp.getResourceBundle().getString("save"));
            controller1.getCancelButton().setText(mainApp.getResourceBundle().getString("cancel"));
            
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }
        catch (IOException e) {
            PlannerLog.Log(e);
            return false;
        }
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public boolean isOkClicked(){
        return this.okClicked;
    }
    
    public void setMainApp(Planner mainApp){
        this.mainApp = mainApp;
    }
    
    public boolean userSelected(){
        if(Conditions.isObjectNull(UserTable.getSelectionModel().getSelectedItem())){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Selection Required");
            a.setContentText("Please select a user.");
            a.showAndWait();
            return false;
        }
        return true;
    }
    
}
