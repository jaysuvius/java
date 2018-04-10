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
import plannerapp.model.Address;

/**
 *
 * @author Dad
 */
public class AddressDao  extends AbstractJdbcDao<Address> implements ICrudDao {
    private final AddressMapperOpt mapperOpt = new AddressMapperOpt();
    private final AddressMapper mapper = new AddressMapper();
    
    @Override
    public Optional<Address> getById(int id){
        return super.getById(id, mapperOpt::map, "Select * from address where addressId=?");
    }
    
    @Override
    public Collection<Address> getByFields(Object entity){
        return super.getByFields(mapper::map, getSelectPrams((Address)entity), "SELECT * FROM address WHERE address=? and address2=? and cityId=? and postalCode=?");
    }
    
    public Collection<Object> getSelectPrams(Address address){
        List<Object> prams = new ArrayList<>();
        prams.add(address.getAddress());
        prams.add(address.getAddress2());
        prams.add(address.getCityId());
        prams.add(address.getPostalCode());
        return prams;
    }
    
    @Override
    public int getNextId(){
        return super.getNextId("SELECT max(addressId) as maxId FROM address");
    }
    
    @Override
    public Collection<Address> getAll(){
        return super.getAll(mapper::map, "SELECT * FROM address");
    }
    
    
    @Override
    public boolean insert(Object entity){
        return super.insert(getInsertPrams((Address)entity), "INSERT INTO address VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }
    
    public Collection<Object> getInsertPrams(Address address){
        List<Object> prams = new ArrayList<>();
        prams.add(getNextId());
        prams.add(address.getAddress());
        prams.add(address.getAddress2());
        prams.add(address.getCityId());
        prams.add(address.getPostalCode());
        prams.add(address.getPhone());
        prams.add(Date.valueOf(address.getCreateDate()));
        prams.add(address.getCreatedBy());
        prams.add(Date.valueOf(address.getLastUpdate()));
        prams.add(address.getLastUpdateBy());
        return prams;
    }
    
    public Collection<Object> getUpdatePrams(Address address){
        List<Object> prams = new ArrayList<>();
        prams.add(address.getAddress());
        prams.add(address.getAddress2());
        prams.add(address.getCityId());
        prams.add(address.getPostalCode());
        prams.add(address.getPhone());
        prams.add(address.getLastUpdateBy());
        prams.add(Date.valueOf(address.getLastUpdate()));
        prams.add(address.getId());
        return prams;
    }
    
    @Override
    public boolean update(Object entity){
        return super.update(getUpdatePrams((Address)entity), "UPDATE address SET address=?, address2=?, cityId=?, postalCode=?, phone=?, lastUpdateBy=?, lastUpdate=? WHERE addressId=?");
    }
    
    @Override
    public boolean deleteById(int id){
        return super.deleteById(id, "DELETE FROM address WHERE addressId=?");
    }
    
    public ObservableList<Address> getObservableList(){
        ObservableList<Address> addressList = FXCollections.observableArrayList();
        for(Address a : getAll()){
            addressList.add(a);
        }
        return addressList;
    }
    
     private static class AddressMapperOpt implements IEntityMapperOpt<Address>{
        public Optional<Address> map(ResultSet rs){
            Optional<Address> address = null;
            try{
                address = Optional.of(new Address());
                address.get().setId(rs.getInt("addressId") );
                address.get().setAddress( rs.getString("address") );
                address.get().setAddress2( rs.getString("address2") );
                address.get().setCityId( rs.getInt("cityId") );
                address.get().setPostalCode( rs.getString("postalCode") );
                address.get().setPhone( rs.getString("phone") );
                address.get().setCreateDate(rs.getDate("createDate").toLocalDate() );
                address.get().setCreatedBy( rs.getString("createdBy") );
                address.get().setLastUpdate( rs.getDate("lastUpdate").toLocalDate() );
                address.get().setLastUpdateBy(rs.getString("lastUpdateBy") );
            }
            catch (SQLException e){
                PlannerLog.Log(e);
            }
            return address;
        }
    }
     
     private static class AddressMapper implements IEntityMapper<Address>{
        public Address map(ResultSet rs){
            Address address = null;
            try{
                address = new Address();
                address.setId(rs.getInt("addressId") );
                address.setAddress( rs.getString("address") );
                address.setAddress2( rs.getString("address2") );
                address.setCityId( rs.getInt("cityId") );
                address.setPostalCode( rs.getString("postalCode") );
                address.setPhone( rs.getString("phone") );
                address.setCreateDate(rs.getDate("createDate").toLocalDate() );
                address.setCreatedBy( rs.getString("createdBy") );
                address.setLastUpdate( rs.getDate("lastUpdate").toLocalDate() );
                address.setLastUpdateBy(rs.getString("lastUpdateBy") );
            }
            catch (SQLException e){
                PlannerLog.Log(e);
            }
            return address;
        }
    }
}
