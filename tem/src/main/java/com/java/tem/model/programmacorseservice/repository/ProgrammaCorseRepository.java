package com.java.tem.model.programmacorseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.tem.model.programmacorseservice.entity.ProgrammaCorse;

@Repository
public interface ProgrammaCorseRepository extends JpaRepository<ProgrammaCorse, Long>{
	
}
