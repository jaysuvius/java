/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.view;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import plannerapp.exception.PlannerLog;
import plannerapp.model.User;

/**
 *
 * @author Dad
 */
public class Planner extends Application {
    private User currentUser;
    private Stage primaryStage;
    private Locale plannerLocale;
    private ResourceBundle plannerResourceBundle;
    
    public User getCurrentUser(){
        return currentUser;
    }
    
    public void setCurrentUser(User u){
        this.currentUser = u;
    }
    
    public void setLocale(Locale locale){
        this.plannerLocale = locale;
    }
    
    public Locale getLocale(){
        return this.plannerLocale;
    }
    
    public ResourceBundle getResourceBundle(){
        return this.plannerResourceBundle;
    }
    
    public void setResourceBundle(ResourceBundle rb){
        this.plannerResourceBundle = rb;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        try {

            this.currentUser = new User();
            this.currentUser.setUserName("Jeremy");
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Planner.class.getResource("LoginView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            LoginViewController controller = loader.getController();
            //controller.setMainApp(this);
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Login");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            LoginViewController controller1 = loader.getController();
            //controller1.setDialogStage(dialogStage);
            controller1.setMainApp(this);
            controller1.setEnglish();
            controller1.setLocale();
            controller1.setPrimaryStage(dialogStage);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            PlannerLog.Log(e);
        }

    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        

        
        launch(args);
    }
    
}
