package com.java.tem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.accountservice.entity.AccountUtilities;
import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.daticorsaservice.DatiCorsa;
import com.java.tem.model.programmacorseservice.entity.daticorsaservice.DatiCorsaService;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Conducente;

@Controller
public class DatiCorsaController {
	
	@Autowired
	DatiCorsaService datiCorsaService;
	
	@Autowired
	AccountService accountService;
	
	@GetMapping("/daticorsa/add")
	  public String addDatiCorsa(Model model) {
	    model.addAttribute("datiCorsa", new DatiCorsa());

	    return "insert-daticorsa";
	  }
	
	
	@PostMapping("/daticorsa/submit")
	  public String processDatiCorsa(DatiCorsa datiCorsa) {
	    if (AccountUtilities.isAuthenticated()) {
	      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	      String currentUserName = authentication.getName();
	      
	      Utente utente = accountService.getUserByUsername(currentUserName);
	      datiCorsa.setAzienda(utente);
	      this.datiCorsaService.addDatiCorsa(datiCorsa);
	      
	      
	      return "dati-corsa-success";
	    } else {
	    	return "login-required";
	    }
	}
}
