package com.excilys.arnaud.persistance;

import com.excilys.arnaud.model.Company;
import com.excilys.arnaud.model.Computer;
import com.excilys.arnaud.model.ComputerList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ComputerDAOMySQL implements ComputerDAO {
  COMPUTERDAO;
  private static final Logger logger = LoggerFactory.getLogger(ComputerDAOMySQL.class);
  private static final String SQL_INSERT = "INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (? ,? ,? ,? )";
  private static final String SQL_UPDATE = "UPDATE computer SET name=?,introduced=?, discontinued=?, company_id=? WHERE id=?";
  private static final String SQL_DEL = "DELETE FROM computer WHERE id=?";
  private static final String SQL_FIND_ID = "SELECT computer.id,computer.name, introduced, discontinued, company_id, company.name "
      + "FROM computer LEFT JOIN company ON company_id=company.id WHERE computer.id=?";
  private static final String SQL_FIND_NAME = "SELECT computer.id,computer.name, introduced, discontinued, company_id, company.name "
      + "FROM computer LEFT JOIN company ON company_id=company.id WHERE computer.name=?";
  private static final String SQL_COMPUTERS = "SELECT computer.id,computer.name, introduced, discontinued, company_id, company.name "
      + "FROM computer LEFT JOIN company ON company_id=company.id";
  private static final String SQL_N_COMPUTERS = "SELECT computer.id,computer.name, introduced, discontinued, company_id, company.name "
      + "FROM computer LEFT JOIN company ON company_id=company.id LIMIT ?, ?";
  private static final String SQL_N_COMPUTERS_PATTERN = "SELECT computer.id,computer.name, introduced, discontinued, company_id, company.name "
      + "FROM computer LEFT JOIN company ON company_id=company.id " + "WHERE computer.name LIKE ? LIMIT ?, ?";
  private static final String SQL_NUMBER_OF_COMPUTERS = "SELECT COUNT(*) FROM computer";
  private static final String SQL_NUMBER_OF_COMPUTERS_PATTERN = "SELECT COUNT(*) FROM computer WHERE name LIKE ?";

  private ComputerDAOMySQL() {

  }

  @Override
  public long add(Computer computer) throws DAOException {
    long id;
    ResultSet valeursAutoGenerees = null;
    PreparedStatement addStatement = null;
    Connection connection = null;
    if (computer != null) {
      try {
        connection = DataBaseConnection.CONNECTION.getConnection();
        addStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
        addStatement.setString(1, computer.getName());

        if (computer.getIntroduced() == null) {
          addStatement.setNull(2, Types.TIMESTAMP);
          addStatement.setNull(3, Types.TIMESTAMP);
        } else {
          addStatement.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced()));
          if (computer.getDiscontinued() == null) {
            addStatement.setNull(3, Types.TIMESTAMP);
          } else {
            addStatement.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued()));
          }
        }

        if (computer.getCompany() == null) {
          addStatement.setString(4, null);
        } else {
          addStatement.setLong(4, computer.getCompany().getId());
        }

        int statut = addStatement.executeUpdate();
        if (statut == 0) {
          throw new DAOException("Échec de la création de l'ordinateur, " + "aucune ligne ajoutée dans la table.");
        }
        /* Récupération de l'id auto-généré par la requête d'insertion */
        valeursAutoGenerees = addStatement.getGeneratedKeys();
        if (valeursAutoGenerees.next()) {
          id = valeursAutoGenerees.getLong(1);
          computer.setId(id);
        } else {
          logger.debug("Échec de la création de l'ordinateur en base, " + "aucun ID auto-généré retourné.");
          throw new DAOException("Échec de la création de l'ordinateur en base, " + "aucun ID auto-généré retourné.");
        }
        logger.info("Création de l'ordinateur {} en base", id);
        return id;

      } catch (SQLException e) {
        logger.debug(e.getMessage());
        throw new DAOException(e.getMessage(), e.getCause());
      } finally {
        DAOUtils.closePreparedStatement(addStatement);
        DAOUtils.closeResultatSet(valeursAutoGenerees);
        DAOUtils.closeConnection(connection);
      }
    }
    return -1;
  }

  @Override
  public boolean update(Computer computer) throws DAOException {
    PreparedStatement updateStatement = null;
    Connection connection = null;

    if (computer != null) {
      try {
        connection = DataBaseConnection.CONNECTION.getConnection();
        updateStatement = connection.prepareStatement(SQL_UPDATE);
        updateStatement.setString(1, computer.getName());

        if (computer.getIntroduced() == null) {
          updateStatement.setNull(2, Types.TIMESTAMP);
          updateStatement.setNull(3, Types.TIMESTAMP);
        } else {
          updateStatement.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced()));
          if (computer.getDiscontinued() == null) {
            updateStatement.setNull(3, Types.TIMESTAMP);
          } else {
            updateStatement.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued()));
          }
        }

        if (computer.getCompany() == null) {
          updateStatement.setString(4, null);
        } else {
          updateStatement.setLong(4, computer.getCompany().getId());
        }

        updateStatement.setLong(5, computer.getId());

        int statut = updateStatement.executeUpdate();
        if (statut == 0) {
          logger.debug("Fail to update computer {}.", computer.getId());
          throw new DAOException("Fail to update computer.");
        } else {
          logger.info("Computer {} updated", computer.getId());
          return true;
        }

      } catch (SQLException e) {
        logger.debug(e.getMessage());
        throw new DAOException(e);
      } finally {
        DAOUtils.closePreparedStatement(updateStatement);
        DAOUtils.closeConnection(connection);
      }
    }
    return false;
  }

  @Override
  public boolean del(long id) throws DAOException {
    PreparedStatement statement = null;
    Connection connection = null;

    try {
      connection = DataBaseConnection.CONNECTION.getConnection();
      statement = connection.prepareStatement(SQL_DEL);
      statement.setString(1, String.valueOf(id));
      int resultat = statement.executeUpdate();
      if (resultat == 1) {
        logger.info("Computer {} deleted", id);
        return true;
      } else {
        logger.info("Computer {} not deleted", id);
        return false;
      }
    } catch (SQLException e) {
      logger.debug(e.getMessage());
      throw new DAOException(e);
    } finally {
      DAOUtils.closePreparedStatement(statement);
      DAOUtils.closeConnection(connection);
    }

  }

  @Override
  public Optional<Computer> findById(long id) throws DAOException {
    PreparedStatement statement = null;
    ResultSet resultat = null;
    Connection connection = null;

    try {
      connection = DataBaseConnection.CONNECTION.getConnection();
      statement = connection.prepareStatement(SQL_FIND_ID);
      statement.setString(1, String.valueOf(id));
      resultat = statement.executeQuery();
      if (resultat.next()) {
        LocalDateTime introduced = null;
        LocalDateTime discontinued = null;
        Company comp = null;
        if (resultat.getString("company_id") != null) {
          comp = new Company(resultat.getLong("company_id"), resultat.getString("company.name"));
        }

        if (resultat.getString("introduced") != null) {

          introduced = resultat.getTimestamp("introduced").toLocalDateTime();
          if (resultat.getString("discontinued") != null) {
            discontinued = resultat.getTimestamp("discontinued").toLocalDateTime();
          }
        }

        return Optional.ofNullable(new Computer(Integer.parseInt(resultat.getString("computer.id")),
            resultat.getString("computer.name"), comp, introduced, discontinued));
      }

    } catch (SQLException e) {
      logger.debug(e.getMessage());
      throw new DAOException(e);
    } finally {
      DAOUtils.closePreparedStatement(statement);
      DAOUtils.closeResultatSet(resultat);
      DAOUtils.closeConnection(connection);
    }
    return Optional.empty();
  }

  @Override
  public Optional<Computer> findByName(String name) throws DAOException {
    PreparedStatement statement = null;
    ResultSet resultat = null;
    Connection connection = null;

    if (name != null) {
      try {
        connection = DataBaseConnection.CONNECTION.getConnection();
        statement = connection.prepareStatement(SQL_FIND_NAME);
        statement.setString(1, name);
        resultat = statement.executeQuery();
        if (resultat.next()) {
          LocalDateTime introduced = null;
          LocalDateTime discontinued = null;
          Company comp = null;
          if (resultat.getString("company_id") != null) {
            comp = new Company(resultat.getLong("company_id"), resultat.getString("company.name"));
          }

          if (resultat.getString("introduced") != null) {

            introduced = resultat.getTimestamp("introduced").toLocalDateTime();
            if (resultat.getString("discontinued") != null) {
              discontinued = resultat.getTimestamp("discontinued").toLocalDateTime();
            }
          }

          return Optional.ofNullable(new Computer(Integer.parseInt(resultat.getString("computer.id")),
              resultat.getString("computer.name"), comp, introduced, discontinued));
        }

      } catch (SQLException e) {
        logger.debug(e.getMessage());
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
  public ComputerList getComputers() throws DAOException {
    Statement statement = null;
    ResultSet resultat = null;
    Connection connection = null;

    try {
      ComputerList list = new ComputerList();
      connection = DataBaseConnection.CONNECTION.getConnection();
      statement = connection.createStatement();
      resultat = statement.executeQuery(SQL_COMPUTERS);
      while (resultat.next()) {
        LocalDateTime introduced = null;
        LocalDateTime discontinued = null;
        Company comp = null;
        if (resultat.getString("company_id") != null) {
          comp = new Company(resultat.getLong("company_id"), resultat.getString("company.name"));
        }

        if (resultat.getString("introduced") != null) {
          ;
          introduced = resultat.getTimestamp("introduced").toLocalDateTime();
          if (resultat.getString("discontinued") != null) {
            discontinued = resultat.getTimestamp("discontinued").toLocalDateTime();
          }
        }

        list.add(new Computer(Integer.parseInt(resultat.getString("computer.id")), resultat.getString("computer.name"),
            comp, introduced, discontinued));
      }
      return list;

    } catch (SQLException e) {
      logger.debug(e.getMessage());
      throw new DAOException(e);
    } finally {
      DAOUtils.closeStatement(statement);
      DAOUtils.closeResultatSet(resultat);
      DAOUtils.closeConnection(connection);
    }
  }

  @Override
  public ComputerList getNComputers(int begin, int nbComputer) throws DAOException {
    PreparedStatement statement = null;
    ResultSet resultat = null;
    Connection connection = null;

    if (begin >= 0 && nbComputer > 0) {
      try {
        ComputerList list = new ComputerList();
        connection = DataBaseConnection.CONNECTION.getConnection();
        statement = connection.prepareStatement(SQL_N_COMPUTERS);
        statement.setInt(1, begin);
        statement.setInt(2, nbComputer);
        resultat = statement.executeQuery();
        while (resultat.next()) {
          LocalDateTime introduced = null;
          LocalDateTime discontinued = null;
          Company comp = null;
          if (resultat.getString("company_id") != null) {
            comp = new Company(resultat.getLong("company_id"), resultat.getString("company.name"));
          }

          if (resultat.getString("introduced") != null) {
            ;
            introduced = resultat.getTimestamp("introduced").toLocalDateTime();
            if (resultat.getString("discontinued") != null) {
              discontinued = resultat.getTimestamp("discontinued").toLocalDateTime();
            }
          }

          list.add(new Computer(Integer.parseInt(resultat.getString("computer.id")),
              resultat.getString("computer.name"), comp, introduced, discontinued));
        }
        return list;

      } catch (SQLException e) {
        logger.debug(e.getMessage());
        throw new DAOException(e);
      } finally {
        DAOUtils.closePreparedStatement(statement);
        DAOUtils.closeResultatSet(resultat);
        DAOUtils.closeConnection(connection);
      }
    }
    return null;
  }

  @Override
  public ComputerList getNComputers(String pattern, int begin, int nbComputer) throws DAOException {
    if (pattern == null) {
      return getNComputers(begin, nbComputer);
    }
    PreparedStatement statement = null;
    ResultSet resultat = null;
    Connection connection = null;

    if (begin > 0 && nbComputer > 0) {
      try {
        ComputerList list = new ComputerList();
        connection = DataBaseConnection.CONNECTION.getConnection();
        statement = connection.prepareStatement(SQL_N_COMPUTERS_PATTERN);
        statement.setString(1, "%" + pattern + "%");
        statement.setInt(2, begin);
        statement.setInt(3, nbComputer);
        resultat = statement.executeQuery();
        while (resultat.next()) {
          LocalDateTime introduced = null;
          LocalDateTime discontinued = null;
          Company comp = null;
          if (resultat.getString("company_id") != null) {
            comp = new Company(resultat.getLong("company_id"), resultat.getString("company.name"));
          }

          if (resultat.getString("introduced") != null) {
            ;
            introduced = resultat.getTimestamp("introduced").toLocalDateTime();
            if (resultat.getString("discontinued") != null) {
              discontinued = resultat.getTimestamp("discontinued").toLocalDateTime();
            }
          }

          list.add(new Computer(Integer.parseInt(resultat.getString("computer.id")),
              resultat.getString("computer.name"), comp, introduced, discontinued));
        }
        return list;

      } catch (SQLException e) {
        logger.debug(e.getMessage());
        throw new DAOException(e);
      } finally {
        DAOUtils.closePreparedStatement(statement);
        DAOUtils.closeResultatSet(resultat);
        DAOUtils.closeConnection(connection);
      }
    }
    return null;
  }

  @Override
  public int getNumberOfComputer() throws DAOException {
    Statement statement = null;
    ResultSet resultat = null;
    Connection connection = null;

    try {
      connection = DataBaseConnection.CONNECTION.getConnection();
      statement = connection.createStatement();
      resultat = statement.executeQuery(SQL_NUMBER_OF_COMPUTERS);
      resultat.next();
      return resultat.getInt(1);

    } catch (SQLException e) {
      logger.debug(e.getMessage());
      throw new DAOException(e);
    } finally {
      DAOUtils.closeStatement(statement);
      DAOUtils.closeResultatSet(resultat);
      DAOUtils.closeConnection(connection);
    }
  }

  @Override
  public int getNumberOfComputer(String pattern) throws DAOException {
    PreparedStatement statement = null;
    ResultSet resultat = null;
    Connection connection = null;

    if (pattern == null) {
      return getNumberOfComputer();
    }

    try {
      connection = DataBaseConnection.CONNECTION.getConnection();
      statement = connection.prepareStatement(SQL_NUMBER_OF_COMPUTERS_PATTERN);
      statement.setString(1, "%" + pattern + "%");
      resultat = statement.executeQuery();
      resultat.next();
      return resultat.getInt(1);

    } catch (SQLException e) {
      logger.debug(e.getMessage());
      throw new DAOException(e);
    } finally {
      DAOUtils.closePreparedStatement(statement);
      DAOUtils.closeResultatSet(resultat);
      DAOUtils.closeConnection(connection);
    }
  }

  @Override
  public boolean[] dels(long[] ids) throws DAOException {
    PreparedStatement statement = null;
    Connection connection = null;

    if (ids != null && ids.length != 0) {
      try {
        connection = DataBaseConnection.CONNECTION.getConnection();
        statement = connection.prepareStatement(SQL_DEL);

        statement.setString(1, String.valueOf(ids[0]));
        statement.addBatch();

        for (int i = 1; i < ids.length; i++) {
          statement.setString(1, String.valueOf(ids[i]));
          statement.addBatch();
        }
        int[] resultat = statement.executeBatch();
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

      } catch (SQLException e) {
        logger.debug(e.getMessage());
        throw new DAOException(e);
      } finally {
        DAOUtils.closePreparedStatement(statement);
        DAOUtils.closeConnection(connection);
      }
    }
    return null;
  }

}
