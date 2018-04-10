/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.bl;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import plannerapp.dal.AppointmentDao;
import plannerapp.exception.ValidationException;
import plannerapp.model.Appointment;
import plannerapp.model.Customer;
import plannerapp.validation.Conditions;
import plannerapp.view.Planner;

/**
 *
 * @author Dad
 */
public class AppointmentBo {
    private final AppointmentDao appointmentData = new AppointmentDao();
    private final CustomerBo customerBo = new CustomerBo();
    private Planner mainApp;
    
    public void setMainApp(Planner mainApp){
        this.mainApp = mainApp;
    }

    public Optional<Appointment> getById(int id){
        Optional<Appointment> appt = appointmentData.getById(id);
        appt.get().setCustomerId(appt.get().getCustomerId());
        return appt;
    }
    
    public void appointmentExists(Appointment appointment){
        if (appointmentData.getByFields(appointment).size() > 0){
            throw new ValidationException(mainApp.getResourceBundle().getString("appointmentExists"));
        }
    }
    
    public int getNewId(Appointment appointment){
        Collection<Appointment> aList = appointmentData.getByFields(appointment);
        if (aList.size() > 0){
            for(Appointment a : aList){
                return a.getId();
            }
        }
        return 0;
    }
        
    public void validateAppointment(Appointment appointment){
        boolean isValid = true;
        StringBuilder error = new StringBuilder();
        if(Conditions.isEmpty(appointment.getTitle())){
            error.append(mainApp.getResourceBundle().getString("titleReqired") +"\n");
            isValid = false;
        }
        if(Objects.isNull(appointment.getDescription())){
            error.append(mainApp.getResourceBundle().getString("addressRequired") +"\n");
            isValid = false;
        }
        if (Conditions.isEmpty(appointment.getLocation())){
            error.append(mainApp.getResourceBundle().getString("locationRequired") +"\n");
            isValid = false;
        }
        if (Conditions.isEmpty(appointment.getContact())){
            error.append(mainApp.getResourceBundle().getString("contactRequired") +"\n");
            isValid = false;
        }
        if (!isValid){
            throw new ValidationException(error.toString());
        }
    }
    
    public void insert(Appointment appointment){
        try{
            validateAppointment(appointment);
            appointmentExists(appointment);
            appointmentData.insert(appointment);   
        }
        catch (ValidationException ex){
            throw ex;
        }
    }
    
     public void update(Appointment appointment){
        try{
            validateAppointment(appointment);
            appointmentData.update(appointment);   
        }
        catch (ValidationException ex){
            throw ex;
        }
    }
     
    public ObservableList<Appointment> getObservableList(){
        ObservableList<Appointment> aList = appointmentData.getObservableList();
        for (Appointment a : aList){
            a.setCustomer(getCustomer(a.getCustomerId()));
        }
        return aList;
    }
    
       
    public Collection<Appointment> getByDateRange(ZonedDateTime start, ZonedDateTime end){
        Collection<Appointment> apts = appointmentData.getByDateRange(start, end);
        for (Appointment a : apts){

        Appointment apt = (Appointment)a;
        apt.setCustomer(getCustomer(apt.getCustomerId()));

        }
        return apts;
    }
    
    public Optional<Customer> getCustomer(int id){
        return customerBo.getById(id);
    }
    
    public boolean delete(int id){

        return appointmentData.deleteById(id);
    }
}
