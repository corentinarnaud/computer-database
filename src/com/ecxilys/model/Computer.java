package com.ecxilys.model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class Computer {
	private static final String FORMAT = "MM/dd/yyyy";
	
	private int id =-1;
	private String name;
	private Company company;
	private LocalDateTime introduced;
	private LocalDateTime discontinued;
	

	
	public Computer(int id, String name, Company company, LocalDateTime introduced, LocalDateTime discontinued){
		this.id=id;
		this.name=name;
		
		
		this.company=company;

		
		this.introduced=introduced;
		this.discontinued=discontinued;
	}
	
	public Computer(String name, Company company, LocalDateTime introduced, LocalDateTime discontinued){
		this(-1, name, company, introduced, discontinued);
	}
	
	
	
	public String toString(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);
		String string = id+"\t"+name+"\t\t\t";
		if(company!=null)
			string+=company.getName();
		else
			string+="???";
		string+="\t\t";
		if(introduced != null){
			string+=introduced.format(formatter)+"\t";
			if(discontinued!=null)
				string += discontinued.format(formatter);
			else
				string += "???";
				
		}
		else
			string += "???\t\t???";
		
		return string;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDateTime introduced) {
		this.introduced = introduced;
	}

	public LocalDateTime getDiscontinued() {
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



	public void setDiscontinued(LocalDateTime discontinued) {
		this.discontinued = discontinued;
	}



	public Company getCompany() {
		return company;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + id;
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	

}
