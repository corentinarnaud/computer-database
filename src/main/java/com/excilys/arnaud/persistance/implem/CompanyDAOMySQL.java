package com.excilys.arnaud.persistance.implem;

import com.excilys.arnaud.model.metier.Company;
import com.excilys.arnaud.model.metier.CompanyList;
import com.excilys.arnaud.persistance.CompanyDAO;
import com.excilys.arnaud.persistance.DAOUtils;
import com.excilys.arnaud.persistance.DataBaseManager;
import com.excilys.arnaud.persistance.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyDAOMySQL implements CompanyDAO {

  private static final String SQL_FIND_ID   = "SELECT name  FROM company WHERE id=?";
  private static final String SQL_FIND_NAME = "SELECT id  FROM company WHERE name=?";
  private static final String SQL_COMPANIES = "SELECT id, name  FROM company";
  private static final String SQL_N_COMPANIES = "SELECT id, name  FROM company LIMIT ?, ?";
  private static final String SQL_COUNT     = "SELECT COUNT(*) FROM company";
  private static final String SQL_DEL = "DELETE FROM computer WHERE id=?";
  private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAOMySQL.class);
  @Autowired
  private DataBaseManager database;
  
  
  private JdbcTemplate jdbcTemplate;

  @Autowired
  public void setDataSource(DataSource dataSource) {
      this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  
  public CompanyDAOMySQL(){
    LOGGER.info("CompanyDao mysql instanciated");
  }

  @Override
  public Optional<Company> findById(long id) throws DAOException {
    PreparedStatement statement = null;
    ResultSet resultat = null;
    Connection connection = null;

    try {
      connection = database.getConnection();
      statement = connection.prepareStatement(SQL_FIND_ID);
      statement.setLong(1, id);
      resultat = statement.executeQuery();
      if (resultat.next()) {
        return Optional.ofNullable(new Company(id, resultat.getString("name")));
      }

    } catch (SQLException e) {
      LOGGER.debug(e.getMessage());
      throw new DAOException(e);
    } finally {
      DAOUtils.closePreparedStatement(statement);
      DAOUtils.closeResultatSet(resultat);
      DAOUtils.closeConnection(connection);
    }
    return Optional.empty();
  }

  @Override
  public Optional<Company> findByName(String name) throws DAOException {
    PreparedStatement statement = null;
    ResultSet resultat = null;
    Connection connection = null;

    if (name != null) {
      try {
        connection = database.getConnection();
        statement = connection.prepareStatement(SQL_FIND_NAME);
        statement.setString(1, name);
        resultat = statement.executeQuery();
        if (resultat.next()) {
          return Optional.ofNullable(new Company(resultat.getLong("id"), name));
        }

      } catch (SQLException e) {
        LOGGER.debug(e.getMessage());
        throw new DAOException(e);
      } finally {
        DAOUtils.closePreparedStatement(statement);
        DAOUtils.closeResultatSet(resultat);
        DAOUtils.closeConnection(connection);
      }
    }
    return Optional.empty();
  }

  @Override
  public CompanyList getCompanies() throws DAOException {
    Statement statement = null;
    ResultSet resultat = null;
    Connection connection = null;

    try {
      connection = database.getConnection();
      statement = connection.createStatement();
      resultat = statement.executeQuery(SQL_COMPANIES);
      ArrayList<Company> list = new ArrayList<Company>();
      while (resultat.next()) {
        list.add(new Company(Integer.parseInt(resultat.getString("id")), 
                                              resultat.getString("name")));
      }
      return new CompanyList(list);

    } catch (SQLException e) {
      LOGGER.debug(e.getMessage());
      throw new DAOException(e);
    } finally {
      DAOUtils.closeStatement(statement);
      DAOUtils.closeResultatSet(resultat);
      DAOUtils.closeConnection(connection);
    }
  }

  @Override
  public CompanyList getNCompanies(int begin, int nbCompanies)
      throws DAOException {
    PreparedStatement statement = null;
    ResultSet resultat = null;
    Connection connection = null;

    try {
      connection = database.getConnection();
      statement = connection.prepareStatement(SQL_N_COMPANIES);
      statement.setInt(1, begin);
      statement.setInt(2, nbCompanies);
      resultat = statement.executeQuery();
      ArrayList<Company> list = new ArrayList<Company>();
      while (resultat.next()) {
        list.add(new Company(Integer.parseInt(resultat.getString("id")), 
                                              resultat.getString("name")));
      }
      return new CompanyList(list);

    } catch (SQLException e) {
      LOGGER.debug(e.getMessage());
      throw new DAOException(e);
    } finally {
      DAOUtils.closePreparedStatement(statement);
      DAOUtils.closeResultatSet(resultat);
      DAOUtils.closeConnection(connection);
    }
  }

  @Override
  public int getNumberOfCompany() {
    Statement statement = null;
    ResultSet resultat = null;
    Connection connection = null;

    try {
      connection = database.getConnection();
      statement = connection.createStatement();
      resultat = statement.executeQuery(SQL_COUNT);
      resultat.next();

      return resultat.getInt(1);

    } catch (SQLException e) {
      LOGGER.debug(e.getMessage());
      throw new DAOException(e);
    } finally {
      DAOUtils.closeStatement(statement);
      DAOUtils.closeResultatSet(resultat);
      DAOUtils.closeConnection(connection);
    }
  }

  @Override
  public boolean delCompany(long id) {
    PreparedStatement statement = null;
    Connection connection = null;

    try {
      connection = database.getConnection();
      statement = connection.prepareStatement(SQL_DEL);
      statement.setString(1, String.valueOf(id));
      int resultat = statement.executeUpdate();
      if (resultat == 1) {
        LOGGER.info("Company {} deleted", id);
        return true;
      } else {
        LOGGER.info("Company {} not deleted", id);
        return false;
      }
    } catch (SQLException e) {
      LOGGER.debug(e.getMessage());
      throw new DAOException(e);
    } finally {
      DAOUtils.closePreparedStatement(statement);
      DAOUtils.closeConnection(connection);
    }
  }

}
