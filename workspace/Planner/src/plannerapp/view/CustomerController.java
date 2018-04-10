/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.view;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import plannerapp.bl.CustomerBo;
import plannerapp.exception.PlannerLog;
import plannerapp.exception.ValidationException;
import plannerapp.model.Address;
import plannerapp.model.Customer;

/**
 * FXML Controller class
 *
 * @author Dad
 */
public class CustomerController implements Initializable {
    @FXML
    private TextField CustomerNameTextField;
    @FXML
    private TextField AddressTextField;
    @FXML
    private TextField Address2TextField;
    @FXML
    private TextField CityTextField;
    @FXML
    private TextField PostalCodeTextField;
    @FXML
    private TextField PhoneTextField;
    @FXML
    private CheckBox ActiveCheckBox;
    @FXML
    private Label customerLabel;
    @FXML
    private Label customerNameLabel;
    @FXML
    private Label activeLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label address2Label;
    @FXML
    private Label cityLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Button selectAddressButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    public Label getCustomerLabel(){
        return this.customerLabel;
    }
    public Label getCustomerNameLabel(){
        return this.customerNameLabel;
    }
    public Label getActiveLabel(){
        return this.activeLabel;
    }
    public Label getAddressLabel(){
        return this.addressLabel;
    }
    public Label getAddress2Label(){
        return this.address2Label;
    }
    public Label getCityLabel(){
        return this.cityLabel;
    }
    public Label getPostalCodeLabel(){
        return this.postalCodeLabel;
    }
    public Label getPhoneLabel(){
        return this.phoneLabel;
    }
    public Button getSelectAddressButton(){
        return this.selectAddressButton;
    }
    public Button getSaveButton(){
        return this.saveButton;
    }
    public Button getCancelButton(){
        return this.cancelButton;
    }
    private Planner mainApp;
    private Stage dialogStage;
    private Customer customer;
    private ObservableList<Customer> customerList;
    private final boolean okClicked = false;
    private final CustomerBo customerBo = new CustomerBo();
    private TableView<Customer> customerTable;
    private boolean isEdit;
    
    public ObservableList<Customer> getCustomerList(){
        return this.customerList;
    }
    
    public void setCustomerList(ObservableList<Customer> cList){
        this.customerList = cList;
    }
    
    public TextField getCustomerNameTextField(){
        return this.CustomerNameTextField;
    }
    
    public TextField getAddressTextField(){
        return this.AddressTextField;
    }
    
    public TextField getAddress2TextField(){
        return this.Address2TextField;
    }
    
    public TextField getCityTextField(){
        return this.CityTextField;
    }
    
    public TextField getPostalCodeTextField(){
        return this.PostalCodeTextField;
    }
    
    public TextField getPhoneTextField(){
        return this.PhoneTextField;
    }
    
    public CheckBox getActiveCheckBox(){
        return this.ActiveCheckBox;
    }
     
    public void setIsEditMode(boolean isEditMode){
        this.isEdit = isEditMode;
    }
    
    public Customer getCustomer(){
        return this.customer;
    }
    
    public void setCustomerTable(TableView<Customer> customerTable){
        this.customerTable = customerTable;
    }
    
    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.AddressTextField.setDisable(true);
        this.Address2TextField.setDisable(true);
        this.CityTextField.setDisable(true);
        this.PostalCodeTextField.setDisable(true);
        this.PhoneTextField.setDisable(true);
    }
    
    @FXML
    public boolean pickAddress(){
        try {
         // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Planner.class.getResource("AddAddressView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            AddAddressController controller = loader.getController();
            //controller.setMainApp(this);
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(mainApp.getResourceBundle().getString("address"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            AddAddressController controller1 = loader.getController();
            controller1.setDialogStage(dialogStage);
            if(customer.getAddress().isPresent()){
                controller1.setAddress(customer.getAddress().get());
                this.AddressTextField.setText(controller1.getAddress().getAddress());
                this.Address2TextField.setText(controller1.getAddress().getAddress2());
                this.CityTextField.setText(controller1.getAddress().getCity().get().getCity());
                this.PostalCodeTextField.setText(controller1.getAddress().getPostalCode());
                this.PhoneTextField.setText(controller1.getAddress().getPhone());  
            }
            controller1.getSelectAddressLabel().setText(mainApp.getResourceBundle().getString("selectAddress"));
            //controller1.getSelectButton().setText(mainApp.getResourceBundle().getString("select"));
            controller1.getAddButton().setText(mainApp.getResourceBundle().getString("add"));
            controller1.getDeleteButton().setText(mainApp.getResourceBundle().getString("delete"));
            controller1.getSearchButton().setText(mainApp.getResourceBundle().getString("search"));
            controller1.getSearchLabel().setText(mainApp.getResourceBundle().getString("search"));
            controller1.getEditButton().setText(mainApp.getResourceBundle().getString("edit"));
            controller1.getAddressColumn().setText(mainApp.getResourceBundle().getString("address"));
            controller1.getAddress2Column().setText(mainApp.getResourceBundle().getString("address2"));
            controller1.getCityColumn().setText(mainApp.getResourceBundle().getString("city"));
            controller1.getPcColumn().setText(mainApp.getResourceBundle().getString("postalCode"));
            controller1.setMainApp(this.mainApp);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            if (controller1.isOkClicked()){
                customer.setAddress(Optional.of(controller1.getAddress()));
                customer.setAddressId(controller1.getAddress().getId());
                this.AddressTextField.setText(controller1.getAddress().getAddress());
                this.Address2TextField.setText(controller1.getAddress().getAddress2());
                this.CityTextField.setText(controller1.getAddress().getCity().get().getCity());
                this.PostalCodeTextField.setText(controller1.getAddress().getPostalCode());
                this.PhoneTextField.setText(controller1.getAddress().getPhone());
            }
            
            return controller.isOkClicked();
        } catch (IOException e) {
            PlannerLog.Log(e);
            return false;
        }
    }
    
    public void save(){
        try{
            customerBo.setMainApp(this.mainApp);
            customer.setCustomerName(CustomerNameTextField.getText());
            customer.setActive(ActiveCheckBox.isSelected());
            if(customer.getAddress().isPresent()){
                customer.setAddressId(customer.getAddress().get().getId());
            }
            customer.setCreatedBy(mainApp.getCurrentUser().getUserName());
            customer.setCreateDate(LocalDate.now());
            customer.setLastUpdate(LocalDate.now());
            customer.setLastUpdateBy(mainApp.getCurrentUser().getUserName());
            if (isEdit){
                customerBo.update(customer);
            }
            else{
                customerBo.insert(customer);
                customer.setId(customerBo.getNewId(customer));
                this.customerList.add(customer);
            }
            this.customerTable.refresh();
            this.dialogStage.close();
        }
        catch (ValidationException ex){
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle(mainApp.getResourceBundle().getString("invalid"));
            a.setContentText(ex.getMessage());
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
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
}
