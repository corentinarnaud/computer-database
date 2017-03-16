package com.excilys.arnaud.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.arnaud.model.Company;
import com.excilys.arnaud.model.CompanyList;



public enum CompanyDAOMySQL implements CompanyDAO{
	CONPANYDAO();
	private static final String SQL_FIND_ID = "SELECT id, name  FROM company WHERE id=?";
	private static final String SQL_FIND_Name = "SELECT id, name  FROM company WHERE name=?";
	
	
	private Connection connexion = null;
	
	private CompanyDAOMySQL(){
		this.connexion=DataBaseConnection.CONNECTION.getConnection();
	}
	
	
	public Optional<Company> findById(long id) throws DAOException{
		PreparedStatement statement = null;
		ResultSet resultat = null;
		
		try {
			statement = connexion.prepareStatement(SQL_FIND_ID);
			statement.setLong(1, id);
			resultat = statement.executeQuery();
			if(resultat.next())
				return Optional.ofNullable(new Company(Integer.parseInt(resultat.getString("id")),resultat.getString("name")));

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtils.closePreparedStatement(statement);
			DAOUtils.closeResultatSet(resultat);
		}
		return Optional.empty();
	}
	
	
	public Optional<Company> findByName(String name) throws DAOException{
		PreparedStatement statement = null;
		ResultSet resultat = null;
		
		try {
			statement = connexion.prepareStatement(SQL_FIND_Name);
			statement.setString(1, name());
			resultat = statement.executeQuery();
			if(resultat.next())
				return Optional.ofNullable(new Company(Integer.parseInt(resultat.getString("id")),resultat.getString("name")));

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally{
			DAOUtils.closePreparedStatement(statement);
			DAOUtils.closeResultatSet(resultat);
		}
		return Optional.empty();
	}



	public CompanyList getCompanies() throws DAOException{
		Statement statement = null;
		ResultSet resultat = null;
		
		try {
			statement = connexion.createStatement();
			resultat = statement.executeQuery( "SELECT id, name  FROM company");
			ArrayList<Company> list = new ArrayList<Company>();
			while(resultat.next())
				list.add(new Company(Integer.parseInt(resultat.getString("id")),resultat.getString("name")));
			return new CompanyList(list);

		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			DAOUtils.closeStatement(statement);
			DAOUtils.closeResultatSet(resultat);
		}
	}



}
