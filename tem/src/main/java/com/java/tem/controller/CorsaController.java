package com.java.tem.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

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
import com.java.tem.model.programmacorseservice.repository.CorsaRepository;


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
	
	@GetMapping("/corsa/insert")
	public String insertCorsa(Model model) throws ResourcesNotExistException {
		
		
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
		model.addAttribute("linea", new Linea());
		
		model.addAttribute("corsa", new Corsa());
		
		return "/insert-corsa";
	}
	
	@PostMapping("/corsa/submit")
	public String processCorsa(@ModelAttribute("corsa") Corsa corsa, @RequestParam(value = "mezzo") Long mezzo_id, @RequestParam(value = "linea") Long linea_id, @RequestParam(value= "conducente") Long conducente_id) {
		Conducente conducente = risorseService.getConducente(conducente_id).get();
		Linea linea = risorseService.getLinea(linea_id).get();
		Mezzo mezzo = risorseService.getMezzo(mezzo_id).get();
		
		System.out.println(conducente.getNome());
		System.out.println(linea.getNome());
		System.out.println(mezzo.getTipo());
		
		Set<Conducente> conducenti = new HashSet<Conducente>();
		Set<Mezzo> mezzi = new HashSet<Mezzo>();
		
		conducenti.add(conducente);
		mezzi.add(mezzo);

		corsa.setConducenti(conducenti);
		corsa.setMezzi(mezzi);
		corsa.setLinea(linea);
		ProgrammaCorse programmaCorse = programmaCorseService.getProgrammaCorseById(2L).get();
		corsa.setProgramma(programmaCorse);
		
		corsaService.addCorsa(corsa);
		
		return "home";
	}
	
	
}
 