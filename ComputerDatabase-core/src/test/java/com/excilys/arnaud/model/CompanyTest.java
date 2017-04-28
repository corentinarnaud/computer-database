package com.excilys.arnaud.model;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CompanyTest {
  
  
  @Test
  public void companyTest() {
    Company company = new Company(10, "toto");
    assertNotNull(company);
  }

}
