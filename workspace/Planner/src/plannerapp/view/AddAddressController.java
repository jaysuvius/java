/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.view;

import java.io.IOException;
import java.net.URL;
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
import plannerapp.model.Address;
import plannerapp.bl.AddressBo;
import plannerapp.exception.PlannerLog;
import plannerapp.validation.Conditions;
/**
 * FXML Controller class
 *
 * @author Dad
 */
public class AddAddressController implements Initializable {
    @FXML
    private TableView<Address> AddressTable;
    @FXML
    private TableColumn<Address, String> AddressColumn;
    @FXML
    private TableColumn<Address, String> Address2Column;
    @FXML
    private TableColumn<Address, String> CityColumn;
    @FXML
    private TableColumn<Address, String> PcColumn;
    @FXML
    private Label selectAddressLabel;
    @FXML
    private Label searchLabel;
    @FXML
    private Button searchButton;
    @FXML
    private Button editButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    public TableColumn<Address, String> getAddressColumn(){
        return this.AddressColumn;
    }
    public TableColumn<Address, String> getAddress2Column(){
        return this.Address2Column;
    }
    public TableColumn<Address, String> getCityColumn(){
        return this.AddressColumn;
    }
    public TableColumn<Address, String> getPcColumn(){
        return this.AddressColumn;
    }
    public Label getSelectAddressLabel(){
        return this.selectAddressLabel;
    }
    public Label getSearchLabel(){
        return this.searchLabel;
    }
    public Button getSearchButton(){
        return this.searchButton;
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
    
    @FXML
    private TextField FindAddressTextField;
    
    private Planner mainApp;
    private Stage dialogStage;
    private Address address;
    private ObservableList<Address> addressList;
    private boolean okClicked = false;
    private final AddressBo addressBo = new AddressBo();
    
    public Address getAddress(){
        return this.address;
    }
    
    public void setAddress(Address address){
        this.address = address;
    }
    
    public ObservableList<Address> getAddressList(){
        return this.addressList;
    }
    
    public void setAddressList(ObservableList<Address> addresses){
        this.addressList = addresses;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AddressColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAddress()));
        Address2Column.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAddress2()));
        CityColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCity().isPresent() ? cellData.getValue().getCity().get().getCity() : ""));
        PcColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPostalCode()));
        initAddressList();
    }
    
    public void initAddressList(){
        addressList = addressBo.getObservableList();
        AddressTable.setItems(addressList);
        AddressTable.refresh();
    }

    public boolean add(){
       if(address == null){
           address = new Address();
       }
       return spawnAddressView(false);
    }
    
    public boolean spawnAddressView(boolean isEditMode){
        try {
         // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Planner.class.getResource("AddressView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            AddressController controller = loader.getController();
            //controller.setMainApp(this);
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(mainApp.getResourceBundle().getString("addCustomer"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            AddressController controller1 = loader.getController();
            controller1.setDialogStage(dialogStage);
            //controller.setAnimal(animal);
            controller1.setMainApp(this.mainApp);
            if (isEditMode){
                dialogStage.addEventHandler(WindowEvent.WINDOW_SHOWING, (WindowEvent window) -> {
                    controller1.getAddressTextField().setText(address.getAddress());
                    controller1.getAddress2TextField().setText(address.getAddress2());
                    controller1.getCityTextField().setText(address.getCity().get().getCity());
                    controller1.getPostalCodeTextField().setText(address.getPostalCode());
                    controller1.getPhoneTextField().setText(address.getPhone());
                    controller1.setAddress(address);
                    controller1.setAddressList(addressList);
                    controller1.setAddressTable(AddressTable);
                    controller1.setIsEditMode(isEditMode);
                }); 
            }
            else{
                controller1.setAddress(address);
                controller1.setAddressList(addressList);
                controller1.setAddressTable(AddressTable);
                controller1.setIsEditMode(isEditMode);
            }
            controller1.getAddAddressLabel().setText(mainApp.getResourceBundle().getString("address"));
            controller1.getAddressLabel().setText(mainApp.getResourceBundle().getString("address"));
            controller1.getAddress2Label().setText(mainApp.getResourceBundle().getString("address2"));
            controller1.getCityLabel().setText(mainApp.getResourceBundle().getString("city"));
            controller1.getPostalCodeLabel().setText(mainApp.getResourceBundle().getString("postalCode"));
            controller1.getPhoneLabel().setText(mainApp.getResourceBundle().getString("phone"));
            controller1.getSelectButton().setText(mainApp.getResourceBundle().getString("select"));
            controller1.getSaveButton().setText(mainApp.getResourceBundle().getString("save"));
            controller1.getCancelButton().setText(mainApp.getResourceBundle().getString("cancel"));
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            
            return controller.isOkClicked();
        }
        catch (IOException e) {
            PlannerLog.Log(e);
            return false;
        }
    }
    
    public boolean addressSelected(){
        if(Conditions.isObjectNull(AddressTable.getSelectionModel().getSelectedItem())){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle(mainApp.getResourceBundle().getString("selectionRequired"));
            a.setContentText(mainApp.getResourceBundle().getString("selectAddress"));
            a.showAndWait();
            return false;
        }
        return true;
    }

    public boolean delete(){
        if(addressSelected()){
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle(mainApp.getResourceBundle().getString("confirm"));
            a.setContentText(mainApp.getResourceBundle().getString("confirmDelete"));
            a.showAndWait();
            if(a.getResult() == ButtonType.OK){
                Boolean success = addressBo.delete(AddressTable.getSelectionModel().getSelectedItem().getId());
                if (success){
                    initAddressList();
                }
                return success;
            }
        }
        return false;
    }
    
    public void findAddress(){
        String searchText = FindAddressTextField.getText();
        FilteredList<Address> searchCountryResults = searchAddresses(searchText);
        SortedList<Address> sortedData = new SortedList<>(searchCountryResults);
        sortedData.comparatorProperty().bind(AddressTable.comparatorProperty());
        AddressTable.setItems(sortedData);
    }
    
    public FilteredList<Address> searchAddresses(String s){
        return addressList.filtered(p -> p.getAddress().toLowerCase().contains(s.toLowerCase()));
    }
    
    public void select(){
        if (addressSelected()){
            address = AddressTable.getSelectionModel().getSelectedItem();
            this.okClicked = true;
            this.dialogStage.close();
        }
        return;
    }
    
    public boolean edit(){
        if(addressSelected()){
            address = AddressTable.getSelectionModel().getSelectedItem();
            return spawnAddressView(true);
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
}
