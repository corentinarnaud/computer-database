package com.ecxilys.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class DAOUtils {
	
	public static void closeResultatSet(ResultSet resultat) throws DAOException{
		if(resultat!=null){
			try {
				resultat.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
	}
	
	
	public static void closePreparedStatement(PreparedStatement statement) throws DAOException{
		if(statement!=null){
			try {
				statement.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}	
	}
	
	public static void closeStatement(Statement statement) throws DAOException{
		if(statement!=null){
			try {
				statement.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}	
	}
	
	
}
