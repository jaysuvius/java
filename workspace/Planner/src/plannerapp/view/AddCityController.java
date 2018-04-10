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
import javafx.scene.control.Alert.AlertType;
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
import plannerapp.bl.CityBo;
import plannerapp.exception.PlannerLog;
import plannerapp.model.City;
import plannerapp.validation.Conditions;

/**
 * FXML Controller class
 *
 * @author Dad
 */
public class AddCityController implements Initializable {
    @FXML
    private TableView<City> CityTable;
    @FXML
    private TableColumn<City, String> CityColumn;
    @FXML
    private TableColumn<City, String> CountryColumn;
    @FXML
    private TextField FindCityTextfield;
    @FXML
    private Label selectCityLabel;
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
    public TableColumn<City, String> getCityColumn(){
        return this.CityColumn;
    }
    public TableColumn<City, String> getCountryColumn(){
        return this.CountryColumn;
    }
    public Label getSelectCityLabel(){
        return this.selectCityLabel;
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
    private boolean okClicked = false;
    private final CityBo cityBo = new CityBo();
    private ObservableList<City> cityList;
    private City city;
    
    public City getCity(){
        return this.city;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        CityColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCity()));
        CountryColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCountry().isPresent() ? cellData.getValue().getCountry().get().getCountry().toString() : ""));
        initCityList();
    }
    
    public void initCityList(){
        cityList = cityBo.getObservableList();
        CityTable.setItems(cityList);
        CityTable.refresh();
    }  
    
    public boolean add(){
        if (city == null){
            city = new City();
        }
        return spawnCityView(false);
    }
    
    public boolean spawnCityView(boolean isEditMode){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Planner.class.getResource("CityView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            CityController controller = loader.getController();
            //controller.setMainApp(this);
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("City");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            CityController controller1 = loader.getController();
            controller1.setDialogStage(dialogStage);
            controller1.setMainApp(this.mainApp);
            // Show the dialog and wait until the user closes it
            if (isEditMode){
                dialogStage.addEventHandler(WindowEvent.WINDOW_SHOWING, (WindowEvent window) -> {
                    controller1.getCityTextField().setText(city.getCity());
                    controller1.getCountryTextField().setText(city.getCountry().isPresent() ? city.getCountry().get().getCountry() : "");
                    controller1.setCity(city);
                    controller1.setCityList(cityList);
                    controller1.setCityTable(CityTable);
                    controller1.setIsEditMode(isEditMode);
                }); 
            }
            else{
                controller1.setCity(city);
                controller1.setCityList(cityList);
                controller1.setCityTable(CityTable);
                controller1.setIsEditMode(isEditMode);
            }
            controller1.getAddCityLabel().setText(mainApp.getResourceBundle().getString("city"));
            controller1.getCityLabel().setText(mainApp.getResourceBundle().getString("city"));
            controller1.getCountryLabel().setText(mainApp.getResourceBundle().getString("country"));
            controller1.getSelectCountryButton().setText(mainApp.getResourceBundle().getString("selectCountry"));
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
    
    public boolean isCitySelected(){
        if(!Conditions.isObjectNull(CityTable.getSelectionModel().getSelectedItem())){
            return true;
        }
        else {
            Alert a = new Alert(AlertType.WARNING);
            a.setTitle(mainApp.getResourceBundle().getString("selectionRequired"));
            a.setContentText(mainApp.getResourceBundle().getString("selectCity"));
            a.showAndWait();
            return false;                    
        }

    }
    
    public void delete(){
        if(isCitySelected()){
            Alert a = new Alert(AlertType.CONFIRMATION);
            a.setTitle(mainApp.getResourceBundle().getString("confirm"));
            a.setContentText(mainApp.getResourceBundle().getString("confirmDelete"));
            a.showAndWait();
            if(a.getResult() == ButtonType.OK){
                   if (a.getResult() == ButtonType.OK){
                   cityBo.delete(CityTable.getSelectionModel().getSelectedItem().getId());
                   initCityList();
                }
            }
        }
    }
    
    public void findCity(){
        String searchText = FindCityTextfield.getText();
        FilteredList<City> searchCountryResults = searchCitys(searchText);
        SortedList<City> sortedData = new SortedList<>(searchCountryResults);
        sortedData.comparatorProperty().bind(CityTable.comparatorProperty());
        CityTable.setItems(sortedData);
    }
    
    public FilteredList<City> searchCitys(String s){
        return cityList.filtered(p -> p.getCity().toLowerCase().contains(s.toLowerCase()));
    }
    
    public void select(){
        if(isCitySelected()){
            city = CityTable.getSelectionModel().getSelectedItem();
            this.okClicked = true;
        }
        this.dialogStage.close();
    }
    
    public boolean edit(){
        if(isCitySelected()){
           city = CityTable.getSelectionModel().getSelectedItem();
           return spawnCityView(true);
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
