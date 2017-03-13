package modele;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Computer {
	private String name;
	private String compagnie=null;
	private Date introduced=null;
	private Date discontinued=null;
	
	public Computer(String name){
		this.name=name;
	}
	
	public Computer(String name, Date introduced){
		this.name=name;
		this.introduced=introduced;

	}
	
	public Computer(String name, String compagnie){
		this.name=name;
		this.compagnie=compagnie;
	}
	
	public Computer(String name, String compagnie, Date introduced){
		this.name=name;
		this.compagnie=compagnie;
		this.introduced=introduced;

	}
	
	public Computer(String name, String compagnie, Date introduced, Date discontinued){
		this.name=name;
		this.compagnie=compagnie;
		//if(introduce.compareTo(discontinued)>0)
			//TODO : error
			
		
		this.introduced=introduced;
		this.discontinued=discontinued;
	}
	
	
	
	public String toString(){
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String string = "Computer : "+name+"\nCompanie : "+compagnie;
		if(introduced != null){
			string+="\nintroduced in : "+ df.format(introduced);
			if(discontinued!=null)
				string += "\ndiscontinued in : " +df.format(discontinued);
		}
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

	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}

	

}
