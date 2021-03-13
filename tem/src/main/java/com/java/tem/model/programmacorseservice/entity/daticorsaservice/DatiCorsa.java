package com.java.tem.model.programmacorseservice.entity.daticorsaservice;

import com.java.tem.model.accountservice.entity.Utente;
import java.sql.Time;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "DatiCorsa")
public class DatiCorsa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(min = 2, max = 40)
  private String lineaCorsa;
  private Time orarioCorsa;
  private int numeroPosti;
  private int passeggeriSaliti;
  private int passeggeriNonSaliti;
  public boolean traffico;
  public boolean andata;

  @ManyToOne
  @JoinColumn(name = "azienda_id", nullable = false)
  private Utente azienda;

  public Utente getAzienda() {
    return azienda;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setAzienda(Utente azienda) {
    this.azienda = azienda;
  }

  public String getLineaCorsa() {
    return lineaCorsa;
  }

  public void setLineaCorsa(String lineaCorsa) {
    this.lineaCorsa = lineaCorsa;
  }

  public Time getOrarioCorsa() {
    return orarioCorsa;
  }

  public void setOrarioCorsa(Time orarioCorsa) {
    this.orarioCorsa = orarioCorsa;
  }

  public int getNumeroPosti() {
    return numeroPosti;
  }

  public void setNumeroPosti(int numeroPosti) {
    this.numeroPosti = numeroPosti;
  }

  public int getPasseggeriSaliti() {
    return passeggeriSaliti;
  }

  public void setPasseggeriSaliti(int passeggeriSaliti) {
    this.passeggeriSaliti = passeggeriSaliti;
  }

  public int getPasseggeriNonSaliti() {
    return passeggeriNonSaliti;
  }

  public void setPasseggeriNonSaliti(int passeggeriNonSaliti) {
    this.passeggeriNonSaliti = passeggeriNonSaliti;
  }

  public boolean isTraffico() {
    return traffico;
  }

  public void setTraffico(boolean traffico) {
    this.traffico = traffico;
  }

  public boolean isAndata() {
    return andata;
  }

  public void setAndata(boolean andata) {
    this.andata = andata;
  }

}
