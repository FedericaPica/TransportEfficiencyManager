package com.java.tem.model.programmacorseservice.entity.risorseservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.Corsa;
import com.java.tem.model.programmacorseservice.repository.ConducenteRepository;
import com.java.tem.model.programmacorseservice.repository.LineaRepository;
import com.java.tem.model.programmacorseservice.repository.MezzoRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class RisorseServiceTest {

  @InjectMocks
  private final Mezzo mezzo = new Mezzo();
  @InjectMocks
  private final Linea linea = new Linea();
  @InjectMocks
  private final Conducente conducente = new Conducente();
  @InjectMocks
  private final Utente utente = new Utente();
  @Mock
  private MezzoRepository mezzoRepository;
  @Mock
  private ConducenteRepository conducenteRepository;
  @Mock
  private LineaRepository lineaRepository;
  @InjectMocks
  private RisorseService risorseService;

  //checkOwnershipTESTS
  @Test
  void checkOwnershipIfMezzo() {
    mezzo.setAzienda(utente);
    assertTrue(risorseService.checkOwnership(mezzo, utente));
  }

  @Test
  void checkOwnershipIfConducente() {
    conducente.setAzienda(utente);
    assertTrue(risorseService.checkOwnership(conducente, utente));
  }

  @Test
  void checkOwnershipIfLinea() {
    linea.setAzienda(utente);
    assertTrue(risorseService.checkOwnership(linea, utente));
  }

  @Test
  void checkOwnershipIfNotResource() {
    assertFalse(risorseService.checkOwnership(null, utente));
  }

  //isBoundTESTS

  @Test
  void isBoundIfMezzo() {
    Mezzo mezzo = new Mezzo();
    Corsa corsa = new Corsa();
    Set<Corsa> listaCorse = new HashSet<Corsa>();
    listaCorse.add(corsa);
    mezzo.setCorse(listaCorse);

    assertTrue(risorseService.isBound(mezzo));
  }

  @Test
  void isNotBoundIfMezzo() {
    Mezzo mezzo = new Mezzo();
    Set<Corsa> listaCorse = new HashSet<Corsa>();
    mezzo.setCorse(listaCorse);
    assertFalse(risorseService.isBound(mezzo));
  }

  @Test
  void isBoundIfConducente() {
    Conducente conducente = new Conducente();
    Corsa corsa = new Corsa();
    Set<Corsa> listaCorse = new HashSet<Corsa>();
    listaCorse.add(corsa);
    conducente.setCorse(listaCorse);
    assertTrue(risorseService.isBound(conducente));
  }

  @Test
  void isNotBoundIfConducente() {
    Conducente conducente = new Conducente();
    Set<Corsa> listaCorse = new HashSet<Corsa>();
    conducente.setCorse(listaCorse);
    assertFalse(risorseService.isBound(conducente));
  }

  @Test
  void isBoundIfLinea() {
    Linea linea = mock(Linea.class, RETURNS_DEEP_STUBS);
    Corsa corsa = new Corsa();
    Set<Corsa> listaCorse = new HashSet<Corsa>();
    listaCorse.add(corsa);
    linea.setCorse(listaCorse);
    assertTrue(risorseService.isBound(linea));

  }

  @Test
  void isNotBoundIfLinea() {
    Linea linea = mock(Linea.class, RETURNS_DEEP_STUBS);
    Set<Corsa> listaCorse = new HashSet<Corsa>();
    linea.setCorse(listaCorse);
    when(linea.getCorse().isEmpty()).thenReturn(true);
    assertFalse(risorseService.isBound(linea));
  }

  //deleteTESTS

  @Test
  void deleteMezzo() {
    risorseService.deleteMezzo(mezzo);
    verify(mezzoRepository, times(1)).delete(mezzo);
  }

  @Test
  void deleteConducente() {
    risorseService.deleteConducente(conducente);
    verify(conducenteRepository, times(1)).delete(conducente);
  }

  @Test
  void deleteLinea() {
    risorseService.deleteLinea(linea);
    verify(lineaRepository, times(1)).delete(linea);
  }

  //updateTESTS

  @Test
  void updateMezzo() {
    risorseService.updateMezzo(mezzo);
    verify(mezzoRepository, times(1)).save(mezzo);
  }

  @Test
  void updateConducente() {
    risorseService.updateConducente(conducente);
    verify(conducenteRepository, times(1)).save(conducente);
  }

  @Test
  void updateLinea() {
    risorseService.updateLinea(linea);
    verify(lineaRepository, times(1)).save(linea);
  }

  //getTESTS

  @Test
  void getMezziByAzienda() {
    Utente azienda = new Utente();
    List<Mezzo> mezzi = new ArrayList<Mezzo>();
    when(mezzoRepository.findByAzienda(azienda)).thenReturn(mezzi);
    assertEquals(mezzi, risorseService.getMezziByAzienda(azienda));
  }

  @Test
  void getMezzi() {
    List<Mezzo> mezzi = new ArrayList<Mezzo>();
    when(mezzoRepository.findAll()).thenReturn(mezzi);
    assertEquals(mezzi, risorseService.getMezzi());
  }

  @Test
  void getmezzo() {
    when(risorseService.getMezzo(1L)).thenReturn(Optional.of(mezzo));
    assertEquals(mezzo, mezzoRepository.findById(1L).get());
  }

  @Test
  void getLinea() {
    when(risorseService.getLinea(1L)).thenReturn(Optional.of(linea));
    assertEquals(linea, lineaRepository.findById(1L).get());
  }

  @Test
  void getLineaByName() {
    risorseService.getLineaByName("linea");
    verify(lineaRepository, times(1)).findByNome("linea");
  }

  @Test
  void getLineeByAzienda() {
    Utente azienda = new Utente();
    List<Linea> linee = new ArrayList<Linea>();
    when(lineaRepository.findByAzienda(azienda)).thenReturn(linee);
    assertEquals(linee, risorseService.getLineeByAzienda(azienda));
  }

  @Test
  void getLinee() {
    List<Linea> linee = new ArrayList<Linea>();
    when(lineaRepository.findAll()).thenReturn(linee);
    assertEquals(linee, risorseService.getLinee());
  }

  @Test
  void getConducenti() {
    List<Conducente> conducenti = new ArrayList<Conducente>();
    when(conducenteRepository.findAll()).thenReturn(conducenti);
    assertEquals(conducenti, risorseService.getConducenti());
  }

  @Test
  void getConducentiByAzienda() {
    Utente azienda = new Utente();
    List<Conducente> conducenti = new ArrayList<Conducente>();
    when(conducenteRepository.findByAzienda(azienda)).thenReturn(conducenti);
    assertEquals(conducenti, risorseService.getConducentiByAzienda(azienda));
  }

  @Test
  void getConducente() {
    when(risorseService.getConducente(1L)).thenReturn(Optional.of(conducente));
    assertEquals(conducente, conducenteRepository.findById(1L).get());
  }

  @Test
  void getConducenteByCodiceFiscale() {
    risorseService.getConducenteByCodiceFiscale("ASDFRE1234KCNR56");
    verify(conducenteRepository, times(1)).findConducenteByCodiceFiscale("ASDFRE1234KCNR56");
  }

  //addTESTS

  @Test
  void addMezzo() {
    risorseService.addMezzo(mezzo);
    verify(mezzoRepository, times(1)).save(mezzo);
  }

  @Test
  void addConducente() {
    risorseService.addConducente(conducente);
    verify(conducenteRepository, times(1)).save(conducente);
  }

  @Test
  void addLinea() {
    risorseService.addLinea(linea);
    verify(lineaRepository, times(1)).save(linea);
  }
}