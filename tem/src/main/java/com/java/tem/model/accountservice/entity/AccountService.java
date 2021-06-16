package com.java.tem.model.accountservice.entity;

import com.java.tem.model.accountservice.repository.DettaglioUtenteRepository;
import com.java.tem.model.accountservice.repository.ProfiloRepository;
import com.java.tem.model.accountservice.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/** AccountService.
 */
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
    return utente.getProfilo().getNomeProfilo().equals("Admin");
  }

  /** Check if logged Utente is an Administrator.
   *
   * @return boolean.
   */
  public boolean isAdmin() {
    Utente utente = getLoggedUser();
    return utente.getProfilo().getNomeProfilo().equals("Admin");
  }

  /** It registers an user into the system and crypts his password.
   *
   * @param utente Utente to register.
   * @param dettaglioUtente DettaglioUtente related to Utente.
   * @return Utente.
   */
  public Utente registerUser(Utente utente, DettaglioUtente dettaglioUtente) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    Profilo profilo = profiloRepository.findByRuolo("azienda");
    String encodedPassword = passwordEncoder.encode(utente.getPassword());
    utente.setPassword(encodedPassword);
    utente.setProfilo(profilo);
    DettaglioUtente savedDettaglioUtente = dettaglioUtenteRepository.save(dettaglioUtente);
    utente.setDettaglio(savedDettaglioUtente);
    return userRepo.save(utente);
  }

  /** Return the logged Utente instance.
   *
   * @return instance of logged Utente.
   */
  public Utente getLoggedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUserName = authentication.getName();
    Utente utente = getUserByUsername(currentUserName);
    return utente;
  }

  public List<Utente> getAllUsers() {
    return userRepo.findAllByDettaglioIsNotNull();
  }

  public Boolean checkUserExistanceByEmail(String email) {
    return userRepo.checkUserExistanceByEmail(email);
  }

  /** Returns instance of Utente by given Id.
   *
   * @param id Long
   * @return Utente
   */
  public Utente getUserById(Long id) {
    if (userRepo.findById(id).isPresent()) {
      return userRepo.findById(id).get();
    } else {
      return null;
    }
  }
}


