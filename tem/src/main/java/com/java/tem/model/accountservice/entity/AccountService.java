package com.java.tem.model.accountservice.entity;

import com.java.tem.model.accountservice.repository.DettaglioUtenteRepository;
import com.java.tem.model.accountservice.repository.ProfiloRepository;
import com.java.tem.model.accountservice.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AccountService implements UserDetailsService {

  @Autowired
    private UserRepository userRepo;
  @Autowired
    private ProfiloRepository profiloRepository;
  @Autowired
    private DettaglioUtenteRepository dettaglioUtenteRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Utente user = userRepo.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    return new CustomUserDetails(user, user.getProfilo(), user.getDettaglio());
  }


  public Utente getUserByUsername(String usernameString) throws UsernameNotFoundException {
    Utente user = userRepo.findByEmail(usernameString);
    return user;
  }

  public boolean isAdmin(Utente utente) {
    return utente.getProfilo().equals("Admin");
  }

  public boolean isAdmin() {
    Utente utente = this.getLoggedUser();
    return utente.getProfilo().getNomeProfilo().equals("Admin");
  }

  public void registerUser(Utente utente, DettaglioUtente dettaglioUtente) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    Profilo profilo = profiloRepository.findByRuolo("azienda");
    String encodedPassword = passwordEncoder.encode(utente.getPassword());
    utente.setPassword(encodedPassword);
    utente.setProfilo(profilo);
    DettaglioUtente savedDettaglioUtente = dettaglioUtenteRepository.save(dettaglioUtente);
    utente.setDettaglio(savedDettaglioUtente);
    userRepo.save(utente);
  }

  public static Boolean isAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      return true;
    } else {
      return false;
    }
  }

  public Utente getLoggedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUserName = authentication.getName();
    Utente utente = this.getUserByUsername(currentUserName);
    return utente;
  }

  public List<Utente> getAllUsers() {
    return userRepo.findAllByDettaglioIsNotNull();
  }

  public Boolean checkUserExistanceByEmail(String email) {
    return userRepo.checkUserExistanceByEmail(email);
  }

  public Utente getUserById(Long id) {
    if (userRepo.findById(id).isPresent())
      return userRepo.findById(id).get();
    else return null;
  }


}


