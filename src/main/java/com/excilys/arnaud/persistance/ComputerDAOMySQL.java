package com.excilys.arnaud.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Optional;

import com.excilys.arnaud.model.Company;
import com.excilys.arnaud.model.Computer;
import com.excilys.arnaud.model.ComputerList;




public enum ComputerDAOMySQL implements ComputerDAO{
	COMPUTERDAO;
	
	private static final String SQL_INSERT		="INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (? ,? ,? ,? )";
	private static final String SQL_UPDATE		="UPDATE computer SET name=?,introduced=?, discontinued=?, company_id=? WHERE id=?";
	private static final String SQL_DEL   		="DELETE FROM computer WHERE id=?";
	private static final String SQL_FIND_ID 	="SELECT computer.id,computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id=company.id WHERE computer.id=?";
	private static final String SQL_FIND_NAME 	="SELECT computer.id,computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id=company.id WHERE computer.name=?";
	private static final String SQL_COMPUTERS 	="SELECT computer.id,computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id=company.id";



	
	private ComputerDAOMySQL(){

	}
	
	
	
	public long add(Computer computer) throws DAOException{
		long id;
		ResultSet valeursAutoGenerees = null;
		PreparedStatement addStatement = null;
		Connection connection = null;
		if(computer!=null){
			try {
				connection = DataBaseConnection.CONNECTION.getConnection();
				addStatement = connection.prepareStatement(SQL_INSERT,Statement.RETURN_GENERATED_KEYS);
				addStatement.setString(1, computer.getName());
	
				
				if(computer.getIntroduced()==null){
					addStatement.setNull(2, Types.TIMESTAMP);
					addStatement.setNull(3, Types.TIMESTAMP);
				}
				else{
					addStatement.setTimestamp(2,Timestamp.valueOf(computer.getIntroduced()));
					if(computer.getDiscontinued()==null)
						addStatement.setNull(3, Types.TIMESTAMP);
					else
						addStatement.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued()));
				}
	
				if(computer.getCompany()==null)
					addStatement.setString(4, null);
				else
					addStatement.setLong(4, computer.getCompany().getId());
				
				int statut = addStatement.executeUpdate();
				if ( statut == 0 ) {
		            throw new DAOException( "Échec de la création de l'ordinateur, aucune ligne ajoutée dans la table." );
		        }
				/* Récupération de l'id auto-généré par la requête d'insertion */
		        valeursAutoGenerees = addStatement.getGeneratedKeys();
		        if ( valeursAutoGenerees.next() ) {
		        	id=valeursAutoGenerees.getLong(1);
					computer.setId(id);
		        } else {
		            throw new DAOException( "Échec de la création de l'ordinateur en base, aucun ID auto-généré retourné." );
		        }
	
				return id;
			
			} catch (SQLException e) {
				throw new DAOException(e.getMessage(), e.getCause());
			} finally {
				DAOUtils.closePreparedStatement(addStatement);
				DAOUtils.closeResultatSet(valeursAutoGenerees);
				DAOUtils.closeConnection(connection);
			}
		}
		return -1;
	}
	
	public boolean update(Computer computer) throws DAOException{
		PreparedStatement updateStatement = null;
		Connection connection = null;
		
		if(computer!=null){
			try {
				connection = DataBaseConnection.CONNECTION.getConnection();
				updateStatement = connection.prepareStatement(SQL_UPDATE);
				updateStatement.setString(1, computer.getName());
	
				
				if(computer.getIntroduced()==null){
					updateStatement.setNull(2, Types.TIMESTAMP);
					updateStatement.setNull(3, Types.TIMESTAMP);
				}
				else{
					updateStatement.setTimestamp(2,Timestamp.valueOf(computer.getIntroduced()));
					if(computer.getDiscontinued()==null)
						updateStatement.setNull(3, Types.TIMESTAMP);
					else
						updateStatement.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued()));
				}
	
				if(computer.getCompany()==null)
					updateStatement.setString(4, null);
				else
					updateStatement.setLong(4, computer.getCompany().getId());
				
				updateStatement.setLong(5, computer.getId());
				
				int statut = updateStatement.executeUpdate();
				if ( statut == 0 ) {
		            throw new DAOException( "Fail to update computer." );
		        } else
		        	return true;
				
			} catch (SQLException e) {
				throw new DAOException(e);
			} finally {
				DAOUtils.closePreparedStatement(updateStatement);
				DAOUtils.closeConnection(connection);
			}
		}
		return false;
	}
	
	public boolean del(long id) throws DAOException{
		PreparedStatement statement = null;
		Connection connection = null;
		
		try {
			connection = DataBaseConnection.CONNECTION.getConnection();
			statement = connection.prepareStatement(SQL_DEL);
			statement.setString(1, String.valueOf(id));
			int resultat = statement.executeUpdate();
			return resultat == 1;
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtils.closePreparedStatement(statement);
			DAOUtils.closeConnection(connection);
		}

	}
	
	
	
	public Optional<Computer> findById(long id) throws DAOException{
		PreparedStatement statement = null;
		ResultSet resultat = null;
		Connection connection = null;
		
		try {
			connection = DataBaseConnection.CONNECTION.getConnection();
			statement = connection.prepareStatement(SQL_FIND_ID);
			statement.setString(1, String.valueOf(id));
			resultat = statement.executeQuery();
			if(resultat.next()){
				LocalDateTime introduced = null, discontinued = null;
				Company comp=null;
				if(resultat.getString("company_id")!=null){
					comp= new Company(resultat.getLong("company_id"),resultat.getString("company.name"));					
				}
				
				if(resultat.getString("introduced")!=null){

					introduced=resultat.getTimestamp("introduced").toLocalDateTime();
					if(resultat.getString("discontinued")!=null){
						discontinued=resultat.getTimestamp("discontinued").toLocalDateTime();
					}
				}

				
				return Optional.ofNullable(new Computer(Integer.parseInt(resultat.getString("computer.id")),resultat.getString("computer.name"),comp,
									introduced,discontinued));
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally{
			DAOUtils.closePreparedStatement(statement);
			DAOUtils.closeResultatSet(resultat);
			DAOUtils.closeConnection(connection);
		}
		return Optional.empty();
	}
	
	
	public Optional<Computer> findByName(String name) throws DAOException{
		PreparedStatement statement = null;
		ResultSet resultat = null;
		Connection connection =null;
		
		if(name!=null){
			try {
				connection = DataBaseConnection.CONNECTION.getConnection();
				statement = connection.prepareStatement(SQL_FIND_NAME);
				statement.setString(1, name);
				resultat = statement.executeQuery();
				if(resultat.next()){
					LocalDateTime introduced = null, discontinued = null;
					Company comp=null;
					if(resultat.getString("company_id")!=null){
						comp= new Company(resultat.getLong("company_id"),resultat.getString("company.name"));
					}
					
					if(resultat.getString("introduced")!=null){
	
						introduced=resultat.getTimestamp("introduced").toLocalDateTime();
						if(resultat.getString("discontinued")!=null){
							discontinued=resultat.getTimestamp("discontinued").toLocalDateTime();
						}
					}
	
					
					return Optional.ofNullable(new Computer(Integer.parseInt(resultat.getString("computer.id")),resultat.getString("computer.name"),comp,
										introduced,discontinued));
				}
	
			} catch (SQLException e) {
				throw new DAOException(e);
			} finally{
				DAOUtils.closePreparedStatement(statement);
				DAOUtils.closeResultatSet(resultat);
				DAOUtils.closeConnection(connection);
			}
		}
		return Optional.empty();
	}

	public ComputerList getComputers() throws DAOException {
		Statement statement = null;
		ResultSet resultat = null;
		Connection connection = null;
		
		try {
			ComputerList list = new ComputerList();
			connection = DataBaseConnection.CONNECTION.getConnection();
			statement = connection.createStatement();
			resultat = statement.executeQuery(SQL_COMPUTERS);
			while(resultat.next()){
				LocalDateTime introduced = null, discontinued = null;
				Company comp=null;
				if(resultat.getString("company_id")!=null){
					comp= new Company(resultat.getLong("company_id"),resultat.getString("company.name"));
				}
				
				if(resultat.getString("introduced")!=null){
;
					introduced=resultat.getTimestamp("introduced").toLocalDateTime();
					if(resultat.getString("discontinued")!=null){
						discontinued=resultat.getTimestamp("discontinued").toLocalDateTime();
					}
				}

				
				list.add(new Computer(Integer.parseInt(resultat.getString("computer.id")),resultat.getString("computer.name"),comp,
									introduced,discontinued));
			}
			return list;

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtils.closeStatement(statement);
			DAOUtils.closeResultatSet(resultat);
			DAOUtils.closeConnection(connection);
		}
	}

}
