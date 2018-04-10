/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.dal;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import plannerapp.exception.PlannerLog;
import plannerapp.model.Customer;

/**
 *
 * @author Dad
 */
//TODO: CREATE USING BLOCKS HERE
public class CustomerDao extends AbstractJdbcDao<Customer> implements ICrudDao  {
    private final CustomerMapperOpt mapperOpt = new CustomerMapperOpt();
    private final CustomerMapper mapper = new CustomerMapper();
    
    @Override
    public Optional<Customer> getById(int id){
        return super.getById(id, mapperOpt::map, "Select * from customer where customerId=?");
    }
    
    @Override
    public Collection<Customer> getByFields(Object entity){
        return super.getByFields(mapper::map, getSelectPrams((Customer)entity), "SELECT * FROM customer WHERE customerName=? AND addressId = ?");
    }
    
     public Collection<Object> getInsertPrams(Customer customer){
        List<Object> prams = new ArrayList<>();
        prams.add(getNextId());
        prams.add(customer.getCustomerName());
        prams.add(customer.getAddressId());
        prams.add(customer.isActive());
        prams.add(Date.valueOf(customer.getCreateDate()));
        prams.add(customer.getCreatedBy());
        prams.add(Date.valueOf(customer.getLastUpdate()));
        prams.add(customer.getLastUpdateBy());
        return prams;
    }
    
    public Collection<Object> getUpdatePrams(Customer customer){
        List<Object> prams = new ArrayList<>();
        prams.add(customer.getCustomerName());
        prams.add(customer.getAddressId());
        if (customer.isActive()){
            prams.add(1);
        }
        else{
            prams.add(0);
        }
        prams.add(customer.getLastUpdateBy());
        prams.add(Date.valueOf(customer.getLastUpdate()));
        prams.add(customer.getId());
        return prams;
    }
    
    public Collection<Object> getSelectPrams(Customer customer){
        List<Object> prams = new ArrayList<>();
        prams.add(customer.getCustomerName());
        prams.add(customer.getAddressId());
        return prams;
    }
    
    @Override
    public Collection<Customer> getAll(){
        return super.getAll(mapper::map, "SELECT * FROM customer");
    }
    
    @Override
    public boolean insert(Object entity){
        return super.insert(getInsertPrams((Customer)entity), "INSERT INTO customer VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
    }
    
    @Override
    public int getNextId(){
        return super.getNextId("SELECT max(customerId) as maxId FROM customer");
    }
    
    @Override
    public boolean update(Object entity){
        return super.update(getUpdatePrams((Customer)entity), "UPDATE customer SET customerName=?, addressId=?, active=?, lastUpdateBy=?, lastUpdate=? WHERE customerId=?");
    }
    
    @Override
    public boolean deleteById(int id){
        return super.deleteById(id, "DELETE FROM customer WHERE customerId=?");
    }        
        
    public ObservableList<Customer> getObservableList(){
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        for(Customer c : getAll()){
            customerList.add(c);
        }
        return customerList;
    }
    
     private static class CustomerMapperOpt implements IEntityMapperOpt<Customer>{
        public Optional<Customer> map(ResultSet rs){
            Optional<Customer> customer = null;
            try{
                customer = Optional.of(new Customer());
                customer.get().setId(rs.getInt("customerId") );
                customer.get().setCustomerName(rs.getString("customerName") );
                customer.get().setAddressId( rs.getInt("addressId") );
                if (rs.getInt("active") == 1){
                     customer.get().setActive(true);
                }
                else{
                     customer.get().setActive(false);
                }
                customer.get().setCreateDate(rs.getDate("createDate").toLocalDate() );
                customer.get().setCreatedBy( rs.getString("createdBy") );
                customer.get().setLastUpdate( rs.getDate("lastUpdate").toLocalDate() );
                customer.get().setLastUpdateBy(rs.getString("lastUpdateBy") );;
            }
            catch (SQLException e){
                PlannerLog.Log(e);
            }
            return customer;
        }
    }
     
    private static class CustomerMapper implements IEntityMapper<Customer>{
        public Customer map(ResultSet rs){
            Customer customer = null;
            try{
                customer = new Customer();
                customer.setId(rs.getInt("customerId") );
                customer.setCustomerName(rs.getString("customerName") );
                customer.setAddressId( rs.getInt("addressId") );
                if (rs.getInt("active") == 1){
                     customer.setActive(true);
                }
                else{
                     customer.setActive(false);
                }
                customer.setCreateDate(rs.getDate("createDate").toLocalDate() );
                customer.setCreatedBy( rs.getString("createdBy") );
                customer.setLastUpdate( rs.getDate("lastUpdate").toLocalDate() );
                customer.setLastUpdateBy(rs.getString("lastUpdateBy") );;
            }
            catch (SQLException e){
                PlannerLog.Log(e);
            }
            return customer;
        }
    }
}
