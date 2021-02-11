package com.java.tem.model.programmacorseservice.entity.risorseservice;

import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.Corsa;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Mezzo")
public class Mezzo extends Risorsa {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String targa;
  private int capienza;
  private String tipo;

  @ManyToMany(mappedBy = "mezzi")
  private Set<Corsa> corse = new HashSet<>();
  
  @ManyToOne
  @JoinColumn(name = "azienda_id", nullable = false)
  private Utente azienda;

  public Utente getAzienda() {
    return azienda;
  }

  public void setAzienda(Utente azienda) {
    this.azienda = azienda;
  }

  public Set<Corsa> getCorse() {
    return corse;
  }
  
  public void setCorse(Set<Corsa> corse) {
    this.corse = corse;
  }
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getTarga() {
    return targa;
  }
 
  public void setTarga(String targa) {
    this.targa = targa;
  }
  
  public int getCapienza() {
    return capienza;
  }
  
  public void setCapienza(int capienza) {
    this.capienza = capienza;
  }
  
  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
}
