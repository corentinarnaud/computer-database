package com.ecxilys.persistance;

import com.ecxilys.model.Company;
import com.ecxilys.model.CompanyList;

public interface CompanyDAO {
	
	public Company findById(int id);
	
	public Company findByName(int name);
	
	public CompanyList getCompanies();

}
