package com.ecxilys.model;

import java.util.ArrayList;

public class CompanyList {
	private ArrayList<Company> company;
	
	public CompanyList(ArrayList<Company> company){
		this.company=company;
	}
	
	public Company get(int index){
		return company.get(index);
	}
	
	public String toString(){
		String string ="";
		for(int i = 0; i<company.size();i++){
			string+=company.get(i)+"\n";
		}
		
		
		return string;
	}
}
