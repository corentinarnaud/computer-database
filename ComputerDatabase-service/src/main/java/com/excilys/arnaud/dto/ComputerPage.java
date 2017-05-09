package com.excilys.arnaud.dto;

import java.util.List;

public class ComputerPage extends Page<ComputerDto> {
  private String pattern;
  private int order;
  
  /**
   * @param list : List of computer
   * @param nbElement : Total number of computer
   * @param currentPage : Current page
   * @param pattern : Pattern of the search by name, empty for all
   * @param order : number to the column where the order by is make
   */
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
