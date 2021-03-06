package com.java.tem.controller;

import com.java.tem.exceptions.UserAlreadyExistsException;
import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.accountservice.entity.DettaglioUtente;
import com.java.tem.model.accountservice.entity.Profilo;
import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.accountservice.repository.DettaglioUtenteRepository;
import com.java.tem.model.accountservice.repository.ProfiloRepository;
import com.java.tem.model.accountservice.repository.UserRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Controller
@RequestMapping("/")
public class AccountController implements WebMvcConfigurer {
 
  @Autowired
    private UserRepository userRepo;

  @Autowired
    private AccountService accountService;

  @GetMapping("/")
    public String viewHomePage() {
    return "index";
  }
  
  @GetMapping("/register")
    public String showRegistrationForm(Model model) {
    model.addAttribute("user", new Utente());
    model.addAttribute("profilo", new Profilo());
    model.addAttribute("dettaglioUtente", new DettaglioUtente());
         
    return "signup_form";
  }
  
  @PostMapping("/process_register")
    public String processRegister(@ModelAttribute("user") @Valid Utente user,
                                  BindingResult bindingResult,
                                  @ModelAttribute("dettaglioUtente") @Valid DettaglioUtente dettaglioUtente,
                                  BindingResult bindingResult2) throws UserAlreadyExistsException {
    
    
    if (bindingResult.hasErrors() || bindingResult2.hasErrors()) {
      return "signup_form";
    }
    if (userRepo.checkUserExistanceByEmail(user.getEmail())) {
      throw new UserAlreadyExistsException("Utente già esistente.");
    } else {
      accountService.registerUser(user, dettaglioUtente);
      return "register_success";
    } 
    
  }
  
  @GetMapping("/users")
    public String listUsers(Model model) {
    List<Utente> listUsers = userRepo.findAll();
    model.addAttribute("listUsers", listUsers);
         
    return "users";
  }
   
  @GetMapping("/logout")
    public String logout() {
    return "index";
  }
  
  @GetMapping("/login")
  public String login() {
    return "login";
  }
  
  @GetMapping("/home")
  public String home() {
    return "home";
  }
}
