package com.excilys.arnaud.servlets.tags;

import java.io.IOException;

import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Link extends SimpleTagSupport {
  private int page = 1;
  private int nbElements = 10;
  private String search = "";
  private int orderBy = 0;
  
  public Link() {
    
  }

  public void setPage(int page) {
    this.page = page;
  }
  
  public void setNbElements(int nbElements) {
    this.nbElements = nbElements;
  }
  
  public void setSearch(String search) {
    this.search = search;
  }
  
  public void setOrderBy(int orderBy) {
    this.orderBy = orderBy;
  }
  
  @Override
  public void doTag() throws IOException {
    String link = "/ComputerDatabase/dashboard?page=" + page;
    if (nbElements != 10 && nbElements != 0) {
      link += "&elements=" + nbElements;
    }
    if (search.compareTo("") != 0) {
      link += "&search=" + search;
    }
    if (orderBy > 0) {
      link += "&order=" + orderBy;
    }
    
    getJspContext().getOut().write(link);
  }
}
