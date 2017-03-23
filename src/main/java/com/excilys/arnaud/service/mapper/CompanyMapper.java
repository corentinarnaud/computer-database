package com.excilys.arnaud.service.mapper;

import com.excilys.arnaud.model.dto.CompanyDto;
import com.excilys.arnaud.model.metier.Company;

public enum CompanyMapper {
  COMPANYMAPPER;

  /** Map a company to a companyDto.
   * 
   * @param company A company
   * @return A companyDto who correspond to company
   */
  public CompanyDto companyToDto(Company company) {
    return new CompanyDto(String.valueOf(company.getId()),company.getName());
  }
  
  /** Map a companyDto to a company.
   * 
   * @param companyDto a CompanyDto
   * @return A company who correspond to companyDto
   */
  public Company dtoToCompany(CompanyDto companyDto) {
    try {
      return new Company(Long.parseLong(companyDto.getId()), companyDto.getName());
    } catch (NumberFormatException e) {
      throw new MapperException(companyDto.getId() + " must be a long");
    }
  }
}
