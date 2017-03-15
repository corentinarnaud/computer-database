package com.ecxilys.persistance;

import java.util.Optional;

import com.ecxilys.model.Company;
import com.ecxilys.model.CompanyList;

public interface CompanyDAO {
	
	public Optional<Company> findById(long id) throws DAOException;
	
	public Optional<Company> findByName(String name) throws DAOException;
	
	public CompanyList getCompanies() throws DAOException;

}
