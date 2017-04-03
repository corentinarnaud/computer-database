package com.excilys.arnaud.servlets.tags;

import java.io.IOException;

import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Link extends SimpleTagSupport {
  private int page = 1;
  private int nbElements = 10;
  private String search = "";
  
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
  
  @Override
  public void doTag() throws IOException {
    String link = "/ComputerDatabase/dashboard?page=" + page;
    if (nbElements != 10 && nbElements != 0) {
      link += "&elements=" + nbElements;
    }
    if (search.compareTo("") != 0) {
      link += "&search=" + search;
    }
    
    getJspContext().getOut().write(link);
  }
}
