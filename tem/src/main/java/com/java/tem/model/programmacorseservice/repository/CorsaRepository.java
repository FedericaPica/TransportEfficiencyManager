package com.java.tem.model.programmacorseservice.repository;

import com.java.tem.model.programmacorseservice.entity.Corsa;
import com.java.tem.model.programmacorseservice.entity.ProgrammaCorse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Corsa Repository.
 *
 */
@Repository
public interface CorsaRepository extends JpaRepository<Corsa, Long> {
  Corsa findCorsaByProgramma(ProgrammaCorse programmaCorse);

  List<Corsa> findCorseByProgramma(ProgrammaCorse programmaCorse);

}
