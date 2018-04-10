/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.dal;

import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Dad
 */
public class ConnectionFactory {
    public static final String URL = "jdbc:mysql://52.206.157.109/U03sSn";
    public static final String USER = "U03sSn";
    public static final String PASS = "53688073125";
    
    static {
      try{
            DriverManager.registerDriver(new Driver());
      } catch (SQLException ex) {
          throw new RuntimeException("Error connecting to the database", ex);
      }
    }
    /**
     * Get a connection to database
     * @return Connection object
     */
          //TODO: CLOSE CONNECTION BLOCK
    public static Connection getConnection(){
    

      try {
          return DriverManager.getConnection(URL, USER, PASS);
      } catch (SQLException ex) {
          throw new RuntimeException("Error connecting to the database", ex);
      }
    }
    
}
