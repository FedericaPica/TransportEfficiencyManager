package com.java.tem.model.accountservice.repository;

import com.java.tem.model.accountservice.entity.Utente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** User Repository.
 *
 */
@Repository
public interface UserRepository extends JpaRepository<Utente, Long> {
  @Query("SELECT u FROM Utente u WHERE u.email = ?1")
  Utente findByEmail(String email);

  @Query("SELECT count(u) > 0 FROM Utente u WHERE u.email = ?1")
  Boolean checkUserExistanceByEmail(String email);

  List<Utente> findAllByDettaglioIsNotNull();

}
