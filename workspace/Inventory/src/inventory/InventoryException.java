/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory;

/**
 *
 * @author Jeremy May
 * C482
 * Western Governors University
 */
public class InventoryException extends Exception{
    
    public InventoryException(){}
    
    public InventoryException(String message){
        super(message);
    }
    
    public InventoryException(Throwable cause){
        super(cause);
    }
    
    public InventoryException(String message, Throwable cause){
        super(message, cause);
    }
    
    public InventoryException(String message, Throwable cause, boolean enableSupression, boolean writableStackTrace){
        super(message, cause, enableSupression, writableStackTrace);
    } 
}
