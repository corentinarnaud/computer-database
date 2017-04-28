package com.excilys.arnaud.dto;

public class CompanyDto {
  private String   id;
  private String name;

  public CompanyDto(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String toString() {
    return id + "\t" + name;
  }

 

}