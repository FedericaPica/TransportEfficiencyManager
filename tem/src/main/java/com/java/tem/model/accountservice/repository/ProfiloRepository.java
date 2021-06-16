package com.java.tem.model.accountservice.repository;

import com.java.tem.model.accountservice.entity.Profilo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** Profilo Repository.
 *
 */
@Repository
public interface ProfiloRepository extends JpaRepository<Profilo, Long> {
  @Query("SELECT p FROM Profilo p WHERE p.nomeProfilo = ?1")
  Profilo findByRuolo(String ruolo);
}
