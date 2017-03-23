package com.excilys.arnaud.service.mapper;

import com.excilys.arnaud.model.dto.CompanyDto;
import com.excilys.arnaud.model.dto.ComputerDto;
import com.excilys.arnaud.model.metier.Company;
import com.excilys.arnaud.model.metier.Computer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;



public enum ComputerMapper {
  COMPUTERMAPPER;
  
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  
  public Computer mapDtoToMetier(ComputerDto computerDto) {
    LocalDateTime introduced;
    LocalDateTime discontinued;
    Company company;
    
    try {
      if (computerDto.getIntroduced() != null) {
        try {
          introduced = LocalDateTime.from(LocalDate.parse(
              computerDto.getIntroduced(), formatter).atStartOfDay());
        } catch (DateTimeParseException e) {
          throw new MapperException(computerDto.getIntroduced() + " must be at format dd/MM/yyyy");
        }
      
        if (computerDto.getDiscontinued() != null) {
          try {
            discontinued = LocalDateTime.from(LocalDate.parse(
              computerDto.getDiscontinued(), formatter).atStartOfDay());
          } catch (DateTimeParseException e) {
            throw new MapperException(computerDto.getDiscontinued() 
                + " must be at format dd/MM/yyyy");
          }
        } else {
          discontinued = null;
        }
      } else {
        introduced = null;
        discontinued = null;
      }
      
      if (computerDto.getCompany() != null) {
        company = CompanyMapper.COMPANYMAPPER.dtoToCompany(computerDto.getCompany());
      } else {
        company = null;
      }
      
      return new Computer(Long.parseLong(computerDto.getId()),computerDto.getName(),
                                       company,
                                       introduced, discontinued);
    } catch (NumberFormatException e) {
      throw new MapperException(computerDto.getId() + " can't be parse to long");
    }
  }
  
  public ComputerDto mapMetierToDto(Computer computer){
    String introduced;
    String discontinued;
    CompanyDto company;
    
    if (computer.getIntroduced() != null) {
      introduced = computer.getIntroduced().format(formatter);
      if (computer.getDiscontinued() != null) {
        discontinued = computer.getDiscontinued().format(formatter);
      } else {
        discontinued = null;
      }
    } else {
      discontinued = null;
      introduced = null;
    }
    
    if (computer.getCompany() != null) {
      company = new CompanyDto(String.valueOf(computer.getCompany().getId()),
                               computer.getCompany().getName());
    } else {
      company = null;
    }
    
    return new ComputerDto(String.valueOf(computer.getId()),
                                          company, introduced, discontinued);
  }

}
