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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import plannerapp.model.Address;
import plannerapp.bl.AddressBo;
import plannerapp.exception.PlannerLog;
/**
 * FXML Controller class
 *
 * @author Dad
 */
public class AddressController implements Initializable {
    @FXML
    TextField AddressTextField;
    @FXML
    TextField Address2TextField;
    @FXML
    TextField CityTextField;
    @FXML
    TextField PhoneTextField;
    @FXML
    TextField PostalCodeTextField;
    @FXML
    Label addAddressLabel;
    @FXML
    Label addressLabel;
    @FXML
    Label address2Label;
    @FXML
    Label cityLabel;
    @FXML
    Label postalCodeLabel;
    @FXML
    Label phoneLabel;
    @FXML
    Button selectButton;
    @FXML
    Button saveButton;
    @FXML
    Button cancelButton;
    public Label getAddAddressLabel(){
        return this.addAddressLabel;
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
    public Button getSelectButton(){
        return this.selectButton;
    }
    public Button getSaveButton(){
        return this.saveButton;
    }
    public Button getCancelButton(){
        return this.cancelButton;
    }
    
    private Planner mainApp;
    private Stage dialogStage;
    private Address address;
    private ObservableList<Address> addressList;
    private boolean okClicked = false;
    private TableView<Address> addressTable;
    private boolean isEditMode = false;
    private AddressBo addressBo = new AddressBo();
    
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
    
    public void setAddress(Address address){
        this.address = address;
    }
    
    public void setAddressList(ObservableList<Address> addresses){
        this.addressList = addresses;
    }
    
    public void setAddressTable(TableView<Address> addressTable){
        this.addressTable = addressTable;
    }
    
    public void setIsEditMode(boolean isEdit){
        this.isEditMode = isEdit;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CityTextField.setDisable(true);

    }

    public void save(){
        try{
            addressBo.setMainApp(this.mainApp);
            address.setAddress(AddressTextField.getText());
            address.setAddress2(Address2TextField.getText());
            address.setPostalCode(PostalCodeTextField.getText());
            address.setPhone(PhoneTextField.getText());
            address.setCityId(address.getCityId());
            address.setCity(address.getCity());
            address.setCreateDate(LocalDate.now());
            address.setCreatedBy(this.mainApp.getCurrentUser().getUserName());
            address.setLastUpdate(LocalDate.now());
            address.setLastUpdateBy(this.mainApp.getCurrentUser().getUserName());
            if (isEditMode) {
                addressBo.update(address);
            }
            else{
                addressBo.insert(address);
                address.setId(addressBo.getNewId(address));
                addressList.add(address);
            }
            addressTable.setItems(addressList);
            addressTable.refresh();
        this.dialogStage.close();
        }
        catch(Exception ex){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle(mainApp.getResourceBundle().getString("invalid"));
            a.setContentText(ex.getMessage());
            a.showAndWait();
        }       
    }   
    
  
    public void cancel(){
        this.dialogStage.close();
    }
    
    public boolean pickCity(){
        try {
         // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Planner.class.getResource("AddCityView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            AddCityController controller = loader.getController();
            //controller.setMainApp(this);
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(mainApp.getResourceBundle().getString("addCity"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            AddCityController controller1 = loader.getController();
            controller1.setDialogStage(dialogStage);
            controller1.setMainApp(this.mainApp);
            controller1.getSearchLabel().setText(mainApp.getResourceBundle().getString("search"));
            controller1.getSelectCityLabel().setText(mainApp.getResourceBundle().getString("selectCity"));
            controller1.getAddButton().setText(mainApp.getResourceBundle().getString("add"));
            controller1.getSearchButton().setText(mainApp.getResourceBundle().getString("search"));
            controller1.getSelectButton().setText(mainApp.getResourceBundle().getString("select"));
            controller1.getEditButton().setText(mainApp.getResourceBundle().getString("edit"));
            controller1.getDeleteButton().setText(mainApp.getResourceBundle().getString("delete"));
            controller1.getCityColumn().setText(mainApp.getResourceBundle().getString("city"));
            controller1.getCountryColumn().setText(mainApp.getResourceBundle().getString("country"));
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            if (controller1.isOkClicked()){
                address.setCity(Optional.of(controller1.getCity()));
                address.setCityId(controller1.getCity().getId());
                this.CityTextField.setText(controller1.getCity().getCity());
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
}
