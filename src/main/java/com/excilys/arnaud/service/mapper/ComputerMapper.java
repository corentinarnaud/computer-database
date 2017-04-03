package com.excilys.arnaud.service.mapper;

import com.excilys.arnaud.model.dto.CompanyDto;
import com.excilys.arnaud.model.dto.ComputerDto;
import com.excilys.arnaud.model.dto.ComputerDtoList;
import com.excilys.arnaud.model.metier.Company;
import com.excilys.arnaud.model.metier.Computer;
import com.excilys.arnaud.model.metier.ComputerList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;



public enum ComputerMapper {
  COMPUTERMAPPER;
  
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  
  /** Map computer dto to computer metier.
   * @param computerDto A computer dto
   * @return The computer metier that correspond to computerDto
   */
  public Computer computerDtoToComputer(ComputerDto computerDto) {
    LocalDateTime introduced;
    LocalDateTime discontinued;
    long id = 0;
    Company company;
    
    try {
      if (computerDto.getId() != null && computerDto.getId() != "" ) {
        id = Long.parseLong(computerDto.getId());
      }
      
      if (computerDto.getIntroduced() != null && !computerDto.getIntroduced().equals("")) {
        try {
          introduced = LocalDateTime.from(LocalDate.parse(
              computerDto.getIntroduced(), formatter).atStartOfDay());
        } catch (DateTimeParseException e) {
          throw new MapperException(computerDto.getIntroduced() + " must be at format dd/MM/yyyy");
        }
      
        if (computerDto.getDiscontinued() != null && !computerDto.getDiscontinued().equals("")) {
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
      
      return new Computer(id,computerDto.getName(),
                                       company,
                                       introduced, discontinued);
    } catch (NumberFormatException e) {
      throw new MapperException(computerDto.getId() + " can't be parse to long");
    }
  }
  
  /** Map computer metier to computer dto.
   * @param computer The computer metier to map
   * @return the computer dto mapped
   */
  public ComputerDto computerToComputerDto(Computer computer) {
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
    
    return new ComputerDto(String.valueOf(computer.getId()), computer.getName(),
                                          company, introduced, discontinued);
  }
  
  /** Map a computerList to a computerDtoList.
   * 
   * @param computerList a list of computer, not null
   * @return A computer list who correspond to computerDto list in input
   */
  public ComputerDtoList computerListToComputerDtoList(ComputerList computerList) {
    ComputerDtoList computerDtoList = new ComputerDtoList();
    for (int i = 0;i < computerList.size(); i++) {
      computerDtoList.add(i, computerToComputerDto(computerList.get(i)));
    }
    return computerDtoList;
  }

}
