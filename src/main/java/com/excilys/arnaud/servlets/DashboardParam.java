package com.excilys.arnaud.servlets;

public enum DashboardParam {
  PAGE("page"),
  SEARCH("search"),
  ELEMENTS("elements"),
  ORDER("order");
  
  private String name;
  
  private DashboardParam(String name){
    this.name= name;
  }

  @Override
  public String toString(){
    return name;
  }
}
