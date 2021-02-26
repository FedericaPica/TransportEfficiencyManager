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
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "Conducente")
@DynamicUpdate(true)
public class Conducente extends Risorsa {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  @Size(min = 2, max = 50)
  private String nome;
  @Size(min = 2, max = 50)
  private String cognome;
  @Size(min = 16, max = 16)
  private String codiceFiscale;
  
  @ManyToMany(mappedBy = "conducenti")
  private Set<Corsa> corse = new HashSet<>();
  
  @ManyToOne
  @JoinColumn(name = "azienda_id")
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
