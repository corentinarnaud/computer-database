package com.excilys.arnaud.service;

import com.excilys.arnaud.model.dto.CompanyDto;
import com.excilys.arnaud.persistance.DAOFactory;
import com.excilys.arnaud.service.mapper.CompanyMapper;
import java.util.List;



public class CompanyPage extends Page<CompanyDto> {

  public CompanyPage() {
    this.nbElement = DAOFactory.DAOFACTORY.getCompanyDAO().getNumberOfCompany();
    nbPage = (int) Math.ceil((float) nbElement / ELMEMENT_BY_PAGE);
  }

  @Override
  public List<CompanyDto> getNextPage() {
    if (currentPage < nbPage - 1) {
      currentPage++;
    }
    return getPage();
  }

  @Override
  public List<CompanyDto> getPrevPage() {
    if (currentPage > 0) {
      currentPage--;
    }
    return getPage();
  }

  @Override
  public List<CompanyDto> getPageN(int n) {
    if (n >= 0 && n < nbPage) {
      currentPage = n;
    }
    return getPage();
  }
  
  @Override
  public List<CompanyDto> getPage() {
    int begin = ELMEMENT_BY_PAGE * currentPage;
    return CompanyMapper.COMPANYMAPPER.companyListToCompanyDtoList(
        DAOFactory.DAOFACTORY.getCompanyDAO().getNCompanies(begin, ELMEMENT_BY_PAGE));
  }

}
