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
import plannerapp.model.Country;
import plannerapp.bl.CountryBo;
import plannerapp.exception.PlannerLog;
import plannerapp.validation.Conditions;
/**
 * FXML Controller class
 *
 * @author Dad
 */
public class AddCountryController implements Initializable {
    @FXML
    private TableView<Country> CountryTable;
    @FXML
    private TableColumn<Country, String> CountryColumn;
    @FXML
    private TextField SearchCountryTextField;
    @FXML
    private Label selectCountryLabel;
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
    public TableColumn<Country, String> getCountryColumn(){
        return this.CountryColumn;
    }
    public Label getSelectCountryLabel(){
        return this.selectCountryLabel;
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
    private Country country;
    private ObservableList<Country> countryList;
    private boolean okClicked = false;
    private final CountryBo countryBo = new CountryBo();
    
    public Country getCountry(){
        return this.country;
    }
    
    public TableView getCountryTable(){
        return CountryTable;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CountryColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCountry())); 
        initCountryList();
    }
    
    public void initCountryList(){
        countryList = countryBo.getObservableList();
        CountryTable.setItems(countryList);
        CountryTable.refresh();
    }

    public boolean add()
    {
        if (country == null)
            country = new Country();
        return spawnCountryView(false);
    }
    
    private boolean spawnCountryView(boolean isEditMode){
        try {
         // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Planner.class.getResource("CountryView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            CountryController controller = loader.getController();
            //controller.setMainApp(this);
            // Create the dialog Stage.
            Stage dialogStage = new Stage();

            dialogStage.setTitle(mainApp.getResourceBundle().getString("country"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            CountryController controller1 = loader.getController();
            controller1.setDialogStage(dialogStage);
            controller1.setMainApp(this.mainApp);
            if (isEditMode){
                dialogStage.addEventHandler(WindowEvent.WINDOW_SHOWING, (WindowEvent window) -> {
                    controller1.getCountryTextField().setText(country.getCountry());
                    controller1.setCountry(country);
                    controller1.setCountryList(countryList);
                    controller1.setCountryTable(CountryTable);
                    controller1.setIsEditMode(isEditMode);
                }); 
            }
            else{
                controller1.setCountry(country);
                controller1.setCountryList(countryList);
                controller1.setCountryTable(CountryTable);
                controller1.setIsEditMode(isEditMode);
            }
            controller1.getAddCountryLabel().setText(mainApp.getResourceBundle().getString("country"));
            controller1.getCountryLabel().setText(mainApp.getResourceBundle().getString("country"));
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

    public Boolean delete(){
        if(isCountrySelected()){
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle(mainApp.getResourceBundle().getString("confirm"));
            a.setContentText(mainApp.getResourceBundle().getString("confirmDelete"));
            a.showAndWait();
            if(a.getResult() == ButtonType.OK){
                Boolean success = countryBo.delete(CountryTable.getSelectionModel().getSelectedItem().getId());
                if (success){
                    initCountryList();
                }
                return success;
            }
        }
        return false;
    }
    
    public void findCountry (){
        String searchText = SearchCountryTextField.getText();
        FilteredList<Country> searchCountryResults = searchCountrys(searchText);
        SortedList<Country> sortedData = new SortedList<>(searchCountryResults);
        sortedData.comparatorProperty().bind(CountryTable.comparatorProperty());
        CountryTable.setItems(sortedData);
    }
    
    public FilteredList<Country> searchCountrys(String s){
        return countryList.filtered(p -> p.getCountry().toLowerCase().contains(s.toLowerCase()));
    }
    
    public void edit(){
        if (isCountrySelected()){
            country =  CountryTable.getSelectionModel().getSelectedItem();
            spawnCountryView(true);
        }  
    }
    
    public boolean isCountrySelected(){
        if(!Conditions.isObjectNull(CountryTable.getSelectionModel().getSelectedItem())){
            return true;
        }
        else {
            Alert a = new Alert(AlertType.WARNING);
            a.setTitle(mainApp.getResourceBundle().getString("selectionRequired"));
            a.setContentText(mainApp.getResourceBundle().getString("selectCountry"));
            a.showAndWait();
            return false;                    
        }
    }
    
    public void select(){
        if (isCountrySelected()){
            country = CountryTable.getSelectionModel().getSelectedItem();
            this.okClicked = true;
            this.dialogStage.close();
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
