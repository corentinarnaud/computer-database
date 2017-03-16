package com.excilys.arnaud.persistance;

import java.util.Optional;

import com.excilys.arnaud.model.Company;
import com.excilys.arnaud.model.CompanyList;




public interface CompanyDAO {
	
	public Optional<Company> findById(long id) throws DAOException;
	
	public Optional<Company> findByName(String name) throws DAOException;
	
	public CompanyList getCompanies() throws DAOException;

}
