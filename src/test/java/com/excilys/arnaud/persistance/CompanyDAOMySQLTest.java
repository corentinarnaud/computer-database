package com.excilys.arnaud.persistance;

import static org.junit.Assert.*;

import com.excilys.arnaud.model.Company;
import com.excilys.arnaud.model.CompanyList;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class CompanyDAOMySQLTest {
  private CompanyDAOMySQL companyDAOMySQL = CompanyDAOMySQL.CONPANYDAO;

  @Test
  public void findByIdTest() {
    Optional<Company> company;

    company = companyDAOMySQL.findById(1);
    assertTrue(company.isPresent());
    assertTrue(company.get().getId() == 1);
    assertTrue(company.get().getName().compareTo("Apple Inc.") == 0);

    company = companyDAOMySQL.findById(-1);
    assertFalse(company.isPresent());

    company = companyDAOMySQL.findById(963);
    assertFalse(company.isPresent());
  }

  @Test
  public void findByIdName() {
    Optional<Company> company;

    company = companyDAOMySQL.findByName("Apple Inc.");
    assertTrue(company.isPresent());
    assertTrue(company.get().getId() == 1);
    assertTrue(company.get().getName().compareTo("Apple Inc.") == 0);

    company = companyDAOMySQL.findByName("hswbbsnusn");
    assertFalse(company.isPresent());

    company = companyDAOMySQL.findByName(null);
    assertFalse(company.isPresent());

  }

  @Test
  public void getCompaniesTest() {
    CompanyList companyList = companyDAOMySQL.getCompanies();
    assertFalse(companyList == null);
    assertTrue(companyList.size() == 42);
    assertTrue(companyList.get(5).getId() == 5 + 1);
  }

}
