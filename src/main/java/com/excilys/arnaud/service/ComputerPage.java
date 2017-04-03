package com.excilys.arnaud.service;

import com.excilys.arnaud.model.dto.ComputerDto;
import com.excilys.arnaud.model.dto.ComputerDtoList;
import com.excilys.arnaud.persistance.DAOFactory;
import com.excilys.arnaud.service.mapper.ComputerMapper;


public class ComputerPage extends Page<ComputerDto> {
  private String pattern;
  private ComputerDtoList list;
  private int orderBy;
  
  /** Constructor.
   * 
   */
  public ComputerPage() {
    this("");
  }
  
  /** Constructor.
   * @param pattern the pattern of the page, not null
   */
  public ComputerPage(String pattern) {
    this.pattern = pattern;
    this.nbElement = DAOFactory.DAOFACTORY.getComputerDAO().getNumberOfComputer(this.pattern);
    nbPage = (int) Math.ceil((float) nbElement / ELMEMENT_BY_PAGE);
    currentPage = 0;
    orderBy = 0;
  }
 
  @Override
  public ComputerDtoList getPage() {
    int begin = ELMEMENT_BY_PAGE * currentPage;
    this.list = ComputerMapper.COMPUTERMAPPER.computerListToComputerDtoList(
        DAOFactory.DAOFACTORY.getComputerDAO().getNComputers(pattern, begin, ELMEMENT_BY_PAGE, orderBy));
    this.nbElement = DAOFactory.DAOFACTORY.getComputerDAO().getNumberOfComputer(pattern);
    nbPage = (int) Math.ceil((float) nbElement / ELMEMENT_BY_PAGE);
    return list;
  }
  

  @Override
  public ComputerDtoList getPageN(int n) {
    if (n >= 0 && n < nbPage) {
      currentPage = n;
    }
    return getPage();
  }
  
  
  /** set the pattern of the page.
   * @param pattern String  
   */
  public void setPattern(String pattern) {
    if (pattern != null) {
      if (!pattern.isEmpty()) {
        this.pattern = pattern;
      } else {
        this.pattern = null;
      }
      this.nbElement = DAOFactory.DAOFACTORY.getComputerDAO().getNumberOfComputer(this.pattern);
      nbPage = (int) Math.ceil((float) nbElement / ELMEMENT_BY_PAGE);
      currentPage = 0;
    }
  }
  
  public void setOrderBy(int orderBy) {
    this.orderBy = orderBy;
  }
  


  @Override
  public void setElementByPage(int numberOfElement) {
    if (this.ELMEMENT_BY_PAGE != numberOfElement) {
      this.ELMEMENT_BY_PAGE = numberOfElement;
      this.nbElement = DAOFactory.DAOFACTORY.getComputerDAO().getNumberOfComputer(this.pattern);
      this.nbPage = (int) Math.ceil((float) nbElement / this.ELMEMENT_BY_PAGE);
      currentPage = 0;
    }
  }
  
  public String getPattern() {
    return this.pattern;
  }

}
