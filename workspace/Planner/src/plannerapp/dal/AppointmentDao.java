/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.dal;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import plannerapp.exception.PlannerLog;
import plannerapp.model.Appointment;

/**
 *
 * @author Dad
 */
public class AppointmentDao extends AbstractJdbcDao<Appointment> implements ICrudDao  {
    private final AppointmentMapperOpt mapperOpt = new AppointmentMapperOpt();
    private final AppointmentMapper mapper = new AppointmentMapper();
    
    @Override
    public Optional<Appointment> getById(int id){
        return super.getById(id, mapperOpt::map, "SELECT * FROM appointment WHERE appointmentId=?");
    }
    
    @Override
    public Collection<Appointment> getAll(){
        return super.getAll(mapper::map, "SELECT * FROM appointment");
    }
    
    @Override
    public boolean insert(Object entity){
       return super.insert(getInsertPrams((Appointment)entity), "INSERT INTO appointment VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }
    
    @Override
    public boolean update(Object entity){
        return super.update(getUpdatePrams((Appointment)entity), "UPDATE appointment SET customerId=?, title=?, description=?, location=?, contact=?, url=?, start=?, end=?, lastUpdateBy=?, lastUpdate=? WHERE appointmentId=?");
    }
    
    @Override
    public Collection<Appointment> getByFields(Object entity){
        return super.getByFields(mapper::map, getSelectPrams((Appointment)entity), "SELECT * FROM appointment WHERE customerId=? AND title=? AND description=? AND location=? AND contact=? AND url=? AND start=? AND end=?");
    }
    
    public Collection<Appointment> getByDateRange(ZonedDateTime start, ZonedDateTime end){
        List<Object> prams = new ArrayList<>();
        prams.add(Date.valueOf(start.toLocalDate()));
        prams.add(Date.valueOf(end.toLocalDate()));
        prams.add(Date.valueOf(start.toLocalDate()));
        prams.add(Date.valueOf(end.toLocalDate()));
        Collection<Appointment> apts = super.getByFields(mapper::map, prams, "SELECT * FROM appointment WHERE start>=? AND start<=? OR end>=? AND end<=?");
        List<Appointment> aList = new ArrayList<>();
        for(Appointment a : apts){
            aList.add(a);
        }
        return aList;
    }
    
    @Override
    public boolean deleteById(int id){
        return super.deleteById(id, "DELETE FROM appointment WHERE appointmentId=?");
    }
    
    @Override
    public int getNextId(){
        return super.getNextId("SELECT max(appointmentId) as maxId FROM appointment");
    }
    
    public ObservableList<Appointment> getObservableList(){
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        for(Appointment a : getAll()){
            appointmentList.add(a);
        }
        return appointmentList;
    }
    
    public Collection<Object> getInsertPrams(Appointment appointment){
        List<Object> prams = new ArrayList<>();
        prams.add(getNextId());
        prams.add(appointment.getCustomerId());
        prams.add(appointment.getTitle());
        prams.add(appointment.getDescription());
        prams.add(appointment.getLocation());
        prams.add(appointment.getContact());
        prams.add(appointment.getUrl());
        prams.add(Date.from(appointment.getStart().toInstant()));
        prams.add(Date.from(appointment.getEnd().toInstant()));
        prams.add(Date.valueOf(appointment.getCreateDate()));
        prams.add(appointment.getCreatedBy());
        prams.add(Date.valueOf(appointment.getLastUpdate()));
        prams.add(appointment.getLastUpdateBy());
        return prams;
    }
    
    public Collection<Object> getUpdatePrams(Appointment appointment){
        List<Object> prams = new ArrayList<>();
        prams.add(appointment.getCustomerId());
        prams.add(appointment.getTitle());
        prams.add(appointment.getDescription());
        prams.add(appointment.getLocation());
        prams.add(appointment.getContact());
        prams.add(appointment.getUrl());
        prams.add(Date.from(appointment.getStart().toInstant()));
        prams.add(Date.from(appointment.getEnd().toInstant()));
        prams.add(appointment.getLastUpdateBy());
        prams.add(Date.valueOf(appointment.getLastUpdate()));
        prams.add(appointment.getId());
        return prams;
    }
    
    public Collection<Object> getSelectPrams(Appointment appointment){
        List<Object> prams = new ArrayList<>();
        prams.add(appointment.getCustomerId());
        prams.add(appointment.getTitle());
        prams.add(appointment.getDescription());
        prams.add(appointment.getLocation());
        prams.add(appointment.getContact());
        prams.add(appointment.getUrl());
        prams.add(Date.from(appointment.getStart().toInstant()));
        prams.add(Date.from(appointment.getEnd().toInstant()));
        return prams;
    }

    private static class AppointmentMapperOpt implements IEntityMapperOpt<Appointment>{
        @Override
        public Optional<Appointment> map(ResultSet rs){
            Optional<Appointment> appointment = null;
            try{
                appointment = Optional.of(new Appointment());
                appointment.get().setId(rs.getInt("appointmentId") );
                appointment.get().setCustomerId( rs.getInt("customerId") );
                appointment.get().setTitle( rs.getString("title") );
                appointment.get().setDescription( rs.getString("description") );
                appointment.get().setLocation( rs.getString("location") );
                appointment.get().setContact( rs.getString("contact") );
                appointment.get().setUrl( rs.getString("url") );
                appointment.get().setStart(rs.getTimestamp("start").toInstant().atZone(ZoneId.systemDefault()) );
                appointment.get().setEnd(rs.getTimestamp("end").toInstant().atZone(ZoneId.systemDefault()) );
                appointment.get().setCreateDate(rs.getDate("createDate").toLocalDate() );
                appointment.get().setCreatedBy( rs.getString("createdBy") );
                appointment.get().setLastUpdate( rs.getDate("lastUpdate").toLocalDate() );
                appointment.get().setLastUpdateBy(rs.getString("lastUpdateBy") );
            }
            catch (SQLException e){
                 PlannerLog.Log(e);
            }
            return appointment;
        }
    }
    
     private static class AppointmentMapper implements IEntityMapper<Appointment>{
        @Override
        public Appointment map(ResultSet rs){
            Appointment appointment = null;
            try{
                appointment = new Appointment();
                appointment.setId(rs.getInt("appointmentId") );
                appointment.setCustomerId( rs.getInt("customerId") );
                appointment.setTitle( rs.getString("title") );
                appointment.setDescription( rs.getString("description") );
                appointment.setLocation( rs.getString("location") );
                appointment.setContact( rs.getString("contact") );
                appointment.setUrl( rs.getString("url") );
                appointment.setStart(rs.getTimestamp("start").toInstant().atZone(ZoneId.systemDefault()) );
                appointment.setEnd(rs.getTimestamp("end").toInstant().atZone(ZoneId.systemDefault()) );
                appointment.setCreateDate(rs.getDate("createDate").toLocalDate() );
                appointment.setCreatedBy( rs.getString("createdBy") );
                appointment.setLastUpdate( rs.getDate("lastUpdate").toLocalDate() );
                appointment.setLastUpdateBy(rs.getString("lastUpdateBy") );
            }
            catch (SQLException e){
                 PlannerLog.Log(e);   
            }
            return appointment;
        }
    }
}
