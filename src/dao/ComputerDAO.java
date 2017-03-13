package dao;

import modele.Computer;

public interface ComputerDAO {
	
	void add(Computer computer) throws DAOException;
	void update(Computer computer) throws DAOException;
	void del(Computer computer) throws DAOException;
	Computer find(String name) throws DAOException;

}
