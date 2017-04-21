package com.excilys.arnaud.persistance;

import static org.junit.Assert.*;

import com.excilys.arnaud.model.metier.Company;
import com.excilys.arnaud.model.metier.CompanyList;
import com.excilys.arnaud.persistance.implem.CompanyDAOMySQL;
import com.excilys.arnaud.springConfig.MainConfig;

import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MainConfig.class)
public class CompanyDAOMySQLTest {
  @Autowired
  private CompanyDAO companyDAOMySQL;

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
