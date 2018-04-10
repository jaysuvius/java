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

import java.util.HashMap;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PartForm extends Stage {

	private Main m;
	private Inhouse inHousePart;
	private Outsourced outsourcedPart;
	private GridPane root;
	private Scene scene;
	private Text sceneTitle;
	private ToggleGroup tGroup;
	private RadioButton inHouseRadio;
	private RadioButton outsourcedRadio;
	private Text idLabel;
	private TextField idField;
	private Text nameLabel;
	private TextField nameField;
	private Text invLabel;
	private TextField invField;
	private Text priceLabel;
	private TextField priceField;
	private Text maxLabel;
	private TextField maxField;
	private Text minLabel;
	private TextField minField;
	private Text machineCompanyLabel;
	private TextField machineCompanyField;
	private Button saveButton;
	private Button cancelButton;
	private boolean isAdd;

	public GridPane getRoot(){
		return root;
	}
	public void setRoot(GridPane root){
		this.root = root;
	}
	public Scene getPartScene(){
		return scene;
	}
	public void setPartScene(Scene scene){
		this.scene = scene;
	}
	public Text getSceneTitle(){
		return sceneTitle;
	}
	public void setSceneTitle(Text title){
		this.sceneTitle = title;
	}
	public ToggleGroup getTGroup(){
		return tGroup;
	}
	public void setTGroup(ToggleGroup tg){
		this.tGroup = tg;
	}
	public RadioButton getInHouseRadio(){
		return inHouseRadio;
	}
	public void setInHouseRadio(RadioButton rb){
		this.inHouseRadio = rb;
	}
	public RadioButton getOutsourcedRadio(){
		return outsourcedRadio;
	}
	public void setOutsourcedRadio(RadioButton rb){
		this.outsourcedRadio = rb;
	}
	public Text getIdLabel(){
		return idLabel;
	}
	public void setIdLabel(Text idLabel){
		this.idLabel = idLabel;
	}
	public TextField getIdField(){
		return idField;
	}
	public void setIdField(TextField field){
		this.idField = field;
	}
	public Text getNameLabel(){
		return nameLabel;
	}
	public void setNameLabel(Text label){
		this.nameLabel = label;
	}
	public TextField getNameField(){
		return nameField;
	}
	public void setNameField(TextField field){
		this.nameField = field;
	}
	public Text getInvLabel(){
		return invLabel;
	}
	public void setInvLabel(Text label){
		this.invLabel = label;
	}
	public TextField getInvField(){
		return invField;
	}
	public void setInvField(TextField field){
		this.invField = field;
	}
	public Text getPriceLabel(){
		return priceLabel;
	}
	public void setPriceLabel(Text label){
		this.priceLabel = label;
	}
	public TextField getPriceField(){
		return priceField;
	}
	public void setPriceField(TextField field){
		this.priceField = field;
	}
	public Text getMaxLabel(){
		return maxLabel;
	}
	public void setMaxLabel(Text label){
		this.maxLabel = label;
	}
	public TextField getMaxField(){
		return maxField;
	}
	public void setMaxField(TextField field){
		this.maxField = field;
	}
	public Text getMinLabel(){
		return minLabel;
	}
	public void setMinLabel(Text label){
		this.minLabel = label;
	}
	public TextField getMinField(){
		return minField;
	}
	public void setMinField(TextField field){
		this.minField = field;
	}
	public Text getMachineCompanyLabel(){
		return machineCompanyLabel;
	}
	public void setMachineCompanyLabel(Text label){
		this.machineCompanyLabel = label;
	}
	public TextField getMachineCompanyField(){
		return machineCompanyField;
	}
	public void setMachineCompanyField(TextField field){
		this.machineCompanyField = field;
	}
	public Button getSaveButton(){
		return saveButton;
	}
	public void setSaveButton(Button button){
		this.saveButton = button;
	}
	public Button getCancelButton(){
		return cancelButton;
	}
	public void setCancelButton(Button button){
		this.cancelButton = button;
	}
	public Inhouse getInhousePart(){
		return inHousePart;
	}
	public void setInhousePart(Inhouse part){
		this.inHousePart = part;
	}
	public Outsourced getOutsourcedPart(){
		return outsourcedPart;
	}
	public void setOutsourcedPart(Outsourced part){
		this.outsourcedPart = part;
	}
	public ObservableList<Part> getAllParts(){
		return m.getPartsList();
	}
	public void setAllParts(ObservableList<Part> allParts){
		this.m.setPartsList(allParts);
	}
	public boolean getIsAdd(){
		return isAdd;
	}
	public void setIsAdd(boolean isAdd){
		this.isAdd = isAdd;
	}
	
	public PartForm(Main m){
		this.isAdd = true;
		this.m = m;
	}
        
        public Main getMain(){
            return m;
        }
	
	public PartForm(Main m, Part part){
		this.m = m;
		this.isAdd = false;
		if (part instanceof Inhouse){
			setInhousePart((Inhouse)part);
		}
		else{
			setOutsourcedPart((Outsourced)part);
		}
	}

	public void showAddPartForm(){
		try {
			Scene scene = setUpDefaults();
			if(!getIsAdd()){
				setupModifyPartForm();
			}
			this.setScene(scene);
			this.show();			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setupModifyPartForm(){
		Part p = null;
		if (getInhousePart() != null){
			Inhouse inhouse = getInhousePart();
			p = (Part)inhouse;
			getMachineCompanyField().setText(((Integer)inhouse.getMachineId()).toString());
			getInHouseRadio().setSelected(true);
		}
		if (getOutsourcedPart() != null){
			Outsourced outsourced = getOutsourcedPart();
			p = (Part)outsourced;
			getMachineCompanyField().setText(outsourced.getCompanyName());
			getOutsourcedRadio().setSelected(true);
		}
		if (p != null){
			getIdField().setText(((Integer)p.getPartId()).toString());
			getNameField().setText(p.getName());
			getInvField().setText(((Integer)p.getInStock()).toString());
			getMinField().setText(((Integer)p.getMin()).toString());
			getMaxField().setText(((Integer)p.getMax()).toString());
			getPriceField().setText(((Double)p.getPrice()).toString());
		}
	}
	
	public Scene setUpDefaults(){
		//Setup grid pane
		GridPane root = new GridPane();
		setRoot(root);
		root.setAlignment(Pos.CENTER);
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(25,25,25,25));
		Scene scene = new Scene(root,400,300);
		setPartScene(scene);
		Text sceneTitle = getSceneTitle();
		sceneTitle = new Text("Part");
		sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
		setSceneTitle(sceneTitle);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		this.setTitle("Part");
		root.add(sceneTitle, 0, 0, 2, 1);
		
		ToggleGroup tg = new ToggleGroup();
		setTGroup(tg);
		
		RadioButton rb1 = new RadioButton();
		rb1.setText("In-House");
		rb1.setToggleGroup(tg);
		rb1.selectedProperty().addListener(new ChangeListener<Boolean>() {
			 @Override
			 public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected){
				if (isNowSelected){
					getMachineCompanyLabel().setText("Machine");

				}
				else{
					getMachineCompanyLabel().setText("Company");
				}
			 }
		});
		setInHouseRadio(rb1);
		
		RadioButton rb2 = new RadioButton();
		rb2.setText("Outsourced");
		rb2.setToggleGroup(tg);
		setOutsourcedRadio(rb2);
		
		HBox radiohbox = new HBox(20);
		radiohbox.getChildren().addAll(rb1, rb2);
		
		root.add(radiohbox, 2, 0, 2, 1);
		
		Text id = new Text("ID:");
		TextField idField = new TextField();
		idField.setDisable(true);
		root.add(id, 1, 1);
		root.add(idField, 2, 1);
		setIdLabel(id);
		setIdField(idField);
		
		Text name = new Text("Name:");
		TextField nameField = new TextField();
		root.add(name, 1, 2);
		root.add(nameField, 2, 2);
		setNameLabel(name);
		setNameField(nameField);
		
		Text inv = new Text("Inv:");
		TextField invField = new TextField();
                invField.setText("0");
		root.add(inv, 1, 3);
		root.add(invField, 2, 3);
		setInvLabel(inv);
		setInvField(invField);
		
		Text price = new Text("Price/Cost:");
		TextField priceField = new TextField();
		root.add(price, 1, 4);
		root.add(priceField, 2, 4);
		setPriceLabel(price);
		setPriceField(priceField);
		
		HBox hbox = new HBox(20);
		Text min = new Text("Min:");
		TextField minField = new TextField();
		Text max = new Text("Max:");
		TextField maxField = getMaxField();
		maxField = new TextField();
		hbox.getChildren().addAll(minField, max, maxField);
		root.add(min, 1, 5);
		root.add(hbox, 2, 5);
		setMinLabel(min);
		setMinField(minField);
		setMaxLabel(max);
		setMaxField(maxField);
		
		Text machineCompany = new Text();
		setMachineCompanyLabel(machineCompany);
		TextField machineCompanyField = new TextField();
		setMachineCompanyField(machineCompanyField);
		root.add(machineCompany, 1, 7);
		root.add(machineCompanyField, 2, 7);
		
		Button saveButton = new Button();
		saveButton.setText("Save");
		setSaveButton(saveButton);
		if (getIsAdd()){
			saveButton.setOnAction(event -> {
                            
                                try{
                                    validPart();
                                }
                                catch (InventoryException ex){
                                    Alert a = new Alert(AlertType.WARNING);
                                    a.setTitle("Validation Error");
                                    a.setContentText(ex.getMessage());
                                    a.showAndWait();
                                    return;
                                }
                                addPart();
                                m.getPartsTable().refresh();
                                this.close();
			});
		}
		else {
			saveButton.setOnAction(event -> {
				Part p;
				if (getInhousePart() != null){
					p = (Part)getInhousePart();
				}
				else{
					p = (Part)getOutsourcedPart();
				}
                                
                                try{
                                    validPart();
                                }
                                catch (InventoryException ex){
                                    Alert a = new Alert(AlertType.WARNING);
                                    a.setTitle("Validation Error");
                                    a.setContentText(ex.getMessage());
                                    a.showAndWait();
                                    return;
                                }
                                modifyPart(p);
                                m.getPartsTable().refresh();
                                this.close();

			});
		}
		
		Button cancelButton = new Button();
		cancelButton.setText("Cancel");
		cancelButton.setOnAction(event ->{
                            Alert a = new Alert(AlertType.CONFIRMATION);
                            a.setTitle("Sure?");
                            a.setContentText("Are you sure you wish to cancel?");
                            Optional <ButtonType> action = a.showAndWait();
                            if(action.get() == ButtonType.OK){
                                this.close();
                            }
		});
		setCancelButton(cancelButton);

		HBox buttonBox = new HBox(20);
		buttonBox.setPadding(new Insets(10,10,10,10));
		buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
		buttonBox.getChildren().addAll(saveButton, cancelButton);
		root.add(buttonBox, 2, 8);
		
		rb1.setSelected(true);
		
		//root.setGridLinesVisible(true);
		
		return scene;
	}
	
	public int getNextPartId(){
		int highestPartId = 1;
		for (Part p : getAllParts()){
			if (p.getPartId() > highestPartId);{
				highestPartId = p.getPartId();
			}
		}
		return highestPartId + 1;
	}
	
	public void validPart() throws InventoryException{
	
		HashMap<String, TextField> fields = new HashMap<String, TextField>();
		fields.put("Name", getNameField());
		fields.put("Price", getPriceField());
		fields.put("InStock", getInvField());
		fields.put("Min", getMinField());
		fields.put("Max", getMaxField());

		StringBuilder errors = Utils.validFields(fields);
		
		if (getInHouseRadio().isSelected()){
			if (getMachineCompanyField().getText() == null || getMachineCompanyField().getText().isEmpty()){
				errors.append("Machine field Required\n");
			}
			if (!Utils.isInt(getMachineCompanyField().getText())){
				errors.append("Machine Id must be an integer\n");
			}
		}
		else{
			if (getMachineCompanyField().getText() == null || getMachineCompanyField().getText().isEmpty()){
				errors.append("Company field Required\n");
			}
		}
                
                if (errors.toString().length() > 0){
                    throw new InventoryException(errors.toString());
                }
		
	}
	
	public void addPart(){
		int id = getNextPartId();
		String name = getNameField().getText();
		Double price = Double.parseDouble(getPriceField().getText());
		int inStock = Integer.parseInt(getInvField().getText());
		int min = Integer.parseInt(getMinField().getText());
		int max = Integer.parseInt(getMaxField().getText());
		String machineCompany = getMachineCompanyField().getText();
		if (getInHouseRadio().isSelected()){
			Inhouse part = new Inhouse(id, name, price, inStock, min, max, Integer.parseInt(machineCompany));
			getAllParts().add(part);
		}
		else{
			Outsourced part = new Outsourced(id, name, price, inStock, min, max, machineCompany);
			getAllParts().add(part);
		}
	}
	
	public void modifyPart(Part p){
		String name = getNameField().getText();
		Double price = Double.parseDouble(getPriceField().getText());
		int inStock = Integer.parseInt(getInvField().getText());
		int min = Integer.parseInt(getMinField().getText());
		int max = Integer.parseInt(getMaxField().getText());
		String machineCompany = getMachineCompanyField().getText();
		p.setName(name);
		p.setPrice(price);
		p.setInStock(inStock);
		p.setMin(min);
		p.setMax(max);
		if (getInHouseRadio().isSelected()){
                    if(p instanceof Inhouse){
                        Inhouse part = (Inhouse)p;
			part.setMachineId(Integer.parseInt(machineCompany));
                    }
                    else
                    {
                        Inhouse part = new Inhouse(p.getPartId(), p.getName(), p.getPrice(), p.getInStock(), p.getMin(), p.getMax(), Integer.parseInt(machineCompany));
                        setOutsourcedPart(null);
                        setInhousePart(part);
                        if (getMain().getPartsList().contains(p)){
                            getMain().getPartsList().set(getMain().getPartsList().indexOf(p), part);
                        }
                        p = part;
                        getMain().getInventory().updatePart(p.getPartId(), p);
                    }

		}
		else{
                    if (p instanceof Outsourced){
                        Outsourced part = (Outsourced)p;
			part.setCompanyName(machineCompany);
                    }
                    else{
                        Outsourced part = new Outsourced(p.getPartId(), p.getName(), p.getPrice(), p.getInStock(), p.getMin(), p.getMax(), machineCompany);
                        setOutsourcedPart(part);
                        setInhousePart(null);
                        if (getMain().getPartsList().contains(p)){
                            getMain().getPartsList().set(getMain().getPartsList().indexOf(p), part);
                        }
                        p = part;
                        getMain().getInventory().updatePart(p.getPartId(), p);
                    }

		}
	}
}
