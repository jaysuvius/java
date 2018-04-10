/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.model;

import java.beans.*;
import java.io.Serializable;

/**
 *
 * @author Dad
 */
public class Country extends PlannerEntity implements Serializable {
    private static final long serialVersionUID = -2773454969330118077L;
    
    public static final String COUNTRY_PROPERTY = "country";
    
    private String country;
    
    public Country() {
        super.setPropertySupport(new PropertyChangeSupport(this));
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String value) {
        String oldValue = country;
        country = value;
        super.getPropertySupport().firePropertyChange(COUNTRY_PROPERTY, oldValue, country);
    }

}
