package com.java.tem.model.programmacorseservice.repository;

import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.Corsa;
import com.java.tem.model.programmacorseservice.entity.CorsaService;
import com.java.tem.model.programmacorseservice.entity.ProgrammaCorseService;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Conducente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Linea;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Mezzo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import com.java.tem.model.programmacorseservice.entity.ProgrammaCorse;
import java.util.Set;

@Repository
public class ProgrammaManualeMaker implements Strategy {
	@Autowired
	private AccountService accountService;

	@Autowired
	private ProgrammaCorseService programmaCorseService;

	@Autowired
	private CorsaService corsaService;

	@Override
	public ProgrammaCorse doOperation() {
		return new ProgrammaCorse();
	}

	@Override
	public ProgrammaCorse doOperation(ProgrammaCorse programmaCorse) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = authentication.getName();
		Utente utente = accountService.getUserByUsername(currentUserName);

		programmaCorse.setAzienda(utente);
		
		programmaCorseService.addProgrammaCorse(programmaCorse);
		return programmaCorse;
	}

	public void creaCorsa(Corsa corsa, Linea linea, Set<Mezzo> mezzi, Set<Conducente> conducenti, ProgrammaCorse programmaCorse) {

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
