package com.java.tem.aimodule;


import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Immutable;


@Entity
@Immutable
@Table(name = "generazione_automatica")
public class DatiGenerazione {

  @Id
  private Long id;

  private String lineaCorsa;

  private String traffico;

  private int attesi;

  private Long aziendaId;

  private LocalTime orario;

  private String conducente;

  private String mezzo;

  private boolean andata;

  public boolean isAndata() {
    return andata;
  }

  public void setAndata(boolean andata) {
    this.andata = andata;
  }

  public LocalTime getOrario() {
    return orario;
  }

  public void setOrario(LocalTime orario) {
    this.orario = orario;
  }

  public String getConducente() {
    return conducente;
  }

  public void setConducente(String conducente) {
    this.conducente = conducente;
  }

  public String getMezzo() {
    return mezzo;
  }

  public void setMezzo(String mezzo) {
    this.mezzo = mezzo;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLineaCorsa() {
    return lineaCorsa;
  }

  public void setLineaCorsa(String lineaCorsa) {
    this.lineaCorsa = lineaCorsa;
  }

  public String getTraffico() {
    return traffico;
  }

  public void setTraffico(String traffico) {
    this.traffico = traffico;
  }

  public int getAttesi() {
    return attesi;
  }

  public void setAttesi(int attesi) {
    this.attesi = attesi;
  }

  public Long getAziendaId() {
    return aziendaId;
  }

  public void setAziendaId(Long aziendaId) {
    this.aziendaId = aziendaId;
  }

  @Override
  public String toString() {

    return "[ " + lineaCorsa + " " + traffico + " " + attesi + " " + orario
        + " " + conducente + " " + mezzo + " " + andata + " ]";

  }
}
