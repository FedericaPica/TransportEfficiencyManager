package com.java.tem.model.programmacorseservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.daticorsaservice.DatiCorsa;


@Repository
public interface DatiCorsaRepository extends JpaRepository<DatiCorsa, Long> {
  List<DatiCorsa> findByAzienda(Utente utente);


}