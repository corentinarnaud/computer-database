package com.ecxilys.model;

import java.util.ArrayList;

public class ComputerList{
	private ArrayList<Computer> computer;
	
	public ComputerList(ArrayList<Computer> computer){
		this.computer=computer;
	}
	
	public Computer get(int index){
		return computer.get(index);
	}
	
	
	public String toString(){
		String string ="";
		for(int i = 0; i<computer.size();i++){
			string+=computer.get(i)+"\n";
		}
		
		
		return string;
	}
}
