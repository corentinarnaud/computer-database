package com.excilys.arnaud.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.excilys.arnaud.persistance.exception.DAOException;

public class DAOUtils {

  public static void closeResultatSet(ResultSet resultat) throws DAOException {
    if (resultat != null) {
      try {
        resultat.close();
      } catch (SQLException e) {
        throw new DAOException(e);
      }
    }
  }

  public static void closePreparedStatement(PreparedStatement statement)
      throws DAOException {
    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException e) {
        throw new DAOException(e);
      }
    }
  }

  public static void closeStatement(Statement statement) throws DAOException {
    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException e) {
        throw new DAOException(e);
      }
    }
  }

  public static void closeConnection(Connection connection)
      throws DAOException {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        throw new DAOException("Fail to disconnect", e);
      }
    }
  }

}
