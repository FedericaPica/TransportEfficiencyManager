package com.java.tem.controller;

import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Conducente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Linea;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Mezzo;
import com.java.tem.model.programmacorseservice.entity.risorseservice.RisorseService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
  
  @GetMapping("/risorse/list")
  public String listRisorse(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUserName = authentication.getName();
    Utente utente = accountService.getUserByUsername(currentUserName);
    List<Mezzo> listMezzi = risorseService.getMezziByAzienda(utente);
    List<Conducente> listConducenti = risorseService.getConducentiByAzienda(utente);
    List<Linea> listLinee = risorseService.getLineeByAzienda(utente);
    model.addAttribute("linee", listLinee);
    model.addAttribute("conducenti", listConducenti);
    model.addAttribute("mezzi", listMezzi);
    return "list-risorse"; 
  }
  
  
  @PostMapping("/risorse/submit/conducente")
  public String processRisorsa(Conducente conducente, Model model) {
    if (AccountService.isAuthenticated()) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String currentUserName = authentication.getName();
      Utente utente = accountService.getUserByUsername(currentUserName);
      conducente.setAzienda(utente);
      try {
        this.risorseService.addConducente(conducente);
        model.addAttribute(conducente);
      } catch (Exception e) {

      }

      return "insert-success-conducente";
    }
    return "login-required";
  }
  
  @PostMapping("/risorse/submit/linea")
public String processRisorsa(@ModelAttribute("linea") @Valid Linea linea, BindingResult bindingResult, Model model) {
    if (AccountService.isAuthenticated()) {
      if (bindingResult.hasErrors()) {
        return "insert-linea";
      }
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String currentUserName = authentication.getName();
      Utente utente = accountService.getUserByUsername(currentUserName);
      linea.setAzienda(utente);
      this.risorseService.addLinea(linea);
      model.addAttribute(linea);

      return "insert-success-linea";
    }
    return "login-required";
  }
  
  @PostMapping("/risorse/submit/mezzo")
public String processRisorsa(@ModelAttribute("mezzo") @Valid Mezzo mezzo, BindingResult bindingResult) {
    if (AccountService.isAuthenticated()) {
      if (bindingResult.hasErrors()) {
        return "insert-mezzo";
      }
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
public String updateLinea(@PathVariable("id") Long id, @ModelAttribute("linea") @Valid Linea linea, 
      BindingResult result,
      Model model) {
    if (result.hasErrors()) {
      return "edit-linea";
    }
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUserName = authentication.getName();
    Utente utente = accountService.getUserByUsername(currentUserName);
    linea.setAzienda(utente);
    this.risorseService.updateLinea(linea);
      
    return "update-success";
  }
  
  @PostMapping("risorse/update/mezzo/{id}")
public String updateMezzo(@PathVariable("id") Long id, @ModelAttribute("mezzo") @Valid Mezzo mezzo, 
      BindingResult result,
      Model model) {
    if (result.hasErrors()) {
      return "edit-mezzo";
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