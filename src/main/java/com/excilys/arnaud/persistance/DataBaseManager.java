package com.excilys.arnaud.persistance;

import com.excilys.arnaud.persistance.exception.DAOException;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DataBaseManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseManager.class);
  private static final ThreadLocal<Connection>  threadLocal = new ThreadLocal<>(); 
  
  
  
  @Autowired
  private DataSource dataSource;

  public DataBaseManager() {
    LOGGER.info("manager created");
  }


  
  /** .
   * @return a connection
   */
  public Connection getConnection() {

    try { 
      if (threadLocal.get() == null || threadLocal.get().isClosed()) {
        threadLocal.set(dataSource.getConnection());
      }
    } catch (SQLException e) {
      LOGGER.debug("Fail to connect to the base");
      throw new DAOException("Fail to connect to the base", e);
    }
    return threadLocal.get();
  }
  
  public void rollBack(){
    try {
      if ( threadLocal.get() != null && !threadLocal.get().isClosed()) {
        threadLocal.get().rollback();
      }
    } catch (SQLException e) {
      LOGGER.debug("Fail to connect to roll back");
      e.printStackTrace();
    }
  }

}
