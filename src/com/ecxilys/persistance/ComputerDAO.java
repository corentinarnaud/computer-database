package com.ecxilys.persistance;

import com.ecxilys.model.Computer;
import com.ecxilys.model.ComputerList;

public interface ComputerDAO {

	
	public int add(Computer computer) throws DAOException;
	
	public boolean update(Computer computer);
	
	public boolean del(int id);
	
	public Computer findById(int id);
	
	public Computer findByName(String name);
	
	public ComputerList getComputers();
	
}
