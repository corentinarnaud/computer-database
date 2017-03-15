package com.ecxilys.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.ecxilys.model.Company;
import com.ecxilys.model.Computer;
import com.ecxilys.model.ComputerList;

public enum ComputerDAOMySQL implements ComputerDAO{
	COMPUTERDAO;
	
	private static final String SQL_INSERT="INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (? ,? ,? ,? )";
	private static final String SQL_UPDATE="UPDATE computer SET name=?,introduced=?, discontinued=?, company_id=? WHERE id=?";
	
	private Connection connexion = null;
	private DateFormat df; 

	
	private ComputerDAOMySQL(){
		this.connexion=DataBaseConnection.CONNECTION.getConnection();
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	}
	
	@Override
	public int add(Computer computer) throws DAOException{
		int id=-1;
		try {
			ResultSet valeursAutoGenerees = null;
			
			PreparedStatement addStatement = connexion.prepareStatement(SQL_INSERT,Statement.RETURN_GENERATED_KEYS);
			addStatement.setString(1, computer.getName());

			
			if(computer.getIntroduced()==null){
				addStatement.setNull(2, Types.TIMESTAMP);
				addStatement.setNull(3, Types.TIMESTAMP);
			}
			else{
				addStatement.setTimestamp(2,new Timestamp(computer.getIntroduced().getTime()));
				if(computer.getDiscontinued()==null)
					addStatement.setNull(3, Types.TIMESTAMP);
				else
					addStatement.setTimestamp(3, new Timestamp(computer.getDiscontinued().getTime()));
			}

			if(computer.getCompany()==null)
				addStatement.setString(4, null);
			else
				addStatement.setInt(4, computer.getCompany().getId());
			
			int statut = addStatement.executeUpdate();
			if ( statut == 0 ) {
	            throw new DAOException( "Échec de la création de l'ordinateur, aucune ligne ajoutée dans la table." );
	        }
			/* Récupération de l'id auto-généré par la requête d'insertion */
	        valeursAutoGenerees = addStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	        	id=valeursAutoGenerees.getInt(1);
				computer.setId(id);
	        } else {
	            throw new DAOException( "Échec de la création de l'ordinateur en base, aucun ID auto-généré retourné." );
	        }

			return id;
		
		} catch (SQLException e) {
			throw new DAOException(e.getMessage(), e.getCause());
		}
	}
	
	@Override
	public boolean update(Computer computer){
		try {
			PreparedStatement updateStatement = connexion.prepareStatement(SQL_UPDATE);
			updateStatement.setString(1, computer.getName());

			
			if(computer.getIntroduced()==null){
				updateStatement.setNull(2, Types.TIMESTAMP);
				updateStatement.setNull(3, Types.TIMESTAMP);
			}
			else{
				updateStatement.setTimestamp(2,new Timestamp(computer.getIntroduced().getTime()));
				if(computer.getDiscontinued()==null)
					updateStatement.setNull(3, Types.TIMESTAMP);
				else
					updateStatement.setTimestamp(3, new Timestamp(computer.getDiscontinued().getTime()));
			}

			if(computer.getCompany()==null)
				updateStatement.setString(4, null);
			else
				updateStatement.setInt(4, computer.getCompany().getId());
			
			updateStatement.setInt(5, computer.getId());
			
			int statut = updateStatement.executeUpdate();
			if ( statut == 0 ) {
	            throw new DAOException( "Échec de la modification de l'ordinateur, aucune ligne modifié dans la table." );
	        }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean del(int id){
		try {
			Statement statement = connexion.createStatement();
			int resultat = statement.executeUpdate( "DELETE FROM computer WHERE id="+id+";" );
			return resultat == 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	@Override
	public Computer findById(int id){
		try {
			//TODO: JOIN
			Statement statement = connexion.createStatement();
			ResultSet resultat = statement.executeQuery( "SELECT id,name, introduced, discontinued, company_id  "
													   + "FROM computer WHERE id="+id+";" );
			if(resultat.next()){
				Date introduced = null, discontinued = null;
				Company comp=null;
				if(resultat.getString("company_id")!=null){
					comp=CompanyDAOMySQL.CONPANYDAO.findById(Integer.parseInt(resultat.getString("company_id")));
				}
				
				if(resultat.getString("introduced")!=null){

					introduced=df.parse(resultat.getString("introduced"));
					if(resultat.getString("discontinued")!=null){
						discontinued=df.parse(resultat.getString("discontinued"));
					}
				}

				
				return new Computer(Integer.parseInt(resultat.getString("id")),resultat.getString("name"),comp,
									introduced,discontinued);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public Computer findByName(String name){
		try {
			//TODO: JOIN
			Statement statement = connexion.createStatement();
			ResultSet resultat = statement.executeQuery( "SELECT id,name, introduced, discontinued, company_id  "
													   + "FROM computer WHERE name="+name+";" );
			if(resultat.next()){
				Date introduced = null, discontinued = null;
				Company comp=null;
				if(resultat.getString("company_id")!=null){
					comp=CompanyDAOMySQL.CONPANYDAO.findById(Integer.parseInt(resultat.getString("company_id")));
				}
				
				if(resultat.getString("introduced")!=null){

					introduced=df.parse(resultat.getString("introduced"));
					if(resultat.getString("discontinued")!=null){
						discontinued=df.parse(resultat.getString("discontinued"));
					}
				}

				
				return new Computer(Integer.parseInt(resultat.getString("id")),resultat.getString("name"),comp,
									introduced,discontinued);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ComputerList getComputers() {
		try {
			//TODO JOIN
			ComputerList list = new ComputerList();
			Statement statement = connexion.createStatement();
			ResultSet resultat = statement.executeQuery( "SELECT id,name, introduced, discontinued, company_id  "
													   + "FROM computer" );
			while(resultat.next()){
				Date introduced = null, discontinued = null;
				Company comp=null;
				if(resultat.getString("company_id")!=null){
					comp=CompanyDAOMySQL.CONPANYDAO.findById(Integer.parseInt(resultat.getString("company_id")));
				}
				
				if(resultat.getString("introduced")!=null){

					introduced=df.parse(resultat.getString("introduced"));
					if(resultat.getString("discontinued")!=null){
						discontinued=df.parse(resultat.getString("discontinued"));
					}
				}

				
				list.add(new Computer(Integer.parseInt(resultat.getString("id")),resultat.getString("name"),comp,
									introduced,discontinued));
			}
			return list;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
