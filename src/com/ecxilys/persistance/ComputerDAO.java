package com.ecxilys.persistance;

import com.ecxilys.model.Computer;
import com.ecxilys.model.ComputerList;

public interface ComputerDAO {

	
	public int add(Computer computer) throws DAOException;
	
	public boolean update(Computer computer) throws DAOException;
	
	public boolean del(long id) throws DAOException;
	
	public Computer findById(long id) throws DAOException;
	
	public Computer findByName(String name) throws DAOException;
	
	public ComputerList getComputers() throws DAOException;
	
}
