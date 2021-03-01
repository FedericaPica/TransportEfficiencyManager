package com.java.tem.model.programmacorseservice.entity.risorseservice;

import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.repository.ConducenteRepository;
import com.java.tem.model.programmacorseservice.repository.LineaRepository;
import com.java.tem.model.programmacorseservice.repository.MezzoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RisorseService  {
  @Autowired
  ConducenteRepository conducenteRepository;
  @Autowired
  LineaRepository lineaRepository;
  @Autowired
  MezzoRepository mezzoRepository;
  
  public RisorseService() { 
  
  }
  
  public void addMezzo(Mezzo mezzo) {
    mezzoRepository.save(mezzo);
  }
 
  public void addConducente(Conducente conducente) throws Exception {
	  if (conducente.getCodiceFiscale().length() != 16)
		  throw new Exception(); // ToDo
    conducenteRepository.save(conducente);
  }
  
  public void addLinea(Linea linea) {
    lineaRepository.save(linea);
  }
  
  /** Simply returns a list of Conducente elements.

   * @return conducenti  List set of conducente. 
   *
   */
  public List<Conducente> getConducenti() {
    List<Conducente> conducenti = new ArrayList<Conducente>();
    conducenti = conducenteRepository.findAll();
    return conducenti;
  }
  
  public List<Conducente> getConducentiByAzienda(Utente utente) {
	  List<Conducente> conducenti = new ArrayList<Conducente>();
	  conducenti = conducenteRepository.findByAzienda(utente);
	  return conducenti;
  }
  
  public Optional<Conducente> getConducente(Long id) {
    return conducenteRepository.findById(id);
  }

  /** Simply returns a list of Linea elements.

   * @return linee  List set of linea. 
   *
   */
  public List<Linea> getLinee() {
    List<Linea> linee = new ArrayList<Linea>();
    lineaRepository.findAll().forEach(linee::add);
    return linee;
  }
  
  public List<Linea> getLineeByAzienda(Utente utente) {
	  List<Linea> linee = new ArrayList<Linea>();
	  linee = lineaRepository.findByAzienda(utente);
	  return linee;
  }
  
  public Optional<Linea> getLinea(Long id) {
    return lineaRepository.findById(id);
  }
 
  /** Simply returns a list of Mezzo elements.

   * @return mezzi  List set of mezzo. 
   *
   */
  public List<Mezzo> getMezzi() {
    List<Mezzo> mezzi = new ArrayList<Mezzo>();
    mezzoRepository.findAll().forEach(mezzi::add);
    return mezzi;
  }

  public List<Mezzo> getMezziByAzienda(Utente utente) {
	  List<Mezzo> mezzi = new ArrayList<Mezzo>();
	  mezzi = mezzoRepository.findByAzienda(utente);
	  return mezzi;
  }
  
  
  public Optional<Mezzo> getMezzo(Long id) {
    return mezzoRepository.findById(id);
  }
  
  public void updateMezzo(Mezzo mezzo) {
    mezzoRepository.save(mezzo);
  }
  
  public void updateLinea(Linea linea) {
    lineaRepository.save(linea);
  }
  
  public void updateConducente(Conducente conducente) {
    conducenteRepository.save(conducente);
  }
  
  public void deleteMezzo(Mezzo mezzo) {
    mezzoRepository.delete(mezzo);
  }
  
  public void deleteLinea(Linea linea) {
    lineaRepository.delete(linea);
  }
  
  public void deleteConducente(Conducente conducente) {
    conducenteRepository.delete(conducente);
  }
  
}
