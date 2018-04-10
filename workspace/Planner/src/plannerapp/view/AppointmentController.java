/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.view;

import chronjobs.ReminderScheduler;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import plannerapp.bl.AppointmentBo;
import plannerapp.exception.PlannerLog;
import plannerapp.exception.ValidationException;
import plannerapp.model.Appointment;
import plannerapp.model.Customer;
import plannerapp.validation.Conditions;

/**
 * FXML Controller class
 *
 * @author Dad
 */
public class AppointmentController implements Initializable {
 @FXML
    TextField CustomerTextField;
    @FXML
    TextField TitleTextField;
    @FXML
    TextField DescriptionTextField;
    @FXML
    TextField LocationTextField;
    @FXML
    TextField ContactTextField;
    @FXML
    TextField UrlTextField;
    @FXML
    DatePicker StartDatePicker;
    @FXML
    DatePicker EndDatePicker;
    @FXML
    ComboBox StartTimePicker;
    @FXML
    ComboBox EndTimePicker;
    @FXML
    Label appointmentLabel;
    @FXML
    Label customerLabel;
    @FXML
    Label titleLabel;
    @FXML
    Label descriptionLabel;
    @FXML
    Label locationLabel;
    @FXML
    Label contactLabel;
    @FXML
    Label urlLabel;
    @FXML
    Label startDateLabel;
    @FXML
    Label startTimeLabel;
    @FXML
    Label endDateLabel;
    @FXML
    Label endTimeLabel;
    @FXML
    Button selectCustomerButton;
    @FXML
    Button saveButton;
    @FXML
    Button cancelButton;
    public Label getAppointmentLabel(){
        return this.appointmentLabel;
    }
    public Label getCustomerLabel(){
        return this.customerLabel;
    }
    public Label getTitleLabel(){
        return this.titleLabel;
    }
    public Label getDescriptionLabel(){
        return this.descriptionLabel;
    }
    public Label getContactLabel(){
        return this.contactLabel;
    }
    public Label getLocationLabel(){
        return this.locationLabel;
    }
    public Label getUrlLabel(){
        return this.urlLabel;
    }
    public Label getStartDateLabel(){
        return this.startDateLabel;
    }
    public Label getStartTimeLabel(){
        return this.startTimeLabel;
    }
    public Label getEndDateLabel(){
        return this.endDateLabel;
    }
    public Label getEndTimeLabel(){
        return this.endTimeLabel;
    }
    public Button getSelectCustomerButton(){
        return this.selectCustomerButton;
    }
    public Button getSaveButton(){
        return this.saveButton;
    }
    public Button getCancelButton(){
        return this.cancelButton;
    }
    private Planner mainApp;
    private Stage dialogStage;
    private Appointment appointment;
    private ObservableList<Appointment> appointmentList;
    private boolean okClicked = false;
    private TableView<Appointment> appointmentTable;
    private boolean isEditMode = false;
    private AppointmentBo appointmentBo = new AppointmentBo();
    private Optional<Customer> customer;
    
    public TextField getCustomerTextField(){
        return this.CustomerTextField;
    }
    
    public TextField getTitleTextField(){
        return this.TitleTextField;
    }
    
    public TextField getDescriptionTextField(){
        return this.DescriptionTextField;
    }
    
    public TextField getLocationTextField(){
        return this.LocationTextField;
    }
    
    public TextField getContactTextField(){
        return this.ContactTextField;
    }
    
    public TextField getUrlTextField(){
        return this.UrlTextField;
    }
    
    public DatePicker getStartDatePicker(){
        return this.StartDatePicker;
    }
    
    public DatePicker getEndDatePicker(){
        return this.EndDatePicker;
    }
    
    public ComboBox getStartTimePicker(){
        return this.StartTimePicker;
    }
    
    public ComboBox getEndTimePicker(){
        return this.EndTimePicker;
    }
    
    public void setAppointment(Appointment appointment){
        this.appointment = appointment;
    }
    
    public void setAppointmentList(ObservableList<Appointment> appointmentes){
        this.appointmentList = appointmentes;
    }
    
    public void setAppointmentTable(TableView<Appointment> appointmentTable){
        this.appointmentTable = appointmentTable;
    }
    
    public Optional<Customer> getCustomer(){
        return this.customer;
    }
    
    public void setCustomer(Optional<Customer> customer){
        this.customer = customer;
    }
    
    public void setIsEditMode(boolean isEdit){
        this.isEditMode = isEdit;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CustomerTextField.setDisable(true);
        StartTimePicker.getItems().addAll(getTimeList());
        EndTimePicker.getItems().addAll(getTimeList());
        
    }

    public void save(){
        try{
            if (customer == null || !customer.isPresent()){
                Alert a = new Alert(AlertType.ERROR);
                a.setTitle(mainApp.getResourceBundle().getString("customerRequired"));
                a.setContentText(mainApp.getResourceBundle().getString("customerRequired"));
                a.showAndWait();
                return;
            }
            appointmentBo.setMainApp(this.mainApp);
            appointment.setCustomerId(customer.get().getId());
            appointment.setTitle(TitleTextField.getText());
            appointment.setDescription(DescriptionTextField.getText());
            appointment.setLocation(LocationTextField.getText());
            appointment.setContact(ContactTextField.getText());
            appointment.setUrl(UrlTextField.getText());
            appointment.setCreateDate(LocalDate.now());
            appointment.setCreatedBy(this.mainApp.getCurrentUser().getUserName());
            appointment.setLastUpdate(LocalDate.now());
            appointment.setLastUpdateBy(this.mainApp.getCurrentUser().getUserName());
            if(!parseDates()){
                return;
            }
            if (isEditMode) {
                appointmentBo.update(appointment);
            }
            else{
                appointmentBo.insert(appointment);
                appointmentList.add(appointment);
                appointment.setId(appointmentBo.getNewId(appointment));
            }
            scheduleReminder(appointment);
            appointmentTable.setItems(appointmentList);
            appointmentTable.refresh();
            
            this.dialogStage.close();
        }
        catch(ValidationException vex){
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle(mainApp.getResourceBundle().getString("invalid"));
            a.setContentText(vex.getMessage());
            a.showAndWait();
        }
    }
    
    public void scheduleReminder(Appointment a){
        if(a.getStart().toLocalTime().isAfter(LocalTime.now())){
            Long startTime = (a.getStart().minusMinutes(15).toInstant().toEpochMilli() - System.currentTimeMillis())/1000;
            ReminderScheduler rs = new ReminderScheduler(startTime, 5, startTime + 20);
            rs.activateAlarmThenStop(a, mainApp.getResourceBundle());
        }
    }
    
    private Boolean parseDates(){
        StringBuilder sb = new StringBuilder();
        Boolean  isValid = true;
        if (StartDatePicker.getValue() == null || Conditions.isEmpty(StartDatePicker.getValue().toString())){
            sb.append(mainApp.getResourceBundle().getString("validStartDate") +" \n");
            isValid = false;
        }
        if (EndTimePicker.getValue() == null || Conditions.isEmpty(EndTimePicker.getValue().toString())){
            sb.append(mainApp.getResourceBundle().getString("validStartTime") +" \n");
            isValid = false;
        }
        if (EndDatePicker.getValue() == null || Conditions.isEmpty(EndDatePicker.getValue().toString())){
            sb.append(mainApp.getResourceBundle().getString("validEndDate") +" \n");
            isValid = false;
        }
        if (EndDatePicker.getValue() == null || Conditions.isEmpty(StartTimePicker.getValue().toString())){
            sb.append(mainApp.getResourceBundle().getString("validEndTime") +" \n");
            isValid = false;
        }
        if (!isValid){
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle(mainApp.getResourceBundle().getString("invalid"));
            a.setContentText(sb.toString());
            a.showAndWait();
            return false;
        }
        else{
            this.appointment.setStart(parseDate(StartDatePicker.getValue().toString() + "T" + StartTimePicker.getValue()));
            this.appointment.setEnd(parseDate(EndDatePicker.getValue().toString() + "T" + EndTimePicker.getValue()));
//            this.appointment.setStart(parseDate(StartDatePicker.getValue().toString() + "T" + "20:55:00"));
//            this.appointment.setEnd(parseDate(EndDatePicker.getValue().toString() + "T" + "21:30:00"));
        }
        return true;
   }
    
   private ZonedDateTime parseDate(String dateString){
       DateTimeFormatter formatter = new DateTimeFormatterBuilder()
        .parseCaseInsensitive()
        .append(ISO_LOCAL_DATE)
        .optionalStart()           // time made optional
        .appendLiteral('T')
        .append(ISO_LOCAL_TIME)
        .optionalStart()           // zone and offset made optional
        .appendOffsetId()
        .optionalStart()
        .appendLiteral('[')
        .parseCaseSensitive()
        .appendZoneRegionId()
        .appendLiteral(']')
        .optionalEnd()
        .optionalEnd()
        .optionalEnd()
        .toFormatter();

        TemporalAccessor temporalAccessor = formatter.parseBest(dateString, ZonedDateTime::from, LocalDateTime::from, LocalDate::from);
        
        if (temporalAccessor instanceof ZonedDateTime) {
            return ((ZonedDateTime) temporalAccessor);
        }
        if (temporalAccessor instanceof LocalDateTime) {
            return ((LocalDateTime) temporalAccessor).atZone(ZoneId.systemDefault());
        }
        return ((LocalDate) temporalAccessor).atStartOfDay(ZoneId.systemDefault());
   }
    
  
    public void cancel(){
        this.dialogStage.close();
    }
    
    public boolean pickCustomer(){
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
            dialogStage.setTitle(mainApp.getResourceBundle().getString("selectCustomer"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            AddCustomerController controller1 = loader.getController();
            controller1.setDialogStage(dialogStage);
            //controller.setAnimal(animal);
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
            if(controller1.isOkClicked()){
                appointment.setCustomer(Optional.of(controller1.getCustomer()));
                appointment.setCustomerId(controller1.getCustomer().getId());
                this.CustomerTextField.setText(controller1.getCustomer().getCustomerName());
                this.customer = Optional.of(controller1.getCustomer());
            }

            return controller.isOkClicked();
        }
        catch (IOException e) {
            PlannerLog.Log(e);
            return false;
        }
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
    
    public ObservableList<String> getTimeList(){
        ObservableList<String> options = 
            FXCollections.observableArrayList(
                "07:00:00",
                "07:15:00",
                "07:30:00",
                "07:45:00",
                "08:00:00",
                "08:15:00",
                "08:30:00",
                "08:45:00",
                "09:00:00",
                "09:15:00",
                "09:30:00",
                "09:45:00",
                "10:00:00",
                "10:15:00",
                "10:30:00",
                "10:45:00",
                "11:00:00",
                "12:00:00",
                "12:15:00",
                "12:30:00",
                "12:45:00",
                "13:00:00",
                "13:15:00",
                "13:30:00",
                "13:45:00",
                "14:00:00",
                "14:15:00",
                "14:30:00",
                "14:45:00",
                "15:00:00",
                "15:15:00",
                "15:30:00",
                "15:45:00",
                "16:00:00",
                "16:15:00",
                "16:30:00",
                "16:45:00",
                "16:00:00",
                "17:15:00",
                "17:30:00",
                "17:45:00"
            );
        return options;
    }
}
