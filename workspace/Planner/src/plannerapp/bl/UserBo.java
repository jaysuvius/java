/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.bl;

import java.util.Collection;
import javafx.collections.ObservableList;
import plannerapp.dal.UserDao;
import plannerapp.exception.ValidationException;
import plannerapp.model.User;
import plannerapp.validation.Conditions;
import plannerapp.view.Planner;

/**
 *
 * @author Dad
 */
public class UserBo {
    
    private UserDao userData = new UserDao();
    private Planner mainApp;
    
    public void setMainApp(Planner mainApp){
        this.mainApp = mainApp;
    }
    
     public void insert(User user){
        try{
            validateUser(user);
            userExists(user);
            userData.insert(user);
        }
        catch (ValidationException ex){
            throw ex;
        }
    }
    
    public void update(User user){
        try{
            validateUser(user);
            userData.update(user);
        }
        catch (ValidationException ex){
            throw ex;
        }
    }
    
    public void validateUser(User user){
        StringBuilder sb = new StringBuilder();
        boolean isValid = true;
        if (Conditions.isEmpty(user.getUserName())){
            sb.append(mainApp.getResourceBundle().getString("userNameRequired"));
            isValid = false;
        }
        if (!isValid){
            throw new ValidationException(sb.toString());
        }
    }
    
    public void userExists(User user){
        if (userData.getByFields(user).size() > 0){
            throw new ValidationException(mainApp.getResourceBundle().getString("userExists"));
        }
    }
    
    public int getNewId(User appointment){
        Collection<User> aList = userData.getByFields(appointment);
        if (aList.size() > 0){
            for(User a : aList){
                return a.getId();
            }
        }
        return 0;
    }
    
    public User getByUsernamePassword(User user){
        if (userData.getByUserNameAndPassword(user).size() > 0){
            return (User) userData.getByUserNameAndPassword(user).toArray()[0];
        }
        return new User();
    }
    
    public boolean delete(int id){
        return userData.deleteById(id);
    }
    
    public ObservableList<User> getObservableList(){
        return userData.getObservableList();
    }
}
