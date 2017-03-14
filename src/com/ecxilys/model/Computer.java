package com.ecxilys.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Computer {
	private int id =-1;
	private String name;
	private Company company=null;
	private Date introduced=null;
	private Date discontinued=null;
	

	
	public Computer(int id, String name, Company company, Date introduced, Date discontinued){
		this.id=id;
		this.name=name;
		this.company=company;
		//if(introduced!=null && discontinued != null && introduce.compareTo(discontinued)>0)
			//TODO : error
			
		
		this.introduced=introduced;
		this.discontinued=discontinued;
	}
	
	public Computer(String name, Company company, Date introduced, Date discontinued){
		this(-1, name, company, introduced, discontinued);
	}
	
	
	
	public String toString(){
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String string = id+"\t"+name+"\t\t";
		if(company!=null)
			string+=company.getName();
		else
			string+="???";
		string+="\t\t";
		if(introduced != null){
			string+=df.format(introduced)+"\t";
			if(discontinued!=null)
				string += df.format(discontinued);
			else
				string += "???";
				
		}
		else
			string += "???\t???";
		
		return string;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public int getId() {
		return id;
	}



	public void setId(int id) {
		//On peut changer l'id que si il n'as pas été initialiser 
		if(this.id==-1)
			this.id = id;
	}



	public void setCompany(Company company) {
		this.company = company;
	}



	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}



	public Company getCompany() {
		return company;
	}

	

}
