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
import plannerapp.model.User;

/**
 *
 * @author Dad
 */
public class UserDao extends AbstractJdbcDao<User> implements ICrudDao {
    private final UserMapperOpt mapperOpt = new UserMapperOpt();
    private final UserMapper mapper = new UserMapper();
    
    @Override
    public Optional<User> getById(int id){
        return super.getById(id, mapperOpt::map, "Select * from user where userId=?");
    }
    
    @Override
    public Collection<User> getAll(){
        return super.getAll(mapper::map, "SELECT * FROM user");
    }
    
    @Override
    public Collection<User> getByFields(Object entity){
      return super.getByFields(mapper::map, getSelectPrams((User)entity), "SELECT * FROM user WHERE userName=? AND active=?");
    }
    
    public Collection<User> getByUserNameAndPassword(Object entity){
        return super.getByFields(mapper::map, getUserPasswordPrams((User)entity), "SELECT * FROM user WHERE userName=? AND password=?");
    }
    
    @Override
    public boolean insert(Object entity){
        return super.insert(getInsertPrams((User)entity), "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
    }
    
    @Override
    public boolean update(Object entity){
        return super.update(getUpdatePrams((User)entity), "UPDATE user SET userName=?, password=?, active=?, lastUpdatedBy=?, lastUpdate=? WHERE userId=?");
    }
    
    @Override
    public boolean deleteById(int id){
        return super.deleteById(id, "DELETE FROM user WHERE userId=?");
    }
    
    @Override
    public int getNextId(){
        return super.getNextId("SELECT Max(userId)as maxId from user");
    }
    
    public ObservableList<User> getObservableList(){
        ObservableList<User> userList = FXCollections.observableArrayList();
        for(User a : getAll()){
            userList.add(a);
        }
        return userList;
    }
    
    public Collection<Object> getInsertPrams(User user){
        List<Object> prams = new ArrayList<>();
        prams.add(getNextId());
        prams.add(user.getUserName());
        prams.add(user.getPassword());
        if(user.isActive()){
            prams.add(1);
        }
        else{
            prams.add(2);
        }
        prams.add(user.getCreatedBy());
        prams.add(Date.valueOf(user.getCreateDate()));
        prams.add(Date.valueOf(user.getLastUpdate()));
        prams.add(user.getLastUpdateBy());
        return prams;
    }
    
    public Collection<Object> getUpdatePrams(User user){
        List<Object> prams = new ArrayList<>();
        prams.add(user.getUserName());
        prams.add(user.getPassword());
        if(user.isActive()){
            prams.add(1);
        }
        else{
            prams.add(2);
        }
        prams.add(user.getLastUpdateBy());
        prams.add(Date.valueOf(user.getLastUpdate()));
        prams.add(user.getId());
        return prams;
    }
    
     public Collection<Object> getSelectPrams(User user){
        List<Object> prams = new ArrayList<>();
        prams.add(user.getUserName());
        if(user.isActive()){
            prams.add(1);
        }
        else{
            prams.add(2);
        }
        return prams;
    }
     
    public Collection<Object> getUserPasswordPrams(User user){
        List<Object> prams = new ArrayList<>();
        prams.add(user.getUserName());
        prams.add(user.getPassword());
        return prams;
    }
    
     private static class UserMapperOpt implements IEntityMapperOpt<User>{
        @Override
        public Optional<User> map(ResultSet rs){
            Optional<User> user = null;
            try{
                user = Optional.of(new User());
                user.get().setId(rs.getInt("userId") );
                user.get().setUserName(rs.getString("userName") );
                user.get().setPassword( rs.getString("password") );
                if (rs.getInt("active") == 1){
                    user.get().setActive( true );
                }
                else{
                    user.get().setActive( false );
                }
                user.get().setCreateDate(rs.getDate("createDate").toLocalDate() );
                user.get().setCreatedBy( rs.getString("createdBy") );
                user.get().setLastUpdate( rs.getDate("lastUpdate").toLocalDate() );
                user.get().setLastUpdateBy(rs.getString("lastUpdatedBy") );
            }
            catch (SQLException e){
                PlannerLog.Log(e); 
            }
            return user;
        }
    }
     
     private static class UserMapper implements IEntityMapper<User>{
        @Override
        public User map(ResultSet rs){
            User user = null;
            try{
                user = new User();
                user.setId(rs.getInt("userId") );
                user.setUserName(rs.getString("userName") );
                user.setPassword( rs.getString("password") );
                if (rs.getInt("active") == 1){
                    user.setActive( true );
                }
                else{
                    user.setActive( false );
                }
                user.setCreateDate(rs.getDate("createDate").toLocalDate() );
                user.setCreatedBy( rs.getString("createBy") );
                user.setLastUpdate( rs.getDate("lastUpdate").toLocalDate() );
                user.setLastUpdateBy(rs.getString("lastUpdatedBy") );
            }
            catch (SQLException e){
                PlannerLog.Log(e);
            }
            return user;
        }
    }
    
}
