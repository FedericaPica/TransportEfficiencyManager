package com.java.tem.model.programmacorseservice.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.repository.CorsaRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class CorsaServiceTest {

  Utente azienda;
  Corsa corsa;
  ProgrammaCorse programma;
  @InjectMocks
  private CorsaService corsaService;
  @MockBean
  private CorsaRepository corsaRepository;

  @BeforeEach
  void setUp() {
    azienda = new Utente();
    corsa = new Corsa();
    programma = new ProgrammaCorse();
    corsa.setId(1L);
    corsa.setProgramma(programma);
  }

  @Test
  void addCorsaTest() {
    corsaService.addCorsa(corsa);
    verify(corsaRepository, times(1)).save(corsa);
  }

  @Test
  void getCorsaByIdTest() {
    when(corsaService.getCorsaById(1L)).thenReturn(Optional.of(corsa));
    assertEquals(corsa, corsaRepository.findById(1L).get());
  }


  @Test
  void deleteCorsaByIdTest() {
    corsaService.deleteCorsaById(1L);
    verify(corsaRepository, times(1)).deleteById(1L);

  }

  @Test
  void deleteCorsaTest() {
    corsaService.deleteCorsa(corsa);
    verify(corsaRepository, times(1)).delete(corsa);

  }

  @Test
  void getCorseByProgrammaTest() {
    corsaService.getCorseByProgramma(programma);
    verify(corsaRepository, times(1)).findCorseByProgramma(programma);
  }

}