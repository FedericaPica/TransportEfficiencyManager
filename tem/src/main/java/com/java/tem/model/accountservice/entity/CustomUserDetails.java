package com.java.tem.model.accountservice.entity;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/** DAO Class.
 *
 */
public class CustomUserDetails implements UserDetails {
  private final Utente user;
  private final Profilo profilo;
  private final DettaglioUtente dettaglioUtente;


  /** Given the data, it binds CustomUserDetails to this data.
   *
   * @param user Utente
   * @param profilo Profilo
   * @param dettaglioUtente DettaglioUtente
   */
  public CustomUserDetails(Utente user, Profilo profilo, DettaglioUtente dettaglioUtente) {
    this.user = user;
    this.profilo = profilo;
    this.dettaglioUtente = dettaglioUtente;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public String getFullName() {
    return user.getUsername();
  }

}