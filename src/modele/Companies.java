package modele;

import java.util.ArrayList;

public class Companies{
	private static Companies INSTANCE= new Companies();
	private ArrayList<String> companiesList;
	private Boolean load=false;
	
	private Companies(){
		companiesList=new ArrayList<String>();
	}
	
	public static Companies getInstance()
	{	return INSTANCE;
	}
	
	
	public String getCompanies(int id) throws IndexOutOfBoundsException{
		return companiesList.get(id);
	}
	
	public void loadData(ArrayList<String> companies){
		if(load==false){
			companiesList=companies;
			load=true;
			System.out.println("Companies charged");
		}
		else{
			System.out.println("Companies already charged");
		}
	}

	public Boolean isLoad(){
		return load;
	}
	
	public int size(){
		return companiesList.size();
	}
}