/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.model;

import java.beans.*;
import java.io.Serializable;
import java.util.Optional;

/**
 *
 * @author Dad
 */
public class Customer extends PlannerEntity implements Serializable {
    
    public static final String CUSTOMER_NAME_PROPERTY = "customerName";
    public static final String ADDRESS_ID_PROPERTY = "addressId";
    public static final String ACTIVE_PROPERTY = "isActive";
    public static final String ADDRESS_PROPERTY = "address";
    private static final long serialVersionUID = 9063574247560000224L;

    private String customerName = "";
    private int addressId = 0;
    private boolean isActive = false;
    private Optional<Address> address = null;
    
    public Customer() {
        super.setPropertySupport(new PropertyChangeSupport(this));
        this.address = Optional.of(new Address());
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String value) {
        String oldValue = customerName;
        customerName = value;
        super.getPropertySupport().firePropertyChange(CUSTOMER_NAME_PROPERTY, oldValue, customerName);
    }
    
    public int getAddressId() {
        return addressId;
    }
    
    public void setAddressId(int value) {
        int oldValue = addressId;
        addressId = value;
        super.getPropertySupport().firePropertyChange(ADDRESS_ID_PROPERTY, oldValue, addressId);
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean value) {
        boolean oldValue = isActive;
        isActive = value;
        super.getPropertySupport().firePropertyChange(ACTIVE_PROPERTY, oldValue, isActive);
    }
    
    public Optional<Address> getAddress(){
        return this.address;
    }
    
    public void setAddress(Optional<Address> value){
        Optional<Address> oldValue = address;
        address = value;
        super.getPropertySupport().firePropertyChange(ADDRESS_PROPERTY, oldValue, address);
    }
    
    public String isActiveString(){
        if(isActive){
            return"true";
        }
        else{
            return "false";
        }
    }
    
}
