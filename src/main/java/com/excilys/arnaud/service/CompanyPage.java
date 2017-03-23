package com.excilys.arnaud.service;

import java.util.List;

import com.excilys.arnaud.model.metier.Company;
import com.excilys.arnaud.persistance.DAOFactory;

public class CompanyPage extends Page<Company> {

  public CompanyPage() {
    this.list = DAOFactory.DAOFACTORY.getCompanyDAO().getNCompanies(0, ELMEMENT_BY_PAGE);
    this.nbElement = DAOFactory.DAOFACTORY.getCompanyDAO().getNumberOfCompany();
    nbPage=(int)Math.ceil((float)nbElement/ELMEMENT_BY_PAGE);
  }

  @Override
  public List<Company> getNextPage() {
    if(currentPage<nbPage-1) {
      currentPage++;
      int begin = ELMEMENT_BY_PAGE*currentPage;
      this.list = DAOFactory.DAOFACTORY.getCompanyDAO().getNCompanies(begin, ELMEMENT_BY_PAGE);
    }
    return getPage();
  }

  @Override
  public List<Company> getPrevPage() {
    if(currentPage>0) {
      currentPage--;
      int begin = ELMEMENT_BY_PAGE*currentPage;
      this.list = DAOFactory.DAOFACTORY.getCompanyDAO().getNCompanies(begin, ELMEMENT_BY_PAGE);
    }
    return getPage();
  }

  @Override
  public List<Company> getPageN(int n) {
    if(n>=0 && n<nbPage){
      currentPage=n;
      int begin = ELMEMENT_BY_PAGE*currentPage;
      this.list = DAOFactory.DAOFACTORY.getCompanyDAO().getNCompanies(begin, ELMEMENT_BY_PAGE);
    }
    return getPage();
  }

}
