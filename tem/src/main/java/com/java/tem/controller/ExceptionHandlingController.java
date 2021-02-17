package com.java.tem.controller;

import com.java.tem.exceptions.UserAlreadyExistsException;
import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingController {

  @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Utente già esistente")
  @ExceptionHandler(UserAlreadyExistsException.class)
  public void userAlreadyExists() {

  }

}
