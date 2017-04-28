package com.excilys.arnaud.service;

public class ServiceException extends Exception {

  /** .
   * 
   */
  private static final long serialVersionUID = -6172940909819132304L;

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public ServiceException(Throwable cause) {
    super(cause);
  }

}
