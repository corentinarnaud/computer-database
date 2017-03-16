package com.excilys.arnaud.service;

import java.util.Optional;

import com.excilys.arnaud.model.Company;
import com.excilys.arnaud.model.CompanyList;
import com.excilys.arnaud.persistance.CompanyDAO;
import com.excilys.arnaud.persistance.DAOFactory;

public enum CompanyService {
	COMPANYSERVICE;
	
	private CompanyDAO companyDAO = DAOFactory.DAOFACTORY.getCompanyDAO();
	
	
	public Optional<Company> findById(long id){
		return companyDAO.findById(id);
	}

	
	public Optional<Company> findByName(String name){
		return companyDAO.findByName(name);
	}
	
	public CompanyList getCompanies(){
		return companyDAO.getCompanies();
	}
	
	
}
