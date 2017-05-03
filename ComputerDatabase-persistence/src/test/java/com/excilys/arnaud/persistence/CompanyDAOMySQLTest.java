package com.excilys.arnaud.persistence;

import static org.junit.Assert.*;

import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.arnaud.persistence.CompanyDAO;
import com.excilys.arnaud.model.Company;
import com.excilys.arnaud.model.CompanyList;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class)
public class CompanyDAOMySQLTest {
  @Autowired
  private CompanyDAO companyDAOMySQL;
/*
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
*/
  @Test
  public void getCompaniesTest() {
    CompanyList companyList = companyDAOMySQL.getCompanies();
    assertFalse(companyList == null);
    assertTrue(companyList.size() == 43);
    assertTrue(companyList.get(5).getId() == 5 + 1);
  }
  /*
  @Test
  public void getCompaniesNTestNormalCase() {
    CompanyList companyList = companyDAOMySQL.getNCompanies(10, 10);
    assertFalse(companyList == null);
    assertTrue(companyList.size() == 10);
    assertTrue(companyList.get(5).getId() == 15 + 1);
  }
  
  
  @Test
  public void getCompaniesNTestNegatifBegin() {
    CompanyList companyList = companyDAOMySQL.getNCompanies(-12, 10);
    assertFalse(companyList == null);
    assertTrue(companyList.size() == 10);
    assertTrue(companyList.get(5).getId() == 5 + 1);
  }
  
  @Test
  public void getCompaniesNTestNegatifNumber() {
    CompanyList companyList = companyDAOMySQL.getNCompanies(0, -10);
    assertFalse(companyList == null);
    assertTrue(companyList.size() == 43);
    assertTrue(companyList.get(5).getId() == 5 + 1);
  }
  
  @Test
  public void getNumberOfCompanyTest(){
    int nb = companyDAOMySQL.getNumberOfCompany();
    assertTrue(nb == 43);
  }
  
  @Test
  public void delCompanyTestThrowError(){
    try {
      assertFalse(companyDAOMySQL.delCompany(1));
    } catch (Exception e){
      e.printStackTrace();
    }
  }
*/
}
