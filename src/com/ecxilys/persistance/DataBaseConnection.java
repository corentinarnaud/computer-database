package com.ecxilys.persistance;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

public enum DataBaseConnection {
	CONNECTION;
	
	private static final String PROPERTY_FILE       = "/config.properties";
	private static final String PROPERTY_BASE            = "base";
    private static final String PROPERTY_URL             = "url";
    private static final String PROPERTY_ARGUMENTS       = "arguments";
    private static final String PROPERTY_DRIVER          = "driver";
    private static final String PROPERTY_USER            = "user";
    private static final String PROPERTY_PASSWORD        = "password";
    
    private String url;
    private String driver;
    private String user;
    private String password;
    private String base;
    private String arguments;
    
    private Optional<Connection> connection = Optional.empty();
    
    private DataBaseConnection(){
    	getProperties();
    }

    private void getProperties() throws DAOConfigurationException{

        
    	Properties properties = new Properties();
    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fichierProperties = classLoader.getResourceAsStream( PROPERTY_FILE );

        if ( fichierProperties == null ) {
            throw new DAOConfigurationException( "Property file " + PROPERTY_FILE + " not found." );
        }
        
        try{
        	properties.load(fichierProperties);
        	url 		= properties.getProperty(PROPERTY_URL);
        	driver 		= properties.getProperty(PROPERTY_DRIVER);
        	base 		= properties.getProperty(PROPERTY_BASE);
        	arguments 	= properties.getProperty(PROPERTY_ARGUMENTS);
        	user 		= properties.getProperty(PROPERTY_USER);
        	password	= properties.getProperty(PROPERTY_PASSWORD);
        } catch ( IOException e ) {
            throw new DAOConfigurationException( "Fail to load property file " + PROPERTY_FILE, e );
        }
    	
    	
    }
    private void connect() throws DAOConfigurationException{
    	

		try {
			
			Class.forName( driver );
			connection = Optional.ofNullable(DriverManager.getConnection( base+url+arguments, user, password ));
		} catch ( ClassNotFoundException e ) {
			throw new DAOConfigurationException( "Driver not found", e );
		} catch ( SQLException e ) {
			throw new DAOException("Fail to connect to the base", e);
		}
		
	}
    
    
    public Connection getConnection(){
    	if(!connection.isPresent())
    		connect();
    	return connection.get();
    }
	
	public void close() throws DAOException{
		if ( connection.isPresent()){
			try {
			    connection.get().close();
			    connection = Optional.empty();
			} catch ( SQLException e ) {
				throw new DAOException("Fail to disconnect", e);
			}
		}
	}
}
