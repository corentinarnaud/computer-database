package com.ecxilys.persistance;

import java.util.Optional;

import com.ecxilys.model.Computer;
import com.ecxilys.model.ComputerList;

public interface ComputerDAO {

	
	/**
	 * @param computer
	 * @return int : the auto-generated id
	 * @throws DAOException
	 */
	public int add(Computer computer) throws DAOException;
	
	/**
	 * @param computer
	 * @return true if update works, false otherwise
	 * @throws DAOException
	 */
	public boolean update(Computer computer) throws DAOException;
	
	/**
	 * @param id
	 * @return true if deletion works, false otherwise
	 * @throws DAOException
	 */
	public boolean del(long id) throws DAOException;
	
	/**
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public Optional<Computer> findById(long id) throws DAOException;
	
	/**
	 * @param name
	 * @return
	 * @throws DAOException
	 */
	public Optional<Computer> findByName(String name) throws DAOException;
	
	/**
	 * @return
	 * @throws DAOException
	 */
	public ComputerList getComputers() throws DAOException;
	
}
