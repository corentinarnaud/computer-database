package com.excilys.arnaud.model.dto;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ComputerDtoTest {
  
  @Test
  public void constructerComputerDto() {
    String id = "156867+7";
    String name = "qiofhza";
    CompanyDto company = null;
    String introduced = null;
    String discontinued = null;
    
    ComputerDto computer = new ComputerDto(id, name, company, introduced, discontinued);
    assertTrue(computer.getName() == name);
    assertTrue(computer.getCompany() == company);
    assertTrue(computer.getIntroduced() == introduced);
    assertTrue(computer.getDiscontinued() == discontinued);
  }

}
