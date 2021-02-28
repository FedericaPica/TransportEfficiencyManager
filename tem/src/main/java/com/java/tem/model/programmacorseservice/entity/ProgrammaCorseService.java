package com.java.tem.model.programmacorseservice.entity;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.tem.model.programmacorseservice.repository.ProgrammaCorseRepository;
import com.java.tem.model.programmacorseservice.repository.Strategy;
import com.java.tem.model.programmacorseservice.repository.StrategyType;

@Component
public class ProgrammaCorseService {

	@Autowired
	private ProgrammaCorseFactory programmaCorseFactory;
	
	@Autowired
	private ProgrammaCorseRepository programmaCorseRepository;
	
	public ProgrammaCorseService() { 
	}
	
	public void generaProgrammaCorse(String genType, ProgrammaCorse programmaCorse) {
		Strategy strategy;
		
		switch(genType) {
			case "manuale": 
				strategy = programmaCorseFactory.findStrategy(StrategyType.Manuale);
				strategy.doOperation(programmaCorse);
				break;
			case "automatico":
				strategy = programmaCorseFactory.findStrategy(StrategyType.Automatico);
				strategy.doOperation();
				break;
				
			default:
				strategy = programmaCorseFactory.findStrategy(StrategyType.Manuale);
				strategy.doOperation(programmaCorse);
		}
	}
	
	
	public void addProgrammaCorse(ProgrammaCorse programmaCorse) {
		programmaCorseRepository.save(programmaCorse);
	}
	
	public Optional<ProgrammaCorse> getProgrammaCorseById(Long id) {
		return programmaCorseRepository.findById(id);
	}
	
	public void updateProgrammaCorse(ProgrammaCorse programmaCorse) {
		programmaCorseRepository.save(programmaCorse);
	}
	
	public void deleteProgrammaCorseById(Long id) {
		programmaCorseRepository.deleteById(id);
	}
	

	
}
