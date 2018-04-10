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
import plannerapp.model.City;

/**
 *
 * @author Dad
 */
public class CityDao extends AbstractJdbcDao<City> implements ICrudDao{
    private final CityMapperOpt mapperOpt = new CityMapperOpt();
    private final CityMapper mapper = new CityMapper();
    
    @Override
    public Optional<City> getById(int id){
        return super.getById(id, mapperOpt::map, "SELECT * FROM city WHERE cityId=?");
    }
    
    @Override
    public Collection<City> getAll(){
        return super.getAll(mapper::map, "SELECT * FROM city");
    }
    
   
    @Override
    public boolean insert(Object entity){
       return super.insert(getInsertPrams((City)entity),"INSERT INTO city VALUES (?, ?, ?, ?, ?, ?, ?)");
    }
    
    @Override
    public boolean update(Object entity){
        return super.update(getUpdatePrams((City)entity), "UPDATE city SET city=?, countryId=?, lastUpdateBy=?, lastUpdate=? WHERE cityId=?");
    }
    
    @Override
    public boolean deleteById(int id){
        return super.deleteById(id, "DELETE FROM city WHERE cityId=?");
    }
    
    @Override
    public Collection<City> getByFields(Object entity){
        return super.getByFields(mapper::map, getSelectPrams((City)entity), "Select * from city where city=?");
    }
    
    @Override
    public int getNextId(){
        return super.getNextId("SELECT max(cityId) as maxId FROM city");
    }
    
    public ObservableList<City> getObservableList(){
        ObservableList<City> CityList = FXCollections.observableArrayList();
        getAll().forEach((c) -> {
            CityList.add(c);
        });
        return CityList;
    }
    
    public Collection<Object> getSelectPrams(City city){
        List<Object> prams = new ArrayList<>();
        prams.add(city.getCity());
        return prams;
    }
        
    public Collection<Object> getInsertPrams(City city){
        List<Object> prams = new ArrayList<>();
        prams.add(getNextId());
        prams.add(city.getCity());
        prams.add(city.getCountryId());
        prams.add(Date.valueOf(city.getCreateDate()));
        prams.add(city.getCreatedBy());
        prams.add(Date.valueOf(city.getLastUpdate()));
        prams.add(city.getLastUpdateBy());
        return prams;
    }
    
    public Collection<Object> getUpdatePrams(City city){
        List<Object> prams = new ArrayList<>();
        prams.add(city.getCity());
        prams.add(city.getCountryId());
        prams.add(city.getLastUpdateBy());
        prams.add(Date.valueOf(city.getLastUpdate()));
        prams.add(city.getId());
        return prams;
    }
        
    private static class CityMapperOpt implements IEntityMapperOpt<City>{
        @Override
        public Optional<City> map(ResultSet rs){
            Optional<City> city = null;
            try{
                city = Optional.of(new City());
                city.get().setId(rs.getInt("cityId") );
                city.get().setCity(rs.getString("city") );
                city.get().setCountryId( rs.getInt("countryId") );
                city.get().setCreateDate(rs.getDate("createDate").toLocalDate() );
                city.get().setCreatedBy( rs.getString("createdBy") );
                city.get().setLastUpdate( rs.getDate("lastUpdate").toLocalDate() );
                city.get().setLastUpdateBy(rs.getString("lastUpdateBy"));
            }
            catch (SQLException e){
                PlannerLog.Log(e);
            }
            return city;
        }
    }
    
    private static class CityMapper implements IEntityMapper<City>{
        @Override
        public City map(ResultSet rs){
            City city = null;
            try{
                city = new City();
                city.setId(rs.getInt("cityId") );
                city.setCity(rs.getString("city") );
                city.setCountryId( rs.getInt("countryId") );
                city.setCreateDate(rs.getDate("createDate").toLocalDate() );
                city.setCreatedBy( rs.getString("createdBy") );
                city.setLastUpdate( rs.getDate("lastUpdate").toLocalDate() );
                city.setLastUpdateBy(rs.getString("lastUpdateBy"));
            }
            catch (SQLException e){
                PlannerLog.Log(e);
            }
            return city;
        }
    }
    
}
