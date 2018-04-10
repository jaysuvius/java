/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.view;

import java.io.IOException;
import plannerapp.model.Customer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import plannerapp.bl.CustomerBo;
import plannerapp.exception.PlannerLog;
import plannerapp.validation.Conditions;

/**
 * FXML Controller class
 *
 * @author Dad
 */
public class AddCustomerController implements Initializable {
    @FXML
    private TableView<Customer> CustomerTable;
    @FXML
    private TableColumn<Customer, String> CustomerNameColumn;
    @FXML
    private TableColumn<Customer, String> ActiveColumn;
    @FXML
    private TextField FindCustomerNameTextField;
    @FXML
    private Label selectCustomerLabel;
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
    public TableColumn<Customer, String> getCustomerNameColumn(){
        return this.CustomerNameColumn;
    }
    public TableColumn<Customer, String> getActiveColumn(){
        return this.ActiveColumn;
    }
    public Label getSelectCustomerLabel(){
        return this.selectCustomerLabel;
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
    private boolean okClicked;
    private ObservableList<Customer> customerList;
    private CustomerBo customerBo = new CustomerBo();
    private boolean isEdit = false;
    private Customer customer;
    
    public Customer getCustomer(){
        return customer;
    }
    public void setCustomer(Customer customer){
        this.customer = customer;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CustomerNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCustomerName()));
        ActiveColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().isActiveString()));
        initCustomerList();
    }
    
    public void initCustomerList(){
        customerList = customerBo.getObservableList();
        CustomerTable.setItems(customerList);
        CustomerTable.refresh();
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean add()
    {
        if (customer == null){
            customer = new Customer();
        }
        isEdit = false;
        return spawnCustomerController();
    }
        
    public boolean spawnCustomerController(){
        try{
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Planner.class.getResource("CustomerView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            CustomerController controller = loader.getController();
            //controller.setMainApp(this);
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(mainApp.getResourceBundle().getString("addCustomer"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            CustomerController controller1 = loader.getController();
            controller1.setDialogStage(dialogStage);
            //controller.setAnimal(animal);
            controller1.setMainApp(this.mainApp);
            if (isEdit){
                dialogStage.addEventHandler(WindowEvent.WINDOW_SHOWING, (WindowEvent window) -> {
                    controller1.getCustomerNameTextField().setText(customer.getCustomerName());
                    if(customer.isActive()){
                        controller1.getActiveCheckBox().setSelected(true);
                    }
                    else{
                        controller1.getActiveCheckBox().setSelected(false);
                    }
                    if(customer.getAddress().isPresent()){
                        controller1.getAddressTextField().setText(customer.getAddress().get().getAddress());
                        controller1.getAddress2TextField().setText(customer.getAddress().get().getAddress2());
                        controller1.getCityTextField().setText(customer.getAddress().get().getCity().get().getCity());
                        controller1.getPostalCodeTextField().setText(customer.getAddress().get().getPostalCode());
                        controller1.getPhoneTextField().setText(customer.getAddress().get().getPhone());
                    }
                    controller1.setCustomer(customer);
                    controller1.setCustomerList(customerList);
                    controller1.setCustomerTable(CustomerTable);
                    controller1.setIsEditMode(isEdit);
                }); 
            }
            else{
                controller1.setCustomer(customer);
                controller1.setCustomerList(customerList);
                controller1.setCustomerTable(CustomerTable);
                controller1.setIsEditMode(isEdit);
            }
            // Show the dialog and wait until the user closes it
            controller1.setCustomerList(customerList);
            controller1.getCustomerLabel().setText(mainApp.getResourceBundle().getString("customer"));
            controller1.getCustomerNameLabel().setText(mainApp.getResourceBundle().getString("customerName"));
            controller1.getActiveLabel().setText(mainApp.getResourceBundle().getString("active"));
            controller1.getAddressLabel().setText(mainApp.getResourceBundle().getString("address"));
            controller1.getAddress2Label().setText(mainApp.getResourceBundle().getString("address2"));
            controller1.getCityLabel().setText(mainApp.getResourceBundle().getString("city"));
            controller1.getPostalCodeLabel().setText(mainApp.getResourceBundle().getString("postalCode"));
            controller1.getPhoneLabel().setText(mainApp.getResourceBundle().getString("phone"));
            controller1.getSelectAddressButton().setText(mainApp.getResourceBundle().getString("selectAddress"));
            controller1.getSaveButton().setText(mainApp.getResourceBundle().getString("save"));
            controller1.getCancelButton().setText(mainApp.getResourceBundle().getString("cancel"));
            
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }
        catch (IOException e) {
            PlannerLog.Log(e);
            return false;
        }
    }

    public void delete(){
        if(customerSelected()){
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle(mainApp.getResourceBundle().getString("confirm"));
            a.setContentText(mainApp.getResourceBundle().getString("confirmDelete"));
            a.showAndWait();
            if(a.getResult() == ButtonType.OK){
                customerBo.delete(CustomerTable.getSelectionModel().getSelectedItem().getId());
                initCustomerList();   
            }

        }
    }
    
    public void findCustomer(){
        String searchText = FindCustomerNameTextField.getText();
        FilteredList<Customer> searchCustomerResults = searchCustomers(searchText);
        SortedList<Customer> sortedData = new SortedList<>(searchCustomerResults);
        sortedData.comparatorProperty().bind(CustomerTable.comparatorProperty());
        CustomerTable.setItems(sortedData);
    }
    
    public FilteredList<Customer> searchCustomers(String s){
        return customerList.filtered(p -> p.getCustomerName().toLowerCase().contains(s.toLowerCase()));
    }
    
    public void select(){
        if(customerSelected()){
            customer = CustomerTable.getSelectionModel().getSelectedItem();
            this.okClicked = true;
            this.dialogStage.close();
        }
    }
    
    public boolean edit(){
        if(customerSelected()){
            customer = CustomerTable.getSelectionModel().getSelectedItem();
            isEdit = true;
            return spawnCustomerController();
        }
        return false;
    }
    
    public boolean isOkClicked(){
        return this.okClicked;
    }
    
    public void setMainApp(Planner mainApp){
        this.mainApp = mainApp;
    }
    
        public boolean customerSelected(){
        if(Conditions.isObjectNull(CustomerTable.getSelectionModel().getSelectedItem())){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle(mainApp.getResourceBundle().getString("selectionRequired"));
            a.setContentText(mainApp.getResourceBundle().getString("selectCustomer"));
            a.showAndWait();
            return false;
        }
        return true;
    }
}
