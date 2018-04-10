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
import plannerapp.model.Country;

/**
 *
 * @author Dad
 */
public class CountryDao extends AbstractJdbcDao<Country> implements ICrudDao{
    private final CountryMapperOpt mapperOpt = new CountryMapperOpt();
    private final CountryMapper mapper = new CountryMapper();
    
    @Override
    public Optional<Country> getById(int id){
        return super.getById(id, mapperOpt::map, "SELECT * FROM country WHERE countryId = ?");
    }
    
    @Override
    public Collection<Country> getAll(){
        return super.getAll(mapper::map, "SELECT * FROM country");
    }
    

    @Override
    public boolean insert(Object entity) {
        return super.insert(getInsertPrams((Country)entity), "INSERT INTO country VALUES (?, ?, ?, ?, ?, ?)");
    }
    
    public Collection<Object> getSelectPrams(Country country){
        List<Object> prams = new ArrayList<>();
        prams.add(country.getCountry());
        return prams;
    }
    
    public Collection<Object> getInsertPrams(Country country){
        List<Object> prams = new ArrayList<>();
        prams.add(getNextId());
        prams.add(country.getCountry());
        prams.add(Date.valueOf(country.getCreateDate()));
        prams.add(country.getCreatedBy());
        prams.add(Date.valueOf(country.getLastUpdate()));
        prams.add(country.getLastUpdateBy());
        return prams;
    }
    
    public Collection<Object> getUpdatePrams(Country country){
        List<Object> prams = new ArrayList<>();
        prams.add(country.getCountry());
        prams.add(Date.valueOf(country.getLastUpdate()));
        prams.add(country.getLastUpdateBy());
        prams.add(country.getId());
        return prams;
    }

    @Override
    public boolean update(Object entity) {
        return super.update(getUpdatePrams((Country)entity), "UPDATE country SET country=?, lastUpdate=?, lastUpdateBy=? WHERE countryId=?");
    }
    
    @Override
    public boolean deleteById(int id){
        return super.deleteById(id, "DELETE FROM country WHERE countryId=?");
    }
    
    @Override
    public Collection<Country> getByFields(Object entity) {
        return super.getByFields(mapper::map, getSelectPrams((Country)entity), "SELECT * FROM country WHERE country=?");
    }
    
    @Override
    public int getNextId(){
        return super.getNextId("SELECT max(countryId) as maxId FROM country");
    }
    
    public ObservableList<Country> getObservableList(){
        ObservableList<Country> countryList = FXCollections.observableArrayList();
        countryList.addAll(getAll());
        return countryList;
    }

    private static class CountryMapperOpt implements IEntityMapperOpt<Country> {
        @Override
        public Optional<Country> map(ResultSet rs) {
            Optional<Country> country = null;
            try {
                country = Optional.of(new Country());
                country.get().setId(rs.getInt("countryId") );
                country.get().setCountry( rs.getString("country") );
                country.get().setCreateDate(rs.getDate("createDate").toLocalDate() );
                country.get().setCreatedBy( rs.getString("createdBy") );
                country.get().setLastUpdate( rs.getDate("lastUpdate").toLocalDate() );
                country.get().setLastUpdateBy(rs.getString("lastUpdateBy") );
            } catch (SQLException e) {
                PlannerLog.Log(e);
            }
            return country; // returns null ref if error, otherwise a valid country.
        }
    }
    
    private static class CountryMapper implements IEntityMapper<Country> {
        @Override
        public Country map(ResultSet rs) {
            Country country = null;
            try {
                country = new Country();
                country.setId(rs.getInt("countryId") );
                country.setCountry( rs.getString("country") );
                country.setCreateDate(rs.getDate("createDate").toLocalDate() );
                country.setCreatedBy( rs.getString("createdBy") );
                country.setLastUpdate( rs.getDate("lastUpdate").toLocalDate() );
                country.setLastUpdateBy(rs.getString("lastUpdateBy") );
            } catch (SQLException e) {
                PlannerLog.Log(e);
            }
            return country; // returns null ref if error, otherwise a valid country.
        }
    }
}
