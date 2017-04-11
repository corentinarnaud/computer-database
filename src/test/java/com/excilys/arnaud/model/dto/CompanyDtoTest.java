package com.excilys.arnaud.model.dto;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CompanyDtoTest {
  
  @Test
  public void constructerTest() {
    String id = "658763";
    String name = "nifougqhçp";
    CompanyDto company = new CompanyDto(id, name);
    
    assertTrue(company.getId() == id);
    assertTrue(company.getName() == name);
  }

}
