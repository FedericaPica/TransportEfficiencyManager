package com.java.tem.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;

/** NotAuthorizedException.
 * 
 */
@SuppressWarnings("serial")
public class BoundResourceException extends SQLIntegrityConstraintViolationException {
  public BoundResourceException(String errorMessage) {
    super(errorMessage);
  }
}
