package com.java.tem.model.programmacorseservice.repository;

import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.ProgrammaCorse;
import com.java.tem.model.programmacorseservice.entity.daticorsaservice.DatiGenerazione;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Conducente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Linea;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Mezzo;
import com.java.tem.model.programmacorseservice.entity.risorseservice.RisorseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Repository
public class ProgrammaAutomaticoMaker implements Strategy {

    @Autowired
    private AccountService accountService;

    @Autowired
    private DatiGenerazioneRepository datiGenerazioneRepository;

    @Autowired
    private RisorseService risorseService;


    @Override
    public ProgrammaCorse doOperation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Utente utente = accountService.getUserByUsername(currentUserName);

        List<DatiGenerazione> datiGenerazione = datiGenerazioneRepository
                .findDatiGenerazioneByAziendaId(utente.getId());

        List<Conducente> conducenti = risorseService.getConducentiByAzienda(utente);
        List<Mezzo> mezzi = risorseService.getMezziByAzienda(utente);

        for (DatiGenerazione d: datiGenerazione) {
            List<LocalTime> orari = new ArrayList<LocalTime>();
            orari.add(d.getOrario());
            orari.add(d.getOrario().plusMinutes(30));

        }

        for(DatiGenerazione gen: datiGenerazione) {
            System.out.println(gen.toString());
        }
        return null;
    }

    public boolean checkOrario(DatiGenerazione datiGenerazione, LocalTime orario) {
        /*
         * Given a tested Orario, if Traffico is false ("No") this method confirms its validity; else this will return
         * false and Orario will be shifted by 30 mins, trying to avoid congestion
         */
        return (datiGenerazione.getOrario().equals(orario) && datiGenerazione.getTraffico().equals("No")) ||
                !datiGenerazione.getOrario().equals(orario) && datiGenerazione.getTraffico().equals("Si");
    }

    public boolean checkConducente(DatiGenerazione datiGenerazione,
            Conducente conducente, List<DatiGenerazione> listaDatiGenerazione) {
        /*
         * Checks if a given Conducente has already driven during the working day. If true, it tries to assign him routes
         * starting from his last destination. If false, it simply assigns the resource to the given route.
         */

        int start = listaDatiGenerazione.indexOf(datiGenerazione);

        if(start == 0)
            return true;


        for(int i = start-1; i-- > 0; ) {
            DatiGenerazione d = listaDatiGenerazione.get(i);


            if(conducente.getCodiceFiscale().equals(d.getConducente())) {
                // Checks if last object's destination is equal to current object destination

                Linea linea_precedente = risorseService.getLineaByName(d.getLinea_corsa()).get();

                if(!d.isAndata()) linea_precedente.setDestinazione(linea_precedente.getPartenza());

                Linea linea_corrente = risorseService.getLineaByName(datiGenerazione.getLinea_corsa()).get();

                if(!datiGenerazione.isAndata()) linea_corrente.setPartenza(linea_corrente.getDestinazione());

                return linea_corrente.getPartenza().equals(linea_precedente.getDestinazione());

            }
        }
        return true;
    }


    @Override
    public ProgrammaCorse doOperation(ProgrammaCorse programmaCorse) {
        return null;
    }

    @Override
    public StrategyType getStrategyType() {
        return StrategyType.Automatico;
    }
}
