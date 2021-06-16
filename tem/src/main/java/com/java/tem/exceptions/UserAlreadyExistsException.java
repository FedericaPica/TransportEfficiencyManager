package com.java.tem.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;

/** UserAlreadyExistsException.
 * 
 */
@SuppressWarnings("serial")
public class UserAlreadyExistsException extends SQLIntegrityConstraintViolationException {
  public UserAlreadyExistsException(String errorMessage) {
    super(errorMessage);
  }
}
