package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import modele.Companies;
import modele.Computer;
import modele.ComputerList;

public class MySQLAccess {
	private String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private String utilisateur = "admincdb";
	private String motDePasse = "qwerty1234";
	private Connection connexion = null;
	
	
	private static MySQLAccess INSTANCE= new MySQLAccess();
	
	private MySQLAccess(){
		connect();
	}
	
	public static MySQLAccess getInstance()
	{	return INSTANCE;
	}
	
	
	
	public void connect(){

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
	
	public void loadCompanies(){
		Companies comp = Companies.getInstance();
		if(comp.isLoad()==false){
			try {
				Statement statement = connexion.createStatement();
				ResultSet resultat = statement.executeQuery( "SELECT name  FROM company;" );
				
				ArrayList<String> companies = new ArrayList<String>();
				while(resultat.next()){
					companies.add(resultat.getString("name"));
				}
				comp.loadData(companies);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void loadComputer(){
		ComputerList computer = ComputerList.getInstance();
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
		
		try {
			Statement statement = connexion.createStatement();
			ResultSet resultat = statement.executeQuery( "SELECT name, introduced, discontinued, company_id FROM computer;" );
			
			String name, company;
			Date introduced = null, discontinued = null;

			while(resultat.next()){
				name = resultat.getString("name");
				company = resultat.getString("company_id");
				try{

					if(resultat.getString("introduced")!=null){

						introduced=df.parse(resultat.getString("introduced"));
						if(resultat.getString("discontinued")!=null){
							discontinued=df.parse(resultat.getString("discontinued"));
						}
						else discontinued=null;
					}
					else introduced=null;
					
					computer.add(new Computer(name, company, introduced, discontinued));
				} catch(ParseException e){
					System.out.println("Wrong date format, computer "+name+" ignored");
				}
			}
				

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
