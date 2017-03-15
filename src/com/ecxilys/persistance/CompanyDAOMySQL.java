package com.ecxilys.persistance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ecxilys.model.Company;
import com.ecxilys.model.CompanyList;

public enum CompanyDAOMySQL implements CompanyDAO{
	CONPANYDAO();
	private Connection connexion = null;
	
	private CompanyDAOMySQL(){
		this.connexion=DataBaseConnection.CONNECTION.getConnection();
	}
	
	
	@Override
	public Company findById(int id) throws DAOException{
		try {
			Statement statement = connexion.createStatement();
			ResultSet resultat = statement.executeQuery( "SELECT id, name  FROM company WHERE id="+id+" ;" );
			if(resultat.next())
				return new Company(Integer.parseInt(resultat.getString("id")),resultat.getString("name"));

		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return null;
	}
	
	
	@Override
	public Company findByName(int name) throws DAOException{
		try {
			Statement statement = connexion.createStatement();
			ResultSet resultat = statement.executeQuery( "SELECT id, name  FROM company WHERE name="+name+" ;" );
			if(resultat.next())
				return new Company(Integer.parseInt(resultat.getString("id")),resultat.getString("name"));

		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return null;
	}



	@Override
	public CompanyList getCompanies() throws DAOException{
		try {
			Statement statement = connexion.createStatement();
			ResultSet resultat = statement.executeQuery( "SELECT id, name  FROM company");
			ArrayList<Company> list = new ArrayList<Company>();
			while(resultat.next())
				list.add(new Company(Integer.parseInt(resultat.getString("id")),resultat.getString("name")));
			return new CompanyList(list);

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}



}
