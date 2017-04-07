package com.excilys.arnaud.persistance;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public enum DataBaseConnection {
  CONNECTION;
  private static final String PROPERTY_FILE      = "resources/config.properties";
  private static final String PROPERTY_BASE      = "base";
  private static final String PROPERTY_URL       = "url";
  private static final String PROPERTY_ARGUMENTS = "arguments";
  private static final String PROPERTY_DRIVER    = "driver";
  private static final String PROPERTY_USER      = "user";
  private static final String PROPERTY_PASSWORD  = "password";
  private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseConnection.class);
  private static final ThreadLocal<Connection>  threadLocal = new ThreadLocal<>(); 
  
  
  private String url;
  private String driver;
  private String user;
  private String password;
  private String base;
  private String arguments;
  private HikariDataSource ds;

  private DataBaseConnection() {
    getProperties();
    initialistation();
    
  }
  
  private void initialistation() {
    try {
      Class.forName(driver);
      HikariConfig cfg = new HikariConfig();
      cfg.setJdbcUrl(base + url + arguments);
      cfg.setUsername(user);
      cfg.setPassword(password);
      cfg.setMaximumPoolSize(100);
      ds = new HikariDataSource(cfg);
      
      // To close the datasource when the server is closing
      Runtime.getRuntime().addShutdownHook(new Thread() {
          @Override
          public void run() {
              DataBaseConnection.this.ds.close();
          }
      });
      
    } catch (ClassNotFoundException e) {
      LOGGER.debug("Driver not found");
      throw new DAOConfigurationException("Driver not found", e);
    }
  }

  private void getProperties() throws DAOConfigurationException {

    Properties properties = new Properties();
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    InputStream fichierProperties = classLoader
        .getResourceAsStream(PROPERTY_FILE);

    if (fichierProperties == null) {
      throw new DAOConfigurationException(
          "Property file " + PROPERTY_FILE + " not found.");
    }

    try {
      properties.load(fichierProperties);
      url = properties.getProperty(PROPERTY_URL);
      driver = properties.getProperty(PROPERTY_DRIVER);
      base = properties.getProperty(PROPERTY_BASE);
      arguments = properties.getProperty(PROPERTY_ARGUMENTS);
      user = properties.getProperty(PROPERTY_USER);
      password = properties.getProperty(PROPERTY_PASSWORD);
    } catch (IOException e) {
      LOGGER.debug("Fail to load property file " + PROPERTY_FILE);
      throw new DAOConfigurationException(
          "Fail to load property file " + PROPERTY_FILE, e);
    }

  }


  
  /** .
   * @return a connection
   */
  public Connection getConnection() {

    try { 
      if (threadLocal.get() == null || threadLocal.get().isClosed()) {
        threadLocal.set(ds.getConnection());
      }
    } catch (SQLException e) {
      LOGGER.debug("Fail to connect to the base");
      throw new DAOException("Fail to connect to the base", e);
    }
    return threadLocal.get();
  }

}
