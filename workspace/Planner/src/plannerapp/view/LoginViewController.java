/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.view;

import chronjobs.ReminderScheduler;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import plannerapp.bl.AppointmentBo;
import plannerapp.bl.UserBo;
import plannerapp.exception.PlannerLog;
import plannerapp.model.User;

/**
 * FXML Controller class
 *
 * @author Dad
 */
public class LoginViewController implements Initializable {
    @FXML
    Label loginLabel;
    @FXML
    Label localeLabel;
    @FXML
    Label userNameLabel;
    @FXML
    Label passwordLabel;
    @FXML
    Button loginButton;
    
    @FXML
    ComboBox LocaleComboBox;
    @FXML
    TextField UserNameTextField;
    @FXML
    PasswordField PasswordField;
    
    private Stage primaryStage;
    private Planner mainApp;
    private UserBo userBo = new UserBo();
    
    public void setMainApp(Planner mainApp){
        this.mainApp = mainApp;
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LocaleComboBox.getItems().add("English");
        LocaleComboBox.getItems().add("Español");
        LocaleComboBox.getSelectionModel().select("English");
    }
    
    public void setLocale(){
        if(LocaleComboBox.getValue() == "English"){
            setEnglish();
        }
        else{
           setEspanol();
        }
        setFormLocale();
    }
    
    public void setEnglish(){
        String lang = "en";
        String country = "US";

        Locale l = new Locale(lang, country);
        ResourceBundle r = ResourceBundle.getBundle("plannerapp/Bundle_en_US", l);
        mainApp.setLocale(l);
        mainApp.setResourceBundle(r);
    }
    
    public void setEspanol(){
        String lang = "es";
        String country = "MX";

        Locale l = new Locale(lang, country);
        ResourceBundle r = ResourceBundle.getBundle("plannerapp/Bundle_es_MX", l);
        mainApp.setLocale(l);
        mainApp.setResourceBundle(r); 
    }
    
    public void setFormLocale(){
        loginLabel.setText(mainApp.getResourceBundle().getString("login"));
        localeLabel.setText(mainApp.getResourceBundle().getString("locale"));
        userNameLabel.setText(mainApp.getResourceBundle().getString("userName"));
        passwordLabel.setText(mainApp.getResourceBundle().getString("password"));
        loginButton.setText(mainApp.getResourceBundle().getString("login"));
    }

    public boolean login(){
        User u = new User();
        User ou = u;
        ou.setUserName(UserNameTextField.getText());
        ou.setPassword(PasswordField.getText());
        
        User authUser = userBo.getByUsernamePassword(ou);
        
        if(Objects.isNull(authUser.getUserName())){
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle(mainApp.getResourceBundle().getString("invalid"));
            a.setContentText(mainApp.getResourceBundle().getString("loginFailed"));
            a.showAndWait();
            return false;
        }

        mainApp.setCurrentUser(authUser);

        scheduleReminders();
        //this.primaryStage.close();
        launchMainMenu();

        return true;
    }
    
    public void scheduleReminders(){
        AppointmentBo aptbo = new AppointmentBo();
        ZonedDateTime aptStart = ZonedDateTime.now();
        ZonedDateTime end = ZonedDateTime.now().plusMonths(1);
        aptbo.getByDateRange(aptStart, end).stream().filter((a) -> (a.getStart().toLocalTime().isAfter(LocalTime.now()))).forEachOrdered((a) -> {
            Long startTime = (a.getStart().minusMinutes(15).toInstant().toEpochMilli() - System.currentTimeMillis())/1000;
            ReminderScheduler rs = new ReminderScheduler(startTime, 5, startTime + 20);
            rs.activateAlarmThenStop(a, mainApp.getResourceBundle());
        });
    }
    
    public void launchMainMenu(){
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Planner.class.getResource("MainMenuView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            MainMenuViewController controller = loader.getController();
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(mainApp.getResourceBundle().getString("mainMenu"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            MainMenuViewController controller1 = loader.getController();
            controller1.setDialogStage(dialogStage);
            controller1.getWelcomeLabel().setText(mainApp.getResourceBundle().getString("greeting"));
            controller1.getManageUsersButton().setText(mainApp.getResourceBundle().getString("manageUsers"));
            controller1.getManageCustomersButton().setText(mainApp.getResourceBundle().getString("manageCustomers"));
            controller1.getManageAppointmentsButton().setText(mainApp.getResourceBundle().getString("manageAppointments"));
            controller1.getViewMonthButton().setText(mainApp.getResourceBundle().getString("viewMonthCalendar"));
            controller1.getViewWeekButton().setText(mainApp.getResourceBundle().getString("viewWeekCalendar"));
            controller1.getCustomerReportButton().setText(mainApp.getResourceBundle().getString("customerReport"));
            controller1.getAppointmentReportButton().setText(mainApp.getResourceBundle().getString("appointmentReport"));
            //controller.setAnimal(animal);
            controller1.setMainApp(mainApp);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            
        } catch (IOException e) {
            PlannerLog.Log(e);
        }
    }
    
    public void setPrimaryStage(Stage stage){
        this.primaryStage = stage;
    }
    
}
