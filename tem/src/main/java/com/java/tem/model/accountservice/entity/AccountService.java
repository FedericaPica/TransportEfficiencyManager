package com.java.tem.model.accountservice.entity;

import com.java.tem.model.accountservice.repository.DettaglioUtenteRepository;
import com.java.tem.model.accountservice.repository.ProfiloRepository;
import com.java.tem.model.accountservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

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
}


