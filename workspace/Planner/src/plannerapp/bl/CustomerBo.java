/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.bl;

import java.util.Collection;
import java.util.Optional;
import javafx.collections.ObservableList;
import plannerapp.dal.CustomerDao;
import plannerapp.exception.ValidationException;
import plannerapp.model.Address;
import plannerapp.model.Customer;
import plannerapp.validation.Conditions;
import plannerapp.view.Planner;

/**
 *
 * @author Dad
 */
public class CustomerBo {
    private final CustomerDao customerData = new CustomerDao();
    private final AddressBo addressBo = new AddressBo();
    private Planner mainApp;
    
    public void setMainApp(Planner mainApp){
        this.mainApp = mainApp;
    }
    
    public Optional<Customer> getById(int id){
        return customerData.getById(id);
    }
    
    public Collection<Customer> getByCustomer(Optional<Customer> customer){
        return customerData.getByFields(customer);
    }
    
    public void customerExists(Customer customer){
        if(customerData.getByFields(customer).size() > 0){
            throw new ValidationException(mainApp.getResourceBundle().getString("customerExists"));
        }
    }
    
    public int getNewId(Customer appointment){
        Collection<Customer> aList = customerData.getByFields(appointment);
        if (aList.size() > 0){
            for(Customer a : aList){
                return a.getId();
            }
        }
        return 0;
    }
    
    public void validateCustomer(Customer customer){
        StringBuilder sb = new StringBuilder();
        boolean isValid = true;
        if (Conditions.isEmpty(customer.getCustomerName())){
            isValid = false;
            sb.append(mainApp.getResourceBundle().getString("customerRequired") + "\n");
        }
        if(!customer.getAddress().isPresent() || customer.getAddress().get().getId() == 0){
            isValid = false;
            sb.append(mainApp.getResourceBundle().getString("addressRequired")+"\n");
        }
        if(!isValid){
            throw new ValidationException(sb.toString());
        }
    }
    
    public void insert(Customer customer){
        try{
            validateCustomer(customer);
            customerExists(customer);
            customerData.insert(customer);
        }
        catch(ValidationException ex){
            throw ex;
        }
    }
    
    public void update(Customer customer){
        try{
            validateCustomer(customer);
            customerData.update(customer);
        }
        catch (ValidationException ex){
            throw ex;
        }
    }
    
    public ObservableList<Customer> getObservableList(){
        ObservableList<Customer> cList = customerData.getObservableList();
        for (Customer c : cList){
            c.setAddress(getAddress(c.getAddressId()));
        }
        return cList;
    }
    
    public Optional<Address> getAddress(int id){
        return addressBo.getById(id);
    }
    
    public boolean delete(int id){
        return customerData.deleteById(id);
    }
}
