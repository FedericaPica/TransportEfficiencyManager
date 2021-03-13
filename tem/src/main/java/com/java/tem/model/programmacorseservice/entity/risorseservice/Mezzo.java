package com.java.tem.model.programmacorseservice.entity.risorseservice;

import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.Corsa;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


@Entity
@Table(name = "Mezzo")
public class Mezzo extends Risorsa {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(min = 7, max = 10)
  private String targa;
  @Min(2)
  @Max(85)
  private int capienza;
  @Size(min = 2, max = 50)
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

  @Override
  public String toString() {
    return getTipo();
  }
}
