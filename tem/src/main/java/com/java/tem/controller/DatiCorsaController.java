package com.java.tem.controller;

import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.accountservice.entity.AccountUtilities;
import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.daticorsaservice.DatiCorsa;
import com.java.tem.model.programmacorseservice.entity.daticorsaservice.DatiCorsaService;
import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

  @GetMapping("/daticorsa/edit/{id}")
public String showFormDatiCorsa(@PathVariable("id") Long id, Model model) {
    DatiCorsa datiCorsa = (DatiCorsa) this.datiCorsaService.getDatiCorsa(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid Conducente Id:" + id));
    model.addAttribute("datiCorsa", datiCorsa);
    return "edit-daticorsa";
  }
	
  @PostMapping("/daticorsa/submit")
public String processDatiCorsa(@ModelAttribute("datiCorsa") @Valid DatiCorsa datiCorsa, BindingResult bindingResult, Model model) {
    if (AccountUtilities.isAuthenticated()) {
      if (bindingResult.hasErrors()) {
        return "insert-daticorsa";
      }
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String currentUserName = authentication.getName();
      Utente utente = accountService.getUserByUsername(currentUserName);
      datiCorsa.setAzienda(utente);
      this.datiCorsaService.addDatiCorsa(datiCorsa);
      model.addAttribute(datiCorsa);
      return "dati-corsa-success";
    } else {
      return "login-required";
    }
  }

  @PostMapping("daticorsa/update/{id}")
public String updateDatiCorsa(@PathVariable("id") Long id, DatiCorsa datiCorsa, 
      BindingResult result,
        Model model) {
    if (result.hasErrors()) {
      return "edit-daticorsa";
    }
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUserName = authentication.getName();
    Utente utente = accountService.getUserByUsername(currentUserName);
    datiCorsa.setAzienda(utente);
    this.datiCorsaService.updateDatiCorsa(datiCorsa);
    return "update-success";
  }

  @GetMapping("daticorsa/delete/{id}")
    public String deleteMezzo(@PathVariable("id") Long id, Model model) 
        throws IllegalArgumentException {
    DatiCorsa datiCorsa = datiCorsaService.getDatiCorsa(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid daticorsa Id:" + id));
    datiCorsaService.deleteDatiCorsa(datiCorsa);

    return "home";
  }
  
  @GetMapping("/daticorsa/list")
  public String listDatiCorsa(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUserName = authentication.getName();
    Utente utente = accountService.getUserByUsername(currentUserName);
    List<DatiCorsa> listDatiCorsa = datiCorsaService.getDatiCorsaByAzienda(utente);
    model.addAttribute("datiCorse", listDatiCorsa);
    return "list-daticorsa"; 
  }
}
