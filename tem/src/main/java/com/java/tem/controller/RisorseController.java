package com.java.tem.controller;

import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.accountservice.entity.AccountUtilities;
import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Conducente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Linea;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Mezzo;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Risorsa;
import com.java.tem.model.programmacorseservice.entity.risorseservice.RisorseService;
import com.sun.istack.Nullable;

import javax.lang.model.element.ModuleElement;

import org.aspectj.weaver.ast.Test;
import org.aspectj.weaver.patterns.HasMemberTypePatternForPerThisMatching;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RisorseController {

  @Autowired
  RisorseService risorseService;
  
  @Autowired
  AccountService accountService;

  @GetMapping("/risorse")
  public String homeRisorse() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      return "risorse-index";
    }
    return "login-required";
     
  }
  @GetMapping("/risorse/add/conducente")
  public String addConducente(Model model) {
    model.addAttribute("conducente", new Conducente());
    String method = "/risorse/submit/conducente";
    model.addAttribute("method", method);
    return "insert-conducente";
  }
  
  @GetMapping("/risorse/add/mezzo")
  public String addMezzo(Model model) {
    model.addAttribute("mezzo", new Mezzo());
    return "insert-mezzo";
  }
  
  @GetMapping("/risorse/add/linea")
  public String addLinea(Model model) {
    model.addAttribute("linea", new Linea());
    return "insert-linea";
  }
  
  
  @PostMapping("/risorse/submit/conducente")
  public String processRisorsa(Conducente conducente) {
    if (AccountUtilities.isAuthenticated()) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String currentUserName = authentication.getName();
      Utente utente = accountService.getUserByUsername(currentUserName);
      conducente.setAzienda(utente);
      try {
    	  this.risorseService.addConducente(conducente);
      } catch(Exception e) {
    	  
      }

      return "insert-success-conducente";
    }
    return "login-required";
  }
  
  @PostMapping("/risorse/submit/linea")
  public String processRisorsa(Linea linea) {
    if (AccountUtilities.isAuthenticated()) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String currentUserName = authentication.getName();
      Utente utente = accountService.getUserByUsername(currentUserName);
      linea.setAzienda(utente);
      this.risorseService.addLinea(linea);

      return "insert-success-linea";
    }
    return "login-required";
  }
  
  @PostMapping("/risorse/submit/mezzo")
  public String processRisorsa(Mezzo mezzo) {
    if (AccountUtilities.isAuthenticated()) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String currentUserName = authentication.getName();
      Utente utente = accountService.getUserByUsername(currentUserName);
      mezzo.setAzienda(utente);
      this.risorseService.addMezzo(mezzo);

      return "insert-success";
    }
    return "login-required";
  }
  
  @GetMapping("/risorse/conducente/edit/{id}")
  public String showUpdateFormConducente(@PathVariable("id") Long id, Model model) 
       throws Throwable {


    Conducente conducente = (Conducente) this.risorseService.getConducente(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid Conducente Id:" + id));
      
    model.addAttribute("conducente", conducente);
    return "edit-conducente";
  }
  
  @GetMapping("/risorse/linea/edit/{id}")
  public String showUpdateFormLinea(@PathVariable("id") Long id, Model model) throws Throwable {


    Linea linea = (Linea) this.risorseService.getLinea(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid linea Id:" + id));
      
    model.addAttribute("linea", linea);
    return "edit-linea";
  }
  
  @GetMapping("/risorse/mezzo/edit/{id}")
  public String showUpdateFormMezzo(@PathVariable("id") Long id, Model model) throws Throwable {


    Mezzo mezzo = (Mezzo) this.risorseService.getMezzo(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid linea Id:" + id));
      
    model.addAttribute("mezzo", mezzo);
    return "edit-mezzo";
  }
  
  @PostMapping("risorse/update/conducente/{id}")
  public String updateConducente(@PathVariable("id") Long id, Conducente conducente, 
      BindingResult result,
      Model model) {
    if (result.hasErrors()) {
      conducente.setId(id);
      return "edit-conducente";
    }
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUserName = authentication.getName();
    Utente utente = accountService.getUserByUsername(currentUserName);
    conducente.setAzienda(utente);
    this.risorseService.updateConducente(conducente);
      
    return "update-success";
  }
  
  @PostMapping("risorse/update/linea/{id}")
  public String updateLinea(@PathVariable("id") Long id, Linea linea, 
      BindingResult result,
      Model model) {
    if (result.hasErrors()) {
    	linea.setId(id);
      return "edit-conducente";
    }
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUserName = authentication.getName();
    Utente utente = accountService.getUserByUsername(currentUserName);
    linea.setAzienda(utente);
    this.risorseService.updateLinea(linea);
      
    return "update-success";
  }
  
  @PostMapping("risorse/update/mezzo/{id}")
  public String updateMezzo(@PathVariable("id") Long id, Mezzo mezzo, 
      BindingResult result,
      Model model) {
    if (result.hasErrors()) {
    	mezzo.setId(id);
      return "edit-conducente";
    }
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUserName = authentication.getName();
    Utente utente = accountService.getUserByUsername(currentUserName);
    mezzo.setAzienda(utente);
    this.risorseService.updateMezzo(mezzo);
      
    return "update-success";
  }
  
  @GetMapping("risorse/delete/mezzo/{id}")
  public String deleteMezzo(@PathVariable("id") Long id, Model model) 
      throws IllegalArgumentException {
    Mezzo mezzo = risorseService.getMezzo(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid mezzo Id:" + id));
    risorseService.deleteMezzo(mezzo);

    return "index";
  }
  
  @GetMapping("risorse/delete/conducente/{id}")
  public String deleteConducente(@PathVariable("id") Long id, Model model) 
      throws IllegalArgumentException {
    Conducente conducente = risorseService.getConducente(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid conducente Id:" + id));
    risorseService.deleteConducente(conducente);

    return "index";
  }
  
  @GetMapping("risorse/delete/linea/{id}")
  public String deleteLinea(@PathVariable("id") Long id, Model model) 
      throws IllegalArgumentException {
    Linea linea = risorseService.getLinea(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid linea Id:" + id));
    risorseService.deleteLinea(linea);

    return "index";
  }
}