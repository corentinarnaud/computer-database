package com.excilys.arnaud.persistance;

public enum DAOFactory {
	DAOFACTORY;
	
	public CompanyDAO getCompanyDAO(){
		return CompanyDAOMySQL.CONPANYDAO;
	}
	
	public ComputerDAO getComputerDAO(){
		return ComputerDAOMySQL.COMPUTERDAO;
	}

}
