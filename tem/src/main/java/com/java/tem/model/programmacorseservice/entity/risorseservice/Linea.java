package com.java.tem.model.programmacorseservice.entity.risorseservice;

import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.Corsa;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "Linea")
@DynamicUpdate
public class Linea extends Risorsa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Size(min = 2, max = 50)
  private String nome;
  @Size(min = 2, max = 50)
  private String partenza;
  @Size(min = 2, max = 50)
  private String destinazione;
  
  @OneToMany(mappedBy = "linea")
  private Set<Corsa> corse;

  @ManyToOne
  @JoinColumn(name = "azienda_id", nullable = false)
  private Utente azienda;
  
  public Set<Corsa> getCorse() {
    return corse;
  }

  public void setCorse(Set<Corsa> corse) {
    this.corse = corse;
  }

  public Utente getAzienda() {
    return azienda;
  }

  public void setAzienda(Utente azienda) {
    this.azienda = azienda;
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
  
  public String getPartenza() {
    return partenza;
  }
  
  public void setPartenza(String partenza) {
    this.partenza = partenza;
  }

  public String getDestinazione() {
    return destinazione;
  }
  
  public void setDestinazione(String destinazione) {
    this.destinazione = destinazione;
  }
}
