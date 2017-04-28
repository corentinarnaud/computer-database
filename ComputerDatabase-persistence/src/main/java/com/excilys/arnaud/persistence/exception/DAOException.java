package com.excilys.arnaud.persistence.exception;

public class DAOException extends RuntimeException {

  /** .
   * 
   */
  private static final long serialVersionUID = 8698901543165093606L;

  public DAOException(String message) {
    super(message);
  }

  public DAOException(String message, Throwable cause) {
    super(message, cause);
  }

  public DAOException(Throwable cause) {
    super(cause);
  }

}
