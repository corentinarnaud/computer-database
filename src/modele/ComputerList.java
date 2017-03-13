package modele;

import java.util.ArrayList;

public class ComputerList extends ArrayList<Computer>{
	
	private ComputerList(){
		super();
	}
	
	private static ComputerList INSTANCE = new ComputerList();
	
	public static ComputerList getInstance()
	{	return INSTANCE;
	}

}
