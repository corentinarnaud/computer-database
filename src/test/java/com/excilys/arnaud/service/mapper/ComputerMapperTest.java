package com.excilys.arnaud.service.mapper;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.excilys.arnaud.mapper.ComputerMapper;
import com.excilys.arnaud.mapper.MapperException;
import com.excilys.arnaud.model.dto.ComputerDto;
import com.excilys.arnaud.model.metier.Computer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Test;



public class ComputerMapperTest {
  
  @Test
  public void mapDtoToMetierTest() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String beforeDate = "1254-08-12";
    String afterDate = "2135-08-12";
    LocalDateTime introduced = LocalDateTime.from(LocalDate.parse(
        beforeDate, formatter).atStartOfDay());
    LocalDateTime discontinued = LocalDateTime.from(LocalDate.parse(
        afterDate, formatter).atStartOfDay());
    ComputerDto computerDto = new ComputerDto("15656", "dsghsrhges", null, beforeDate, afterDate);
    Computer resultat = new Computer(15656, "dsghsrhges", null, introduced, discontinued);
    Computer computer = ComputerMapper.computerDtoToComputer(computerDto);
    
    assertTrue(computer.equals(resultat));
    
    computerDto = new ComputerDto("15656", "dsghsrhges", null, "sdgqsgg", null);
    try {
      computer = ComputerMapper.computerDtoToComputer(computerDto);
      fail("Eception Was Not Thrown");
    } catch (MapperException e) {
      assertTrue(e.getMessage().contains(" must be at format yyyy-MM-dd"));
    }
    
    computerDto = new ComputerDto("15656", "dsghsrhges", null, "1235-12-10", "sdgqsgg");
    try {
      computer = ComputerMapper.computerDtoToComputer(computerDto);
      fail("Eception Was Not Thrown");
    } catch (MapperException e) {
      assertTrue(e.getMessage().contains(" must be at format yyyy-MM-dd"));
    }
    
    computerDto = new ComputerDto("vsgswr", "dsghsrhges", null, null, null);
    try {
      computer = ComputerMapper.computerDtoToComputer(computerDto);
      fail("Eception Was Not Thrown");
    } catch (MapperException e) {
      assertTrue(e.getMessage().contains(" can't be parse to long"));
    }
  }
  
  
  @Test
  public void mapMetierToDtoTest() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String beforeDate = "1254-08-12";
    String afterDate = "2135-08-12";
    LocalDateTime introduced = LocalDateTime.from(LocalDate.parse(
        beforeDate, formatter).atStartOfDay());
    LocalDateTime discontinued = LocalDateTime.from(LocalDate.parse(
        afterDate, formatter).atStartOfDay());
    Computer computer = new Computer(15656, "dsghsrhges", null, introduced, discontinued);
    ComputerDto computerDto = ComputerMapper.computerToComputerDto(computer);

    assertTrue(computerDto.getId().equals("15656"));
    assertTrue(computerDto.getName().equals("dsghsrhges"));
    assertTrue(computerDto.getIntroduced().equals(beforeDate));
    assertTrue(computerDto.getDiscontinued().equals(afterDate));
    assertTrue(computerDto.getCompany() == null);
    
  }

}
