package com.java.tem.controller;

import com.java.tem.exceptions.DoesNotBelongToAzienda;
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
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

  @GetMapping("/risorse/list")
  public String listRisorse(Model model) {
    Utente utente = accountService.getLoggedUser();
    List<Mezzo> listMezzi = risorseService.getMezziByAzienda(utente);
    List<Conducente> listConducenti = risorseService.getConducentiByAzienda(utente);
    List<Linea> listLinee = risorseService.getLineeByAzienda(utente);
    boolean isAdmin = accountService.isAdmin();
    model.addAttribute("azienda", utente);
    model.addAttribute("linee", listLinee);
    model.addAttribute("conducenti", listConducenti);
    model.addAttribute("mezzi", listMezzi);
    model.addAttribute("adminCheck", isAdmin);
    return "list-risorse";
  }

  @GetMapping("/risorse/list/{aziendaId}")
  public String listRisorseByAzienda(@PathVariable("aziendaId") Long aziendaId, Model model) {
    Utente azienda = accountService.getUserById(aziendaId);
    List<Mezzo> listMezzi = risorseService.getMezziByAzienda(azienda);
    List<Conducente> listConducenti = risorseService.getConducentiByAzienda(azienda);
    List<Linea> listLinee = risorseService.getLineeByAzienda(azienda);
    boolean isAdmin = accountService.isAdmin();
    model.addAttribute("azienda", azienda);
    model.addAttribute("linee", listLinee);
    model.addAttribute("conducenti", listConducenti);
    model.addAttribute("mezzi", listMezzi);
    model.addAttribute("adminCheck", isAdmin);

    return "list-risorse";
  }


  @PostMapping("/risorse/submit/conducente")
  public String processRisorsa(@ModelAttribute("conducente") @Valid Conducente conducente,
                               BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "insert-conducente";
    }
    Utente utente = accountService.getLoggedUser();
    conducente.setAzienda(utente);
    this.risorseService.addConducente(conducente);
    model.addAttribute(conducente);
    return "insert-success-conducente";
  }

  @PostMapping("/risorse/submit/linea")
  public String processRisorsa(@ModelAttribute("linea") @Valid Linea linea,
                               BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "insert-linea";
    }
    Utente utente = accountService.getLoggedUser();
    linea.setAzienda(utente);
    this.risorseService.addLinea(linea);
    model.addAttribute(linea);

    return "insert-success-linea";
  }

  @PostMapping("/risorse/submit/mezzo")
  public String processRisorsa(@ModelAttribute("mezzo") @Valid Mezzo mezzo,
                               BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "insert-mezzo";
    }
    Utente utente = accountService.getLoggedUser();
    mezzo.setAzienda(utente);
    this.risorseService.addMezzo(mezzo);

    return "insert-success";
  }

  @GetMapping("/risorse/conducente/edit/{id}")
  public ModelAndView showUpdateFormConducente(@PathVariable("id") Long id, Model model)
      throws IllegalArgumentException {

    Conducente conducente = this.risorseService.getConducente(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid Conducente Id:" + id));
    Utente utente = accountService.getLoggedUser();

    try {
      if (!risorseService.checkOwnership(conducente, utente)) {
        throw new DoesNotBelongToAzienda("La risorsa non appartiene all'azienda");
      }
      model.addAttribute("mezzo", conducente);
      return new ModelAndView("edit-conducente");
    } catch (DoesNotBelongToAzienda exc) {
      model.addAttribute("error", exc.getMessage());
      return new ModelAndView("redirect:/home", (ModelMap) model);
    }
  }

  @GetMapping("/risorse/linea/edit/{id}")
  public ModelAndView showUpdateFormLinea(@PathVariable("id") Long id, Model model)
      throws IllegalArgumentException {
    Linea linea = this.risorseService.getLinea(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid linea Id:" + id));
    Utente utente = accountService.getLoggedUser();

    try {
      if (!risorseService.checkOwnership(linea, utente)) {
        throw new DoesNotBelongToAzienda("La risorsa non appartiene all'azienda");
      }
      model.addAttribute("mezzo", linea);
      return new ModelAndView("edit-linea");
    } catch (DoesNotBelongToAzienda exc) {
      model.addAttribute("error", exc.getMessage());
      return new ModelAndView("redirect:/home", (ModelMap) model);
    }
  }

  @GetMapping("/risorse/mezzo/edit/{id}")
  public ModelAndView showUpdateFormMezzo(@PathVariable("id") Long id, Model model)
      throws Throwable {
    Mezzo mezzo = this.risorseService.getMezzo(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid linea Id:" + id));
    Utente utente = accountService.getLoggedUser();

    try {
      if (!risorseService.checkOwnership(mezzo, utente)) {
        throw new DoesNotBelongToAzienda("La risorsa non appartiene all'azienda");
      }
      model.addAttribute("mezzo", mezzo);
      return new ModelAndView("edit-mezzo");
    } catch (DoesNotBelongToAzienda exc) {
      model.addAttribute("error", exc.getMessage());
      return new ModelAndView("redirect:/home", (ModelMap) model);
    }
  }

  @PostMapping("risorse/update/conducente/{id}")
  public String updateConducente(@PathVariable("id") Long id, Conducente conducente,
                                 BindingResult result,
                                 Model model) {
    if (result.hasErrors()) {
      return "edit-conducente";
    }
    Utente utente = accountService.getLoggedUser();
    conducente.setAzienda(utente);
    this.risorseService.updateConducente(conducente);

    return "update-success";
  }

  @PostMapping("risorse/update/linea/{id}")
  public String updateLinea(@PathVariable("id") Long id,
                            @ModelAttribute("linea") @Valid Linea linea,
                            BindingResult result,
                            Model model) {
    if (result.hasErrors()) {
      return "edit-linea";
    }
    Utente utente = accountService.getLoggedUser();
    linea.setAzienda(utente);
    this.risorseService.updateLinea(linea);

    return "update-success";
  }

  @PostMapping("risorse/update/mezzo/{id}")
  public String updateMezzo(@PathVariable("id") Long id,
                            @ModelAttribute("mezzo") @Valid Mezzo mezzo,
                            BindingResult result,
                            Model model) {
    if (result.hasErrors()) {
      return "edit-mezzo";
    }
    Utente utente = accountService.getLoggedUser();
    mezzo.setAzienda(utente);
    this.risorseService.updateMezzo(mezzo);

    return "update-success";
  }

  @GetMapping("risorse/delete/mezzo/{id}")
  public ModelAndView deleteMezzo(@PathVariable("id") Long id, Model model)
      throws IllegalArgumentException, DoesNotBelongToAzienda {
    Mezzo mezzo = risorseService.getMezzo(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid mezzo Id:" + id));
    Utente utente = accountService.getLoggedUser();

    try {
      if (!risorseService.checkOwnership(mezzo, utente)) {
        throw new DoesNotBelongToAzienda("La risorsa non appartiene all'azienda");
      }
      risorseService.deleteMezzo(mezzo);
    } catch (DoesNotBelongToAzienda exc) {
      model.addAttribute("error", exc.getMessage());
    }
    return new ModelAndView("redirect:/home", (ModelMap) model);

  }

  @GetMapping("risorse/delete/conducente/{id}")
  public ModelAndView deleteConducente(@PathVariable("id") Long id, Model model)
      throws IllegalArgumentException {
    Conducente conducente = risorseService.getConducente(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid conducente Id:" + id));
    Utente utente = accountService.getLoggedUser();

    try {
      if (!risorseService.checkOwnership(conducente, utente)) {
        throw new DoesNotBelongToAzienda("La risorsa non appartiene all'azienda");
      }
      risorseService.deleteConducente(conducente);
    } catch (DoesNotBelongToAzienda exc) {
      model.addAttribute("error", exc.getMessage());
    }
    return new ModelAndView("redirect:/home", (ModelMap) model);
  }

  @GetMapping("risorse/delete/linea/{id}")
  public ModelAndView deleteLinea(@PathVariable("id") Long id, Model model)
      throws IllegalArgumentException {
    Linea linea = risorseService.getLinea(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid linea Id:" + id));
    Utente utente = accountService.getLoggedUser();

    try {
      if (!risorseService.checkOwnership(linea, utente)) {
        throw new DoesNotBelongToAzienda("La risorsa non appartiene all'azienda");
      }
      risorseService.deleteLinea(linea);
    } catch (DoesNotBelongToAzienda exc) {
      model.addAttribute("error", exc.getMessage());
    }
    return new ModelAndView("redirect:/home", (ModelMap) model);
  }
}