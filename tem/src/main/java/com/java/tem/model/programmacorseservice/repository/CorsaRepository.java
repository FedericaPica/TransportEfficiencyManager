package com.java.tem.model.programmacorseservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.tem.model.programmacorseservice.entity.Corsa;
import com.java.tem.model.programmacorseservice.entity.ProgrammaCorse;

@Repository
public interface CorsaRepository extends JpaRepository<Corsa, Long>{
	public Corsa findCorsaByProgramma(ProgrammaCorse programmaCorse);
	
	public List<Corsa> findCorseByProgramma(ProgrammaCorse programmaCorse);
	
}
