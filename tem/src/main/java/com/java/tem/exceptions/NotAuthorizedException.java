package com.java.tem.exceptions;

public class NotAuthorizedException extends Exception {
  public NotAuthorizedException(String errorMessage) {
    super(errorMessage);
  }
}
