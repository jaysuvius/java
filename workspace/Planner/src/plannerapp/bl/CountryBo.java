/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.bl;
import java.util.Collection;
import javafx.collections.ObservableList;
import plannerapp.dal.CountryDao;
import plannerapp.model.Country;
import plannerapp.exception.ValidationException;
import plannerapp.validation.Conditions;
import plannerapp.view.Planner;


/**
 *
 * @author Dad
 */
public class CountryBo {
    private CountryDao countryData = new CountryDao();
    private Planner mainApp;
    
    public void setMainApp(Planner mainApp){
        this.mainApp = mainApp;
    }
    
    public void insert(Country country){
        try{
            validateCountry(country);
            countryExists(country);
            countryData.insert(country);
        }
        catch (ValidationException ex){
            throw ex;
        }
    }
    
    public void update(Country country){
        try{
            validateCountry(country);
            countryData.update(country);
        }
        catch (ValidationException ex){
            throw ex;
        }
    }
    
    public void validateCountry(Country country){
        StringBuilder sb = new StringBuilder();
        boolean isValid = true;
        if (Conditions.isEmpty(country.getCountry())){
            sb.append(mainApp.getResourceBundle().getString("countryNameRequired"));
            isValid = false;
        }
        if (!isValid){
            throw new ValidationException(sb.toString());
        }
    }
    
    public void countryExists(Country country){
        if (countryData.getByFields(country).size() > 0){
            throw new ValidationException(mainApp.getResourceBundle().getString("countryExists"));
        }
    }
    
    public int getNewId(Country appointment){
        Collection<Country> aList = countryData.getByFields(appointment);
        if (aList.size() > 0){
            for(Country a : aList){
                return a.getId();
            }
        }
        return 0;
    }
    
    public boolean delete(int id){
            return countryData.deleteById(id);
    }
    
    public ObservableList<Country> getObservableList(){
        return countryData.getObservableList();
    }
}
