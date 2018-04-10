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

import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;

import javafx.scene.control.TextField;

public class Utils {

	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
	public static boolean isInt(String str)  
	{  
	  try  
	  {  
	    int d = Integer.parseInt(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
	public static StringBuilder validFields(HashMap<String, TextField> fields){
		StringBuilder errors = new StringBuilder();
                Boolean hasErrors = false;
		if (fields.get("Name").getText() == null || fields.get("Name").getText().isEmpty()){
			errors.append("Name Field Required\n");
                        hasErrors = true;
		}
		if (fields.get("Price").getText() == null || fields.get("Price").getText().isEmpty()){
			errors.append("Price Field Required\n");
                        hasErrors = true;
		}
		if (!Utils.isNumeric(fields.get("Price").getText())){
			errors.append("Price Field Must Be Numeric\n");
                        hasErrors = true;
		}
		if (fields.get("InStock").getText() == null || fields.get("InStock").getText().isEmpty()){
			errors.append("Quantity in stock field required\n");
                        hasErrors = true;
		}
		if (!Utils.isInt(fields.get("InStock").getText())){
			errors.append("Quantity in stock must be an integer\n");
                        hasErrors = true;
		}
		if (fields.get("Min").getText() == null || fields.get("Min").getText().isEmpty()){
			errors.append("Price Field Required\n");
                        hasErrors = true;
		}
		if (!Utils.isInt(fields.get("Min").getText())){
			errors.append("Minimum quantity must be an integer\n");
                        hasErrors = true;
		}
		if (fields.get("Max").getText() == null || fields.get("Max").getText().isEmpty()){
			errors.append("Maximum quantity Field Required\n");
                        hasErrors = true;
		}
		if (!Utils.isInt(fields.get("Max").getText())){
			errors.append("Maximum quantity must be an integer\n");
                        hasErrors = true;
		}
                
                if (!hasErrors){
                    int min = Integer.parseInt(fields.get("Min").getText());
                    int max = Integer.parseInt(fields.get("Max").getText());
                    int inv = Integer.parseInt(fields.get("InStock").getText());
                    
                    if(inv < min || inv > max){
                        errors.append("Amount in stock must be greater than minimum and less than maximum stock values\n");
                    }
                    if(min > max){
                        errors.append("Minimum Stock Value must be less than maximum stock value\n");
                    }

                }
		return errors;
	}
        
        public static void searchParts(TextField searchTextField, TableView partsTable, ObservableList<Part> partsList){
            String searchText = searchTextField.getText();
            FilteredList<Part> searchPartResults = searchParts(partsList, searchText);
            SortedList<Part> sortedData = new SortedList<>(searchPartResults);
            sortedData.comparatorProperty().bind(partsTable.comparatorProperty());
            partsTable.setItems(sortedData);
        }    
        
        public static FilteredList<Part> searchParts(ObservableList<Part> parts, String s){
            return parts.filtered(p -> p.getName().toLowerCase().contains(s.toLowerCase())); 
        }
        
        public static void searchProducts(TextField searchTextField, TableView productTable, ObservableList<Product> productList){
            String searchText = searchTextField.getText();
            FilteredList<Product> searchProductResults = searchProducts(productList, searchText);
            SortedList<Product> sortedData = new SortedList<>(searchProductResults);
            sortedData.comparatorProperty().bind(productTable.comparatorProperty());
            productTable.setItems(sortedData);
        }    
        
        public static FilteredList<Product> searchProducts(ObservableList<Product> products, String s){
            return products.filtered(p -> p.getName().toLowerCase().contains(s.toLowerCase())); 
        }
        
}
