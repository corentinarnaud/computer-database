package com.excilys.arnaud.persistance.mysql;

public enum ComputerAtrribute {
  ID("computer.id"),
  NAME("computer.name"),
  INTRODUCED("introduced"),
  DISCONTINUED("discontinued"),
  COMPANY("company.name");
  
  private String name;
  
  private ComputerAtrribute(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
}
