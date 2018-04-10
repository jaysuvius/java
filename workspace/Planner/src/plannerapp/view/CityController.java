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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import plannerapp.bl.CityBo;
import plannerapp.exception.PlannerLog;
import plannerapp.exception.ValidationException;
import plannerapp.model.City;

/**
 * FXML Controller class
 *
 * @author Dad
 */
public class CityController implements Initializable {
    
    @FXML
    private TextField CityTextField;
    @FXML
    private TextField CountryTextField;
    @FXML
    private Label addCityLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label countryLabel;
    @FXML
    private Button selectCountryButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    public Label getAddCityLabel(){
        return this.addCityLabel;
    }
    public Label getCityLabel(){
        return this.cityLabel;
    }
    public Label getCountryLabel(){
        return this.countryLabel;
    }
    public Button getSelectCountryButton(){
        return this.selectCountryButton;
    }
    public Button getSaveButton(){
        return this.saveButton;
    }
    public Button getCancelButton(){
        return this.cancelButton;
    }
    private Planner mainApp;
    private Stage dialogStage;
    private City city;
    private ObservableList<City> cityList;
    private final boolean okClicked = false;
    private final CityBo cityBo = new CityBo();
    private TableView cityTable;
    private boolean isEditMode = false;
    
    public void setCityList(ObservableList<City> cityList){
        this.cityList = cityList;
    }
    
    public void setCityTable(TableView cityTable){
        this.cityTable = cityTable;
    }
    
    public TextField getCityTextField(){
        return CityTextField;
    }
    
    public TextField getCountryTextField(){
        return CountryTextField;
    }
    
    public void setIsEditMode(boolean isEdit){
        this.isEditMode = isEdit;
    }
    
    public City getCity(){
        return city;
    }
    
    public void setCity(City value){
        this.city = value;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.CountryTextField.setDisable(true);
        if (city == null){
            city = new City();
        }
    }    
    
    public boolean pickCountry(){
        try {
         // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Planner.class.getResource("AddCountryView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            AddCountryController controller = loader.getController();
            //controller.setMainApp(this);
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(mainApp.getResourceBundle().getString("addCountry"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            AddCountryController controller1 = loader.getController();
            controller1.setDialogStage(dialogStage);
            //controller.setAnimal(animal);
            controller1.setMainApp(this.mainApp);
            controller1.getCountryColumn().setText(mainApp.getResourceBundle().getString("country"));
            controller1.getSelectCountryLabel().setText(mainApp.getResourceBundle().getString("selectCountry"));
            controller1.getSearchLabel().setText(mainApp.getResourceBundle().getString("search"));
            controller1.getSelectButton().setText(mainApp.getResourceBundle().getString("select"));
            controller1.getEditButton().setText(mainApp.getResourceBundle().getString("edit"));
            controller1.getAddButton().setText(mainApp.getResourceBundle().getString("add"));
            controller1.getDeleteButton().setText(mainApp.getResourceBundle().getString("delete"));

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            if(controller1.isOkClicked()){
                this.city.setCountry(Optional.of(controller1.getCountry()));
                this.city.setCountryId(controller1.getCountry().getId());
                this.CountryTextField.setText(controller1.getCountry().getCountry());    
            }
            return controller.isOkClicked();
        }
        catch (IOException e) {
            PlannerLog.Log(e);
            return false;
        }
    }
    
    public void save(){
        cityBo.setMainApp(this.mainApp);
        try{
            city.setCity(CityTextField.getText());
            city.setCountry(city.getCountry());
            city.setCountryId(city.getCountry().isPresent() ? city.getCountry().get().getId() : 0);
            city.setCreateDate(LocalDate.now());
            city.setCreatedBy(this.mainApp.getCurrentUser().getUserName());
            city.setLastUpdate(LocalDate.now());
            city.setLastUpdateBy(this.mainApp.getCurrentUser().getUserName());
            if (isEditMode) {
                cityBo.update(city);
            }
            else{
                cityBo.insert(city);
                city.setId(cityBo.getNewId(city));
                cityList.add(city);
            }
            this.cityTable.refresh();
            this.dialogStage.close();
        }
        catch (ValidationException vex){
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle(mainApp.getResourceBundle().getString("invalid"));
            a.setContentText(vex.getMessage());
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
