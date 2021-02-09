package com.java.tem.controller;

import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.accountservice.entity.AccountUtilities;
import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Conducente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Linea;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Mezzo;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Risorsa;
import com.java.tem.model.programmacorseservice.entity.risorseservice.RisorseService;
import javax.lang.model.element.ModuleElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RisorseController<T> {

  @Autowired
  RisorseService risorseService;
  
  @Autowired
  AccountService accountService;

  @GetMapping("/risorse")
  public String homeRisorse() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      return "risorse/risorse-index";
    }
    return "errors/login-required.html";
     
  }
  @GetMapping("/risorse/add/conducente")
  public String addConducente(Model model) {
    model.addAttribute("conducente", new Conducente());
    return "risorse/insert-conducente";
  }
  
  @GetMapping("/risorse/add/mezzo")
  public String addMezzo(Model model) {
    model.addAttribute("mezzo", new Mezzo());
    return "risorse/insert-mezzo";
  }
  
  @GetMapping("/risorse/add/linea")
  public String addLinea(Model model) {
    model.addAttribute("linea", new Linea());
    return "risorse/insert-linea";
  }
  
  
  @PostMapping("/risorse/submit")
  public String processConducente(T model) {
    if (AccountUtilities.isAuthenticated()) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String currentUserName = authentication.getName();
      Utente utente = accountService.getUserByUsername(currentUserName);

      if (model instanceof Mezzo) {
        Mezzo mezzo = (Mezzo) model;
        // risorseService.addMezzo(mezzo);
      } else if (model instanceof Linea) {
        Linea linea = (Linea) model;
        // risorseService.addLinea(linea);
      } else {
        Conducente conducente = (Conducente) model;
        conducente.setAzienda(utente);
        risorseService.addConducente(conducente);
      }
      return "insert-success";
    }
    return "error/login-required";
  }
  
  /*@GetMapping("/risorsa/conducente/edit/{id}")
  public String showUpdateForm(@PathVariable("id") long id, Model model) {
      Conducente conducente = 
        .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
      
      model.addAttribute("user", user);
      return "update-user";
  }*/
}
