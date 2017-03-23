package com.excilys.arnaud.service.mapper;

import com.excilys.arnaud.model.dto.ComputerDto;
import com.excilys.arnaud.model.metier.Company;
import com.excilys.arnaud.model.metier.Computer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;



public enum ComputerMapper {
  COMPUTERMAPPER;
  
  private DateTimeFormatter formatterDto = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  
  public Computer mapDtoTo(ComputerDto computerDto) {
    LocalDateTime introduced;
    LocalDateTime discontinued;
    Company company;
    
    try {
      if (computerDto.getIntroduced() != null) {
        try {
          introduced = LocalDateTime.from(LocalDate.parse(
              computerDto.getIntroduced(), formatterDto).atStartOfDay());
        } catch (DateTimeParseException e) {
          throw new MapperException(computerDto.getIntroduced() + " must be at format yyyy-MM-dd");
        }
      
        if (computerDto.getDiscontinued() != null) {
          try {
            discontinued = LocalDateTime.from(LocalDate.parse(
              computerDto.getDiscontinued(), formatterDto).atStartOfDay());
          } catch (DateTimeParseException e) {
            throw new MapperException(computerDto.getDiscontinued() 
                + " must be at format yyyy-MM-dd");
          }
        } else {
          discontinued = null;
        }
      } else {
        introduced = null;
        discontinued = null;
      }
      
      if (computerDto.getCompany() != null) {
        company = CompanyMapper.COMPANYMAPPER.DtoToCompany(computerDto.getCompany());
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

}
