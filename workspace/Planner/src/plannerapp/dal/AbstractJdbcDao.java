/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import plannerapp.exception.PlannerLog;

/**
 *
 * @author Dad
 * @param <T>
 */
public abstract class AbstractJdbcDao<T> implements ICrudDao{
    
    protected Optional<T> getById(int id, IEntityMapperOpt<T> mapper, String sql) {
        try (Connection connection = ConnectionFactory.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return mapper.map(rs);
            }
        } catch (Exception ex) {
            PlannerLog.Log(ex);
            // TODO: use proper logging / error handling.
        }
        return Optional.empty();
    }
       
    protected Collection<T> getByFields(IEntityMapper<T> mapper, Collection<Object> prams, String sql) {
        Collection<T> listOfT = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection()){
            
            PreparedStatement ps = connection.prepareStatement(sql); 
            int x = 1;

            for(Object pram : prams){
                ps.setObject(x, pram);
                x++;
            }
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                T entity = mapper.map(rs);
                listOfT.add(entity);
            }
            return listOfT;
        } catch (SQLException ex) 
        {
            PlannerLog.Log(ex);
            // TODO: use proper logging / error handling.
        }
        return listOfT;
    }
    

    protected Collection<T> getAll(IEntityMapper<T> mapper, String sql) {
        try (Connection connection = ConnectionFactory.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Set<T> results = new HashSet<>();
            while(rs.next())
            {
                results.add(mapper.map(rs));
            }
            return results;
        } catch (SQLException ex) {
            PlannerLog.Log(ex);
        }
        return Collections.emptySet();
    }

    public boolean insert(Collection<Object> prams, String sql) {
        try (Connection connection = ConnectionFactory.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
            int x = 0;
            for(Object pram : prams){
                x++;
                ps.setObject(x, pram);
            }
            ps.executeUpdate();
        } catch (SQLException ex) {
            PlannerLog.Log(ex);
        }
        return true;
    }

    public boolean update(Collection<Object> prams, String sql) {
        try (Connection connection = ConnectionFactory.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
            int x = 0;
            for(Object pram : prams){
                x++;
                ps.setObject(x, pram);
            }
            ps.executeUpdate();
        } catch (SQLException ex) {
            PlannerLog.Log(ex);
        }
        return true;
    }

    public boolean deleteById(int id, String sql) {
        try (Connection connection = ConnectionFactory.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            PlannerLog.Log(ex);
        }
        return true;
    }
    
    public int getNextId(String sql){
        try (Connection connection = ConnectionFactory.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("maxId") + 1;
            }
        } catch (SQLException ex) {
            PlannerLog.Log(ex);
        }
        return 0;
    }
    
}
