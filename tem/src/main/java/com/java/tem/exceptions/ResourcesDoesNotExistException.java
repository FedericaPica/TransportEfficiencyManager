package com.java.tem.exceptions;

/** ResourcesDoesNotExistException.
 * 
 */
@SuppressWarnings("serial")
public class ResourcesDoesNotExistException extends Exception {
  public ResourcesDoesNotExistException(String errorMessage) {
    super(errorMessage);
  }
}
