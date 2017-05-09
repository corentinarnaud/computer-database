package com.excilys.arnaud.dto;

public class CompanyDto {
  private String   id;
  private String name;

  public CompanyDto(){
    
  }
  
  public CompanyDto(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
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