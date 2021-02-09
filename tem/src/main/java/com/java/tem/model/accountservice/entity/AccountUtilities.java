package com.java.tem.model.accountservice.entity;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public final class AccountUtilities {
  public AccountUtilities() {
		
  }
	
	// Deprecated in favour of Authentication.isAuthenticated()
	public static Boolean isAuthenticated() {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		  if (!(authentication instanceof AnonymousAuthenticationToken)) {
			  return true;
		  } else {
			  return false;
		  }
	}
	
	public static void isAdmin() {
		 // ToDo
	}
}
