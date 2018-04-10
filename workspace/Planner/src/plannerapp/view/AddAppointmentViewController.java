/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.view;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
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
import plannerapp.bl.AppointmentBo;
import plannerapp.exception.PlannerLog;
import plannerapp.model.Appointment;
import plannerapp.model.Customer;
import plannerapp.validation.Conditions;

/**
 * FXML Controller class
 *
 * @author Dad
 */
public class AddAppointmentViewController implements Initializable {
    @FXML
    private TableView<Appointment> AppointmentTable;
    @FXML
    private TableColumn<Appointment, String> TitleColumn;
    @FXML
    private TableColumn<Appointment, String> DescriptionColumn;
    @FXML
    private TableColumn<Appointment, String> StartColumn;
    @FXML
    private TableColumn<Appointment, String> EndColumn;
    @FXML
    private Label selectAppointmentLabel;
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
    @FXML
    private TextField FindAppointmentTextField;
    public TableColumn<Appointment, String> getTitleColumn(){
        return this.TitleColumn;
    }
    public TableColumn<Appointment, String> getDescriptionColumn(){
        return this.DescriptionColumn;
    }
    public TableColumn<Appointment, String> getStartColumn(){
        return this.StartColumn;
    }
    public TableColumn<Appointment, String> getEndColumn(){
        return this.EndColumn;
    }    
    public Label getSelectAppointmentLabel(){
        return this.selectAppointmentLabel;
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
    private Appointment appointment;
    private ObservableList<Appointment> appointmentList;
    private boolean okClicked = false;
    private final AppointmentBo appointmentBo = new AppointmentBo();
    private Optional<Customer> customer;
    
    public void setAppointment (Appointment app){
        this.appointment = app;
    }
    
    public Appointment getAppointment(){
        return this.appointment;
    }
    
    public ObservableList<Appointment> getAppointmentList(){
        return this.appointmentList;
    }
    
    public void setAppointmentList(ObservableList<Appointment> appList){
        this.appointmentList = appList;
    }
    
    public Optional<Customer> getCustomer(){
        return customer;
    }    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TitleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitle()));
        DescriptionColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDescription()));
        StartColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm").format(cellData.getValue().getStart().toLocalDateTime())));
        EndColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm").format(cellData.getValue().getEnd().toLocalDateTime())));
        initAppointmentList();
    }
    
    public void initAppointmentList(){
        appointmentList = appointmentBo.getObservableList();
        AppointmentTable.setItems(appointmentList);
        AppointmentTable.refresh();
    }

public boolean add(){
       if(appointment == null){
           appointment = new Appointment();
       }
       return spawnAppointmentView(false);
    }
    
    public boolean spawnAppointmentView(boolean isEditMode){
        try {
         // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Planner.class.getResource("AppointmentView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            AppointmentController controller = loader.getController();
            //controller.setMainApp(this);
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(mainApp.getResourceBundle().getString("addAppointment"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            AppointmentController controller1 = loader.getController();
            controller1.setDialogStage(dialogStage);
            //controller.setAnimal(animal);
            controller1.setMainApp(this.mainApp);
            if (isEditMode){
                dialogStage.addEventHandler(WindowEvent.WINDOW_SHOWING, (WindowEvent window) -> {
                    controller1.getCustomerTextField().setText(appointment.getCustomer().isPresent() ? appointment.getCustomer().get().getCustomerName():"");
                    controller1.getTitleTextField().setText(appointment.getTitle());
                    controller1.getDescriptionTextField().setText(appointment.getDescription());
                    controller1.getLocationTextField().setText(appointment.getLocation());
                    controller1.getContactTextField().setText(appointment.getContact());
                    controller1.getUrlTextField().setText(appointment.getUrl());
                    controller1.getStartDatePicker().setValue(appointment.getStart().toLocalDate());
                    controller1.getEndDatePicker().setValue(appointment.getEnd().toLocalDate());
                    controller1.getStartTimePicker().getSelectionModel().select(convertDateToComboItem(appointment.getStart().toLocalTime()));
                    controller1.getEndTimePicker().getSelectionModel().select(convertDateToComboItem(appointment.getEnd().toLocalTime()));
                    controller1.setCustomer(appointment.getCustomer());
                    controller1.setAppointment(appointment);
                    controller1.setAppointmentList(appointmentList);
                    controller1.setAppointmentTable(AppointmentTable);
                    controller1.setIsEditMode(isEditMode);
                }); 
            }
            else{
                controller1.setAppointment(appointment);
                controller1.setAppointmentList(appointmentList);
                controller1.setAppointmentTable(AppointmentTable);
                controller1.setIsEditMode(isEditMode);
            }
            controller1.getAppointmentLabel().setText(mainApp.getResourceBundle().getString("addAppointment"));
            controller1.getTitleLabel().setText(mainApp.getResourceBundle().getString("title"));
            controller1.getDescriptionLabel().setText(mainApp.getResourceBundle().getString("description"));
            controller1.getLocationLabel().setText(mainApp.getResourceBundle().getString("location"));
            controller1.getContactLabel().setText(mainApp.getResourceBundle().getString("contact"));
            controller1.getUrlLabel().setText(mainApp.getResourceBundle().getString("url"));
            controller1.getStartDateLabel().setText(mainApp.getResourceBundle().getString("startDate"));
            controller1.getStartTimeLabel().setText(mainApp.getResourceBundle().getString("startTime"));
            controller1.getEndDateLabel().setText(mainApp.getResourceBundle().getString("endDate"));
            controller1.getEndTimeLabel().setText(mainApp.getResourceBundle().getString("endTime"));
            controller1.getTitleLabel().setText(mainApp.getResourceBundle().getString("title"));
            controller1.getSelectCustomerButton().setText(mainApp.getResourceBundle().getString("selectCustomer"));
            controller1.getSaveButton().setText(mainApp.getResourceBundle().getString("save"));
            controller1.getCancelButton().setText(mainApp.getResourceBundle().getString("cancel"));
            controller1.getCustomerLabel().setText(mainApp.getResourceBundle().getString("customer"));

            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            
            return controller.isOkClicked();
        }
        catch (IOException e) {
            PlannerLog.Log(e);
            return false;
        }
    }
    
    public boolean appointmentSelected(){
        if(Conditions.isObjectNull(AppointmentTable.getSelectionModel().getSelectedItem())){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle(mainApp.getResourceBundle().getString("selectionRequired"));
            a.setContentText(mainApp.getResourceBundle().getString("selectAppointment"));
            a.showAndWait();
            return false;
        }
        return true;
    }

    public boolean delete(){
        if(appointmentSelected()){
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle(mainApp.getResourceBundle().getString("confirm"));
            a.setContentText(mainApp.getResourceBundle().getString("confirmDelete"));
            a.showAndWait();
            if(a.getResult() == ButtonType.OK){
                Boolean success = appointmentBo.delete(AppointmentTable.getSelectionModel().getSelectedItem().getId());
                if (success){
                    initAppointmentList();
                }
                return success;
            }
        }
        return false;
    }
    
    public void findAppointment(){
        String searchText = FindAppointmentTextField.getText();
        FilteredList<Appointment> searchCountryResults = searchAppointmentes(searchText);
        SortedList<Appointment> sortedData = new SortedList<>(searchCountryResults);
        sortedData.comparatorProperty().bind(AppointmentTable.comparatorProperty());
        AppointmentTable.setItems(sortedData);
    }
    
    public FilteredList<Appointment> searchAppointmentes(String s){
        return appointmentList.filtered(p -> p.getTitle().toLowerCase().contains(s.toLowerCase()));
    }
    
    public void select(){
        if (appointmentSelected()){
            appointment = AppointmentTable.getSelectionModel().getSelectedItem();
            this.okClicked = true;
            this.dialogStage.close();
        }
        return;
    }
    
    public boolean edit(){
        if(appointmentSelected()){
            appointment = AppointmentTable.getSelectionModel().getSelectedItem();
            return spawnAppointmentView(true);
        }
        return false;
    }
    
    public void setMainApp(Planner mainApp) {
        this.mainApp = mainApp;
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public String convertDateToComboItem(LocalTime time){
        return DateTimeFormatter.ofPattern("HH:mm:ss").format(time);
    }
}
