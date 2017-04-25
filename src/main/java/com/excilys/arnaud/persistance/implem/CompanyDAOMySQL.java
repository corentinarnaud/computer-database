package com.excilys.arnaud.persistance.implem;

import com.excilys.arnaud.model.metier.Company;
import com.excilys.arnaud.model.metier.CompanyList;
import com.excilys.arnaud.persistance.CompanyDAO;
import com.excilys.arnaud.persistance.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyDAOMySQL implements CompanyDAO {

  private static final String SQL_FIND_ID = "SELECT id, name  FROM company WHERE id = ?";
  private static final String SQL_FIND_NAME = "SELECT id, name  FROM company WHERE name=?";
  private static final String SQL_COMPANIES = "SELECT id, name  FROM company";
  private static final String SQL_N_COMPANIES = "SELECT id, name  FROM company LIMIT ?, ?";
  private static final String SQL_COUNT = "SELECT COUNT(*) FROM company";
  private static final String SQL_DEL = "DELETE FROM computer WHERE id = ?";
  private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAOMySQL.class);

  private JdbcTemplate jdbcTemplate;

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public CompanyDAOMySQL() {
    LOGGER.info("CompanyDao mysql instanciated");
  }

  @Override
  public Optional<Company> findById(long id) throws DAOException {
    try {
      return Optional.ofNullable(this.jdbcTemplate.queryForObject(SQL_FIND_ID, new CompanyMapper(), id));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    } catch (DataAccessException e) {
      LOGGER.debug(e.getMessage());
      throw new DAOException(e);
    }
  }

  @Override
  public Optional<Company> findByName(String name) throws DAOException {
    try {
      return Optional.ofNullable(this.jdbcTemplate.queryForObject(SQL_FIND_NAME, new CompanyMapper(), name));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    } catch (DataAccessException e) {
      LOGGER.debug(e.getMessage());
      throw new DAOException(e);
    }
  }

  @Override
  public CompanyList getCompanies() throws DAOException {
    try {
      return new CompanyList(this.jdbcTemplate.query(SQL_COMPANIES, new CompanyMapper()));
    } catch (DataAccessException e) {
      LOGGER.debug(e.getMessage());
      throw new DAOException(e);
    }
  }

  @Override
  public CompanyList getNCompanies(int begin, int nbCompanies) throws DAOException {
    try {
      return new CompanyList(this.jdbcTemplate.query(SQL_N_COMPANIES, new CompanyMapper(), begin, nbCompanies));
    } catch (DataAccessException e) {
      LOGGER.debug(e.getMessage());
      throw new DAOException(e);
    }
  }

  @Override
  public int getNumberOfCompany() {
    try {
      return this.jdbcTemplate.queryForObject(SQL_COUNT, Integer.class);
    } catch (DataAccessException e) {
      LOGGER.debug(e.getMessage());
      throw new DAOException(e);
    }
  }

  @Override
  public boolean delCompany(long id) {
    try {
      int resultat = this.jdbcTemplate.update(SQL_DEL);
      if (resultat == 1) {
        LOGGER.info("Company {} deleted", id);
        return true;
      } else {
        LOGGER.info("Company {} not deleted", id);
        return false;
      }
    } catch (DataAccessException e) {
      LOGGER.debug(e.getMessage());
      throw new DAOException(e);
    }
  }

  private static final class CompanyMapper implements RowMapper<Company> {

    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new Company(Long.parseLong(rs.getString("id")), rs.getString("name"));
    }
  }

}
