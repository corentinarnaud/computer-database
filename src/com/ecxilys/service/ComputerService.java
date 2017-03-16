package com.ecxilys.service;

import java.util.Optional;

import com.ecxilys.model.Computer;
import com.ecxilys.model.ComputerList;
import com.ecxilys.persistance.ComputerDAO;
import com.ecxilys.persistance.DAOFactory;



public enum ComputerService{
	COMPUTERSERVICE;
	private ComputerDAO computerDAO = DAOFactory.DAOFACTORY.getComputerDAO();
	
	
	public int add(Computer computer) throws ServiceException{
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
	
	
	private void checkDate(Computer computer) throws ServiceException{
		if(computer.getIntroduced()!=null && computer.getDiscontinued()!=null 
				&& computer.getIntroduced().isBefore(computer.getDiscontinued()))
			return;
		throw new ServiceException("Date of discontinuation "+computer.getDiscontinued()
					    +" must be after date of introduction "+computer.getIntroduced());
	}

}
