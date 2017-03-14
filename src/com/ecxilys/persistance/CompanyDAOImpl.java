package com.ecxilys.persistance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ecxilys.model.Company;
import com.ecxilys.model.CompanyList;

public class CompanyDAOImpl implements CompanyDAO{
	private Connection connexion = null;
	
	public CompanyDAOImpl(Connection connect){
		this.connexion=connect;
	}
	
	
	@Override
	public Company findById(int id){
		try {
			Statement statement = connexion.createStatement();
			ResultSet resultat = statement.executeQuery( "SELECT id, name  FROM company WHERE id="+id+" ;" );
			if(resultat.next())
				return new Company(Integer.parseInt(resultat.getString("id")),resultat.getString("name"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public Company findByName(int name){
		try {
			Statement statement = connexion.createStatement();
			ResultSet resultat = statement.executeQuery( "SELECT id, name  FROM company WHERE name="+name+" ;" );
			if(resultat.next())
				return new Company(Integer.parseInt(resultat.getString("id")),resultat.getString("name"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



	@Override
	public CompanyList getCompanies() {
		try {
			Statement statement = connexion.createStatement();
			ResultSet resultat = statement.executeQuery( "SELECT id, name  FROM company");
			ArrayList<Company> list = new ArrayList<Company>();
			while(resultat.next())
				list.add(new Company(Integer.parseInt(resultat.getString("id")),resultat.getString("name")));
			return new CompanyList(list);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



}
