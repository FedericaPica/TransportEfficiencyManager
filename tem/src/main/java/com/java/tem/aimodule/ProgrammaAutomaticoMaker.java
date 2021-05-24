package com.java.tem.aimodule;

import com.java.tem.exceptions.GenerationFailedException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalTime;
import java.util.*;

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
  public ProgrammaCorse doOperation(ProgrammaCorse programmaCorse) throws GenerationFailedException {
    Utente utente = accountService.getLoggedUser();
    programmaCorse.setAzienda(utente);
    programmaCorseRepository.save(programmaCorse);


    listaDatiGenerazione = datiGenerazioneRepository
        .findDatiGenerazioneByAziendaId(utente.getId());
    /*for (DatiGenerazione d: listaDatiGenerazione) {
      System.out.println(d.toString());
    }*/
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


      for (DatiGenerazione d : listaDatiGenerazione) {
        List<LocalTime> orari = new ArrayList<LocalTime>();
        orari.add(d.getOrario());
        orari.add(d.getOrario().plusMinutes(30));
        
        /*System.out.println(listaDatiGenerazione.indexOf(d) + " iterazione:");
        System.out.println("Dominio: " + orari.toString());*/
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
      } else {
        throw new GenerationFailedException("Impossibile trovare una generazione ottimale per i dati forniti.");
      }
    return programmaCorse;
  }

  private boolean ricercaBacktrackingConducente(List<Conducente> conducenti,
                                                ArrayList<ArrayList<Object>> illegalValuesConducenti) {
	  //System.out.println("iterazione");
    boolean isEmpty = true;
    int indice = -1;
    for (DatiGenerazione d : listaDatiGenerazione) {
      if (d.getConducente() == null) {
        indice = listaDatiGenerazione.indexOf(d);
        isEmpty = false;
        /*System.out.println("la prima corsa trovata senza valore alla variabile Conducente è la numero "
        		+ indice + ":" + d.toString());*/
        break;
      }
    }
    if (isEmpty) {
      //System.out.println("è stato assegnato un valore al conducente di ogni corsa :)");
      return true;
    }
    DatiGenerazione posizioneCorrente = listaDatiGenerazione.get(indice);
    for (Conducente c : conducenti) {
       //System.out.println(c.getNome() + c.getCognome());
       //System.out.println("CHECK");
      if (checkConducente(c, posizioneCorrente, illegalValuesConducenti)) {
        String cF = c.getCodiceFiscale();
        posizioneCorrente.setConducente(cF);
        listaDatiGenerazione.set(indice, posizioneCorrente);
        //System.out.println("il valore scelto viene assegnato alla variabile: " + posizioneCorrente.toString());
  
        //System.out.println("FORWARD");
        if (forwardConducente(c, illegalValuesConducenti, posizioneCorrente, conducenti)) {
        	//System.out.println("Lista degli illegali aggiornata: ");
        	/*for (ArrayList<Object> i: illegalValuesConducenti) {
        		System.out.println(i.toString());
        	}*/
          if (ricercaBacktrackingConducente(conducenti, illegalValuesConducenti)) {
            return true;
          } else {
            posizioneCorrente.setConducente(null);
            illegalValuesConducenti.remove(illegalValuesConducenti.size() - 1);
            //System.out.println("L'assegnamento non è andato a buon fine :(");
            //System.out.println("viene rimosso il conducente assegnato" + posizioneCorrente.toString());
            //System.out.println("viene rimosso il range illegale" + illegalValuesConducenti.toString());
          }
        }
      }
    }
    return false;
  }

  private boolean ricercaBacktrackingMezzo(List<Mezzo> mezzi,
                                           ArrayList<ArrayList<Object>> illegalValuesMezzi) {
	//System.out.println("iterazione");
    boolean isEmpty = true;
    int indice = -1;
    for (DatiGenerazione d : listaDatiGenerazione) {
      if (d.getMezzo() == null) {
        indice = listaDatiGenerazione.indexOf(d);
        isEmpty = false;
        /*System.out.println("la prima corsa trovata senza valore alla variabile Mezzo è la numero "
		+ indice + ":" + d.toString());*/
        break;
      }
    }
    if (isEmpty) {
      //System.out.println("è stato assegnato un valore al mezzo di ogni corsa :)");
      return true;
    }
    DatiGenerazione posizioneCorrente = listaDatiGenerazione.get(indice);
    for (Mezzo m : mezzi) {
      //System.out.println(m.getTipo());
      //System.out.println("CHECK");
      if (checkMezzo(m, posizioneCorrente, illegalValuesMezzi)) {
        Long id = m.getId();
        posizioneCorrente.setMezzo(id.toString());
        listaDatiGenerazione.set(indice, posizioneCorrente);
      //System.out.println("il valore scelto viene assegnato alla variabile: " + posizioneCorrente.toString());
        
      //System.out.println("FORWARD");
        if (forwardMezzo(m, illegalValuesMezzi, posizioneCorrente, mezzi)) {
        	/*System.out.println("Lista degli illegali aggiornata: ");
        	for (ArrayList<Object> i: illegalValuesMezzi) {
        		System.out.println(i.toString());}*/
          if (ricercaBacktrackingMezzo(mezzi, illegalValuesMezzi)) {
            return true;
          } else {
            posizioneCorrente.setMezzo(null);
            illegalValuesMezzi.remove(illegalValuesMezzi.size() - 1);
          /*System.out.println("L'assegnamento non è andato a buon fine :(");
          System.out.println("viene rimosso il mezzo assegnato" + posizioneCorrente.toString());
          System.out.println("viene rimosso il range illegale" + illegalValuesMezzi.toString());*/
          }
        }
      }
    }
    return false;
  }

  public boolean checkOrario(DatiGenerazione datiGenerazione, LocalTime orario) {
    /*
     * Given a tested Orario, if Traffico is false ("No") this method confirms its validity;
     * else this will return
     * false and Orario will be shifted by 30 mins, trying to avoid congestion
     */
	  /*if (datiGenerazione.getOrario().equals(orario)
		        && datiGenerazione.getTraffico().equals("No")) {
		  System.out.println("Non c'è traffico, quindi " + orario + " è valido");
	  }
	  if (!datiGenerazione.getOrario().equals(orario) && datiGenerazione.getTraffico().equals("Si")) {
		  System.out.println("C'è traffico, quindi " + orario + " è valido");
	  }
	  if (datiGenerazione.getOrario().equals(orario)
		        && datiGenerazione.getTraffico().equals("Si")) {
		  System.out.println("C'è traffico, quindi " + orario + " non è valido"); 
	  }
	  if (!datiGenerazione.getOrario().equals(orario) && datiGenerazione.getTraffico().equals("No")) {
		  System.out.println("Non c'è traffico, quindi " + orario + " non è valido");
	  }*/
    return datiGenerazione.getOrario().equals(orario)
        && datiGenerazione.getTraffico().equals("No")
        ||
        !datiGenerazione.getOrario().equals(orario) && datiGenerazione.getTraffico().equals("Si");
  }

  public boolean checkConducente(Conducente conducente, DatiGenerazione posizioneCorrente,
                                 ArrayList<ArrayList<Object>> illegalValuesConducenti) {
    // Returns true because he/she is the first driver
    if (listaDatiGenerazione.indexOf(posizioneCorrente) == 0) {
    	//System.out.println("OK: è la prima corsa, non c'è bisogno di ulteriori controlli");
      return true;
    }

    for (int j = illegalValuesConducenti.size() - 1; j >= 0; j--) {
      ArrayList<Object> o = illegalValuesConducenti.get(j);
      Conducente illegalConducente = (Conducente) o.get(0);
      LocalTime illegalStartRange = (LocalTime) o.get(1);
      LocalTime illegalEndRange = (LocalTime) o.get(2);

      if (illegalConducente.getCodiceFiscale().equals(conducente.getCodiceFiscale())) {
    	  //System.out.println(conducente.getCognome() + " è presente nella lista degli illegali: " + illegalValuesConducenti.get(j).toString());
        if (posizioneCorrente.getOrario().isAfter(illegalStartRange) &&
            posizioneCorrente.getOrario().isBefore(illegalEndRange)) {
        	//System.out.println("NOT OK: alle " + posizioneCorrente.getOrario() + " è ancora in viaggio");
          return false;
        } else {
        	//System.out.println("ma non è più in viaggio");
          break;
        }
      }
    }

    Linea lC = risorseService.getLineaByName(posizioneCorrente.getLineaCorsa()).get();
    Linea lineaCorrente = (Linea) lC.clone();
    if (!posizioneCorrente.isAndata()) {
      lineaCorrente.setPartenza(lineaCorrente.getDestinazione());
    }
    //System.out.println("questa corsa parte da: " + lineaCorrente.getPartenza());

    int currentIdx = listaDatiGenerazione.indexOf(posizioneCorrente);

    // Backscrolling listaDatiGenerazione
    for (int i = currentIdx - 1; i >= 0; i--) {
        DatiGenerazione posizionePrecedente = listaDatiGenerazione.get(i);
        if (conducente.getCodiceFiscale().equals(posizionePrecedente.getConducente())) {
          //System.out.println(conducente.getCognome() + " ha già effettuato una corsa");
          Linea lP = risorseService.getLineaByName(posizionePrecedente.getLineaCorsa()).get();
          Linea lineaPrecedente = (Linea) lP.clone();
          if (!posizionePrecedente.isAndata()) {
            lineaPrecedente.setDestinazione(lineaPrecedente.getPartenza());
          }
          //System.out.println("e si trova a: " + lineaPrecedente.getDestinazione());

          /*if (lineaCorrente.getPartenza().equals(lineaPrecedente.getDestinazione())) {
          	System.out.println("OK: il luogo della sua precedente destinazione è uguale a quello dell'attuale partenza");
          }
          if (!lineaCorrente.getPartenza().equals(lineaPrecedente.getDestinazione())) {
          	System.out.println("NOT OK: il luogo della sua precedente destinazione è diverso da quello dell'attuale partenza");
          }*/
          return lineaCorrente.getPartenza().equals(lineaPrecedente.getDestinazione());
        }
      }
    //System.out.println("OK: " + conducente.getCognome() + " non ha ancora effettuato nessuna corsa, quindi è disponibile");
    return true;
  }
  
  public boolean forwardConducente(Conducente conducente,
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
    //System.out.println ("il conducente " + conducente.getCognome() + " non sarà disponibile dalle " + inizioRange + " alle " + fineRange);

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
        	//System.out.println("NOT OK: per la corsa " + d.toString() + "non c'è alcun conducente disponibile");
          return false;
        }
      } else {
    	//System.out.println("ma non è più in viaggio");
        break;
      }
    }
    //System.out.println("OK: nessun dominio delle corse successive è stato svuotato");
    return true;

  }

  public boolean checkMezzo(Mezzo mezzo, DatiGenerazione posizioneCorrente,
                            ArrayList<ArrayList<Object>> illegalValuesMezzi) {

    if (mezzo.getCapienza() < posizioneCorrente.getAttesi()) {
     // System.out.println("NOT OK: la capienza " + mezzo.getCapienza() + " è minore dei " + posizioneCorrente.getAttesi() + " passeggeri attesi");
      return false;
    }

    if (listaDatiGenerazione.indexOf(posizioneCorrente) == 0) {
      //System.out.println("OK: è la prima corsa, non c'è bisogno di ulteriori controlli");
      return true;
    }

    int j;
    for (j = illegalValuesMezzi.size() - 1; j >= 0; j--) {
      ArrayList<Object> o = illegalValuesMezzi.get(j);
      Mezzo illegalMezzo = (Mezzo) o.get(0);
      LocalTime illegalStartRange = (LocalTime) o.get(1);
      LocalTime illegalEndRange = (LocalTime) o.get(2);

      if (illegalMezzo.getId().equals(mezzo.getId())) {
    	//System.out.println(mezzo.getCapienza() + " è presente nella lista degli illegali: " + illegalValuesMezzi.get(j).toString());
        if (posizioneCorrente.getOrario().isAfter(illegalStartRange) &&
            posizioneCorrente.getOrario().isBefore(illegalEndRange)) {
        	//System.out.println("NOT OK: alle " + posizioneCorrente.getOrario() + " è ancora in viaggio");
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
    //System.out.println("questa corsa parte da: " + lineaCorrente.getPartenza());

    int currentIdx = listaDatiGenerazione.indexOf(posizioneCorrente);

// Backscrolling listaDatiGenerazione
    for (int i = currentIdx - 1; i >= 0; i--) {
      DatiGenerazione posizionePrecedente = listaDatiGenerazione.get(i);

      if (mezzo.getId().toString().equals(posizionePrecedente.getMezzo())) {
    	//System.out.println(mezzo.getTipo() + " ha già effettuato una corsa");
        Linea lP = risorseService.getLineaByName(posizionePrecedente.getLineaCorsa()).get();
        Linea lineaPrecedente = (Linea) lP.clone();
        if (!posizionePrecedente.isAndata()) {
          lineaPrecedente.setDestinazione(lineaPrecedente.getPartenza());
        }
       //System.out.println("e si trova a: " + lineaPrecedente.getDestinazione());
        
        /*if (lineaCorrente.getPartenza().equals(lineaPrecedente.getDestinazione())) {
        	System.out.println("OK: il luogo della sua precedente destinazione è uguale a quello dell'attuale partenza");
        }
        if (!lineaCorrente.getPartenza().equals(lineaPrecedente.getDestinazione())) {
        	System.out.println("NOT OK: il luogo della sua precedente destinazione è diverso da quello dell'attuale partenza");
        }*/
        return lineaCorrente.getPartenza().equals(lineaPrecedente.getDestinazione());
      }
    }
  //System.out.println("OK: " + mezzo.getTipo() + " non ha ancora effettuato nessuna corsa, quindi è disponibile");
    return true;
  }


  public boolean forwardMezzo(Mezzo mezzo, ArrayList<ArrayList<Object>> illegalValuesMezzi,
                               DatiGenerazione posizioneCorrente, List<Mezzo> mezzi) {

    Linea lineaCorrente = risorseService.getLineaByName(posizioneCorrente.getLineaCorsa()).get();
    LocalTime inizioRange = posizioneCorrente.getOrario();
    LocalTime fineRange = inizioRange.plusMinutes(lineaCorrente.getDurata());


    ArrayList<Object> item = new ArrayList<Object>();
    item.add(mezzo);
    item.add(inizioRange);
    item.add(fineRange);
    illegalValuesMezzi.add(item);
    //System.out.println ("il mezzo " + mezzo.getTipo() + " non sarà disponibile dalle " + inizioRange + " alle " + fineRange);
    
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
          //System.out.println("NOT OK: per la corsa " + d.toString() + "non c'è alcun mezzo disponibile");
          return false;
        }
      } else {
        break;
      }
    }
    //System.out.println("OK: nessun dominio delle corse successive è stato svuotato");
    return true;

  }

  @Override
  public StrategyType getStrategyType() {
    return StrategyType.Automatico;
  }

public AccountService getAccountService() {
	return accountService;
}

public void setAccountService(AccountService accountService) {
	this.accountService = accountService;
}

public DatiGenerazioneRepository getDatiGenerazioneRepository() {
	return datiGenerazioneRepository;
}

public void setDatiGenerazioneRepository(DatiGenerazioneRepository datiGenerazioneRepository) {
	this.datiGenerazioneRepository = datiGenerazioneRepository;
}

public RisorseService getRisorseService() {
	return risorseService;
}

public void setRisorseService(RisorseService risorseService) {
	this.risorseService = risorseService;
}

public ProgrammaCorseRepository getProgrammaCorseRepository() {
	return programmaCorseRepository;
}

public void setProgrammaCorseRepository(ProgrammaCorseRepository programmaCorseRepository) {
	this.programmaCorseRepository = programmaCorseRepository;
}

public CorsaService getCorsaService() {
	return corsaService;
}

public void setCorsaService(CorsaService corsaService) {
	this.corsaService = corsaService;
}

public List<DatiGenerazione> getListaDatiGenerazione() {
	return listaDatiGenerazione;
}

public void setListaDatiGenerazione(List<DatiGenerazione> listaDatiGenerazione) {
	this.listaDatiGenerazione = listaDatiGenerazione;
}
}