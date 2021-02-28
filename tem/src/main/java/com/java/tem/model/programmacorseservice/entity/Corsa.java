package com.java.tem.model.programmacorseservice.entity;

import com.java.tem.model.programmacorseservice.entity.risorseservice.Conducente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Linea;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Mezzo;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Corsa")
public class Corsa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  private Time orario;
  @ManyToMany
  @JoinTable(name = "Conducente_Corsa",
      joinColumns = {@JoinColumn(name = "corsa_id")},
      inverseJoinColumns = {@JoinColumn(name = "conducente_id")})
  private Set<Conducente> conducenti = new HashSet<>();
@ManyToMany
  @JoinTable(name = "Mezzo_Corsa",
      joinColumns = {@JoinColumn(name = "corsa_id")}, 
      inverseJoinColumns = {@JoinColumn(name = "mezzo_id")})
  private Set<Mezzo> mezzi = new HashSet<>(); 
  
  @ManyToOne
  @JoinColumn(name = "linea_id")
    private Linea linea;

  @ManyToOne
  @JoinColumn(name = "programma_id", nullable = false)
    private ProgrammaCorse programma;
    
  public Set<Mezzo> getMezzi() {
    return mezzi;
  }
  
  public void setMezzi(Set<Mezzo> mezzi) {
    this.mezzi = mezzi;
  }
  
  public Set<Conducente> getConducenti() {
    return conducenti;
  }
  
  public void setConducenti(Set<Conducente> conducenti) {
    this.conducenti = conducenti;
  }
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Time getOrario() {
    return orario;
  }
  
  public void setOrario(Time orario) {
    this.orario = orario;
  }
  
  public Linea getLinea() {
	return linea;
  }

  public void setLinea(Linea linea) {
    this.linea = linea;
  }

  public ProgrammaCorse getProgramma() {
    return programma;
  }

  public void setProgramma(ProgrammaCorse programma) {
    this.programma = programma;
  }
}
