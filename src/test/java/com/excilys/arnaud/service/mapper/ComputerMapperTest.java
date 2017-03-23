package com.excilys.arnaud.service.mapper;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.excilys.arnaud.model.dto.ComputerDto;
import com.excilys.arnaud.model.metier.Computer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Test;



public class ComputerMapperTest {
  
  @Test
  public void mapDtoToMetierTest() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String beforeDate = "12/08/1254";
    String afterDate = "12/08/2135";
    LocalDateTime introduced = LocalDateTime.from(LocalDate.parse(
        beforeDate, formatter).atStartOfDay());
    LocalDateTime discontinued = LocalDateTime.from(LocalDate.parse(
        afterDate, formatter).atStartOfDay());
    ComputerDto computerDto = new ComputerDto("15656", "dsghsrhges", null, beforeDate, afterDate);
    Computer resultat = new Computer(15656, "dsghsrhges", null, introduced, discontinued);
    Computer computer = ComputerMapper.COMPUTERMAPPER.mapDtoToMetier(computerDto);
    
    assertTrue(computer.equals(resultat));
    
    computerDto = new ComputerDto("15656", "dsghsrhges", null, "sdgqsgg", null);
    try {
      computer = ComputerMapper.COMPUTERMAPPER.mapDtoToMetier(computerDto);
      fail("Eception Was Not Thrown");
    } catch (MapperException e) {
      assertTrue(e.getMessage().contains(" must be at format dd/MM/yyyy"));
    }
    
    computerDto = new ComputerDto("15656", "dsghsrhges", null, "10/12/1235", "sdgqsgg");
    try {
      computer = ComputerMapper.COMPUTERMAPPER.mapDtoToMetier(computerDto);
      fail("Eception Was Not Thrown");
    } catch (MapperException e) {
      assertTrue(e.getMessage().contains(" must be at format dd/MM/yyyy"));
    }
    
    computerDto = new ComputerDto("vsgswr", "dsghsrhges", null, null, null);
    try {
      computer = ComputerMapper.COMPUTERMAPPER.mapDtoToMetier(computerDto);
      fail("Eception Was Not Thrown");
    } catch (MapperException e) {
      assertTrue(e.getMessage().contains(" can't be parse to long"));
    }
  }
  
  
  @Test
  public void mapMetierToDtoTest() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String beforeDate = "12/08/1254";
    String afterDate = "12/08/2135";
    LocalDateTime introduced = LocalDateTime.from(LocalDate.parse(
        beforeDate, formatter).atStartOfDay());
    LocalDateTime discontinued = LocalDateTime.from(LocalDate.parse(
        afterDate, formatter).atStartOfDay());
    Computer computer = new Computer(15656, "dsghsrhges", null, introduced, discontinued);
    ComputerDto computerDto = ComputerMapper.COMPUTERMAPPER.mapMetierToDto(computer);

    assertTrue(computerDto.getId().equals("15656"));
    assertTrue(computerDto.getName().equals("dsghsrhges"));
    assertTrue(computerDto.getIntroduced().equals(beforeDate));
    assertTrue(computerDto.getDiscontinued().equals(afterDate));
    assertTrue(computerDto.getCompany() == null);
    
  }

}
