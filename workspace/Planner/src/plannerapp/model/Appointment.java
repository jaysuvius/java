/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.model;

import java.beans.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Optional;

/**
 *
 * @author Dad
 */
public class Appointment extends PlannerEntity implements Serializable {

    private static final long serialVersionUID = -6243862722517351399L;
    
    public static final String CUSTOMER_ID_PROPERTY = "customerId";
    public static final String TITLE_PROPERTY = "title";
    public static final String DESCRIPTION_PROPERTY = "description";
    public static final String LOCATION_PROPERTY = "location";
    public static final String CONTACT_PROPERTY = "contact";
    public static final String URL_PROPERTY = "url";
    public static final String START_PROPERTY = "start";
    public static final String END_PROPERTY = "end";

    private int customerId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String url;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private Optional<Customer> customer;
  
    public Appointment() {
        super.setPropertySupport(new PropertyChangeSupport(this));
        this.customer = Optional.of(new Customer());
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int value) {
        int oldValue = customerId;
        customerId = value;
        super.getPropertySupport().firePropertyChange(CUSTOMER_ID_PROPERTY, oldValue, customerId);
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String value) {
        String oldValue = title;
        title = value;
        super.getPropertySupport().firePropertyChange(TITLE_PROPERTY, oldValue, title);
    }      
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String value) {
        String oldValue = description;
        description = value;
        super.getPropertySupport().firePropertyChange(DESCRIPTION_PROPERTY, oldValue, description);
    } 
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String value) {
        String oldValue = location;
        location = value;
        super.getPropertySupport().firePropertyChange(LOCATION_PROPERTY, oldValue, location);
    } 
    
    public String getContact() {
        return contact;
    }
    
    public void setContact(String value) {
        String oldValue = contact;
        contact = value;
        super.getPropertySupport().firePropertyChange(CONTACT_PROPERTY, oldValue, contact);
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String value) {
        String oldValue = url;
        url = value;
        super.getPropertySupport().firePropertyChange(URL_PROPERTY, oldValue, url);
    }
    
    public ZonedDateTime getStart() {
        return start;
    }
    
    public void setStart(ZonedDateTime value) {
        ZonedDateTime oldValue = start;
        start = value;
        super.getPropertySupport().firePropertyChange(START_PROPERTY, oldValue, start);
    }

    public ZonedDateTime getEnd() {
        return end;
    }
    
    public void setEnd(ZonedDateTime value) {
        ZonedDateTime oldValue = end;
        end = value;
        super.getPropertySupport().firePropertyChange(END_PROPERTY, oldValue, end);
    }
    
    public void setCustomer(Optional<Customer> customer){
        this.customer = customer;
    }
    
    public Optional<Customer> getCustomer(){
        return this.customer;
    }
}
