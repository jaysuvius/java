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
import java.util.Optional;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public class ProductForm extends Stage {

	private Main m;
	private ObservableList<Part> associatedParts;
	private Product product;
	private GridPane root;
	private GridPane data;
	private GridPane tables;
	private Scene scene;
	private Text sceneTitle;
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
	private Button saveButton;
	private Button cancelButton;
	private Button searchButton;
	private TextField searchTextField;
	private Button addButton;
	private Button deleteButton;
	private TableView<Part> allPartsTable;
	private TableView<Part> associatedPartsTable;
	private boolean isAdd;

	public Product getProduct(){
		return product;
	}
	public void setProduct(Product product){
		this.product = product;
	}
	public ObservableList<Part> getAllParts(){
		return m.getPartsList();
	}
	public void setAllParts(ObservableList<Part> parts){
		m.setPartsList(parts);
	}
	public ObservableList<Part> getAssociatedParts(){
		return associatedParts;
	}
	public void setAssociatedParts(ObservableList<Part> parts){
		this.associatedParts = parts;
	}
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
	public Button getAddButton(){
		return addButton;
	}
	public void setAddButton(Button button){
		this.addButton = button;
	}
	public Button getSearchButton(){
		return searchButton;
	}
	public void setSearchButton(Button button){
		this.searchButton = button;
	}
	public TextField getSearchTextField(){
		return searchTextField;
	}
	public void setSearchTextField(TextField field){
		this.searchTextField = field;
	}
	public Button getDeleteButton(){
		return deleteButton;
	}
	public void setDeleteButton(Button button){
		this.deleteButton = button;
	}
	public TableView<Part> getAllPartsTable(){
		return allPartsTable;
	}
	public void setAllPartsTable(TableView<Part> table){
		this.allPartsTable = table;
	}
	public TableView<Part> getAssociatedPartsTable(){
		return associatedPartsTable;
	}
	public void setAssociatedPartsTable(TableView<Part> table){
		this.associatedPartsTable = table;
	}
	public GridPane getTablesGrid(){
		return tables;
	}
	public void setTablesGrid(GridPane tables){
		this.tables = tables;
	}
	public GridPane getDataGrid(){
		return data;
	}
	public void setDataGrid(GridPane data){
		this.data = data;
	}
	public Main getMainForm(){
		return m;
	}
	public void setMainForm(Main m){
		this.m = m;
	}
	public boolean getIsAdd(){
		return isAdd;
	}
	public void setIsAdd(boolean isAdd){
		this.isAdd = isAdd;
	}
	
	public ProductForm(){}
	
	public ProductForm(Main m){
		this.isAdd = true;
		this.m = m;
	}
	
	public ProductForm(Main m, Product product){
		this.isAdd = false;
		this.m = m;
		this.product = product;
	}
	
	public void showProductForm(){
		try {

			Scene scene = setupDefaults();			
			this.setScene(scene);
			this.show();
			if (!getIsAdd()){
				setupModifyProductForm();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Scene setupDefaults(){
		//Setup grid pane
				GridPane root = new GridPane();
				setRoot(root);
				root.setAlignment(Pos.CENTER);
				root.setHgap(10);
				root.setVgap(10);
				root.setPadding(new Insets(25,25,25,25));
				Scene scene = new Scene(root,800,500);
				setPartScene(scene);
				Text sceneTitle = getSceneTitle();
				sceneTitle = new Text("Product");
				sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
				setSceneTitle(sceneTitle);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				this.setTitle("Product");
				root.add(sceneTitle, 0, 0, 6, 1);
				
				GridPane data = new GridPane();
				data.setAlignment(Pos.CENTER);
				data.setHgap(10);
				data.setVgap(10);
				data.setPadding(new Insets(25,25,25,25));
				root.add(data, 1, 3, 4, 5);
				setDataGrid(data);
				
				Text id = new Text("ID:");
				TextField idField = new TextField();
				idField.setDisable(true);
				data.add(id, 0, 0, 1, 1);
				data.add(idField, 1, 0, 2, 1);
				setIdLabel(id);
				setIdField(idField);
				
				Text name = new Text("Name:");
				TextField nameField = new TextField();
				data.add(name, 0, 1);
				data.add(nameField, 1, 1, 1, 1);
				setNameLabel(name);
				setNameField(nameField);
				
				Text inv = new Text("Inv:");
				TextField invField = new TextField();
                                invField.setText("0");
				data.add(inv, 0, 2);
				data.add(invField, 1, 2, 1, 1);
				setInvLabel(inv);
				setInvField(invField);
				
				Text price = new Text("Price/Cost:");
				TextField priceField = new TextField();
				data.add(price, 0, 3);
				data.add(priceField, 1, 3, 1, 1);
				setPriceLabel(price);
				setPriceField(priceField);
				
				HBox hbox = new HBox(20);
				Text min = new Text("Min:");
				TextField minField = new TextField();
				Text max = new Text("Max:");
				TextField maxField = getMaxField();
				maxField = new TextField();
				hbox.getChildren().addAll(minField, max, maxField);
				data.add(min, 0, 4);
				data.add(hbox, 1, 4);
				setMinLabel(min);
				setMinField(minField);
				setMaxLabel(max);
				setMaxField(maxField);
				
				
				GridPane tables = new GridPane();
				root.add(tables, 8, 0, 4, 12);
				setTablesGrid(tables);
				
				HBox searchBox = new HBox();
				searchBox.setPadding(new Insets(10,10,10,10));
				searchBox.setAlignment(Pos.BOTTOM_RIGHT);
				Button searchButton = new Button();
				searchButton.setText("Search");
                                searchButton.setOnAction(event->{
                                   Utils.searchParts(getSearchTextField(), getAllPartsTable(), getAllParts());
                                });
				setSearchButton(searchButton);
				searchBox.getChildren().add(searchButton);
				tables.add(searchBox, 0, 0, 1, 1);
				
				TextField searchText = new TextField();
				setSearchTextField(searchText);
				tables.add(searchText, 1, 0, 2, 1);
				
				TableView<Part> allPartsTable = setupPartsTable(getAllParts());
				tables.add(allPartsTable, 0, 1, 4, 4);
				setAllPartsTable(allPartsTable);
				HBox addHb = new HBox(20);
				addHb.setPadding(new Insets(10, 10, 10, 10));
				addHb.setAlignment(Pos.BOTTOM_RIGHT);
				Button addButton = new Button();
				addButton.setText("Add");
				addButton.setOnAction(event->{
					Part p = getAllPartsTable().getSelectionModel().getSelectedItem();
					if (p == null){
						Alert a = new Alert(AlertType.WARNING);
						a.setTitle("Select Part");
						a.setContentText("You must select a part to add");
					}
					else{
						if (!getAssociatedParts().contains(p)){
							getAssociatedParts().add(p);
						}
						getAssociatedPartsTable().refresh();
					}
				});
				setAddButton(addButton);
				addHb.getChildren().add(addButton);
				tables.add(addHb, 0, 5, 4, 1);
				
				if (!getIsAdd()){
					setAssociatedParts(FXCollections.observableArrayList(getProduct().getAssociatedParts()));
				}
				else{
					Product product = new Product(getNextProductId(), "", 0.00, 0, 0, 0);
					setProduct(product);
					setAssociatedParts(FXCollections.observableArrayList(getProduct().getAssociatedParts()));
				}
				TableView<Part> associatedPartsTable = setupPartsTable(getAssociatedParts());
				tables.add(associatedPartsTable, 0, 6, 4, 4);
				setAssociatedPartsTable(associatedPartsTable);
				HBox deleteHb = new HBox(20);
				deleteHb.setPadding(new Insets(10, 10, 10, 10));
				deleteHb.setAlignment(Pos.BOTTOM_RIGHT);
				Button deleteButton = new Button();
				deleteButton.setText("Delete");
				deleteButton.setOnAction(event->{
					Part p = getAssociatedPartsTable().getSelectionModel().getSelectedItem();
					if (p == null){
						Alert a = new Alert(AlertType.WARNING);
						a.setTitle("Selection Reqired");
						a.setContentText("Please select a part to remove");
					}
					else{
                                                Alert a = new Alert(AlertType.CONFIRMATION);
                                                a.setTitle("Sure?");
                                                a.setContentText("Are you sure you wish to delete?");
                                                Optional <ButtonType> action = a.showAndWait();
                                            
						if (getAssociatedParts().contains(p) && action.get() == ButtonType.OK){
							getAssociatedParts().remove(p);
						}
						getAssociatedPartsTable().refresh();
					}
				});
				setDeleteButton(deleteButton);
				deleteHb.getChildren().add(deleteButton);
				tables.add(deleteHb, 0, 10, 4, 1);
				
				Button saveButton = new Button();
				saveButton.setText("Save");
				saveButton.setOnAction(event->{
                                        try{
                                            validProduct();
                                        }
                                        catch(InventoryException ex){
                                            	Alert a = new Alert(AlertType.WARNING);
						a.setTitle("Invalid");
						a.setContentText(ex.getMessage());
						a.showAndWait();
                                                return;
                                        }
                                        if (getIsAdd()){
                                            addProduct();
                                        }
                                        else{
                                            modifyProduct(getProduct());
                                        }
                                        m.getProductsTable().refresh();
                                        this.close();

				});
				setSaveButton(saveButton);
				
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
				tables.add(buttonBox, 0, 11, 4, 1);
				
				
				//tables.setGridLinesVisible(true);
				
				return scene;
	}
        
	
	public void setupModifyProductForm(){
		Product p = getProduct();
		if (p != null){
			getIdField().setText(((Integer)p.getProductId()).toString());
			getNameField().setText(p.getName());
			getInvField().setText(((Integer)p.getInStock()).toString());
			getMinField().setText(((Integer)p.getMin()).toString());
			getMaxField().setText(((Integer)p.getMax()).toString());
			getPriceField().setText(((Double)p.getPrice()).toString());
		}
	}
	
	public TableView<Part> setupPartsTable(ObservableList<Part> parts){
		TableView<Part> tvParts = new TableView<Part>(parts);
		TableColumn<Part, String> partId = new TableColumn<>("Part Id");
		partId.setCellValueFactory(new PropertyValueFactory<>("partId"));
		tvParts.getColumns().add(partId);
		
		TableColumn<Part, String> partName = new TableColumn<>("Part Name");
		partName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tvParts.getColumns().add(partName);

		TableColumn<Part, String> iLevel = new TableColumn<>("Inventory Level");
		iLevel.setCellValueFactory(new PropertyValueFactory<>("inStock"));
		tvParts.getColumns().add(iLevel);

		TableColumn<Part, String> price = new TableColumn<>("Price/Cost per Unit");
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		tvParts.getColumns().add(price);

		tvParts.setPrefHeight(300);
		tvParts.setPrefWidth(350);
		return tvParts;
	}
	
	public void validProduct() throws InventoryException {
		
		HashMap<String, TextField> fields = new HashMap<String, TextField>();
		fields.put("Name", getNameField());
		fields.put("Price", getPriceField());
		fields.put("InStock", getInvField());
		fields.put("Min", getMinField());
		fields.put("Max", getMaxField());

		StringBuilder errors = Utils.validFields(fields);
		
		if (getAssociatedParts().isEmpty()){
			errors.append("Each product must have at least one associated part");
		}
                else{
                    double totalPrice = 0.0;
                    for (Part p : getAssociatedParts()){
                        totalPrice += p.getPrice();
                    }
                    try{
                        if (totalPrice > Double.parseDouble(getPriceField().getText())){
                            errors.append("Product price must exceed cost of parts");
                        }
                    }
                    catch (Exception ex){
                        errors.append(ex.getMessage() + "\n");
                    }
                }
                
                if (errors.toString().length() > 0){
                    throw new InventoryException(errors.toString());
                }
	}
	
	public int getNextProductId(){
		int highestPartId = 1;
		for (Product p : m.getProductList()){
			if (p.getProductId() > highestPartId);{
				highestPartId = p.getProductId();
			}
		}
		return highestPartId + 1;
	}
	
	public void addProduct(){
		int id = getNextProductId();
		String name = getNameField().getText();
		Double price = Double.parseDouble(getPriceField().getText());
		int inStock = Integer.parseInt(getInvField().getText());
		int min = Integer.parseInt(getMinField().getText());
		int max = Integer.parseInt(getMaxField().getText());
		ArrayList<Part> partsArray = new ArrayList<Part>();
		for (Part p : getAssociatedParts()){
			partsArray.add(p);
		}
		Product p = new Product(partsArray, id, name, price, inStock, min, max);
		getMainForm().getProductList().add(p);
                getMainForm().getInventory().getProducts().add(p);
	}
	
	public void modifyProduct(Product p){
		String name = getNameField().getText();
		Double price = Double.parseDouble(getPriceField().getText());
		int inStock = Integer.parseInt(getInvField().getText());
		int min = Integer.parseInt(getMinField().getText());
		int max = Integer.parseInt(getMaxField().getText());
		p.setName(name);
		p.setPrice(price);
		p.setInStock(inStock);
		p.setMin(min);
		p.setMax(max);

		p.getAssociatedParts().clear();
		
		for(Part part : getAssociatedParts()){
			p.addAssociatedPart(part);
		}
                getMainForm().getInventory().updateProduct(p.getProductId(), p);
		
	}
}
