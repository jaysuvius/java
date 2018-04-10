/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.bl;
import java.util.Collection;
import java.util.Optional;
import javafx.collections.ObservableList;
import plannerapp.exception.ValidationException;
import plannerapp.model.Address;
import plannerapp.validation.Conditions;
import plannerapp.dal.AddressDao;
import plannerapp.model.City;
import plannerapp.view.Planner;


/**
 *
 * @author Dad
 */
public class AddressBo {
    
    private final AddressDao addressData = new AddressDao();
    private final CityBo cityBo = new CityBo();
    private Planner mainApp;
    
    public void setMainApp(Planner mainApp){
        this.mainApp = mainApp;
    }

    public Optional<Address> getById(int id){
        Optional<Address> addy = addressData.getById(id);
        if (addy.isPresent()){
            addy.get().setCity(getCity(addy.get().getCityId()));
        }
        return addy;
    }
    
    public void addressExists(Address address){
        if (addressData.getByFields(address).size() > 0){
            throw new ValidationException(mainApp.getResourceBundle().getString("addressExists"));
        } 
    }
    
    public int getNewId(Address appointment){
        Collection<Address> aList = addressData.getByFields(appointment);
        if (aList.size() > 0){
            for(Address a : aList){
                return a.getId();
            }
        }
        return 0;
    }
    
    public void validateAddress(Address address){
        boolean isValid = true;
        StringBuilder error = new StringBuilder();
        if(Conditions.isEmpty(address.getAddress())){
            error.append(mainApp.getResourceBundle().getString("addressRequired")+ "\n");
            isValid = false;
        }
        if(!address.getCity().isPresent() || address.getCity().get().getId() == 0){
            error.append(mainApp.getResourceBundle().getString("cityRequired")+"\n");
            isValid = false;
        }
        if (Conditions.isEmpty(address.getPhone())){
            error.append(mainApp.getResourceBundle().getString("phoneNumberRequired") +"\n");
            isValid = false;
        }
        if (Conditions.isEmpty(address.getPostalCode())){
            error.append(mainApp.getResourceBundle().getString("postalCodeRequired") +"\n");
            isValid = false;
        }
        if (!isValid){
            throw new ValidationException(error.toString());
        }
    }
    
    public void insert(Address address){
        try{
            validateAddress(address);
            addressExists(address);
            addressData.insert(address);   
        }
        catch (ValidationException ex){
            throw ex;
        }
    }
    
     public void update(Address address){
        try{
            validateAddress(address);
            addressData.update(address);   
        }
        catch (ValidationException ex){
            throw ex;
        }
    }
     
    public ObservableList<Address> getObservableList(){
        ObservableList<Address> aList = addressData.getObservableList();
        for (Address a : aList){
            a.setCity(getCity(a.getCityId()));
        }
        return aList;
    }
    
    public Optional<City> getCity(int id){
        return cityBo.getById(id);
    }
    
    public boolean delete(int id){
        return addressData.deleteById(id);
    }
    
}
