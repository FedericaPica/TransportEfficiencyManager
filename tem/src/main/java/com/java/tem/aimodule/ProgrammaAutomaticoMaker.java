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
import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProgrammaAutomaticoMaker implements Strategy {

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


    listaDatiGenerazione = datiGenerazioneRepository
        .findDatiGenerazioneByAziendaId(utente.getId());

    ArrayList<ArrayList<Object>> illegalValuesConducenti = new ArrayList<ArrayList<Object>>();
    ArrayList<ArrayList<Object>> illegalValuesMezzi = new ArrayList<ArrayList<Object>>();

    List<Conducente> conducenti = risorseService.getConducentiByAzienda(utente);

    List<Mezzo> mezzi = risorseService.getMezziByAzienda(utente);
    Collections.sort(mezzi, new Comparator<Mezzo>() {
      @Override
      public int compare(Mezzo o1, Mezzo o2) {
        return o1.getCapienza() - o2.getCapienza();
      }
    });


    try {
      for (DatiGenerazione d : listaDatiGenerazione) {
        List<LocalTime> orari = new ArrayList<LocalTime>();
        orari.add(d.getOrario());
        orari.add(d.getOrario().plusMinutes(30));

        for (LocalTime t : orari) {
          if (checkOrario(d, t)) {
            d.setOrario(t);
          }
        }
      }

      Collections.sort(listaDatiGenerazione, new Comparator<DatiGenerazione>() {
        @Override
        public int compare(DatiGenerazione o1, DatiGenerazione o2) {
          return o1.getOrario().compareTo(o2.getOrario());
        }
      });

      if (ricercaBacktrackingConducente(conducenti, illegalValuesConducenti)
          && ricercaBacktrackingMezzo(mezzi, illegalValuesMezzi)) {

        for (DatiGenerazione d : listaDatiGenerazione) {
          Set<Conducente> conducentiCorsa = new HashSet<Conducente>();
          Set<Mezzo> mezziCorsa = new HashSet<Mezzo>();
          Corsa corsa = new Corsa();
          corsa.setProgramma(programmaCorse);

          Linea lineaB = risorseService.getLineaByName(d.getLineaCorsa()).get();
          Conducente conducenteB =
              risorseService.getConducenteByCodiceFiscale(d.getConducente()).get();
          Mezzo mezzoB = risorseService.getMezzo(Long.parseLong(d.getMezzo())).get();

          conducentiCorsa.add(conducenteB);
          mezziCorsa.add(mezzoB);

          corsa.setConducenti(conducentiCorsa);
          corsa.setMezzi(mezziCorsa);
          corsa.setLinea(lineaB);
          corsa.setAndata(d.isAndata());
          corsa.setOrario(Time.valueOf(d.getOrario()));
          corsaService.addCorsa(corsa);
        }

      }
    } catch (Exception e) {
      // TODO: handle exception
    }
    return programmaCorse;
  }

  private boolean ricercaBacktrackingConducente(List<Conducente> conducenti,
                                                ArrayList<ArrayList<Object>> illegalValuesConducenti)
      throws IOException {
    boolean isEmpty = true;
    int indice = -1;
    for (DatiGenerazione d : listaDatiGenerazione) {
      if (d.getConducente() == null) {
        indice = listaDatiGenerazione.indexOf(d);
        isEmpty = false;
        break;
      }
    }
    if (isEmpty) {
      return true;
    }
    DatiGenerazione posizioneCorrente = listaDatiGenerazione.get(indice);
    for (Conducente c : conducenti) {

      if (checkConducente(c, posizioneCorrente, illegalValuesConducenti)) {
        String cF = c.getCodiceFiscale();
        posizioneCorrente.setConducente(cF);
        listaDatiGenerazione.set(indice, posizioneCorrente);
        if (forwardConducente(c, illegalValuesConducenti, posizioneCorrente, conducenti)) {
          if (ricercaBacktrackingConducente(conducenti, illegalValuesConducenti)) {
            return true;
          } else {
            posizioneCorrente.setConducente(null);
            illegalValuesConducenti.remove(illegalValuesConducenti.size() - 1);
          }
        }
      }
    }
    return false;
  }

  private boolean ricercaBacktrackingMezzo(List<Mezzo> mezzi,
                                           ArrayList<ArrayList<Object>> illegalValuesMezzi)
      throws IOException {
    boolean isEmpty = true;
    int indice = -1;
    for (DatiGenerazione d : listaDatiGenerazione) {
      if (d.getMezzo() == null) {
        indice = listaDatiGenerazione.indexOf(d);
        isEmpty = false;
        break;
      }
    }
    if (isEmpty) {
      return true;
    }
    DatiGenerazione posizioneCorrente = listaDatiGenerazione.get(indice);
    for (Mezzo m : mezzi) {

      if (checkMezzo(m, posizioneCorrente, illegalValuesMezzi)) {
        Long id = m.getId();
        posizioneCorrente.setMezzo(id.toString());
        listaDatiGenerazione.set(indice, posizioneCorrente);
        if (forwardMezzo(m, illegalValuesMezzi, posizioneCorrente, mezzi)) {
          if (ricercaBacktrackingMezzo(mezzi, illegalValuesMezzi)) {
            return true;
          } else {
            posizioneCorrente.setMezzo(null);
            illegalValuesMezzi.remove(illegalValuesMezzi.size() - 1);
          }
        }
      }
    }
    return false;
  }

  private boolean checkOrario(DatiGenerazione datiGenerazione, LocalTime orario) {
    /*
     * Given a tested Orario, if Traffico is false ("No") this method confirms its validity;
     * else this will return
     * false and Orario will be shifted by 30 mins, trying to avoid congestion
     */
    return datiGenerazione.getOrario().equals(orario)
        && datiGenerazione.getTraffico().equals("No")
        ||
        !datiGenerazione.getOrario().equals(orario) && datiGenerazione.getTraffico().equals("Si");
  }

  public boolean checkConducente(Conducente conducente, DatiGenerazione posizioneCorrente,
                                 ArrayList<ArrayList<Object>> illegalValuesConducenti) {
    // Returns true because he/she is the first driver
    if (listaDatiGenerazione.indexOf(posizioneCorrente) == 0) {
      return true;
    }

    int j;
    for (j = illegalValuesConducenti.size() - 1; j >= 0; j--) {
      ArrayList<Object> o = illegalValuesConducenti.get(j);
      Conducente illegalConducente = (Conducente) o.get(0);
      LocalTime illegalStartRange = (LocalTime) o.get(1);
      LocalTime illegalEndRange = (LocalTime) o.get(2);

      if (illegalConducente.getCodiceFiscale().equals(conducente.getCodiceFiscale())) {

        if (posizioneCorrente.getOrario().isAfter(illegalStartRange) &&
            posizioneCorrente.getOrario().isBefore(illegalEndRange)) {
          return false;
        } else {
          break;
        }
      }
    }

    Linea lC = risorseService.getLineaByName(posizioneCorrente.getLineaCorsa()).get();
    Linea lineaCorrente = (Linea) lC.clone();
    if (!posizioneCorrente.isAndata()) {
      lineaCorrente.setPartenza(lineaCorrente.getDestinazione());
    }

    int currentIdx = listaDatiGenerazione.indexOf(posizioneCorrente);

    // Backscrolling listaDatiGenerazione
    for (int i = currentIdx - 1; i >= 0; i--) {
      DatiGenerazione posizionePrecedente = listaDatiGenerazione.get(i);

      if (conducente.getCodiceFiscale().equals(posizionePrecedente.getConducente())) {
        Linea lP = risorseService.getLineaByName(posizionePrecedente.getLineaCorsa()).get();
        Linea lineaPrecedente = (Linea) lP.clone();
        if (!posizionePrecedente.isAndata()) {
          lineaPrecedente.setDestinazione(lineaPrecedente.getPartenza());
        }

        return lineaCorrente.getPartenza().equals(lineaPrecedente.getDestinazione());
      }
    }
    return true;
  }


  private boolean forwardConducente(Conducente conducente,
                                    ArrayList<ArrayList<Object>> illegalValuesConducenti,
                                    DatiGenerazione posizioneCorrente,
                                    List<Conducente> conducenti) {

    Linea lineaCorrente = risorseService.getLineaByName(posizioneCorrente.getLineaCorsa()).get();
    LocalTime inizioRange = posizioneCorrente.getOrario();
    LocalTime fineRange = inizioRange.plusMinutes(lineaCorrente.getDurata());


    ArrayList<Object> item = new ArrayList<Object>();
    item.add(conducente);
    item.add(inizioRange);
    item.add(fineRange);
    illegalValuesConducenti.add(item);

    int i, j;
    for (i = listaDatiGenerazione.indexOf(posizioneCorrente) + 1;
         i <= listaDatiGenerazione.size() - 1; i++) {
      int count = 0;
      DatiGenerazione d = listaDatiGenerazione.get(i);
      if (d.getOrario().isAfter(inizioRange) && d.getOrario().isBefore(fineRange)) {
        for (j = 0; j <= illegalValuesConducenti.size() - 1; j++) {
          ArrayList<Object> riga = illegalValuesConducenti.get(j);
          if (d.getOrario().isAfter((LocalTime) riga.get(1)) &&
              d.getOrario().isBefore((LocalTime) riga.get(2))) {
            count++;
          }
        }
        if (count == conducenti.size()) {
          return false;
        }
      } else {
        break;
      }
    }
    return true;

  }

  public boolean checkMezzo(Mezzo mezzo, DatiGenerazione posizioneCorrente,
                            ArrayList<ArrayList<Object>> illegalValuesMezzi) {

    if (mezzo.getCapienza() < posizioneCorrente.getAttesi()) {
      return false;
    }

// Returns true because he/she is the first driver
    if (listaDatiGenerazione.indexOf(posizioneCorrente) == 0) {
      return true;
    }

    int j;
    for (j = illegalValuesMezzi.size() - 1; j >= 0; j--) {
      ArrayList<Object> o = illegalValuesMezzi.get(j);
      Mezzo illegalMezzo = (Mezzo) o.get(0);
      LocalTime illegalStartRange = (LocalTime) o.get(1);
      LocalTime illegalEndRange = (LocalTime) o.get(2);

      if (illegalMezzo.getId().equals(mezzo.getId())) {

        if (posizioneCorrente.getOrario().isAfter(illegalStartRange) &&
            posizioneCorrente.getOrario().isBefore(illegalEndRange)) {
          return false;
        } else {
          break;
        }
      }
    }

    Linea lC = risorseService.getLineaByName(posizioneCorrente.getLineaCorsa()).get();
    Linea lineaCorrente = (Linea) lC.clone();
    if (!posizioneCorrente.isAndata()) {
      lineaCorrente.setPartenza(lineaCorrente.getDestinazione());
    }

    int currentIdx = listaDatiGenerazione.indexOf(posizioneCorrente);

// Backscrolling listaDatiGenerazione
    for (int i = currentIdx - 1; i >= 0; i--) {
      DatiGenerazione posizionePrecedente = listaDatiGenerazione.get(i);

      if (mezzo.getId().toString().equals(posizionePrecedente.getMezzo())) {
        Linea lP = risorseService.getLineaByName(posizionePrecedente.getLineaCorsa()).get();
        Linea lineaPrecedente = (Linea) lP.clone();
        if (!posizionePrecedente.isAndata()) {
          lineaPrecedente.setDestinazione(lineaPrecedente.getPartenza());
        }

        return lineaCorrente.getPartenza().equals(lineaPrecedente.getDestinazione());
      }
    }
    return true;
  }


  private boolean forwardMezzo(Mezzo mezzo, ArrayList<ArrayList<Object>> illegalValuesMezzi,
                               DatiGenerazione posizioneCorrente, List<Mezzo> mezzi) {

    Linea lineaCorrente = risorseService.getLineaByName(posizioneCorrente.getLineaCorsa()).get();
    LocalTime inizioRange = posizioneCorrente.getOrario();
    LocalTime fineRange = inizioRange.plusMinutes(lineaCorrente.getDurata());


    ArrayList<Object> item = new ArrayList<Object>();
    item.add(mezzo);
    item.add(inizioRange);
    item.add(fineRange);
    illegalValuesMezzi.add(item);

    int i, j;
    for (i = listaDatiGenerazione.indexOf(posizioneCorrente) + 1;
         i <= listaDatiGenerazione.size() - 1; i++) {
      int count = 0;
      DatiGenerazione d = listaDatiGenerazione.get(i);
      if (d.getOrario().isAfter(inizioRange) && d.getOrario().isBefore(fineRange)) {
        for (j = 0; j <= illegalValuesMezzi.size() - 1; j++) {
          ArrayList<Object> riga = illegalValuesMezzi.get(j);
          if (d.getOrario().isAfter((LocalTime) riga.get(1)) &&
              d.getOrario().isBefore((LocalTime) riga.get(2))) {
            count++;
          }
        }
        if (count == mezzi.size()) {
          return false;
        }
      } else {
        break;
      }
    }
    return true;

  }

  @Override
  public StrategyType getStrategyType() {
    return StrategyType.Automatico;
  }
}