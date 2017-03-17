package com.excilys.arnaud.service;

import java.util.Optional;

import com.excilys.arnaud.model.Computer;
import com.excilys.arnaud.model.ComputerList;
import com.excilys.arnaud.persistance.ComputerDAO;
import com.excilys.arnaud.persistance.DAOFactory;





public enum ComputerService{
	COMPUTERSERVICE;
	private ComputerDAO computerDAO = DAOFactory.DAOFACTORY.getComputerDAO();
	
	
	public long add(Computer computer) throws ServiceException{
		checkDate(computer);
		return computerDAO.add(computer);
	}
	
	public boolean update(Computer computer) throws ServiceException{
		checkDate(computer);
		return computerDAO.update(computer);
	}
	
	public boolean del(long id){
		return computerDAO.del(id);
	}
	
	public Optional<Computer> findById(long id){
		return computerDAO.findById(id);
	}
	
	public Optional<Computer> findByName(String name){
		return computerDAO.findByName(name);
	}
	
	public ComputerList getComputers(){
		return computerDAO.getComputers();
	}
	
	
	public static boolean checkDate(Computer computer) throws ServiceException{
		if(computer.getIntroduced()==null || computer.getDiscontinued()==null 
				|| computer.getIntroduced().isBefore(computer.getDiscontinued()))
			return true;
		throw new ServiceException("Date of discontinuation "+computer.getDiscontinued()
					    +" must be after date of introduction "+computer.getIntroduced());
	}

}
