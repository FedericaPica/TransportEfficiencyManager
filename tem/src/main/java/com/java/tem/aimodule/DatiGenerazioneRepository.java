package com.java.tem.aimodule;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** DatiGenerazioneRepository.
 *
 */
@Repository
public interface DatiGenerazioneRepository extends JpaRepository<DatiGenerazione, Long> {
  @Query("SELECT d FROM DatiGenerazione d WHERE d.aziendaId = ?1")
  List<DatiGenerazione> findDatiGenerazioneByAziendaId(Long id);
}
