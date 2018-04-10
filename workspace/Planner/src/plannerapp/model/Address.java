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
public class Address extends PlannerEntity implements Serializable {
    private static final long serialVersionUID = -4371487239551825609L;
    public static final String ADDRESS_PROPERTY = "address";
    public static final String ADDRESS2_PROPERTY = "address2";
    public static final String CITY_ID_PROPERTY = "cityId";
    public static final String POSTAL_CODE_PROPERTY = "postalCode";
    public static final String PHONE_PROPERTY = "phone";
    public static final String CITY_PROPERTY = "city";

    private String address;
    private String address2;
    private int cityId;
    private String postalCode;
    private String phone;
    private Optional<City> city;
    
    public Address() {
        super.setPropertySupport(new PropertyChangeSupport(this));
        this.city = Optional.of(new City());
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String value) {
        String oldValue = address;
        address = value;
        super.getPropertySupport().firePropertyChange(ADDRESS_PROPERTY, oldValue, address);
    }
    
    public String getAddress2() {
        return address2;
    }
    
    public void setAddress2(String value) {
        String oldValue = address2;
        address2 = value;
        super.getPropertySupport().firePropertyChange(ADDRESS2_PROPERTY, oldValue, address2);
    }
    
    public int getCityId() {
        return cityId;
    }
    
    public void setCityId(int value) {
        int oldValue = cityId;
        cityId = value;
        super.getPropertySupport().firePropertyChange(CITY_ID_PROPERTY, oldValue, cityId);
    }    
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String value) {
        String oldValue = postalCode;
        postalCode = value;
        super.getPropertySupport().firePropertyChange(POSTAL_CODE_PROPERTY, oldValue, postalCode);
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String value) {
        String oldValue = phone;
        phone = value;
        super.getPropertySupport().firePropertyChange(PHONE_PROPERTY, oldValue, phone);
    }
    
    public Optional<City> getCity(){
        return this.city;
    }
    
    public void setCity(Optional<City> value) {
        Optional<City> oldValue = city;
        city = value;
        super.getPropertySupport().firePropertyChange(CITY_PROPERTY, oldValue, city);
    }

}
