package com.java.tem.exceptions;

/** NotAuthorizedException.
 * 
 */
@SuppressWarnings("serial")
public class NotAuthorizedException extends Exception {
  public NotAuthorizedException(String errorMessage) {
    super(errorMessage);
  }
}
