package com.excilys.arnaud.service.mapper;

import com.excilys.arnaud.model.dto.CompanyDto;
import com.excilys.arnaud.model.metier.Company;

public enum CompanyMapper {
  COMPANYMAPPER;

  public CompanyDto CompanyToDto(Company company) {
    return new CompanyDto(String.valueOf(company.getId()),company.getName());
  }
  
  public Company DtoToCompany(CompanyDto companyDto) {
    try {
      return new Company(Long.parseLong(companyDto.getId()), companyDto.getName());
    } catch (NumberFormatException e) {
      throw new MapperException(companyDto.getId() + " n'est pas un long");
    }
  }
}
