package com.java.tem.model.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.tem.model.accountservice.entity.DettaglioUtente;

@Repository
public interface DettaglioUtenteRepository extends JpaRepository<DettaglioUtente, Long>{

}
