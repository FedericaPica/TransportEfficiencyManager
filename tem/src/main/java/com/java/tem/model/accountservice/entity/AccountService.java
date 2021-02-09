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
<<<<<<< HEAD
   
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Utente user = userRepo.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    return new CustomUserDetails(user, user.getProfilo(), user.getDettaglio());
  }

}
=======
	     
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
<<<<<<< Updated upstream
}
=======
}
>>>>>>> 0dad016b421dc5391e84efe5ace2daa7182f6aa9
>>>>>>> Stashed changes
