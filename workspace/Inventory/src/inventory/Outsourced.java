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

public class Outsourced extends Part {
	private String companyName;
	
	public Outsourced(int partId, String name, double price, int inStock, int min, int max, String companyName){
		super(partId, name, price, inStock, min, max);
		this.companyName = companyName;
	}
	
	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}
	public String getCompanyName  (){
		return companyName;
	}
}
