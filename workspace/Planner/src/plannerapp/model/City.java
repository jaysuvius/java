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
public class City extends PlannerEntity implements Serializable {

    private static final long serialVersionUID = 4206393592405862587L;
    
    public static final String CITY_PROPERTY = "city";
    public static final String COUNTRY_ID_PROPERTY = "countryId";
    public static final String COUNTRY_PROPERTY = "country";
    
    private String city;
    private int countryId;
    private Optional<Country> country;
    
    public City() {
        super.setPropertySupport(new PropertyChangeSupport(this));
        this.country = Optional.of(new Country());
    }

    public String getCity() {
        return city;
    }
    
    public void setCity(String value) {
        String oldValue = city;
        city = value;
        super.getPropertySupport().firePropertyChange(CITY_PROPERTY, oldValue, city);
    }
    public int getCountryId() {
        return countryId;
    }
    
    public void setCountryId(int value) {
        int oldValue = countryId;
        countryId = value;
        super.getPropertySupport().firePropertyChange(COUNTRY_ID_PROPERTY, oldValue, countryId);
    }

    public Optional<Country> getCountry() {
        return country;
    }
    
    public void setCountry(Optional<Country> value) {
        Optional<Country> oldValue = country;
        country = value;
        super.getPropertySupport().firePropertyChange(COUNTRY_PROPERTY, oldValue, country);
    }
    
    
}
