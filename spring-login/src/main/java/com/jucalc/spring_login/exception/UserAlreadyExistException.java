package com.jucalc.spring_login.exception;

public class UserAlreadyExistException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public UserAlreadyExistException(String message) {
    super(message);
  }

  public UserAlreadyExistException(Throwable ex) {
    super(ex);
  }

  public UserAlreadyExistException(String message, Throwable ex) {
    super(message, ex);
  }

}
