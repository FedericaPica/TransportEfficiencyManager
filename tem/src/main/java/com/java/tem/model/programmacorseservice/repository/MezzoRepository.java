package com.java.tem.model.programmacorseservice.repository;

import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Mezzo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MezzoRepository extends JpaRepository<Mezzo, Long> {
  List<Mezzo> findByAzienda(Utente utente);

}
