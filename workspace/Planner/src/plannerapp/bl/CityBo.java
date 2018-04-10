/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.bl;
import java.util.Collection;
import java.util.Optional;
import javafx.collections.ObservableList;
import plannerapp.dal.CityDao;
import plannerapp.dal.CountryDao;
import plannerapp.exception.ValidationException;
import plannerapp.model.City;
import plannerapp.model.Country;
import plannerapp.validation.Conditions;
import plannerapp.view.Planner;
/**
 *
 * @author Dad
 */
public class CityBo {
    private CityDao cityData = new CityDao();
    private CountryDao countryData = new CountryDao();
    private Planner mainApp;
    
    public void setMainApp(Planner mainApp){
        this.mainApp = mainApp;
    }
    
    public Optional<City> getById(int id){
        return cityData.getById(id);
    }
    
    public void cityExists(City city){
        if (cityData.getByFields(city).size() > 0){
            throw new ValidationException(mainApp.getResourceBundle().getString("cityExists"));
        } 
    }
    
    public int getNewId(City appointment){
        Collection<City> aList = cityData.getByFields(appointment);
        if (aList.size() > 0){
            for(City a : aList){
                return a.getId();
            }
        }
        return 0;
    }
    
    public void validateCity(City city){
        boolean isValid = true;
        StringBuilder error = new StringBuilder();
        if(Conditions.isEmpty(city.getCity())){
            error.append(mainApp.getResourceBundle().getString("cityNameRequired") +"\n");
            isValid = false;
        }
        if(!city.getCountry().isPresent() || city.getCountry().get().getId() == 0){
            error.append(mainApp.getResourceBundle().getString("countryRequired") +"\n");
            isValid = false;
        }
        if (!isValid){
            throw new ValidationException(error.toString());
        }
    }
    
     public void insert(City city){
        try{
            validateCity(city);
            cityExists(city);
            cityData.insert(city);   
        }
        catch (ValidationException ex){
            throw ex;
        }
    }
    
     public void update(City city){
        try{
            validateCity(city);
            cityData.update(city);   
        }
        catch (ValidationException ex){
            throw ex;
        }
    }
     
    public ObservableList<City> getObservableList(){
        ObservableList<City> cList = cityData.getObservableList();
        for (City c : cList){
            c.setCountry(getCountry(c.getCountryId()));
        }
        return cList;
    }
    
    public Optional<Country> getCountry(int id){
        return countryData.getById(id);
    }
    
    public boolean delete(int id){
        return cityData.deleteById(id);

    }
    
}
