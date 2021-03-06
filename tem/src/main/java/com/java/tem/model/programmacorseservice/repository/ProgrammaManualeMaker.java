package com.java.tem.model.programmacorseservice.repository;

import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.Corsa;
import com.java.tem.model.programmacorseservice.entity.CorsaService;
import com.java.tem.model.programmacorseservice.entity.ProgrammaCorse;
import com.java.tem.model.programmacorseservice.entity.ProgrammaCorseService;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Conducente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Linea;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Mezzo;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/** ProgrammaManualeMaker strategy.
 * 
 */

@Component
public class ProgrammaManualeMaker implements Strategy {
  @Autowired
  private AccountService accountService;

  @Autowired
  private ProgrammaCorseService programmaCorseService;

  @Autowired
  private CorsaService corsaService;

  @Override
  public ProgrammaCorse doOperation(ProgrammaCorse programmaCorse) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUserName = authentication.getName();
    Utente utente = accountService.getUserByUsername(currentUserName);

    programmaCorse.setAzienda(utente);

    return programmaCorseService.addProgrammaCorse(programmaCorse);
  }

  /** It simply creates a Corsa.
  *
  * @param corsa Corsa that's going to be created.
  * @param linea Linea to relate with Corsa.
  * @param mezzi assigned Mezzo
  * @param conducenti assigned Conducente
  * @param programmaCorse linked ProgrammaCorse
  */
  public void creaCorsa(Corsa corsa, Linea linea, Set<Mezzo> mezzi, Set<Conducente> conducenti,
                        ProgrammaCorse programmaCorse) {

    corsa.setConducenti(conducenti);
    corsa.setMezzi(mezzi);
    corsa.setLinea(linea);
    corsa.setProgramma(programmaCorse);

    corsaService.addCorsa(corsa);
  }

  @Override
  public StrategyType getStrategyType() {
    return StrategyType.Manuale;
  }

}
