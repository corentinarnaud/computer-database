package com.excilys.arnaud.service;

import com.excilys.arnaud.mapper.CompanyMapper;
import com.excilys.arnaud.model.dto.CompanyDto;
import com.excilys.arnaud.model.dto.CompanyDtoList;
import com.excilys.arnaud.model.dto.Page;
import com.excilys.arnaud.model.metier.Company;
import com.excilys.arnaud.model.metier.CompanyList;
import com.excilys.arnaud.persistance.CompanyDAO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CompanyService {

  @Autowired
  private CompanyDAO companyDAO;
  private CompanyDtoList companyDtoList = null;

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
  public Page<CompanyDto> getCompanies(int page, int elementsByPage) {
    int begin = elementsByPage * page;
    List<CompanyDto> list = CompanyMapper.companyListToCompanyDtoList(
        companyDAO.getNCompanies(begin, elementsByPage));
    return new Page<CompanyDto>(list, page, elementsByPage);
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
  
}
