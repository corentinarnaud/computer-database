package com.excilys.arnaud.service;

import com.excilys.arnaud.dto.CompanyDto;
import com.excilys.arnaud.dto.CompanyDtoList;
import com.excilys.arnaud.dto.Page;
import com.excilys.arnaud.mapper.CompanyMapper;
import com.excilys.arnaud.model.Company;
import com.excilys.arnaud.persistence.CompanyDAO;
import com.excilys.arnaud.persistence.ComputerDAO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CompanyService {

  @Autowired
  private CompanyDAO companyDAO;
  @Autowired
  private ComputerDAO computerDAO;
  
  private CompanyDtoList companyDtoList = null;
  private int numberCompany = -1;

  /** Look for Company with id id.
   * @param id the id of the company to find
   * @return an Optional that is empty if the company doesn't exist
   */
  @Transactional(readOnly=true)
  public Optional<CompanyDto> findById(long id) {
    Optional<Company> optional = companyDAO.findById(id);
    if (optional.isPresent()) {
      return Optional.of(CompanyMapper.companyToDto(optional.get()));
    }
    return Optional.empty();
  }

  /** Look for Company with name name.
   * @param name the name of the company to find
   * @return an Optional that is empty if the company doesn't exist
   */
  @Transactional(readOnly=true)
  public Optional<CompanyDto> findByName(String name) {
    Optional<Company> optional = companyDAO.findByName(name);
    if (optional.isPresent()) {
      return Optional.of(CompanyMapper.companyToDto(optional.get()));
    }
    return Optional.empty();
  }

  /** Look for companies.
   * @return The Page of all the companies
   */
  @Transactional(readOnly=true)
  public Page<CompanyDto> getCompanyPage(int page, int elementsByPage) {
    int begin = elementsByPage * page;
    List<CompanyDto> list = CompanyMapper.companyListToCompanyDtoList(
        companyDAO.getNCompanies(begin, elementsByPage));
    return new Page<CompanyDto>(list, getNumberCompany(), page);
  }
  
  /** Look for companies.
   * @return The list of all the companies
   */
  @Transactional(readOnly=true)
  public CompanyDtoList getCompanyList() {
    if (companyDtoList == null) {
      companyDtoList = CompanyMapper.companyListToCompanyDtoList(
          companyDAO.getCompanies());
    }
    return companyDtoList;
  }
  
  @Transactional(readOnly=true)
  public int getNumberCompany() {
    if ( numberCompany == -1) {
      numberCompany = companyDAO.getNumberOfCompany();
    }
    return numberCompany;
  }
  
  
  @Transactional()
  public boolean delCompany(long id){
    computerDAO.delsFromCompany(id);
    return companyDAO.delCompany(id);
  }
  
  
}
