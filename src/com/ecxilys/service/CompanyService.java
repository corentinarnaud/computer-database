package com.ecxilys.service;

import java.util.Optional;

import com.ecxilys.model.Company;
import com.ecxilys.model.CompanyList;
import com.ecxilys.persistance.CompanyDAO;
import com.ecxilys.persistance.DAOFactory;

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
