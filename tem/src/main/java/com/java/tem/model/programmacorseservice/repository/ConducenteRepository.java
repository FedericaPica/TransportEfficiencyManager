package com.java.tem.model.programmacorseservice.repository;

import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Conducente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConducenteRepository extends JpaRepository<Conducente, Long> {
  
}
