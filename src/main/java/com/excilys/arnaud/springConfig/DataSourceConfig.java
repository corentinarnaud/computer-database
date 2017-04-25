package com.excilys.arnaud.springConfig;



import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.excilys.arnaud.persistance.exception.DAOConfigurationException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
//@PropertySource({ "classpath:/../resources/config.properties" })
public class DataSourceConfig {
  private static final String PROPERTY_FILE      = "config.properties";
  private static final String PROPERTY_BASE      = "base";
  private static final String PROPERTY_URL       = "url";
  private static final String PROPERTY_ARGUMENTS = "arguments";
  private static final String PROPERTY_DRIVER    = "driver";
  private static final String PROPERTY_USER      = "user";
  private static final String PROPERTY_PASSWORD  = "password";
  private static final String PROPERTY_NB_POOL   = "maximumPoolSize";

  private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

  
  private Properties properties;

  @Bean
  public DataSource dataSource() throws IllegalArgumentException {
    getProperties();
    HikariConfig cfg = new HikariConfig();
    cfg.setDriverClassName(properties.getProperty(PROPERTY_DRIVER));
    cfg.setJdbcUrl(properties.getProperty(PROPERTY_BASE) + properties.getProperty(PROPERTY_URL) + properties.getProperty(PROPERTY_ARGUMENTS));
    cfg.setUsername(properties.getProperty(PROPERTY_USER));
    cfg.setPassword(properties.getProperty(PROPERTY_PASSWORD));
    cfg.setMaximumPoolSize(Integer.parseInt(properties.getProperty(PROPERTY_NB_POOL)));
    
    LOGGER.info("Database created");

    return new HikariDataSource(cfg);
  }
  
  
  private void getProperties(){

    properties = new Properties();
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    InputStream fichierProperties = classLoader
        .getResourceAsStream(PROPERTY_FILE);

    if (fichierProperties == null) {
      throw new DAOConfigurationException(
          "Property file " + PROPERTY_FILE + " not found.");
    }

    try {
      properties.load(fichierProperties);
    } catch (IOException e) {
      LOGGER.debug("Fail to load property file " + PROPERTY_FILE);
      throw new DAOConfigurationException(
          "Fail to load property file " + PROPERTY_FILE, e);
    }

  }

  /*
   * @Bean
   * 
   * @Profile("test") public DataSource testDataSource() { return new
   * EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build(); }
   */
}
