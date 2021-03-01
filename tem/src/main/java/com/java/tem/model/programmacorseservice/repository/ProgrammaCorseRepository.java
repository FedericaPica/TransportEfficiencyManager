package com.java.tem.model.programmacorseservice.repository;

import com.java.tem.model.accountservice.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.tem.model.programmacorseservice.entity.ProgrammaCorse;

import java.util.List;

@Repository
public interface ProgrammaCorseRepository extends JpaRepository<ProgrammaCorse, Long>{

    List<ProgrammaCorse> findByAzienda(Utente utente);
}
