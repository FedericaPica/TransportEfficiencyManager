package com.java.tem.controller;

import com.java.tem.exceptions.DatiCorsaNotExistException;
import com.java.tem.exceptions.DoesNotBelongToAziendaException;
import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.daticorsaservice.DatiCorsa;
import com.java.tem.model.programmacorseservice.entity.daticorsaservice.DatiCorsaService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/** DatiCorsaController.
 *
 */
@Controller
public class DatiCorsaController {

  @Autowired
  DatiCorsaService datiCorsaService;

  @Autowired
  AccountService accountService;

  /** Add DatiCorsa Page.
   *
   */
  @GetMapping("/daticorsa/add")
  public String addDatiCorsa(Model model) {
    model.addAttribute("datiCorsa", new DatiCorsa());

    return "insert-daticorsa";
  }

  /** Edit DatiCorsa Page.
   *
   */
  @GetMapping("/daticorsa/edit/{id}")
  public ModelAndView showFormDatiCorsa(@PathVariable("id") Long id, Model model)
      throws DatiCorsaNotExistException {
    DatiCorsa datiCorsa = datiCorsaService.getDatiCorsa(id)
        .orElseThrow(() -> new DatiCorsaNotExistException("Invalid Dati Corsa Id:" + id));
    model.addAttribute("datiCorsa", datiCorsa);

    Utente utente = accountService.getLoggedUser();
    try {
      if (!datiCorsaService.checkOwnership(datiCorsa, utente)) {
        throw new DoesNotBelongToAziendaException("I dati corsa non appartengono alla tua azienda");
      }
      return new ModelAndView("edit-daticorsa");
    } catch (DoesNotBelongToAziendaException exc) {
      model.addAttribute("error", exc.getMessage());
      return new ModelAndView("redirect:/home", (ModelMap) model);
    }
  }

  /** Submit DatiCorsa Page.
   * method POST
   */
  @PostMapping("/daticorsa/submit")
  public ModelAndView processDatiCorsa(@ModelAttribute("datiCorsa") @Valid DatiCorsa datiCorsa,
                                       BindingResult bindingResult,
                                       Model model) {
    if (bindingResult.hasErrors()) {
      return new ModelAndView("insert-daticorsa");
    }
    Utente utente = accountService.getLoggedUser();
    datiCorsa.setAzienda(utente);
    datiCorsaService.addDatiCorsa(datiCorsa);
    model.addAttribute(datiCorsa);
    return new ModelAndView("dati-corsa-success");


  }

  /** Delete Page.
   * method POST
   */
  @PostMapping("daticorsa/update/{id}")
  public ModelAndView updateDatiCorsa(@PathVariable("id") Long id,
                                      @Valid DatiCorsa datiCorsa,
                                      BindingResult result,
                                      Model model) {
    if (result.hasErrors()) {
      return new ModelAndView("edit-daticorsa");
    }
    Utente utente = accountService.getLoggedUser();
    datiCorsa.setAzienda(utente);
    datiCorsaService.updateDatiCorsa(datiCorsa);
    return new ModelAndView("update-success");
  }

  /** Delete Page.
   * method GET
   */
  @GetMapping("daticorsa/delete/{id}")
  public ModelAndView deleteDatiCorsa(@PathVariable("id") Long id, Model model)
      throws IllegalArgumentException {
    DatiCorsa datiCorsa = datiCorsaService.getDatiCorsa(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid daticorsa Id:" + id));
    Utente utente = accountService.getLoggedUser();

    try {
      if (!datiCorsaService.checkOwnership(datiCorsa, utente)) {
        throw new DoesNotBelongToAziendaException("I dati corsa non appartengono alla tua azienda");
      }
      datiCorsaService.deleteDatiCorsa(datiCorsa);
    } catch (DoesNotBelongToAziendaException exc) {
      model.addAttribute("error", exc.getMessage());
    }
    return new ModelAndView("redirect:/home", (ModelMap) model);
  }

  /** List DatiCorsa Page.
   *
   */
  @GetMapping("/daticorsa/list")
  public String listDatiCorsa(Model model) {
    Utente utente = accountService.getLoggedUser();
    boolean isAdmin = accountService.isAdmin();
    List<DatiCorsa> listDatiCorsa = datiCorsaService.getDatiCorsaByAzienda(utente);
    model.addAttribute("datiCorse", listDatiCorsa);
    model.addAttribute("adminCheck", isAdmin);
    return "list-daticorsa";
  }

  /** List DatiCorsa by Utente ID Page.
   *
   */
  @GetMapping("/daticorsa/list/{aziendaId}")
  public String listDatiCorsaByAzienda(@PathVariable("aziendaId") Long id, Model model) {
    Utente azienda = accountService.getUserById(id);
    boolean isAdmin = accountService.isAdmin();

    List<DatiCorsa> listDatiCorsa = datiCorsaService.getDatiCorsaByAzienda(azienda);
    model.addAttribute("datiCorse", listDatiCorsa);
    model.addAttribute("azienda", azienda);
    model.addAttribute("adminCheck", isAdmin);
    return "list-daticorsa";
  }
}
