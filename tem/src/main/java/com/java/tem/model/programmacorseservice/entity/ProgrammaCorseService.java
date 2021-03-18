package com.java.tem.model.programmacorseservice.entity;

import com.java.tem.exceptions.GenerationFailedException;
import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.repository.ProgrammaCorseRepository;
import com.java.tem.model.programmacorseservice.repository.Strategy;
import com.java.tem.model.programmacorseservice.repository.StrategyType;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProgrammaCorseService {

  @Autowired
  private ProgrammaCorseFactory programmaCorseFactory;

  @Autowired
  private ProgrammaCorseRepository programmaCorseRepository;

  public ProgrammaCorseService() {
  }

  public ProgrammaCorse generaProgrammaCorse(String genType, ProgrammaCorse programmaCorse)
          throws GenerationFailedException {
    Strategy strategy;

    switch (genType) {
      case "manuale":
        strategy = programmaCorseFactory.findStrategy(StrategyType.Manuale);
        ProgrammaCorse returnProgrammaCorse = strategy.doOperation(programmaCorse);
        break;
      case "automatico":
        strategy = programmaCorseFactory.findStrategy(StrategyType.Automatico);
        strategy.doOperation(programmaCorse);
        break;

      default:
        strategy = programmaCorseFactory.findStrategy(StrategyType.Manuale);
        strategy.doOperation(programmaCorse);
    }

    return programmaCorse;
  }


  public void addProgrammaCorse(ProgrammaCorse programmaCorse) {
    programmaCorseRepository.save(programmaCorse);
  }

  public Optional<ProgrammaCorse> getProgrammaCorseById(Long id) {
    return programmaCorseRepository.findById(id);
  }

  public void updateProgrammaCorse(ProgrammaCorse programmaCorse) {
    programmaCorseRepository.save(programmaCorse);
  }

  public void deleteProgrammaCorseById(Long id) {
    programmaCorseRepository.deleteById(id);
  }

  public void deleteProgrammaCorse(ProgrammaCorse programmaCorse) {
    programmaCorseRepository.delete(programmaCorse);
  }

  public List<ProgrammaCorse> getProgrammaCorseByAzienda(Utente utente) {
    return programmaCorseRepository.findByAzienda(utente);
  }

  public boolean checkOwnership(ProgrammaCorse programmaCorse, Utente utente) {
    return programmaCorse.getAzienda().equals(utente);
  }


}
