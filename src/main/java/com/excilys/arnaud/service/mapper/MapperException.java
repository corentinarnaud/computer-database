package com.excilys.arnaud.service.mapper;

public class MapperException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -4882903980685446306L;

  public MapperException(String message) {
    super(message);
  }

  public MapperException(String message, Throwable cause) {
    super(message, cause);
  }

  public MapperException(Throwable cause) {
    super(cause);
  }

}
