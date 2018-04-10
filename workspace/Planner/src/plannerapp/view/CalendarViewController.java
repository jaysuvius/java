/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.view;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;


/**
 *
 * @author Dad
 */
public class CalendarViewController implements Initializable {
    
    @FXML
    private Label label;

    @FXML 
    private GridPane calendarPane;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {



    }    
    
    	public static class LocaleCell extends ListCell<Locale> {
	    @Override
	    public void updateItem(Locale locale, boolean empty) {
	        super.updateItem(locale, empty);
	        setText(locale == null ? null : locale.getDisplayName(locale));
	    }
	}
    
}
