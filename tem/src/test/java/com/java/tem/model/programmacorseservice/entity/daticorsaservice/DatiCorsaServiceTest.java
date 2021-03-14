package com.java.tem.model.programmacorseservice.entity.daticorsaservice;

import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.daticorsaservice.DatiCorsa;
import com.java.tem.model.programmacorseservice.entity.daticorsaservice.DatiCorsaService;
import com.java.tem.model.programmacorseservice.repository.DatiCorsaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)

public class DatiCorsaServiceTest {

  @InjectMocks
  private DatiCorsaService datiCorsaService;
  @MockBean
  private DatiCorsaRepository datiCorsaRepository;

  Utente azienda;
  DatiCorsa datiCorsa;
  @BeforeEach
  void setUp() {
    azienda = new Utente();
    datiCorsa = new DatiCorsa();
    datiCorsa.setId(1L);
  }

  @Test
  void addDatiCorsaTest() {
    datiCorsaService.addDatiCorsa(datiCorsa);
    verify(datiCorsaRepository, times(1)).save(datiCorsa);
  }


  @Test
  void getDatiCorsaTest() {
    List<DatiCorsa> datiCorse = new ArrayList<DatiCorsa>();
    when(datiCorsaRepository.findAll()).thenReturn(datiCorse);
    assertEquals(datiCorse, datiCorsaService.getDatiCorsa());
  }

  @Test
  void getDatiCorsaByAziendaTest() {
    List<DatiCorsa> datiCorse = new ArrayList<DatiCorsa>();
    when(datiCorsaRepository.findByAzienda(azienda)).thenReturn(datiCorse);
    assertEquals(datiCorse, datiCorsaService.getDatiCorsaByAzienda(azienda));
  }

  @Test
  void getDatiCorsaTest2() {
    when(datiCorsaService.getDatiCorsa(1L)).thenReturn(Optional.of(datiCorsa));
    assertEquals(datiCorsa, datiCorsaRepository.findById(1L).get());
  }

  @Test
  void updateDatiCorsaTest() {
    datiCorsaService.updateDatiCorsa(datiCorsa);
    verify(datiCorsaRepository, times(1)).save(datiCorsa);
  }

  @Test
  void checkOwnershipTest() {
    datiCorsa.setAzienda(azienda);
    assertTrue(datiCorsaService.checkOwnership(datiCorsa, azienda));
  }

  @Test
  void deleteDatiCorsaTest() {
    datiCorsaService.deleteDatiCorsa(datiCorsa);
    verify(datiCorsaRepository, times(1)).delete(datiCorsa);
  }

}
