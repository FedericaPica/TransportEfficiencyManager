package com.java.tem.model.programmacorseservice.entity.daticorsaservice;

import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.repository.DatiCorsaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** DatiCorsa Service.
 *
 */
@Component
public class DatiCorsaService {
  @Autowired
  DatiCorsaRepository datiCorsaRepository;

  public DatiCorsaService() {
  }

  public void addDatiCorsa(DatiCorsa datiCorsa) {
    datiCorsaRepository.save(datiCorsa);
  }

  /**
   * Simply returns a list of DatiCorsa elements.
   *
   * @return datiCorse  List set of datiCorsa.
   */
  public List<DatiCorsa> getDatiCorsa() {
    List<DatiCorsa> datiCorse = new ArrayList<DatiCorsa>();
    datiCorsaRepository.findAll().forEach(datiCorse::add);
    return datiCorse;
  }
  
  public Optional<DatiCorsa> getDatiCorsa(Long id) {
    return datiCorsaRepository.findById(id);
  }

  /** Simply returns DatiCorsa by given Utente.
   *
   * @param utente Utente.
   * @return List of DatiCorsa.
   */
  public List<DatiCorsa> getDatiCorsaByAzienda(Utente utente) {
    List<DatiCorsa> datiCorse = new ArrayList<DatiCorsa>();
    datiCorse = datiCorsaRepository.findByAzienda(utente);
    return datiCorse;
  }

  public void updateDatiCorsa(DatiCorsa datiCorsa) {
    datiCorsaRepository.save(datiCorsa);
  }

  public boolean checkOwnership(DatiCorsa datiCorsa, Utente utente) {
    return datiCorsa.getAzienda().equals(utente);
  }

  public void deleteDatiCorsa(DatiCorsa datiCorsa) {
    datiCorsaRepository.delete(datiCorsa);
  }

}
