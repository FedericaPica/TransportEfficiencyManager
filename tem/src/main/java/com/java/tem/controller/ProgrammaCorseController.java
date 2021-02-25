package com.java.tem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.java.tem.model.programmacorseservice.entity.ProgrammaCorseService;

@Controller
public class ProgrammaCorseController {
	
	@Autowired
	private ProgrammaCorseService programmaCorseService;
	
	@GetMapping("/programmacorse")
	public String homeProgrammaCorse() {
		programmaCorseService.generaProgrammaCorse("manuale");
		return "home-programma-corse";
	}
}
