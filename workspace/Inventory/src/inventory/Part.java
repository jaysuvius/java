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

public abstract class Part {
	protected int partId;
	protected String name;
	protected double price;
	protected int inStock;
	protected int min;
	protected int max;
	
	public Part(int partId, String name, double price, int inStock, int min, int max){
		this.partId = partId;
		this.name = name;
		this.price = price;
		this.inStock = inStock;
		this.min = min;
		this.max = max;
	}
	
	public int getPartId(){
		return partId;
	}
	public void setPartId(int id){
		this.partId = id;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public double getPrice(){
		return price;
	}
	public void setPrice(double price){
		this.price = price;
	}
	public int getInStock(){
		return inStock;
	}
	public void setInStock(int inStock){
		this.inStock = inStock;
	}
	public int getMin(){
		return min;
	}
	public void setMin(int min){
		this.min = min;
	}
	public int getMax(){
		return max;
	}
	public void setMax(int max){
		this.max = max;
	}
}
