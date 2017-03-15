package com.ecxilys.model;



public class Company{
	private long id;
	private String name;
	
	
	public Company(long id, String name ){
		this.id=id;
		this.name=name;
	}
	
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	
	public String toString(){
		return id+"\t"+name;
	}

}