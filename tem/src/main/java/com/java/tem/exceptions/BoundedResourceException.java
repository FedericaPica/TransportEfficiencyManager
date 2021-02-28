package com.java.tem.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;

public class BoundedResourceException extends SQLIntegrityConstraintViolationException {
    public BoundedResourceException(String errorMessage) {
        super(errorMessage);
    }
}
