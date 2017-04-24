package com.excilys.arnaud.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ComputerDto {

  
  private String id;
  @NotNull
  @Size(min=1,max=50)
  private String name;
  private CompanyDto company;
  private String introduced;
  private String discontinued;

  public ComputerDto(String id, String name, 
      CompanyDto company, String introduced, String discontinued) {
    this.id = id;
    this.name = name;

    this.company = company;

    this.introduced = introduced;
    this.discontinued = discontinued;
  }

  public ComputerDto(String name, CompanyDto company, String introduced, String discontinued) {
    this("", name, company, introduced, discontinued);
  }

  @Override
  public String toString() {
    String string = id + "\t" + name + "\t\t\t";
    if (company != null) {
      string += company.getName();
    } else {
      string += "???";
    }
    string += "\t\t";
    if (introduced != null) {
      string += introduced + "\t";
      if (discontinued != null) {
        string += discontinued;
      } else {
        string += "???";
      }
    } else {
      string += "???\t\t???";
    }

    return string;
  }

  public String getName() {
    return name;
  }

  public String getIntroduced() {
    return introduced;
  }

  public String getDiscontinued() {
    return discontinued;
  }

  public String getId() {
    return id;
  }

  public CompanyDto getCompany() {
    return company;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCompany(CompanyDto company) {
    this.company = company;
  }

  public void setIntroduced(String introduced) {
    this.introduced = introduced;
  }

  public void setDiscontinued(String discontinued) {
    this.discontinued = discontinued;
  }
  
  
}
