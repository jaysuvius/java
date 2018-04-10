/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Dad
 */
abstract class PlannerEntity implements Serializable {
    private static final long serialVersionUID = 162719378786589160L;
    
    
    public static final String ID_PROPERTY = "id";
    public static final String CREATE_DATE_PROPERTY = "createDate";
    public static final String CREATED_BY_PROPERTY = "createdBy";
    public static final String LAST_UPDATE_PROPERTY = "lastUpdate";
    public static final String LAST_UPDATE_BY_PROPERTY = "lastUpdateBy";
    
    private int id;
    private LocalDate createDate;
    private String createdBy;
    private LocalDate lastUpdate;
    private String lastUpdateBy;
    
    
    private PropertyChangeSupport propertySupport;
    
    public int getId() {
        return id;
    }
    
    public void setId(int value) {
        int oldValue = id;
        id = value;
        propertySupport.firePropertyChange(ID_PROPERTY, oldValue, id);
    }
    
    public LocalDate getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(LocalDate value) {
        LocalDate oldValue = createDate;
        createDate = value;
        propertySupport.firePropertyChange(CREATE_DATE_PROPERTY, oldValue, createDate);
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String value) {
        String oldValue = createdBy;
        createdBy = value;
        propertySupport.firePropertyChange(CREATED_BY_PROPERTY, oldValue, createdBy);
    }    
    // TODO: Use Instant for computations; translate from/to another java.time.* type as necessary.
    public LocalDate getLastUpdate() {
        return lastUpdate;
    }
    
    public void setLastUpdate(LocalDate value) {
        LocalDate oldValue = lastUpdate;
        lastUpdate = value;
        propertySupport.firePropertyChange(LAST_UPDATE_PROPERTY, oldValue, lastUpdate);
    }
        
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }
    
    public void setLastUpdateBy(String value) {
        String oldValue = lastUpdateBy;
        lastUpdateBy = value;
        propertySupport.firePropertyChange(LAST_UPDATE_BY_PROPERTY, oldValue, lastUpdateBy);
    }
    
    public PropertyChangeSupport getPropertySupport(){
        return propertySupport;
    }
    
    public void setPropertySupport(PropertyChangeSupport pcs){
        this.propertySupport = pcs;
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
}
