package com.java.tem.controller;

import java.util.List;

import com.java.tem.model.programmacorseservice.repository.ProgrammaManualeMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.java.tem.exceptions.ResourcesNotExistException;
import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.Corsa;
import com.java.tem.model.programmacorseservice.entity.CorsaService;
import com.java.tem.model.programmacorseservice.entity.ProgrammaCorse;
import com.java.tem.model.programmacorseservice.entity.ProgrammaCorseService;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Conducente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Linea;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Mezzo;
import com.java.tem.model.programmacorseservice.entity.risorseservice.RisorseService;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class CorsaController {
	//@Autowired
	//private CorsaService corsaService;
	
	@Autowired
	private RisorseService risorseService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ProgrammaCorseService programmaCorseService;
	
	@Autowired
	private CorsaService corsaService;

	@Autowired
	private ProgrammaManualeMaker programmaManualeMaker;


	@GetMapping("/corsa/insert/{programmaCorseId}")
	public String insertCorsa(@PathVariable("programmaCorseId") Long programmaCorseId, Model model)
			throws ResourcesNotExistException {
		
		ProgrammaCorse programmaCorse = programmaCorseService.getProgrammaCorseById(programmaCorseId).get();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String currentUserName = authentication.getName();
	    Utente utente = accountService.getUserByUsername(currentUserName);
	    
		List<Linea> linee =  risorseService.getLineeByAzienda(utente);
		List<Conducente> conducenti = risorseService.getConducentiByAzienda(utente);
		List<Mezzo> mezzi = risorseService.getMezziByAzienda(utente);
		
		if(linee.size() == 0 || mezzi.size() == 0 || conducenti.size() == 0) {
			throw new ResourcesNotExistException("Una o più risorse non esistenti.");
		}
		
		model.addAttribute("linee", linee);
		model.addAttribute("mezzi", mezzi);
		model.addAttribute("conducenti", conducenti);
		model.addAttribute("programmaCorse", programmaCorse);
		model.addAttribute("linea", new Linea());
		
		model.addAttribute("corsa", new Corsa());
		
		return "/insert-corsa";
	}
	
	@PostMapping("/corsa/submit/{programmaCorseId}")
	public ModelAndView processCorsa(@ModelAttribute("corsa") Corsa corsa, @RequestParam(value = "mezzo") Long mezzo_id,
									 @RequestParam(value = "linea") Long linea_id,
									 @RequestParam(value= "conducente") Long conducente_id,
									 @PathVariable(value= "programmaCorseId") Long programmaCorse_id) {
		Conducente conducente = risorseService.getConducente(conducente_id).get();
		ProgrammaCorse programmaCorse = programmaCorseService.getProgrammaCorseById(programmaCorse_id).get();
		Linea linea = risorseService.getLinea(linea_id).get();
		Mezzo mezzo = risorseService.getMezzo(mezzo_id).get();

		programmaManualeMaker.creaCorsa(corsa, linea, mezzo, conducente,programmaCorse);
		
		return new ModelAndView("redirect:/corsa/insert/" + programmaCorse_id);
	}
}