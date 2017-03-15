package com.ecxilys.persistance;

import com.ecxilys.model.Company;
import com.ecxilys.model.CompanyList;

public interface CompanyDAO {
	
	public Company findById(int id) throws DAOException;
	
	public Company findByName(int name) throws DAOException;
	
	public CompanyList getCompanies() throws DAOException;

}
