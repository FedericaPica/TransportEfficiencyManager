package com.java.tem.model.programmacorseservice.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.tem.model.programmacorseservice.repository.CorsaRepository;

@Component
public class CorsaService {

	@Autowired
	CorsaRepository corsaRepository;
	
	public CorsaService() {
		
	}
	
	public void addCorsa(Corsa corsa) {
		corsaRepository.save(corsa);
	}
	
	
	public Optional<Corsa> getCorsaById(Long id) {
		return corsaRepository.findById(id);
	}
	
	public void deleteCorsaById(Long id) {
		corsaRepository.deleteById(id);
	}
	
	public List<Corsa> getCorseByProgramma(ProgrammaCorse programmaCorse) {
		return corsaRepository.findCorseByProgramma(programmaCorse);
	}
	
}
