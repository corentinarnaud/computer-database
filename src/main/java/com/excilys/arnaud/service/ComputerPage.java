package com.excilys.arnaud.service;

import java.util.List;
import com.excilys.arnaud.model.Computer;
import com.excilys.arnaud.persistance.DAOFactory;

public class ComputerPage extends Page<Computer> {
  private String pattern = null;

  public ComputerPage() {
    this.list = DAOFactory.DAOFACTORY.getComputerDAO().getNComputers(pattern, 0, ELMEMENT_BY_PAGE);
    this.nbElement = DAOFactory.DAOFACTORY.getComputerDAO().getNumberOfComputer();
    nbPage=(int)Math.ceil((float)nbElement/ELMEMENT_BY_PAGE);
  }
  
  public ComputerPage(String pattern){
    this.pattern=pattern;
    this.list = DAOFactory.DAOFACTORY.getComputerDAO().getNComputers(pattern, 0, ELMEMENT_BY_PAGE);
    this.nbElement = DAOFactory.DAOFACTORY.getComputerDAO().getNumberOfComputer(pattern);
    nbPage=(int)Math.ceil((float)nbElement/ELMEMENT_BY_PAGE);
  }
  
  @Override
  public List<Computer> getNextPage() {
    if(currentPage<nbPage-1) {
      currentPage++;
      int begin = ELMEMENT_BY_PAGE*currentPage;
      this.list = DAOFactory.DAOFACTORY.getComputerDAO().getNComputers(pattern, begin, ELMEMENT_BY_PAGE);
    }
    return list;
  }

  @Override
  public List<Computer> getPrevPage() {
    if(currentPage>0) {
      currentPage--;
      int begin = ELMEMENT_BY_PAGE*currentPage;
      this.list = DAOFactory.DAOFACTORY.getComputerDAO().getNComputers(pattern, begin, ELMEMENT_BY_PAGE);
    }
    return list;
  }

  @Override
  public List<Computer> getPageN(int n) {
    if(n>=0 && n<nbPage){
      currentPage=n;
      int begin = ELMEMENT_BY_PAGE*currentPage;
      this.list = DAOFactory.DAOFACTORY.getComputerDAO().getNComputers(pattern, begin, ELMEMENT_BY_PAGE);
    }
    return list;
  }
  
  
  public void setPattern(String pattern){
    if(pattern != null){
      if(!pattern.isEmpty()){
        this.pattern=pattern;
      } else {
        this.pattern=null;
      }
      this.list = DAOFactory.DAOFACTORY.getComputerDAO().getNComputers(this.pattern, 0, ELMEMENT_BY_PAGE);
      this.nbElement = DAOFactory.DAOFACTORY.getComputerDAO().getNumberOfComputer(this.pattern);
      nbPage=(int)Math.ceil((float)nbElement/ELMEMENT_BY_PAGE);
      currentPage=0;
    }
  }
  
  public String getPattern(){
    return this.pattern;
  }

}
