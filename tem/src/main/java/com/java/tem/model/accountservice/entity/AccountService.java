package com.java.tem.model.accountservice.entity;

import com.java.tem.model.accountservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AccountService implements UserDetailsService {

  @Autowired
    private UserRepository userRepo;

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

}


