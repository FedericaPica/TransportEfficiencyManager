package com.java.tem.controller;

import com.java.tem.exceptions.NotAuthorizedException;
import com.java.tem.exceptions.UserAlreadyExistsException;
import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.accountservice.entity.DettaglioUtente;
import com.java.tem.model.accountservice.entity.Profilo;
import com.java.tem.model.accountservice.entity.Utente;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/** AccountController.
 *
 */
@Controller
@RequestMapping("/")
public class AccountController implements WebMvcConfigurer {

  @Autowired
  private AccountService accountService;

  /** Home Page.
   *
   */
  @GetMapping("/")
  public String viewHomePage(Model model) {
    Utente utente = accountService.getLoggedUser();
    model.addAttribute("utente", utente);
    return "index";
  }

  /** Register Page.
   *
   */
  @GetMapping("/register")
  public String showRegistrationForm(Model model, @RequestParam(required = false) String error) {
    model.addAttribute("user", new Utente());
    model.addAttribute("profilo", new Profilo());
    model.addAttribute("dettaglioUtente", new DettaglioUtente());
    model.addAttribute("error", error);
    return "signup_form";
  }

  /** Register Page.
   * method POST.
   */
  @PostMapping("/process_register")
  public ModelAndView processRegister(@ModelAttribute("user") @Valid Utente user,
                                      BindingResult bindingResult,
                                      @ModelAttribute("dettaglioUtente")
                                      @Valid DettaglioUtente dettaglioUtente,
                                      BindingResult bindingResult2, Model model) {


    if (bindingResult.hasErrors() || bindingResult2.hasErrors()) {
      return new ModelAndView("signup_form");
    }

    try {
      if (accountService.checkUserExistanceByEmail(user.getEmail())) {
        throw new UserAlreadyExistsException("Utente già esistente.");
      }
      Utente utente = accountService.registerUser(user, dettaglioUtente);
      return new ModelAndView("register_success");
    } catch (UserAlreadyExistsException exc) {
      model.addAttribute("error", exc.getMessage());
      return new ModelAndView("redirect:/register", (ModelMap) model);
    }


  }

  /** Users list Page.
   *
   */
  @GetMapping("/users")
  public String listUsers(Model model) throws NotAuthorizedException {
    if (accountService.isAdmin()) {
      List<Utente> listUsers = accountService.getAllUsers();
      model.addAttribute("utenti", listUsers);
      return "admin";
    } else {
      System.out.println(accountService.isAdmin());
      throw new NotAuthorizedException("Utente non autorizzato");
    }
  }

  @GetMapping("/logout")
  public String logout() {
    return "index";
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  /** Home Page.
   *
   */
  @GetMapping("/home")
  public ModelAndView home(@RequestParam(required = false) String error, Model model) {
    if (accountService.isAdmin()) {
      return new ModelAndView("redirect:/users");
    } else {
      model.addAttribute("error", error);
      return new ModelAndView("/home");

    }
  }
}
