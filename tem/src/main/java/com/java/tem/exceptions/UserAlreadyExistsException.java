package com.java.tem.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;

public class UserAlreadyExistsException extends SQLIntegrityConstraintViolationException {
	public UserAlreadyExistsException(String errorMessage) {
		super(errorMessage);
	}
}
