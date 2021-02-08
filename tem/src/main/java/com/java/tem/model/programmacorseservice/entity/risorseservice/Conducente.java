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
@Table(name = "Conducente")
public class Conducente extends Risorsa {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  private String nome;
  private String cognome;
  private String codiceFiscale;
  
  @ManyToMany(mappedBy = "conducenti")
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

  public void setId(long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }
  
  public String getCognome() {
    return cognome;
  }
  
  public void setCognome(String cognome) {
    this.cognome = cognome;
  }
  
  public String getCodiceFiscale() {
    return codiceFiscale;
  }
  
  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }
}
