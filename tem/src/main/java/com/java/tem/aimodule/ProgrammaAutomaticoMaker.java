package com.java.tem.aimodule;

import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.Corsa;
import com.java.tem.model.programmacorseservice.entity.CorsaService;
import com.java.tem.model.programmacorseservice.entity.ProgrammaCorse;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Conducente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Linea;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Mezzo;
import com.java.tem.model.programmacorseservice.entity.risorseservice.RisorseService;
import com.java.tem.model.programmacorseservice.repository.ProgrammaCorseRepository;
import com.java.tem.model.programmacorseservice.repository.Strategy;
import com.java.tem.model.programmacorseservice.repository.StrategyType;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProgrammaAutomaticoMaker implements Strategy {

  private final List<Mezzo> legalListMezzo = new ArrayList<Mezzo>();
  private final List<Conducente> legalListConducente = new ArrayList<Conducente>();
  @Autowired
  private AccountService accountService;
  @Autowired
  private DatiGenerazioneRepository datiGenerazioneRepository;
  @Autowired
  private RisorseService risorseService;
  @Autowired
  private ProgrammaCorseRepository programmaCorseRepository;
  @Autowired
  private CorsaService corsaService;
  private List<DatiGenerazione> listaDatiGenerazione = new ArrayList<DatiGenerazione>();

  @Override
  public ProgrammaCorse doOperation(ProgrammaCorse programmaCorse) {
    Utente utente = accountService.getLoggedUser();
    programmaCorse.setAzienda(utente);
    programmaCorseRepository.save(programmaCorse);

    this.listaDatiGenerazione = datiGenerazioneRepository
        .findDatiGenerazioneByAziendaId(utente.getId());

    List<Conducente> conducenti = risorseService.getConducentiByAzienda(utente);
    List<Mezzo> mezzi = risorseService.getMezziByAzienda(utente);
    Collections.sort(mezzi, new Comparator<Mezzo>() {
      @Override
      public int compare(Mezzo o1, Mezzo o2) {
        return o1.getCapienza() - o2.getCapienza();
      }
    });

    Collections.sort(this.listaDatiGenerazione, new Comparator<DatiGenerazione>() {
      @Override
      public int compare(DatiGenerazione o1, DatiGenerazione o2) {
        return o1.getOrario().compareTo(o2.getOrario());
      }
    });

    this.ricercaBacktracking(mezzi, conducenti, 0);


    for (DatiGenerazione d : this.listaDatiGenerazione) {
      Set<Conducente> conducentiCorsa = new HashSet<Conducente>();
      Set<Mezzo> mezziCorsa = new HashSet<Mezzo>();
      Corsa corsa = new Corsa();
      corsa.setProgramma(programmaCorse);

      Linea lineaB = risorseService.getLineaByName(d.getLineaCorsa()).get();
      Conducente conducenteB = risorseService.getConducenteByCodiceFiscale(d.getConducente()).get();
      Mezzo mezzoB = risorseService.getMezzo(Long.parseLong(d.getMezzo())).get();

      conducentiCorsa.add(conducenteB);
      mezziCorsa.add(mezzoB);

      corsa.setConducenti(conducentiCorsa);
      corsa.setMezzi(mezziCorsa);
      corsa.setLinea(lineaB);
      corsa.setAndata(Boolean.valueOf(d.getAndata()));
      corsa.setOrario(Time.valueOf(d.getOrario()));
      corsaService.addCorsa(corsa);

    }
    return programmaCorse;
  }

  private void ricercaBacktracking(List<Mezzo> mezzi, List<Conducente> conducenti, int count) {
    DatiGenerazione d = this.listaDatiGenerazione.get(count);
    List<LocalTime> orari = new ArrayList<LocalTime>();
    orari.add(d.getOrario());
    orari.add(d.getOrario().plusMinutes(30));

    for (LocalTime t : orari) {
      if (checkOrario(d, t)) {
        d.setOrario(t);
      }
    }

    if (modifiedAC3(mezzi, conducenti, d.getOrario(), d)) {
      for (Conducente c : this.legalListConducente) {
        if (checkConducente(d, c)) {
          d.setConducente(c.getCodiceFiscale());
          break;
        }
      }

      for (Mezzo m : this.legalListMezzo) {
        if (checkMezzo(d, m)) {
          d.setMezzo(m.getId().toString());
          break;
        }
      }
    }

    if (count < this.listaDatiGenerazione.size() - 1) {
      count++;
      ricercaBacktracking(mezzi, conducenti, count);
    }
  }

  private boolean checkOrario(DatiGenerazione datiGenerazione, LocalTime orario) {
    /*
     * Given a tested Orario, if Traffico is false ("No") this method confirms its validity; 
     * else this will return
     * false and Orario will be shifted by 30 mins, trying to avoid congestion
     */
    return (datiGenerazione.getOrario().equals(orario) 
      && datiGenerazione.getTraffico().equals("No")) 
      ||  datiGenerazione.getOrario().equals(orario) && datiGenerazione.getTraffico().equals("Si");
  }

  private boolean checkConducente(DatiGenerazione datiGenerazione,
                                  Conducente conducente) {
    /*
     * Checks if a given Conducente has already driven during the working day. 
     * If true, it tries to assign him routes
     * starting from his last destination. 
     * If false, it simply assigns the resource to the given route.
     */

    int start = this.listaDatiGenerazione.indexOf(datiGenerazione);
    Linea lineaCorrente = risorseService.getLineaByName(datiGenerazione.getLineaCorsa()).get();
    if (!Boolean.getBoolean(datiGenerazione.isAndata())) {
      lineaCorrente.setPartenza(lineaCorrente.getDestinazione());
    }

    if (start == 0) {
      return true;
    }

    for (int i = start - 1; i-- > 0; ) {
      DatiGenerazione d = listaDatiGenerazione.get(i);


      if (conducente.getCodiceFiscale().equals(d.getConducente())) {
        // Checks if last object's destination is equal to current object destination

        Linea lineaPrecedente = risorseService.getLineaByName(d.getLineaCorsa()).get();

        if (!Boolean.getBoolean(datiGenerazione.isAndata())) {
          lineaPrecedente.setDestinazione(lineaPrecedente.getPartenza());
        }

        return lineaCorrente.getPartenza().equals(lineaPrecedente.getDestinazione());

      }
    }
    return true;
  }

  private boolean checkMezzo(DatiGenerazione datiGenerazione,
                             Mezzo mezzo) {

    if (mezzo.getCapienza() < datiGenerazione.getAttesi()) {
      return false;
    }


    Linea lineaCorrente = risorseService.getLineaByName(datiGenerazione.getLineaCorsa()).get();

    if (!Boolean.getBoolean(datiGenerazione.isAndata())) {
      lineaCorrente.setPartenza(lineaCorrente.getDestinazione());
    }

    int start = this.listaDatiGenerazione.indexOf(datiGenerazione);


    if (start == 0) {
      return true;
    }

    for (int i = start - 1; i-- > 0; ) {
      DatiGenerazione d = this.listaDatiGenerazione.get(i);

      if (mezzo.getId().equals(d.getId())) {

        Linea lineaPrecedente = risorseService.getLineaByName(d.getLineaCorsa()).get();

        if (!Boolean.getBoolean(d.isAndata())) {
          lineaPrecedente.setDestinazione(lineaPrecedente.getPartenza());
        }
        return lineaCorrente.getPartenza().equals(lineaPrecedente.getDestinazione());
      }
    }
    return true;

  }

  private boolean modifiedAC3(List<Mezzo> mezzi, List<Conducente> conducenti, LocalTime orario,
                              DatiGenerazione datiGenerazione) {
    // Clears the Legal Lists
    if (!this.legalListMezzo.isEmpty()) {
      this.legalListMezzo.clear();
    }

    if (!this.legalListConducente.isEmpty()) {
      this.legalListConducente.clear();
    }

    this.legalListConducente.addAll(conducenti);
    this.legalListMezzo.addAll(mezzi);

    if (removeIllegalValues(conducenti, orario, datiGenerazione)) {
      return this.legalListConducente.size() != 0;
    }

    if (removeIllegalValues(mezzi, orario, datiGenerazione)) {
      return this.legalListMezzo.size() != 0;
    }

    return true;
  }

  private boolean removeIllegalValues(Object initial, LocalTime orarioCorrente,
                                      DatiGenerazione datiGenerazione) {
    List<Object> init = (List<Object>) initial;
    boolean removed = false;

    for (Object o : init) {

      int start = this.listaDatiGenerazione.indexOf(datiGenerazione);
      for (int i = start - 1; i-- > 0; ) {
        DatiGenerazione d = this.listaDatiGenerazione.get(i);

        if (o instanceof Conducente) {
          Conducente conducente = (Conducente) o;
          //List<Conducente> legalList = new ArrayList<Conducente>();

          if (conducente.getCodiceFiscale().equals(d.getConducente())) {

            Linea lineaPrecedente = risorseService.getLineaByName(d.getLineaCorsa()).get();
            LocalTime orarioPrecedente = d.getOrario();

            if (orarioCorrente
                .isBefore(orarioPrecedente.plusMinutes(lineaPrecedente.getDurata()))) {
              this.legalListConducente.remove(d);
              removed = true;
            }
          }
        } else if (o instanceof Mezzo) {
          Mezzo mezzo = (Mezzo) o;

          if (mezzo.getId().equals(d.getId())) {
            Linea lineaPrecedente = risorseService.getLineaByName(d.getLineaCorsa()).get();
            LocalTime orarioPrecedente = d.getOrario();

            if (orarioCorrente
                .isBefore(orarioPrecedente.plusMinutes(lineaPrecedente.getDurata()))) {
              this.legalListMezzo.remove(d);
              removed = true;
            }
          }
        }
      }
    }
    return removed;
  }

  @Override
  public StrategyType getStrategyType() {
    return StrategyType.Automatico;
  }
}