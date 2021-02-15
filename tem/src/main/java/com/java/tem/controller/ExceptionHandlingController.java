package com.java.tem.controller;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingController {

	@ResponseStatus(value = HttpStatus.CONFLICT)
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public void userAlreadyExists() {
		// ToDo
		System.out.println("Utente già esistente!");
	}
	
}
