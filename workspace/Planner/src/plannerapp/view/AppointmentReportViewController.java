/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.view;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import plannerapp.bl.AppointmentBo;
import plannerapp.model.Appointment;

/**
 * FXML Controller class
 *
 * @author Dad
 */
public class AppointmentReportViewController implements Initializable {
    @FXML
    private TableView<Appointment> AppointmentReportTable;
    @FXML
    private Label AppointmentReportLabel;
    @FXML
    private TableColumn<Appointment, String> YearColumn;
    @FXML
    private TableColumn<Appointment, String> MonthColumn;
    @FXML
    private TableColumn<Appointment, String> CustomerColumn;
    @FXML
    private TableColumn<Appointment, String> TitleColumn;
    @FXML
    private TableColumn<Appointment, String> DescriptionColumn;
    @FXML
    private TableColumn<Appointment, String> ContactColumn;
    @FXML
    private TableColumn<Appointment, String> UrlColumn;
    @FXML
    private Button CloseButton;
    private Planner mainApp;
    private Stage dialogStage;
    private Appointment appointment;
    private ObservableList<Appointment> appointmentList;
    private final AppointmentBo appointmentBo = new AppointmentBo();
    
    public Label getAppointmentReportLabel(){
        return this.AppointmentReportLabel;
    }
    public TableColumn getYearColumn(){
        return this.YearColumn;
    }
    public TableColumn getMonthColumn(){
        return this.MonthColumn;
    }
    public TableColumn getCustomerColumn(){
        return this.CustomerColumn;
    }
    public TableColumn getTitleColumn(){
        return this.TitleColumn;
    }
    public TableColumn getDescriptionColumn(){
        return this.DescriptionColumn;
    }
    public TableColumn getContactColumn(){
        return this.ContactColumn;
    }
    public TableColumn getUrlColumn(){
        return this.UrlColumn;
    }
    public Button getCloseButton(){
        return this.CloseButton;
    }
    public void setMainApp(Planner mainApp){
       this.mainApp = mainApp;
    }
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        YearColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(DateTimeFormatter.ofPattern("yyyy").format(cellData.getValue().getStart().toLocalDateTime())));
        MonthColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(DateTimeFormatter.ofPattern("MMMM").format(cellData.getValue().getEnd().toLocalDateTime())));
        CustomerColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCustomer().isPresent() ? cellData.getValue().getCustomer().get().getCustomerName(): ""));
        TitleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitle()));
        DescriptionColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDescription()));
        ContactColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getContact()));
        UrlColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUrl()));
        initAppointmentList();
    }
    
    public void initAppointmentList(){
        appointmentList = appointmentBo.getObservableList();
        AppointmentReportTable.setItems(appointmentList);
        AppointmentReportTable.refresh();
    }
    public void close(){
        this.dialogStage.close();
    }
    
}
