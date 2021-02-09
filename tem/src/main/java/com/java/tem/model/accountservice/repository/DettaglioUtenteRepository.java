package com.java.tem.model.accountservice.repository;

import com.java.tem.model.accountservice.entity.DettaglioUtente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DettaglioUtenteRepository extends JpaRepository<DettaglioUtente, Long> {

}
