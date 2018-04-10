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
public class User extends PlannerEntity implements Serializable {

    private static final long serialVersionUID = 3884870167833546700L;
    
    public static final String USER_NAME_PROPERTY = "userName";
    public static final String PASSWORD_PROPERTY = "password";
    public static final String ACTIVE_PROPERTY = "active";

    private String userName;
    private String password;
    private Boolean active;

    public User() {
        super.setPropertySupport(new PropertyChangeSupport(this));
    }

    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String value) {
        String oldValue = userName;
        userName = value;
        super.getPropertySupport().firePropertyChange(USER_NAME_PROPERTY, oldValue, userName);
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String value) {
        String oldValue = password;
        password = value;
        super.getPropertySupport().firePropertyChange(PASSWORD_PROPERTY, oldValue, password);
    }
    
    public Boolean isActive() {
        return active;
    }
    
    public void setActive(Boolean value) {
        Boolean oldValue = active;
        active = value;
        super.getPropertySupport().firePropertyChange(ACTIVE_PROPERTY, oldValue, active);
    }
    
    public String isActiveString(){
        if(active){
            return"true";
        }
        else{
            return "false";
        }
    }

}
