package com.java.tem.controller;

import com.java.tem.model.accountservice.entity.DettaglioUtente;
import com.java.tem.model.accountservice.entity.Profilo;
import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.accountservice.repository.DettaglioUtenteRepository;
import com.java.tem.model.accountservice.repository.ProfiloRepository;
import com.java.tem.model.accountservice.repository.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AccountController {
 
  @Autowired
    private UserRepository userRepo;

  @Autowired
    private DettaglioUtenteRepository dettaglioUtenteRepo;

  @Autowired
    private ProfiloRepository profiloRepository;

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
    public String processRegister(Utente user, DettaglioUtente dettaglioUtente) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
    Profilo profilo = profiloRepository.findByRuolo("azienda");
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    user.setProfilo(profilo);
    DettaglioUtente savedDettaglioUtente = dettaglioUtenteRepo.save(dettaglioUtente);
    user.setDettaglio(savedDettaglioUtente);
    userRepo.save(user);
        
    return "register_success";
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
}
