package com.excilys.arnaud.model.dto;

import java.util.List;

public class ComputerPage extends Page<ComputerDto> {
  private String pattern;
  private int order;
  
  public ComputerPage(List<ComputerDto> list, int nbElement, int currentPage, String pattern, int order) {
    super(list, nbElement, currentPage);
    this.pattern = pattern;
    this.order = order;
  }

  public String getPattern() {
    return pattern;
  }

  public int getOrder() {
    return order;
  }

}
