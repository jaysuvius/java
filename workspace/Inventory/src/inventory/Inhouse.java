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

public class Inhouse extends Part {
	private int machineId;
	
	public Inhouse(int partId, String name, double price, int inStock, int min, int max, int machineId){
		super(partId, name, price, inStock, min, max);
		this.machineId = machineId;
	}
	
	public void setMachineId(int machineId){
		this.machineId = machineId;
	}
	public int getMachineId(){
		return machineId;
	}
}
