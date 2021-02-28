package com.java.tem.controller;

import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.ProgrammaCorse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.java.tem.model.programmacorseservice.entity.ProgrammaCorseService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProgrammaCorseController {
	
	@Autowired
	private ProgrammaCorseService programmaCorseService;

	@Autowired
	private AccountService accountService;
	
	@GetMapping("/programmacorse")
	public String homeProgrammaCorse(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = authentication.getName();
		Utente utente = accountService.getUserByUsername(currentUserName);

		List<ProgrammaCorse> programmiList = programmaCorseService.getProgrammaCorseByAzienda(utente);

		model.addAttribute("listaProgrammi", programmiList);
		return "list-programmacorse";
	}

	@GetMapping("/programmacorse/insert/manuale")
	public String insertProgrammaManuale(Model model) {

		model.addAttribute("programmaCorse", new ProgrammaCorse());
		return "insert-programmacorse-manuale";
	}

	@GetMapping("/programmacorse/insert/auto")
	public void insertProgrammaAutomatico() {

	}

	@GetMapping("/programmacorse/delete/{id}")
	public String deleteProgrammaCorse(@PathVariable("id") Long id) {
		ProgrammaCorse programmaCorse = programmaCorseService.getProgrammaCorseById(id).get();
		programmaCorseService.deleteProgrammaCorse(programmaCorse);

		return "index";
	}

	@PostMapping("/programmacorse/manuale/submit")
	public ModelAndView processProgrammaManuale(@ModelAttribute("programmaCorse") ProgrammaCorse programmaCorse) {
		programmaCorseService.generaProgrammaCorse("manuale", programmaCorse);

		return new ModelAndView("redirect:/corsa/insert/" + programmaCorse.getId());
	}
}
