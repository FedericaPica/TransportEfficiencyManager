package com.java.tem.model.accountservice.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.java.tem.model.accountservice.repository.DettaglioUtenteRepository;
import com.java.tem.model.accountservice.repository.ProfiloRepository;
import com.java.tem.model.accountservice.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AccountServiceTest {

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private ProfiloRepository profiloRepository;

  @MockBean
  private DettaglioUtenteRepository dettaglioUtenteRepository;

  @InjectMocks
  private AccountService accountService;

  @Test
  void loadUserByUsernameIfNotNull() {
    Utente utente = new Utente();
    DettaglioUtente dettaglioUtente = new DettaglioUtente();
    Profilo profilo = new Profilo();

    utente.setDettaglio(dettaglioUtente);
    utente.setEmail("test@tem.com");
    utente.setEmail("test@tem.com");

    utente.setProfilo(profilo);
    CustomUserDetails expected = new CustomUserDetails(utente, profilo, dettaglioUtente);

    when(userRepository.findByEmail("test@tem.com")).thenReturn(utente);
    assertEquals(expected.getUsername(),
        accountService.loadUserByUsername(utente.getEmail()).getUsername());
  }

  @Test
  void loadUserByUsernameIfNull() {
    Utente utente = new Utente();
    DettaglioUtente dettaglioUtente = new DettaglioUtente();
    Profilo profilo = new Profilo();

    utente.setDettaglio(dettaglioUtente);
    utente.setEmail("test@tem.com");

    utente.setProfilo(profilo);

    when(userRepository.findByEmail("test@tem.com")).thenReturn(null);
    assertThrows(UsernameNotFoundException.class,
        () -> accountService.loadUserByUsername(utente.getEmail()));
  }

  @Test
  @WithMockUser
  void isAdmin() {
    Utente utente = new Utente();
    Profilo profilo = new Profilo();
    profilo.setNomeProfilo("Admin");
    utente.setProfilo(profilo);

    when(accountService.getLoggedUser()).thenReturn(utente);
    assertTrue(accountService.isAdmin());
  }

  @Test
  @WithMockUser
  void testIsAdmin() {
    Utente utente = new Utente();
    Profilo profilo = new Profilo();
    profilo.setNomeProfilo("Admin");
    utente.setProfilo(profilo);
    assertTrue(accountService.isAdmin(utente));
  }

  @Test
  void registerUser() {
    Profilo profilo = new Profilo();
    profilo.setId(1L);
    profilo.setNomeProfilo("Azienda");
    DettaglioUtente dettaglioUtente = new DettaglioUtente();
    dettaglioUtente.setId(1L);
    dettaglioUtente.setCap("84025");
    dettaglioUtente.setDenominazione("Test Utente");
    dettaglioUtente.setCitta("Eboli");
    dettaglioUtente.setFax("0854444");
    dettaglioUtente.setTelefono("085444444");
    dettaglioUtente.setIndirizzo("Indirizzo esistente");
    dettaglioUtente.setPartitaIVA("12345678999");
    Utente utente = new Utente();
    utente.setEmail("test@tem.com");
    utente.setPassword("passwordsicurissima");
    utente.setProfilo(profilo);
    utente.setDettaglio(dettaglioUtente);

    when(userRepository.save(utente)).thenReturn(utente);
    assertEquals(utente.getId(), accountService.registerUser(utente, dettaglioUtente).getId());

  }

  @Test
  void getUserById() {
    Utente u = new Utente();
    u.setId(1L);
    when(userRepository.findById(1L)).thenReturn(Optional.of(u));

    assertEquals(u, accountService.getUserById(1L));
  }

  @Test
  void getUserByIdIsNotPresent() {
    Utente u = new Utente();
    Utente u2 = new Utente();

    u.setId(1L);
    u2.setId(2L);

    assertEquals(null, accountService.getUserById(3L));

  }
}
