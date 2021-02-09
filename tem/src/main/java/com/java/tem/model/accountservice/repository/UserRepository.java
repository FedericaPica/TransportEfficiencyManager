package com.java.tem.model.accountservice.repository;

import com.java.tem.model.accountservice.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Utente, Long> {
  @Query("SELECT u FROM Utente u WHERE u.email = ?1")
  public Utente findByEmail(String email);
}
