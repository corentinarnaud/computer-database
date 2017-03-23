package com.excilys.arnaud.service;

import com.excilys.arnaud.model.metier.Company;
import com.excilys.arnaud.model.metier.CompanyList;
import com.excilys.arnaud.persistance.CompanyDAO;
import com.excilys.arnaud.persistance.DAOFactory;
import java.util.Optional;

public enum CompanyService {
  COMPANYSERVICE;

  private CompanyDAO companyDAO = DAOFactory.DAOFACTORY.getCompanyDAO();
  private CompanyPage companyPage = null;
  private CompanyList companyList = null;

  public Optional<Company> findById(long id) {
    return companyDAO.findById(id);
  }

  public Optional<Company> findByName(String name) {
    return companyDAO.findByName(name);
  }

  public Page<Company> getCompanies() {
    if(companyPage==null){
      companyPage = new CompanyPage();
    }
    return companyPage;
  }
  
  public CompanyList getCompanyList() {
    if(companyList==null){
      companyList = companyDAO.getCompanies();
    }
    return companyList;
  }
}
