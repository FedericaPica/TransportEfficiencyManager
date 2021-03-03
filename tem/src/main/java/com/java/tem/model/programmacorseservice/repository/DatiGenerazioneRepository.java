package com.java.tem.model.programmacorseservice.repository;

import com.java.tem.model.programmacorseservice.entity.daticorsaservice.DatiGenerazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatiGenerazioneRepository extends JpaRepository<DatiGenerazione, Long> {
    @Query("SELECT d FROM DatiGenerazione d WHERE d.azienda_id = ?1")
    public List<DatiGenerazione> findDatiGenerazioneByAziendaId(Long id);
}
