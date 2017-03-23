package com.excilys.arnaud.service.mapper;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.excilys.arnaud.model.dto.CompanyDto;
import com.excilys.arnaud.model.metier.Company;
import org.junit.Test;

public class CompanyMapperTest {

  @Test
  public void companyToDtoTest() {
    Company company = new Company(48796567 , "dghzipf");
    CompanyDto companyDto = CompanyMapper.COMPANYMAPPER.companyToDto(company);
    
    assertTrue(companyDto.getId().equals("48796567"));
    assertTrue(companyDto.getName().equals("dghzipf"));
  }

  @Test
  public void dtoToCompanyTest() {
    CompanyDto companyDto = new CompanyDto("657367", "gksdrt");
    Company company = CompanyMapper.COMPANYMAPPER.dtoToCompany(companyDto);
    
    assertTrue(company.getId() == 657367);
    assertTrue(company.getName().equals("gksdrt"));
    
    companyDto = new CompanyDto("sdfhg", "gksdrt");
    try {
      company = CompanyMapper.COMPANYMAPPER.dtoToCompany(companyDto);
      fail("Eception Was Not Thrown");
    } catch (MapperException e) {
      assertTrue(e.getMessage().contains(" must be a long"));
    }
  }
}
