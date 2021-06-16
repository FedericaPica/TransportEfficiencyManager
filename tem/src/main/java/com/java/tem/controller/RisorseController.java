package com.java.tem.controller;

import com.java.tem.exceptions.BoundResourceException;
import com.java.tem.exceptions.DoesNotBelongToAziendaException;
import com.java.tem.exceptions.ResourcesDoesNotExistException;
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

/** RisorseController.
 *
 */
@Controller
public class RisorseController {

  @Autowired
  RisorseService risorseService;

  @Autowired
  AccountService accountService;

  @GetMapping("/risorse")
  public String homeRisorse() {
    return "risorse-index";
  }

  /** Add Conducente Page.
   *
   */
  @GetMapping("/risorse/add/conducente")
  public String addConducente(Model model) {
    model.addAttribute("conducente", new Conducente());
    String method = "/risorse/submit/conducente";
    model.addAttribute("method", method);
    return "insert-conducente";
  }

  /** Add Mezzo Page.
   *
   */
  @GetMapping("/risorse/add/mezzo")
  public String addMezzo(Model model) {
    model.addAttribute("mezzo", new Mezzo());
    return "insert-mezzo";
  }

  /** Add Linea Page.
   *
   */
  @GetMapping("/risorse/add/linea")
  public String addLinea(Model model) {
    model.addAttribute("linea", new Linea());
    return "insert-linea";
  }

  /** Risorse List Page.
   *
   */
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

  /** Risorse List by Utente ID Page.
   *
   */
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

  /** Submit Conducente Page.
   * method POST
   */
  @PostMapping("/risorse/submit/conducente")
  public String processRisorsa(@ModelAttribute("conducente") @Valid Conducente conducente,
                               BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "insert-conducente";
    }
    Utente utente = accountService.getLoggedUser();
    conducente.setAzienda(utente);
    risorseService.addConducente(conducente);
    model.addAttribute(conducente);
    return "insert-success-conducente";
  }

  /** Submit Linea Page.
   * method POST
   */
  @PostMapping("/risorse/submit/linea")
  public String processRisorsa(@ModelAttribute("linea") @Valid Linea linea,
                               BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "insert-linea";
    }
    Utente utente = accountService.getLoggedUser();
    linea.setAzienda(utente);
    risorseService.addLinea(linea);
    model.addAttribute(linea);

    return "insert-success-linea";
  }

  /** Submit Mezzo Page.
   * method POST
   */
  @PostMapping("/risorse/submit/mezzo")
  public String processRisorsa(@ModelAttribute("mezzo") @Valid Mezzo mezzo,
                               BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "insert-mezzo";
    }
    Utente utente = accountService.getLoggedUser();
    mezzo.setAzienda(utente);
    risorseService.addMezzo(mezzo);

    return "insert-success";
  }

  /** Edit Conducente Page.
   *
   */
  @GetMapping("/risorse/conducente/edit/{id}")
  public ModelAndView showUpdateFormConducente(@PathVariable("id") Long id, Model model)
      throws ResourcesDoesNotExistException {

    Conducente conducente = risorseService.getConducente(id)
        .orElseThrow(() -> new ResourcesDoesNotExistException("Invalid Conducente Id:" + id));
    Utente utente = accountService.getLoggedUser();

    try {
      if (!risorseService.checkOwnership(conducente, utente)) {
        throw new DoesNotBelongToAziendaException("La risorsa non appartiene all'azienda");
      }
      model.addAttribute("conducente", conducente);
      return new ModelAndView("edit-conducente");
    } catch (DoesNotBelongToAziendaException exc) {
      model.addAttribute("error", exc.getMessage());
      return new ModelAndView("redirect:/home", (ModelMap) model);
    }
  }

  /** Edit Linea Page.
   *
   */
  @GetMapping("/risorse/linea/edit/{id}")
  public ModelAndView showUpdateFormLinea(@PathVariable("id") Long id, Model model)
      throws ResourcesDoesNotExistException {
    Linea linea = risorseService.getLinea(id)
        .orElseThrow(() -> new ResourcesDoesNotExistException("Invalid linea Id:" + id));
    Utente utente = accountService.getLoggedUser();

    try {
      if (!risorseService.checkOwnership(linea, utente)) {
        throw new DoesNotBelongToAziendaException("La risorsa non appartiene all'azienda");
      }
      model.addAttribute("linea", linea);
      return new ModelAndView("edit-linea");
    } catch (DoesNotBelongToAziendaException exc) {
      model.addAttribute("error", exc.getMessage());
      return new ModelAndView("redirect:/home", (ModelMap) model);
    }
  }

  /** Edit Mezzo Page.
   *
   */
  @GetMapping("/risorse/mezzo/edit/{id}")
  public ModelAndView showUpdateFormMezzo(@PathVariable("id") Long id, Model model)
      throws Throwable {
    Mezzo mezzo = risorseService.getMezzo(id)
        .orElseThrow(() -> new ResourcesDoesNotExistException("Invalid mezzo Id:" + id));
    Utente utente = accountService.getLoggedUser();

    try {
      if (!risorseService.checkOwnership(mezzo, utente)) {
        throw new DoesNotBelongToAziendaException("La risorsa non appartiene all'azienda");
      }
      model.addAttribute("mezzo", mezzo);
      return new ModelAndView("edit-mezzo");
    } catch (DoesNotBelongToAziendaException exc) {
      model.addAttribute("error", exc.getMessage());
      return new ModelAndView("redirect:/home", (ModelMap) model);
    }
  }

  /** Update Conducente Page.
   * method POST
   */
  @PostMapping("risorse/update/conducente/{id}")
  public String updateConducente(@PathVariable("id") Long id,
                                 @ModelAttribute("conducente") @Valid Conducente conducente,
                                 BindingResult result,
                                 Model model) {
    if (result.hasErrors()) {
      return "edit-conducente";
    }
    Utente utente = accountService.getLoggedUser();
    conducente.setAzienda(utente);
    risorseService.updateConducente(conducente);

    return "update-success";
  }

  /** Update Linea Page.
   * method POST
   */
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
    risorseService.updateLinea(linea);

    return "update-success";
  }

  /** Update Mezzo Page.
   * method POST
   */
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
    risorseService.updateMezzo(mezzo);

    return "update-success";
  }

  /** Delete Mezzo Page.
   *
   */
  @GetMapping("risorse/delete/mezzo/{id}")
  public ModelAndView deleteMezzo(@PathVariable("id") Long id, Model model)
      throws ResourcesDoesNotExistException, BoundResourceException {
    Mezzo mezzo = risorseService.getMezzo(id)
        .orElseThrow(() -> new ResourcesDoesNotExistException("Invalid mezzo Id:" + id));
    Utente utente = accountService.getLoggedUser();

    try {
      if (!risorseService.checkOwnership(mezzo, utente)) {
        throw new DoesNotBelongToAziendaException("La risorsa non appartiene all'azienda");
      }

      if (risorseService.isBound(mezzo)) {
        throw new BoundResourceException(
            "La risorsa è collegata ad una corsa e pertanto non può essere eliminata.");
      }
      risorseService.deleteMezzo(mezzo);
    } catch (DoesNotBelongToAziendaException exc) {
      model.addAttribute("error", exc.getMessage());
    }
    return new ModelAndView("redirect:/home", (ModelMap) model);

  }

  /** Delete Conducente Page.
   *
   */
  @GetMapping("risorse/delete/conducente/{id}")
  public ModelAndView deleteConducente(@PathVariable("id") Long id, Model model)
      throws ResourcesDoesNotExistException, BoundResourceException {
    Conducente conducente = risorseService.getConducente(id)
        .orElseThrow(() -> new ResourcesDoesNotExistException("Invalid conducente Id:" + id));
    Utente utente = accountService.getLoggedUser();

    try {
      if (!risorseService.checkOwnership(conducente, utente)) {
        throw new DoesNotBelongToAziendaException("La risorsa non appartiene all'azienda");
      }

      if (risorseService.isBound(conducente)) {
        throw new BoundResourceException(
            "La risorsa è collegata ad una corsa e pertanto non può essere eliminata.");
      }
      risorseService.deleteConducente(conducente);
    } catch (DoesNotBelongToAziendaException exc) {
      model.addAttribute("error", exc.getMessage());
    }
    return new ModelAndView("redirect:/home", (ModelMap) model);
  }

  /** Delete Linea Page.
   *
   */
  @GetMapping("risorse/delete/linea/{id}")
  public ModelAndView deleteLinea(@PathVariable("id") Long id, Model model)
      throws ResourcesDoesNotExistException, BoundResourceException {
    Linea linea = risorseService.getLinea(id)
        .orElseThrow(() -> new ResourcesDoesNotExistException("Invalid linea Id:" + id));
    Utente utente = accountService.getLoggedUser();

    try {
      if (!risorseService.checkOwnership(linea, utente)) {
        throw new DoesNotBelongToAziendaException("La risorsa non appartiene all'azienda");
      }

      if (risorseService.isBound(linea)) {
        throw new BoundResourceException(
            "La risorsa è collegata ad una corsa e pertanto non può essere eliminata.");
      }
      risorseService.deleteLinea(linea);
    } catch (DoesNotBelongToAziendaException exc) {
      model.addAttribute("error", exc.getMessage());
    }
    return new ModelAndView("redirect:/home", (ModelMap) model);
  }
}