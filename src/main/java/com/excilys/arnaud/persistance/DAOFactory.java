package com.excilys.arnaud.persistance;

import com.excilys.arnaud.persistance.mysql.CompanyDAOMySQL;
import com.excilys.arnaud.persistance.mysql.ComputerDAOMySQL;

public enum DAOFactory {
  DAOFACTORY;

  public CompanyDAO getCompanyDAO() {
    return CompanyDAOMySQL.CONPANYDAO;
  }

  public ComputerDAO getComputerDAO() {
    return ComputerDAOMySQL.COMPUTERDAO;
  }

}
