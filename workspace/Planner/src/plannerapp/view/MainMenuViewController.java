/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import plannerapp.exception.PlannerLog;

/**
 * FXML Controller class
 *
 * @author Dad
 */
public class MainMenuViewController implements Initializable {
    @FXML
    private Label welcomeLabel;
    @FXML
    private Button manageUsersButton;
    @FXML
    private Button manageCustomersButton;
    @FXML
    private Button manageAppointmentsButton;
    @FXML
    private Button viewMonthButton;
    @FXML
    private Button viewWeekButton;
    @FXML
    private Button CustomerReportButton;
    @FXML
    private Button AppointmentReportButton;
    public Label getWelcomeLabel(){
        return this.welcomeLabel;
    }
    public Button getManageUsersButton(){
        return this.manageUsersButton;
    }
    public Button getManageCustomersButton(){
        return this.manageCustomersButton;
    }
    public Button getManageAppointmentsButton(){
        return this.manageAppointmentsButton;
    }
    public Button getViewMonthButton(){
        return this.viewMonthButton;
    }
    public Button getViewWeekButton(){
        return this.viewWeekButton;
    }
    public Button getCustomerReportButton(){
        return this.CustomerReportButton;
    }
    public Button getAppointmentReportButton(){
        return this.AppointmentReportButton;
    }
    
    private Planner mainApp;
    private Stage dialogStage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public boolean addUser(){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Planner.class.getResource("ManageUsers.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            ManageUsersController controller = loader.getController();
            //controller.setMainApp(this);
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(mainApp.getResourceBundle().getString("manageUsers"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ManageUsersController controller1 = loader.getController();
            controller1.setDialogStage(this.dialogStage);

            controller1.setMainApp(this.mainApp);
            controller1.getSelectUserLabel().setText(mainApp.getResourceBundle().getString("selectUser"));
            controller1.getSearchLabel().setText(mainApp.getResourceBundle().getString("search"));
            controller1.getSearchButton().setText(mainApp.getResourceBundle().getString("search"));
            controller1.getSelectButton().setText(mainApp.getResourceBundle().getString("select"));
            controller1.getEditButton().setText(mainApp.getResourceBundle().getString("edit"));
            controller1.getAddButton().setText(mainApp.getResourceBundle().getString("add"));
            controller1.getDeleteButton().setText(mainApp.getResourceBundle().getString("delete"));
            controller1.getUserNameColumn().setText(mainApp.getResourceBundle().getString("userName"));
            controller1.getActiveColumn().setText(mainApp.getResourceBundle().getString("active"));

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            PlannerLog.Log(e);
            return false;
        }
    }
    
    public boolean manageCustomers(){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Planner.class.getResource("AddCustomerView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            AddCustomerController controller = loader.getController();
            //controller.setMainApp(this);
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(mainApp.getResourceBundle().getString("manageCustomers"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            AddCustomerController controller1 = loader.getController();
            controller1.setDialogStage(dialogStage);
            controller1.setMainApp(this.mainApp);
            controller1.getSelectCustomerLabel().setText(mainApp.getResourceBundle().getString("selectCustomer"));
            controller1.getSearchLabel().setText(mainApp.getResourceBundle().getString("search"));
            controller1.getSearchButton().setText(mainApp.getResourceBundle().getString("search"));
            controller1.getSelectButton().setText(mainApp.getResourceBundle().getString("select"));
            controller1.getEditButton().setText(mainApp.getResourceBundle().getString("edit"));
            controller1.getAddButton().setText(mainApp.getResourceBundle().getString("add"));
            controller1.getDeleteButton().setText(mainApp.getResourceBundle().getString("delete"));
            controller1.getCustomerNameColumn().setText(mainApp.getResourceBundle().getString("customerName"));
            controller1.getActiveColumn().setText(mainApp.getResourceBundle().getString("active"));
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            PlannerLog.Log(e);
            return false;
        }
    }
    
    public boolean addAppointment(){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Planner.class.getResource("AddAppointmentView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            AddAppointmentViewController controller = loader.getController();
            //controller.setMainApp(this);
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(mainApp.getResourceBundle().getString("appointments"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            AddAppointmentViewController controller1 = loader.getController();
            controller1.setDialogStage(dialogStage);
            controller1.setMainApp(this.mainApp);
            controller1.getSelectAppointmentLabel().setText(mainApp.getResourceBundle().getString("selectAppointment"));
            controller1.getSearchLabel().setText(mainApp.getResourceBundle().getString("search"));
            controller1.getSearchButton().setText(mainApp.getResourceBundle().getString("search"));
            controller1.getEditButton().setText(mainApp.getResourceBundle().getString("edit"));
            controller1.getAddButton().setText(mainApp.getResourceBundle().getString("add"));
            controller1.getDeleteButton().setText(mainApp.getResourceBundle().getString("delete"));
            controller1.getTitleColumn().setText(mainApp.getResourceBundle().getString("title"));
            controller1.getDescriptionColumn().setText(mainApp.getResourceBundle().getString("description"));
            controller1.getStartColumn().setText(mainApp.getResourceBundle().getString("start"));
            controller1.getEndColumn().setText(mainApp.getResourceBundle().getString("end"));
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            PlannerLog.Log(e);
            return false;
        }
    }
    
    public boolean viewCalendar(){
      try {

            Stage dialogStage = new Stage();
            dialogStage.setTitle(mainApp.getResourceBundle().getString("monthlyCalendar"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);


            CalendarView calendarView = new CalendarView(mainApp.getLocale()) ;

             Button next = new Button(">");
             next.setOnAction(e -> calendarView.nextMonth());

             Button previous = new Button("<");
             previous.setOnAction(e -> calendarView.previousMonth());

             BorderPane root = new BorderPane(calendarView.getView(), null, next, null, previous);
            Scene scene = new Scene(root);
            
            calendarView.setLocale(mainApp.getLocale());
            
            calendarView.setMainApp(mainApp);
            
            dialogStage.setScene(scene);
		dialogStage.setScene(scene);
		dialogStage.show();

            return true; // controller.isOkClicked();
        } catch (Exception e) {
            PlannerLog.Log(e);
            return false;
        }  
    }
    
       public boolean viewWeek(){
      try {
            // Load the fxml file and create a new stage for the popup dialog.
           
            Stage dialogStage = new Stage();
            dialogStage.setTitle(mainApp.getResourceBundle().getString("weeklyCalendar"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);


            WeekView calendarView = new WeekView(mainApp, mainApp.getLocale()) ;

            Button next = new Button(">");
            next.setOnAction(e -> calendarView.nextWeek());

            Button previous = new Button("<");
            previous.setOnAction(e -> calendarView.previousWeek());

            BorderPane root = new BorderPane(calendarView.getView(), null, next, null, previous);
            Scene scene = new Scene(root);

            dialogStage.setScene(scene);
		dialogStage.setScene(scene);
		dialogStage.show();

            return true; // controller.isOkClicked();
        } catch (Exception e) {
            PlannerLog.Log(e);
            return false;
        }  
    }
    
    public void appointmentReport(){
         try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Planner.class.getResource("AppointmentReportView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            AppointmentReportViewController controller = loader.getController();
            //controller.setMainApp(this);
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(mainApp.getResourceBundle().getString("appointmentReport"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            AppointmentReportViewController controller1 = loader.getController();
            controller1.setDialogStage(dialogStage);
            controller1.setMainApp(this.mainApp);
            controller1.getAppointmentReportLabel().setText(mainApp.getResourceBundle().getString("appointmentReport"));
            controller1.getCloseButton().setText(mainApp.getResourceBundle().getString("close"));
            controller1.getYearColumn().setText(mainApp.getResourceBundle().getString("year"));
            controller1.getMonthColumn().setText(mainApp.getResourceBundle().getString("month"));
            controller1.getCustomerColumn().setText(mainApp.getResourceBundle().getString("customer"));
            controller1.getContactColumn().setText(mainApp.getResourceBundle().getString("contact"));
            controller1.getTitleColumn().setText(mainApp.getResourceBundle().getString("title"));
            controller1.getDescriptionColumn().setText(mainApp.getResourceBundle().getString("description"));
            controller1.getUrlColumn().setText(mainApp.getResourceBundle().getString("url"));

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            PlannerLog.Log(e);
        }
    }   
    
    public void customerReport(){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Planner.class.getResource("CustomerReportView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            CustomerReportViewController controller = loader.getController();
            //controller.setMainApp(this);
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(mainApp.getResourceBundle().getString("customerReport"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            CustomerReportViewController controller1 = loader.getController();
            controller1.setDialogStage(dialogStage);
            controller1.setMainApp(this.mainApp);
            controller1.getCustomerReportLabel().setText(mainApp.getResourceBundle().getString("customerReport"));
            controller1.getNameColumn().setText(mainApp.getResourceBundle().getString("customerName"));
            controller1.getActiveColumn().setText(mainApp.getResourceBundle().getString("active"));
            controller1.getAddressColumn().setText(mainApp.getResourceBundle().getString("address"));
            controller1.getAddress2Column().setText(mainApp.getResourceBundle().getString("address2"));
            controller1.getCityColumn().setText(mainApp.getResourceBundle().getString("city"));
            controller1.getPostalCodeColumn().setText(mainApp.getResourceBundle().getString("postalCode"));
            controller1.getPhoneColumn().setText(mainApp.getResourceBundle().getString("phone"));
            controller1.getCloseButton().setText(mainApp.getResourceBundle().getString("close"));
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            PlannerLog.Log(e);
        }        
    }
       
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setMainApp(Planner mainApp) {
        this.mainApp = mainApp;
    }
}
