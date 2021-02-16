package com.java.tem.model.programmacorseservice.repository;

import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Linea;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface LineaRepository extends JpaRepository<Linea, Long> {
	public List<Linea> findByAzienda(Utente azienda);
}
