package com.excilys.arnaud.persistance;

public class DAOConfigurationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2965452727175597836L;

	public DAOConfigurationException( String message ) {
        super( message );
    }

    public DAOConfigurationException( String message, Throwable cause ) {
        super( message, cause );
    }

    public DAOConfigurationException( Throwable cause ) {
        super( cause );
    }
}
