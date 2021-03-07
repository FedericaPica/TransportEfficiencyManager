package com.java.tem.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;

public class BoundResourceException extends SQLIntegrityConstraintViolationException {
  public BoundResourceException(String errorMessage) {
    super(errorMessage);
  }
}
