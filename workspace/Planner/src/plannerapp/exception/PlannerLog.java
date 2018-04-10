/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.exception;

import java.util.logging.*;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;

/**
 *
 * @author Dad
 */
public class PlannerLog {
   private static PlannerLog instance = null;

   protected PlannerLog() {
      // Exists only to defeat instantiation.
   }
   public static PlannerLog getInstance() {
      if(instance == null) {
         instance = new PlannerLog();
      }
      return instance;
   }
   
   public static void Log(Exception ex){
        Logger l = Logger.getLogger("PlannerLog");
        FileHandler fh;
        try{
            fh= new FileHandler("PlannerLog.Log");
            l.addHandler(fh);
            l.log(Level.SEVERE, ex.getMessage());
                   
        }        
        catch (IOException ioex){
           ioex.printStackTrace();
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
   }
   
}
