/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.view;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import plannerapp.bl.CountryBo;
import plannerapp.exception.ValidationException;
import plannerapp.model.Country;

/**
 * FXML Controller class
 *
 * @author Dad
 */
public class CountryController implements Initializable {

    @FXML
    private TextField CountryTextField;
    @FXML
    private Label addCountryLabel;
    @FXML
    private Label countryLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    public Label getAddCountryLabel(){
        return this.addCountryLabel;
    }
    public Label getCountryLabel(){
        return this.countryLabel;
    }
    public Button getSaveButton(){
        return this.saveButton;
    }
    public Button getCancelButton(){
        return this.cancelButton;
    }

    private Planner mainApp;
    private Stage dialogStage;
    private Country country;
    private ObservableList<Country> countryList;
    private final boolean okClicked = false;
    private boolean isEditMode = false;
    private TableView<Country> CountryTable;
    private final CountryBo countryBo = new CountryBo();

    public void setMainApp(Planner mainApp) {
        this.mainApp = mainApp;
    }

    public void setCountryTable(TableView<Country> value) {
        this.CountryTable = value;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setIsEditMode(boolean value) {
        this.isEditMode = value;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country value) {
        this.country = value;
    }

    public ObservableList<Country> getCountryList() {
        return this.countryList;
    }

    public void setCountryList(ObservableList<Country> value) {
        this.countryList = value;
    }

    public TextField getCountryTextField() {
        return this.CountryTextField;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (this.country == null){
            this.country = new Country();
        }
    }

    public void save() {
        countryBo.setMainApp(this.mainApp);
        try{
            country.setCountry(CountryTextField.getText());
            country.setCreateDate(LocalDate.now());
            country.setCreatedBy(this.mainApp.getCurrentUser().getUserName());
            country.setLastUpdate(LocalDate.now());
            country.setLastUpdateBy(this.mainApp.getCurrentUser().getUserName());
            if (isEditMode) {
                countryBo.update(country);
            }
            else{
                countryBo.insert(country);
                this.countryList.add(country);
            }
            this.CountryTable.refresh();
            this.dialogStage.close();
        }
        catch (ValidationException ex){
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle(mainApp.getResourceBundle().getString("invalid"));
            a.setContentText(ex.getMessage());
            a.showAndWait();
        }
    }

    public void cancel() {
        this.dialogStage.close();
    }

}
