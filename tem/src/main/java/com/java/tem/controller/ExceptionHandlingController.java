package com.java.tem.controller;

import com.java.tem.exceptions.GenerationTypeNotFoundException;
import com.java.tem.exceptions.NotAuthorizedException;
import com.java.tem.exceptions.DoesNotBelongToAzienda;
import com.java.tem.exceptions.ResourcesNotExistException;
import com.java.tem.exceptions.UserAlreadyExistsException;
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
  
  @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Una o più risorse mancanti")
  @ExceptionHandler(ResourcesNotExistException.class)
  public void noResourcesFound() {
	  
  }

  @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Utente non autorizzato")
  @ExceptionHandler(NotAuthorizedException.class)
  public void notAuthorized() {

  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Tipo di Generazione non valido.")
  @ExceptionHandler(GenerationTypeNotFoundException.class)
  public void generationNotFound() {

  }

  @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "La risorsa non appartiene all'azienda")
  @ExceptionHandler(DoesNotBelongToAzienda.class)
  public void resourceDoesNotBelong() {

  }

}
