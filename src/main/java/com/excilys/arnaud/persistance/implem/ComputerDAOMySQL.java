package com.excilys.arnaud.persistance.implem;

import com.excilys.arnaud.model.metier.Company;
import com.excilys.arnaud.model.metier.Computer;
import com.excilys.arnaud.model.metier.ComputerList;
import com.excilys.arnaud.persistance.ComputerDAO;
import com.excilys.arnaud.persistance.exception.DAOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ComputerDAOMySQL implements ComputerDAO {
  private static final Logger logger = LoggerFactory.getLogger(ComputerDAOMySQL.class);
  private static final String SQL_UPDATE = "UPDATE computer SET name=?,introduced=?, discontinued=?, company_id=? WHERE id=?";
  private static final String SQL_DEL = "DELETE FROM computer WHERE id=?";
  private static final String SQL_FIND_ID = "SELECT computer.id,computer.name, introduced, discontinued, company_id, company.name "
      + "FROM computer LEFT JOIN company ON company_id=company.id WHERE computer.id=?";
  private static final String SQL_FIND_NAME = "SELECT computer.id,computer.name, introduced, discontinued, company_id, company.name "
      + "FROM computer LEFT JOIN company ON company_id=company.id WHERE computer.name=?";
  private static final String SQL_COMPUTERS = "SELECT computer.id,computer.name, introduced, discontinued, company_id, company.name "
      + "FROM computer LEFT JOIN company ON company_id=company.id";
  private static final String SQL_N_COMPUTERS = "SELECT computer.id,computer.name, introduced, discontinued, company_id, company.name "
      + "FROM computer LEFT JOIN company ON company_id=company.id ORDER BY orderby ASC LIMIT ?, ?";
  private static final String SQL_N_COMPUTERS_PATTERN = "SELECT computer.id,computer.name, introduced, discontinued, company_id, company.name "
      + "FROM computer LEFT JOIN company ON company_id=company.id "
      + "WHERE computer.name LIKE ? OR company.name LIKE ? ORDER BY orderby ASC LIMIT ?, ?";
  private static final String SQL_N_COMPUTERS_ORDER_COMPANY = "SELECT computer.id,computer.name, introduced, discontinued, company_id, company.name "
      + "FROM company STRAIGHT_JOIN computer ON company_id=company.id ORDER BY company.name ASC " + "LIMIT ?, ?";
  private static final String SQL_N_COMPUTERS_PATTERN_COMPANY = "SELECT computer.id,computer.name, introduced, discontinued, company_id, company.name "
      + "FROM company STRAIGHT_JOIN computer ON company_id=company.id "
      + "WHERE computer.name LIKE ? OR company.name LIKE ? ORDER BY company.name ASC LIMIT ?, ?";
  private static final String SQL_NUMBER_OF_COMPUTERS = "SELECT COUNT(id) FROM computer USE INDEX (PRIMARY);";
  private static final String SQL_NUMBER_OF_COMPUTERS_PATTERN = "SELECT COUNT(computer.id) FROM computer USE INDEX (PRIMARY) "
      + "LEFT JOIN company ON company_id=company.id "
      + "WHERE computer.name LIKE ? OR company.name LIKE ?";
  private static final String SQL_DELETE_FROM_COMPANY = "DELETE FROM computer WHERE company_id=?";



  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert insertComputer;

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.insertComputer = new SimpleJdbcInsert(dataSource).withTableName("computer").usingGeneratedKeyColumns("id");
  }

  @Override
  public long add(Computer computer) throws DAOException {
    Map<String, Object> parameters = new HashMap<String, Object>(5);
    long id;

    if (computer != null) {
      parameters.put("name", computer.getName());
      parameters.put("introduced", computer.getIntroduced() != null ? 
          Timestamp.valueOf(computer.getIntroduced()) : null);
      parameters.put("discontinued", computer.getDiscontinued() != null ? 
          Timestamp.valueOf(computer.getDiscontinued()) : null);
      parameters.put("company_id", computer.getCompany() != null ?
          computer.getCompany().getId() : null);
      try {
        id = insertComputer.executeAndReturnKey(parameters).longValue();
        computer.setId(id);
        logger.info("Création de l'ordinateur {} en base", id);
        return id;

      } catch (DataAccessException e) {
        logger.debug(e.getMessage());
        throw new DAOException(e.getMessage(), e.getCause());
      }
    }
    return -1;
  }

  @Override
  public boolean update(Computer computer) throws DAOException {
    
    if (computer != null) {
      try {
        this.jdbcTemplate.update(SQL_UPDATE, new Object[]{ 
            computer.getName(),
            computer.getIntroduced() != null ? Timestamp.valueOf(computer.getIntroduced()) : null, 
            computer.getDiscontinued() != null ? Timestamp.valueOf(computer.getDiscontinued()) : null,
            computer.getCompany() != null ? computer.getCompany().getId() : null, computer.getId()});
        logger.info("Computer {} updated", computer.getId());
        return true;
      } catch (DataAccessException e) {
        logger.debug(e.getMessage());
        throw new DAOException(e);
      }
    }
    return false;
  }

  @Override
  public boolean del(long id) throws DAOException {
    try {
      this.jdbcTemplate.update(SQL_DEL, id);
      logger.info("Computer {} deleted", id);
      return true;
    } catch (DataAccessException e) {
      logger.debug(e.getMessage());
      throw new DAOException(e);
    }
  }

  @Override
  public Optional<Computer> findById(long id) throws DAOException {
    try {
      return Optional.ofNullable(this.jdbcTemplate.queryForObject(SQL_FIND_ID, new CompanyMapper(), id));

    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    } catch (DataAccessException e) {
      logger.debug(e.getMessage());
      throw new DAOException(e);
    }
  }

  @Override
  public Optional<Computer> findByName(String name) throws DAOException {
    if (name != null) {
      try {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject(SQL_FIND_NAME, new CompanyMapper(), name));
      } catch (EmptyResultDataAccessException e) {
        return Optional.empty();
      } catch (DataAccessException e) {
        logger.debug(e.getMessage());
        throw new DAOException(e);
      }
    }
    return Optional.empty();
  }

  @Override
  public ComputerList getComputers() throws DAOException {
    try {
      return new ComputerList(this.jdbcTemplate.query(SQL_COMPUTERS, new CompanyMapper()));

    } catch (DataAccessException e) {
      logger.debug(e.getMessage());
      throw new DAOException(e);
    } 
  }

  @Override
  public ComputerList getNComputers(int begin, int nbComputer, int orderBy) throws DAOException {
    ComputerAtrribute[] attributes = ComputerAtrribute.values();

    if (begin >= 0 && nbComputer > 0) {
      try {
        String query;
        if (orderBy == 4) {
          query = SQL_N_COMPUTERS_ORDER_COMPANY;

        } else if (orderBy >= 0 && orderBy < attributes.length) {
          query = SQL_N_COMPUTERS.replaceAll("orderby", attributes[orderBy].getName());
        } else {
          query = SQL_N_COMPUTERS.replaceAll("orderby", attributes[0].getName());
        }
        
        return new ComputerList(this.jdbcTemplate.query(query, new CompanyMapper(), begin, nbComputer));
      } catch (DataAccessException e) {
        logger.debug(e.getMessage());
        throw new DAOException(e);
      } 
    }
    return new ComputerList();
  }

  @Override
  public ComputerList getNComputers(String pattern, int begin, int nbComputer, int orderBy) throws DAOException {
    if (pattern == null || pattern.isEmpty()) {
      return getNComputers(begin, nbComputer, orderBy);
    }
    ComputerAtrribute[] attributes = ComputerAtrribute.values();
    if (begin >= 0 && nbComputer > 0) {
      try {
        String query;
        if (orderBy == 4) {
          query = SQL_N_COMPUTERS_PATTERN_COMPANY;
        } else if (orderBy >= 0 && orderBy < attributes.length) {
          query = SQL_N_COMPUTERS_PATTERN.replaceAll("orderby", attributes[orderBy].getName());
        } else {
          query = SQL_N_COMPUTERS_PATTERN.replaceAll("orderby", attributes[0].getName());
        }
        return new ComputerList(this.jdbcTemplate.query(query, new CompanyMapper(), pattern+"%", pattern+"%", begin, nbComputer));

      } catch (DataAccessException e) {
        logger.debug(e.getMessage());
        throw new DAOException(e);
      }
    }
    return new ComputerList();
  }

  @Override
  public int getNumberOfComputer() throws DAOException {
    try {
      return this.jdbcTemplate.queryForObject(SQL_NUMBER_OF_COMPUTERS, Integer.class);

    } catch (DataAccessException e) {
      logger.debug(e.getMessage());
      throw new DAOException(e);
    }
  }

  @Override
  public int getNumberOfComputer(String pattern) throws DAOException {
    if (pattern == null) {
      return getNumberOfComputer();
    }
    try {
      return this.jdbcTemplate.queryForObject(SQL_NUMBER_OF_COMPUTERS_PATTERN, Integer.class, pattern + "%", pattern + "%");

    } catch (DataAccessException e) {
      logger.debug(e.getMessage());
      throw new DAOException(e);
    }
  }

  @Override
  public boolean[] dels(long[] ids) throws DAOException {


    if (ids != null && ids.length != 0) {
      try {
        int[] resultat = jdbcTemplate.batchUpdate(SQL_DEL,
        new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setLong(1, ids[i]);
                }

                public int getBatchSize() {
                    return ids.length;
                }
            });
        if (resultat != null) {
          boolean[] booleanTab = new boolean[resultat.length];
          for (int i = 0; i < resultat.length; i++) {
            booleanTab[i] = resultat[i] != 0;
            if (booleanTab[i]) {
              logger.info("Computer {} deleted", ids[i]);
            } else {
              logger.info("Computer {} not deleted", ids[i]);
            }
          }
          return booleanTab;
        }
        return null;

      } catch (DataAccessException e) {
        logger.debug(e.getMessage());
        throw new DAOException(e);
      } 
    }
    return new boolean[0];
  }

  @Override
  public boolean delsFromCompany(long id) throws DAOException {

    try {
      int resultat = this.jdbcTemplate.update(SQL_DELETE_FROM_COMPANY, id);
      if (resultat == 1) {
        logger.info("Computers of company {} deleted", id);
        return true;
      } else {
        logger.info("Computers of company {} not deleted", id);
        return false;
      }
    } catch (DataAccessException e) {
      logger.debug(e.getMessage());
      throw new DAOException(e);
    }
  }

  private static final class CompanyMapper implements RowMapper<Computer> {

    public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
      LocalDateTime introduced = null;
      LocalDateTime discontinued = null;
      Company comp = null;
      if (rs.getString("company_id") != null) {
        comp = new Company(rs.getLong("company_id"), rs.getString("company.name"));
      }

      if (rs.getTimestamp("introduced") != null) {

        introduced = rs.getTimestamp("introduced").toLocalDateTime();
      }
      if (rs.getTimestamp("discontinued") != null) {
        discontinued = rs.getTimestamp("discontinued").toLocalDateTime();
      }

      return new Computer(Long.parseLong(rs.getString("id")), rs.getString("name"), comp, introduced, discontinued);
    }
  }

}
