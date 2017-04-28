package com.excilys.arnaud.persistence;

import java.util.Optional;

import com.excilys.arnaud.model.Company;
import com.excilys.arnaud.model.CompanyList;
import com.excilys.arnaud.persistence.exception.DAOException;


public interface CompanyDAO{

  public Optional<Company> findById(long id) throws DAOException;

  public Optional<Company> findByName(String name) throws DAOException;

  public CompanyList getCompanies() throws DAOException;
  
  public CompanyList getNCompanies(int begin, int nbCompanies) throws DAOException;
  
  public int getNumberOfCompany();
  
  public boolean delCompany(long id);

}
