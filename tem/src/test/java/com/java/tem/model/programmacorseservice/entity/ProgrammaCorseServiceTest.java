package com.java.tem.model.programmacorseservice.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.java.tem.exceptions.GenerationFailedException;
import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.repository.ProgrammaCorseRepository;
import com.java.tem.model.programmacorseservice.repository.Strategy;
import com.java.tem.model.programmacorseservice.repository.StrategyType;
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

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ProgrammaCorseServiceTest {

  @InjectMocks
  private ProgrammaCorseService programmaCorseService;

  @MockBean
  private ProgrammaCorseRepository programmaCorseRepository;

  @MockBean
  private ProgrammaCorseFactory programmaCorseFactory;

  @MockBean
  private Strategy strategy;

  private ProgrammaCorse programmaCorse;

  @BeforeEach
  void setUp() {
    programmaCorse = new ProgrammaCorse();
    programmaCorse.setId(1L);
    strategy = mock(Strategy.class);
  }

  @Test
  void generaProgrammaCorseManuale() throws GenerationFailedException {
    String generationType = "manuale";
    when(programmaCorseFactory.findStrategy(StrategyType.Manuale)).thenReturn(strategy);
    assertEquals(programmaCorse,
        programmaCorseService.generaProgrammaCorse(generationType, programmaCorse));
  }

  @Test
  void generaProgrammaCorseAutomatico() throws GenerationFailedException {
    String generationType = "automatico";
    when(programmaCorseFactory.findStrategy(StrategyType.Automatico)).thenReturn(strategy);
    when(strategy.doOperation(programmaCorse)).thenReturn(programmaCorse);
    assertEquals(programmaCorse,
        programmaCorseService.generaProgrammaCorse(generationType, programmaCorse));
  }

  @Test
  void generaProgrammaCorseDefault() throws GenerationFailedException {
    String generationType = "";
    when(programmaCorseFactory.findStrategy(StrategyType.Manuale)).thenReturn(strategy);
    when(strategy.doOperation(programmaCorse)).thenReturn(programmaCorse);
    assertEquals(programmaCorse,
        programmaCorseService.generaProgrammaCorse(generationType, programmaCorse));
  }

  @Test
  void addProgrammaCorse() throws GenerationFailedException {
    ProgrammaCorse programmaCorse = new ProgrammaCorse();
    programmaCorseService.addProgrammaCorse(programmaCorse);
    verify(programmaCorseRepository, times(1)).save(programmaCorse);
  }

  @Test
  void getProgrammaCorseById() {
    programmaCorse.setId(1L);
    when(programmaCorseRepository.findById(1L)).thenReturn(Optional.of(programmaCorse));
    assertEquals(programmaCorse, programmaCorseService.getProgrammaCorseById(1L).get(),
        "Passed");
  }

  @Test
  void updateProgrammaCorse() {
    programmaCorseService.updateProgrammaCorse(programmaCorse);
    verify(programmaCorseRepository, times(1))
        .save(programmaCorse);
  }

  @Test
  void deleteProgrammaCorseById() {
    programmaCorse.setId(1L);
    programmaCorseService.deleteProgrammaCorseById(1L);
    verify(programmaCorseRepository, times(1)).deleteById(1L);
  }

  @Test
  void deleteProgrammaCorse() {
    programmaCorseService.deleteProgrammaCorse(programmaCorse);
    verify(programmaCorseRepository, times(1)).delete(programmaCorse);
  }

  @Test
  void getProgrammaCorseByAzienda() {
    Utente utente = new Utente();
    List<ProgrammaCorse> programmaCorseList = new ArrayList<ProgrammaCorse>();
    programmaCorseList.add(programmaCorse);
    when(programmaCorseService.getProgrammaCorseByAzienda(utente)).thenReturn(programmaCorseList);
    assertEquals(programmaCorseList, programmaCorseRepository.findByAzienda(utente));
  }

  @Test
  void checkOwnership() {
    Utente azienda = new Utente();
    programmaCorse.setAzienda(azienda);
    assertTrue(programmaCorseService.checkOwnership(programmaCorse, azienda));
  }
}