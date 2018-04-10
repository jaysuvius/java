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
	
import java.util.Optional;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	private Inventory inventory;
	private ObservableList<Part> partsList;
	private TableView<Part> partsTable;
	private ObservableList<Product> productList;
	private TableView<Product> productsTable;
	
	public Inventory getInventory(){
		return inventory;
	}
	public void setInventory(Inventory inv){
		this.inventory = inv;
	}
	public ObservableList<Part> getPartsList(){
		return partsList;
	}
	public void setPartsList(ObservableList<Part> parts){
		this.partsList = parts;
	}
	public TableView<Part> getPartsTable(){
		return partsTable;
	}
	public void setPartsTable(TableView<Part> partsTable){
		this.partsTable = partsTable;
	}
	public ObservableList<Product> getProductList(){
		return productList;
	}
	public void setProductsList(ObservableList<Product> products){
		this.productList = products;
	}
	public TableView<Product> getProductsTable(){
		return productsTable;
	}
	public void setProductsTable(TableView<Product> productsTable){
		this.productsTable = productsTable;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//Setup grid pane
			GridPane root = new GridPane();
			root.setAlignment(Pos.CENTER);
			root.setHgap(10);
			root.setVgap(10);
			root.setPadding(new Insets(25,25,25,25));
			Scene scene = new Scene(root,800,400);
			Text sceneTitle = new Text("Inventory Management System");
			sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Inventory Management System");
			
			//root.setGridLinesVisible(true);
			
			//Setup Parts Controls
			Label partsLabel = new Label("Parts:");
			TextField partSearchText = new TextField();
			Button partSearchButton = new Button();
			partSearchButton.setText("Search");
			partSearchButton.setOnAction(event -> {
                            Utils.searchParts(partSearchText, getPartsTable(), getPartsList());
			});
			
			//Setup Product Controls
			Label productsLabel = new Label("Products:");
			TextField productsSearchText = new TextField();
			Button productsSearchButton = new Button();
			productsSearchButton.setText("Search");
                        productsSearchButton.setOnAction(event -> {
                            Utils.searchProducts(productsSearchText, getProductsTable(), getProductList());
                        });
			
			//Add Controls to Grid
			root.add(sceneTitle, 0, 0, 4, 1);
			root.add(partsLabel, 0, 1, 2, 1);
			root.add(partSearchButton, 2, 1, 1, 1);
			root.add(partSearchText, 3, 1, 1, 1);
			root.add(productsLabel, 4, 1, 2, 1);
			root.add(productsSearchButton, 6, 1, 1, 1);
			root.add(productsSearchText, 7, 1, 1, 1);
						
			Inventory inv = loadInventory();
			setInventory(inv);
			
			ObservableList<Part> parts = FXCollections.observableArrayList(getInventory().getParts());
			setPartsList(parts);
			TableView<Part> tvParts = getPartsTable(parts);
			setPartsTable(tvParts);
			ObservableList<Product> products = FXCollections.observableArrayList(getInventory().getProducts());
			setProductsList(products);
			TableView<Product> tvProducts = getProductsTable(products);
			setProductsTable(tvProducts);

			root.add(tvParts, 0, 3, 4, 1);
			root.add(tvProducts, 4, 3, 4, 1);
			
			//Setup Parts Edit Buttons
			Button addPartButton = new Button();
			addPartButton.setText("Add");
			addPartButton.setOnAction(event -> {
				PartForm pf = new PartForm(this);
				pf.showAddPartForm();
			});
			Button modifyPartButton = new Button();
			modifyPartButton.setText("Modify");
			modifyPartButton.setOnAction(event->{
				if(getPartsTable().getSelectionModel().getSelectedItem() == null){
					Alert a = new Alert(AlertType.WARNING);
					a.setTitle("Select Part");
					a.setContentText("Select a part to mofidy");
					a.show();
				}
				else{
					PartForm pf = new PartForm(this, getPartsTable().getSelectionModel().getSelectedItem());
					pf.showAddPartForm();
				}
			});
			Button deletePartButton = new Button();
			deletePartButton.setText("Delete");
                        deletePartButton.setOnAction(event ->{
                            if(getPartsTable().getSelectionModel().getSelectedItem() == null){
					Alert a = new Alert(AlertType.WARNING);
					a.setTitle("Select Part");
					a.setContentText("Select a product to Delete");
					a.show();
				}
				else{
                                        Alert a = new Alert(AlertType.CONFIRMATION);
                                        a.setTitle("Sure?");
                                        a.setContentText("Are you sure you wish to delete?");
                                        Optional <ButtonType> action = a.showAndWait();
                                        if(action.get() == ButtonType.OK){
                                            Part p = getPartsTable().getSelectionModel().getSelectedItem();
                                            getPartsList().remove(p);
                                            getPartsTable().refresh();
                                        }

				}
                        });
			HBox hbPartBtn = new HBox(10);
			hbPartBtn.setPadding(new Insets(10,10,10,10));
			hbPartBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbPartBtn.getChildren().addAll(addPartButton, modifyPartButton, deletePartButton);
			root.add(hbPartBtn, 0, 5, 4, 1);
			
			//Setup Products Edit Buttons
			Button addProductButton = new Button();
			addProductButton.setText("Add");
			addProductButton.setOnAction(event ->{
				ProductForm pf = new ProductForm(this);
				pf.showProductForm();
			});
			Button modifyProductButton = new Button();
			modifyProductButton.setText("Modify");
			modifyProductButton.setOnAction(event->{
				if(getProductsTable().getSelectionModel().getSelectedItem() == null){
					Alert a = new Alert(AlertType.WARNING);
					a.setTitle("Select Product");
					a.setContentText("Select a product to mofidy");
					a.show();
				}
				else{
					ProductForm pf = new ProductForm(this, getProductsTable().getSelectionModel().getSelectedItem());
					pf.showProductForm();
				}
			});
			Button deleteProductButton = new Button();
			deleteProductButton.setText("Delete");
                        deleteProductButton.setOnAction(event ->{
                            	if(getProductsTable().getSelectionModel().getSelectedItem() == null){
					Alert a = new Alert(AlertType.WARNING);
					a.setTitle("Select Product");
					a.setContentText("Select a product to delete");
					a.show();
				}
				else{
                                        Alert a = new Alert(AlertType.CONFIRMATION);
                                        a.setTitle("Sure?");
                                        a.setContentText("Are you sure you wish to delete?");
                                        Optional <ButtonType> action = a.showAndWait();
                                        if(action.get() == ButtonType.OK){
                                            Product p = getProductsTable().getSelectionModel().getSelectedItem();
                                            if(p.getAssociatedParts().size() > 1){
                                                Alert err = new Alert(AlertType.ERROR);
                                                err.setTitle("Cannot Delete");
                                                err.setContentText("Cannot delete products that have associated parts");
                                                err.showAndWait();
                                                return;
                                            }
                                            getProductList().remove(p);
                                            getProductsTable().refresh();
                                        }

				}
                        });
			root.add(addProductButton, 5, 5);
			root.add(modifyProductButton, 6, 5);
			root.add(deleteProductButton, 7, 5);
			HBox hbProductBtn = new HBox(10);
			hbProductBtn.setPadding(new Insets(10,10,10,10));
			hbProductBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbProductBtn.getChildren().addAll(addProductButton, modifyProductButton, deleteProductButton);
			root.add(hbProductBtn, 5, 5, 4, 1);
			
			//root.getChildren().addAll(tvParts);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public TableView<Part> getPartsTable(ObservableList<Part> parts){
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
	
	public TableView<Product> getProductsTable(ObservableList<Product> products){
		TableView<Product> tvProducts = new TableView<Product>(products);
		TableColumn<Product, String> partId = new TableColumn<>("Product Id");
		partId.setCellValueFactory(new PropertyValueFactory<>("productId"));
		tvProducts.getColumns().add(partId);
		
		TableColumn<Product, String> partName = new TableColumn<>("Product Name");
		partName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tvProducts.getColumns().add(partName);

		TableColumn<Product, String> iLevel = new TableColumn<>("Inventory Level");
		iLevel.setCellValueFactory(new PropertyValueFactory<>("inStock"));
		tvProducts.getColumns().add(iLevel);

		TableColumn<Product, String> price = new TableColumn<>("Price/Cost per Unit");
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		tvProducts.getColumns().add(price);

		tvProducts.setPrefHeight(300);
		tvProducts.setPrefWidth(350);
		return tvProducts;
	}
	
	public Inventory loadInventory(){
		Inventory i = new Inventory();
		i.addProduct(new Product(1, "Product1", 100, 2, 100, 1000));
		i.addProduct(new Product(2, "Product2", 200, 2, 100, 1000));
		i.addPart(new Inhouse(1, "Part1", 5, 2, 0, 10, 1));
		i.lookupProduct(1).addAssociatedPart(i.lookupPart(1));
		i.addPart(new Inhouse(2, "Part2", 10, 6, 0, 10, 2));
		i.lookupProduct(2).addAssociatedPart(i.lookupPart(2));
		i.addPart(new Inhouse(3, "Part3", 15, 3, 0, 10, 1));
		i.lookupProduct(1).addAssociatedPart(i.lookupPart(3));
		i.addPart(new Inhouse(4, "Part4", 20, 9, 0, 10, 1));
		i.lookupProduct(1).addAssociatedPart(i.lookupPart(4));
		i.addPart(new Inhouse(5, "Part5", 25, 8, 0, 10, 2));
		i.lookupProduct(2).addAssociatedPart(i.lookupPart(5));
		i.addPart(new Outsourced(6, "Part6", 30, 2, 0, 10, "Acme"));
		i.lookupProduct(2).addAssociatedPart(i.lookupPart(6));
		i.addPart(new Outsourced(7, "Part7", 35, 6, 0, 10, "Acme"));
		i.lookupProduct(1).addAssociatedPart(i.lookupPart(7));
		i.addPart(new Outsourced(8, "Part8", 40, 5, 0, 10, "Acme"));
		i.lookupProduct(1).addAssociatedPart(i.lookupPart(8));
		i.addPart(new Outsourced(9, "Part9", 45, 3, 0, 10, "Acme"));
		i.lookupProduct(2).addAssociatedPart(i.lookupPart(9));
		i.addPart(new Outsourced(10, "Part10", 50, 1, 0, 10, "Acme"));
		i.lookupProduct(2).addAssociatedPart(i.lookupPart(10));
		return i;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
