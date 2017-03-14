package com.ecxilys.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DAOFactory {
	private String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private String utilisateur = "admincdb";
	private String motDePasse = "qwerty1234";
	private Connection connexion = null;
	
	
	private static DAOFactory INSTANCE= new DAOFactory();
	
	private DAOFactory(){
		connect();
	}
	
	public static DAOFactory getInstance()
	{	return INSTANCE;
	}
	
	
	
	private void connect(){

		if(connexion==null){	
			/* Chargement du driver JDBC pour MySQL */
			try {
			    Class.forName( "com.mysql.jdbc.Driver" );
			    connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
			} catch ( ClassNotFoundException e ) {
			    /* Gérer les éventuelles erreurs ici. */
			} catch ( SQLException e ) {
			    /* Gérer les éventuelles erreurs ici */
			}
		}
		
	}
	
	public void close(){
		if ( connexion != null ){
			try {
				/* Fermeture de la connexion */
			    connexion.close();
			} catch ( SQLException ignore ) {
				/* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */
			}
		}
	}
	
	
	public CompanyDAO getCompanyDAO(){
		return new CompanyDAOImpl(connexion);
	}
	
	public ComputerDAO getComputerDAO(){
		return new ComputerDAOImpl(connexion);
	}
	
}
