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


public class Product {
	private ArrayList<Part> associatedParts = new ArrayList<Part>();
	private int productId;
	private String name;
	private double price;
	private int inStock;
	private int min;
	private int max;
	
	public Product(){}
	
	public Product(ArrayList<Part> parts, int productId, String name, double price, int inStock, int min, int max){
		this.associatedParts = parts;
		this.productId = productId;
		this.name = name;
		this.price = price; 
		this.inStock = inStock;
		this.min = min;
		this.max = max;
	}
	
	public Product(int productId, String name, double price, int inStock, int min, int max){
		this.associatedParts = new ArrayList<Part>();
		this.productId = productId;
		this.name = name;
		this.price = price; 
		this.inStock = inStock;
		this.min = min;
		this.max = max;
	}
	
	public boolean addAssociatedPart(Part part){
		if (!associatedParts.contains(part)){
			associatedParts.add(part);
		}
		return true;
	}
	
	public boolean removeAssociatedPart(Part part){
		if (associatedParts.contains(part)){
			associatedParts.remove(part);
		}
		else{
			return false;
		}
		return true;
	}
	
	public Part lookupAssociatedPart(Part part){
		Part foundPart = null;
		if (associatedParts.contains(part)){
			foundPart = associatedParts.get(associatedParts.indexOf(part));
		}
		return foundPart;
	}
	
	public ArrayList<Part> getAssociatedParts(){
		return associatedParts;
	}
	
	public void setProductId(int productId){
		this.productId = productId;
	}
	
	public int getProductId(){
		return productId;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setPrice(double price){
		this.price = price;
	}
	
	public double getPrice(){
		return price;
	}
	
	public void setInStock(int inStock){
		this.inStock = inStock;
	}
	
	public int getInStock(){
		return inStock;
	}
	
	public void setMin(int min){
		this.min = min;
	}
	
	public int getMin(){
		return min;
	}
	
	public void setMax(int max){
		this.max = max;
	}
	
	public int getMax(){
		return max;
	}
}
