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

public class Inventory {
	private ArrayList<Part> allParts = new ArrayList<Part>();
	private ArrayList<Product> products = new ArrayList<Product>();
	
	public Inventory(){}
	
	public ArrayList<Part> getParts(){
		return allParts;
	}
	
	ArrayList<Product>getProducts(){
		return products;
	}
	
	public boolean addProduct(Product product){
		if (!getProducts().contains(product)){
			getProducts().add(product);
		}
		return true;
	}
	
	public boolean removeProduct(int productId){
		for (Product p : getProducts()){
			if (p.getProductId() == productId){
				getProducts().remove(p);
				return true;
			}
		}
		return false;
	}
	
	public Product lookupProduct(int productId){
		for (Product p : getProducts()){
			if (p.getProductId() == productId){
				return p;
			}
		}
		return null;
	}
	
	public Product updateProduct(int productId, Product newProduct){
            Product oldProduct = null;
            for(Product p : getProducts()){
                if(p.getProductId() == productId){
                    oldProduct = p;
                }
            }
            getProducts().set(getProducts().indexOf(oldProduct), newProduct);
            return newProduct;
	}
	
	public boolean addPart(Part part){
		if (!getParts().contains(part)){
			getParts().add(part);
			return true;
		}
		return false;
	}
	
	public Part deletePart(Part part){
		if (getParts().contains(part)){
			getParts().remove(part);
			return part;
		}
		return null;
	}
	
	public Part lookupPart(int partId){
		for (Part p : getParts()){
			if (p.getPartId() == partId){
				return p;
			}
		}
		return null;
	}
	
	public Part updatePart(int partId, Part newPart){
            Part oldPart = null;
            for(Part p : getParts()){
                if(p.getPartId() == partId){
                    oldPart = p;
                }
            }
            getParts().set(getParts().indexOf(oldPart), newPart);
            return newPart;
	}
}
