package com.excilys.arnaud.persistence;



import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.excilys.arnaud.persistence.exception.DAOConfigurationException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.excilys.arnaud.persistence")
//@PropertySource({ "classpath:config.properties", "classpath:/hibernate.properties"})
public class PersistenceConfig {
  private static final String PROPERTY_DATASOURCE_FILE      = "config.properties";
  private static final String PROPERTY_DATASOURCE_HIBERNATE = "hibernate.properties";
  private static final String PROPERTY_BASE                 = "base";
  private static final String PROPERTY_URL                  = "url";
  private static final String PROPERTY_ARGUMENTS            = "arguments";
  private static final String PROPERTY_DRIVER               = "driver";
  private static final String PROPERTY_USER                 = "user";
  private static final String PROPERTY_PASSWORD             = "password";
  private static final String PROPERTY_NB_POOL              = "maximumPoolSize";
  private static final String PROPERTY_DIALECT              = "hibernate.dialect";
  private static final String PROPERTY_SHOW_SQL             = "hibernate.show_sql";
  private static final String PROPERTY_BATCH_SIZE           = "hibernate.batch.size";
  private static final String PROPERTY_HBM2DDL_AUTO         = "hibernate.hbm2ddl.auto";
  private static final String PROPERTY_SESSION_CONTEXT      = "hibernate.current.session.context.class";
  private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceConfig.class);

  @Autowired
  Environment env;

  @Bean
  public DataSource dataSource() throws IllegalArgumentException {
    Properties properties = getProperties(PROPERTY_DATASOURCE_FILE);
    HikariConfig cfg = new HikariConfig();
    cfg.setDriverClassName(properties.getProperty(PROPERTY_DRIVER));
    cfg.setJdbcUrl(properties.getProperty(PROPERTY_BASE) + properties.getProperty(PROPERTY_URL) + properties.getProperty(PROPERTY_ARGUMENTS));
    cfg.setUsername(properties.getProperty(PROPERTY_USER));
    cfg.setPassword(properties.getProperty(PROPERTY_PASSWORD));
    cfg.setMaximumPoolSize(Integer.parseInt(properties.getProperty(PROPERTY_NB_POOL)));
    LOGGER.info("Database created");
    return new HikariDataSource(cfg);
  }
  
  
  private Properties getProperties(String propertyFile){

    Properties properties = new Properties();
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    InputStream fichierProperties = classLoader
        .getResourceAsStream(propertyFile);
    if (fichierProperties == null) {
      throw new DAOConfigurationException(
          "Property file " + propertyFile + " not found.");
    }

    try {
      properties.load(fichierProperties);
    } catch (IOException e) {
      LOGGER.debug("Fail to load property file " + propertyFile);
      throw new DAOConfigurationException(
          "Fail to load property file " + propertyFile, e);
    }
    
    return properties;
  }
  
  private Properties getHibernateProperties() {
    Properties properties = getProperties(PROPERTY_DATASOURCE_HIBERNATE);

    properties.put(AvailableSettings.DIALECT, properties.getProperty(PROPERTY_DIALECT));
    properties.put(AvailableSettings.SHOW_SQL, properties.getProperty(PROPERTY_SHOW_SQL));
    properties.put(AvailableSettings.STATEMENT_BATCH_SIZE, properties.getProperty(PROPERTY_BATCH_SIZE));
    properties.put(AvailableSettings.HBM2DDL_AUTO, properties.getProperty(PROPERTY_HBM2DDL_AUTO));
    properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, properties.getProperty(PROPERTY_SESSION_CONTEXT));
    return properties;
  }
  
  @Autowired
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManager(DataSource dataSource) {
      final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(dataSource);
      em.setPackagesToScan(new String[] { "com.excilys.arnaud.model" });
      final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      em.setJpaVendorAdapter(vendorAdapter);
      em.setJpaProperties(getHibernateProperties());
      return em;
  }

  @Bean
  @Autowired
  public JpaTransactionManager txManager(final EntityManagerFactory emf) {
      final JpaTransactionManager txManager = new JpaTransactionManager();
      txManager.setEntityManagerFactory(emf);
      return txManager;
  }




}
