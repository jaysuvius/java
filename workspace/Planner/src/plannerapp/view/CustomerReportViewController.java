/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import plannerapp.bl.CustomerBo;
import plannerapp.model.Customer;

/**
 * FXML Controller class
 *
 * @author Dad
 */
public class CustomerReportViewController implements Initializable {
@FXML
    private TableView<Customer> CustomerReportTable;
    @FXML
    private TableColumn<Customer, String> NameColumn;
    @FXML
    private TableColumn<Customer, String> ActiveColumn;
    @FXML
    private TableColumn<Customer, String> AddressColumn;
    @FXML
    private TableColumn<Customer, String> Address2Column;
    @FXML
    private TableColumn<Customer, String> CityColumn;
    @FXML
    private TableColumn<Customer, String> PostalCodeColumn;
    @FXML
    private TableColumn<Customer, String> PhoneColumn;
    @FXML
    private Label CustomerReportLabel;
    @FXML
    private Button closeButton;
    private Planner mainApp;
    private Stage dialogStage;
    private ObservableList<Customer> customerList;
    private CustomerBo customerBo = new CustomerBo();
    public TableView<Customer> getCustomerTable(){
        return this.CustomerReportTable;
    }   
    public TableColumn<Customer, String> getNameColumn(){
        return this.NameColumn;
    }
    public TableColumn<Customer, String> getActiveColumn(){
        return this.ActiveColumn;
    }
    public TableColumn<Customer, String> getAddressColumn(){
        return this.AddressColumn;
    }
    public TableColumn<Customer, String> getAddress2Column(){
        return this.Address2Column;
    }
    public TableColumn<Customer, String> getCityColumn(){
        return this.CityColumn;
    }
    public TableColumn<Customer, String> getPostalCodeColumn(){
        return this.PostalCodeColumn;
    }
    public TableColumn<Customer, String> getPhoneColumn(){
        return this.PhoneColumn;
    }
    public Label getCustomerReportLabel(){
        return this.CustomerReportLabel;
    }
    public Button getCloseButton(){
        return this.closeButton;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        NameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCustomerName()));
        ActiveColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().isActiveString()));
        AddressColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAddress().isPresent() ? cellData.getValue().getAddress().get().getAddress(): ""));
        Address2Column.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAddress().isPresent() ? cellData.getValue().getAddress().get().getAddress(): ""));
        CityColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAddress().isPresent() ? cellData.getValue().getAddress().get().getCity().isPresent() ? cellData.getValue().getAddress().get().getCity().get().getCity(): "" : ""));
        PostalCodeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAddress().isPresent() ? cellData.getValue().getAddress().get().getPostalCode(): ""));
        PhoneColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAddress().isPresent() ? cellData.getValue().getAddress().get().getPhone(): ""));
        initCustomerList();
    }
    
    public void initCustomerList(){
        customerList = customerBo.getObservableList();
        CustomerReportTable.setItems(customerList);
        CustomerReportTable.refresh();
    } 
    
    public void setMainApp(Planner mainApp){
        this.mainApp = mainApp;
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void close(){
        this.dialogStage.close();
    }
    
}
