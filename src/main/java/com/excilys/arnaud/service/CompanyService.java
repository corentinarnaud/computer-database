package com.excilys.arnaud.service;

import com.excilys.arnaud.model.dto.CompanyDto;
import com.excilys.arnaud.model.dto.CompanyDtoList;
import com.excilys.arnaud.model.metier.Company;
import com.excilys.arnaud.model.metier.CompanyList;
import com.excilys.arnaud.persistance.CompanyDAO;
import com.excilys.arnaud.persistance.DAOFactory;
import com.excilys.arnaud.service.mapper.CompanyMapper;

import java.util.Optional;

public enum CompanyService {
  COMPANYSERVICE;

  private CompanyDAO companyDAO = DAOFactory.DAOFACTORY.getCompanyDAO();
  private CompanyDtoList companyDtoList = null;

  /** Look for Company with id id.
   * @param id the id of the company to find
   * @return an Optional that is empty if the company doesn't exist
   */
  public Optional<CompanyDto> findById(long id) {
    Optional<Company> optional = companyDAO.findById(id);
    if (optional.isPresent()) {
      return Optional.of(CompanyMapper.COMPANYMAPPER.companyToDto(optional.get()));
    }
    return Optional.empty();
  }

  /** Look for Company with name name.
   * @param name the name of the company to find
   * @return an Optional that is empty if the company doesn't exist
   */
  public Optional<CompanyDto> findByName(String name) {
    Optional<Company> optional = companyDAO.findByName(name);
    if (optional.isPresent()) {
      return Optional.of(CompanyMapper.COMPANYMAPPER.companyToDto(optional.get()));
    }
    return Optional.empty();
  }

  /** Look for companies.
   * @return The Page of all the companies
   */
  public CompanyPage getCompanies() {
    return new CompanyPage();
  }
  
  /** Look for companies.
   * @return The list of all the companies
   */
  public CompanyDtoList getCompanyList() {
    if (companyDtoList == null) {
      companyDtoList = CompanyMapper.COMPANYMAPPER.companyListToCompanyDtoList(
          companyDAO.getCompanies());
    }
    return companyDtoList;
  }
  
}
