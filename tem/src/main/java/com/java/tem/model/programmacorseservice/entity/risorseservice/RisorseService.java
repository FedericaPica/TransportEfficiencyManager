package com.java.tem.model.programmacorseservice.entity.risorseservice;

import com.java.tem.model.programmacorseservice.repository.ConducenteRepository;
import com.java.tem.model.programmacorseservice.repository.LineaRepository;
import com.java.tem.model.programmacorseservice.repository.MezzoRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RisorseService<T>  {
  @Autowired
  ConducenteRepository conducenteRepository;
  @Autowired
  LineaRepository lineaRepository;
  @Autowired
  MezzoRepository mezzoRepository;
  
  public RisorseService() { 
  
  }
  
  public void addRisorsa(T risorsa) {
	  
	  if(risorsa instanceof Mezzo) {
		  mezzoRepository.save((Mezzo) risorsa);
	  } else if(risorsa instanceof Linea) {
		  lineaRepository.save((Linea) risorsa);
	  } else {
		  conducenteRepository.save((Conducente) risorsa);
	  }
  }
  
  public void addMezzo(Mezzo mezzo) {
    mezzoRepository.save(mezzo);
  }
 
  public void addConducente(Conducente conducente) {
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
    conducenteRepository.findAll().forEach(conducenti::add);
    return conducenti;
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
  
  public Optional<Mezzo> getMezzo(Long id) {
    return mezzoRepository.findById(id);
  }
  
  public Optional<Linea> getLinea(Long id) {
    return lineaRepository.findById(id);
  }
  
  public Optional<Conducente> getConducente(Long id) {
    return conducenteRepository.findById(id);
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
