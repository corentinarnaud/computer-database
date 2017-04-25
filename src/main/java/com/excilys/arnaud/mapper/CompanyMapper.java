package com.excilys.arnaud.mapper;

import com.excilys.arnaud.model.dto.CompanyDto;
import com.excilys.arnaud.model.dto.CompanyDtoList;
import com.excilys.arnaud.model.metier.Company;
import com.excilys.arnaud.model.metier.CompanyList;

public class CompanyMapper {

  /** Map a company to a companyDto.
   * 
   * @param company A company, not null
   * @return A companyDto who correspond to company
   */
  public static CompanyDto companyToDto(Company company) {
    return new CompanyDto(String.valueOf(company.getId()),company.getName());
  }
  
  /** Map a companyDto to a company.
   * 
   * @param companyDto a CompanyDto, not null
   * @return A company who correspond to companyDto
   */
  public static Company dtoToCompany(CompanyDto companyDto) {
    try {
      return new Company(Long.parseLong(companyDto.getId()), companyDto.getName());
    } catch (NumberFormatException e) {
      throw new MapperException(companyDto.getId() + " must be a long");
    }
  }
  
  /** Map a companyList to a companyDtoList.
   * 
   * @param companyList a list of company, not null
   * @return A company who correspond to companyDto
   */
  public static CompanyDtoList companyListToCompanyDtoList(CompanyList companyList) {
    CompanyDtoList companyDtoList = new CompanyDtoList();
    for (int i = 0;i < companyList.size(); i++) {
      companyDtoList.add(i, companyToDto(companyList.get(i)));
    }
    return companyDtoList;
  }
}
