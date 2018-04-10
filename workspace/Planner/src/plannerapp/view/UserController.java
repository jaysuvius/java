/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.view;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import plannerapp.bl.UserBo;
import plannerapp.exception.ValidationException;
import plannerapp.model.User;

/**
 * FXML Controller class
 *
 * @author Dad
 */
public class UserController implements Initializable {
    private Planner mainApp;

    @FXML
    private TextField UserNameTextField;
    @FXML
    private TextField PasswordTextField;
    @FXML
    private CheckBox ActiveCheckbox;
    @FXML
    private Label addUserLabel;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label activeLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    public Label getAddUserLabel(){
        return this.addUserLabel;
    }
    public Label getuserNameLabel(){
        return this.userNameLabel;
    }
    public Label getPasswordLabel(){
        return this.passwordLabel;
    }
    public Label getActiveLabel(){
        return this.activeLabel;
    }
    public Button getSaveButtpm(){
        return this.saveButton;
    }
    public Button getCancelButton(){
        return this.cancelButton;
    }


    private Stage dialogStage;
    private User user;
    private boolean okClicked = false;
    private UserBo userBo = new UserBo();
    private boolean isEdit = false;
    private ObservableList<User> userList;
    private TableView<User> userTable;

    
    public void setUser(User user){
        this.user = user;
    }
    
    public TextField getUserNameTextField(){
        return this.UserNameTextField;
    }
    
    public TextField getPasswordTextField(){
        return this.PasswordTextField;
    }
    
    public CheckBox getActiveCheckbox(){
        return this.ActiveCheckbox;
    }
    
    public void setIsEdit(boolean isEdit){
        this.isEdit = isEdit;
    }
    
    public void setUserList(ObservableList<User> users){
        this.userList = users;
    }
    
    public void setUserTable(TableView<User> userTable){
        this.userTable = userTable;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void save(){
        userBo.setMainApp(this.mainApp);
        try{
           user.setUserName(UserNameTextField.getText());
            user.setPassword(PasswordTextField.getText());
            if (ActiveCheckbox.selectedProperty().get()){
                user.setActive(true);
            }
            else{
                user.setActive(false);
            }
            user.setLastUpdate(LocalDate.now());
            user.setLastUpdateBy(mainApp.getCurrentUser().getUserName());
            if (isEdit){

                userBo.update(user);
            }
            else{
                user.setCreateDate(LocalDate.now());
                user.setCreatedBy(mainApp.getCurrentUser().getUserName());
                userBo.insert(user);
                user.setId(userBo.getNewId(user));
                userList.add(user);
            }
            userTable.refresh();  
            this.dialogStage.close();
        }
        catch (ValidationException vex){
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle(mainApp.getResourceBundle().getString("invalid"));
            a.setContentText(vex.getMessage());
            a.showAndWait();
        }
    }
    
    public void cancel(){
        this.dialogStage.close();
    }
    
    public void setMainApp(Planner mainApp) {
        this.mainApp = mainApp;
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }
    
}
