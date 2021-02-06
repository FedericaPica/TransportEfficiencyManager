package com.java.tem.model.programmacorseservice.entity.risorseservice;

import com.java.tem.model.programmacorseservice.entity.Corsa;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "Mezzo")
public class Mezzo extends Risorsa {
  @Id
  private Long id;
  private String targa;
  private int capienza;
  private String tipo;

  @ManyToMany(mappedBy = "mezzi")
  private Set<Corsa> corse = new HashSet<>();

  public Set<Corsa> getCorse() {
    return corse;
  }
  
  public void setCorse(Set<Corsa> corse) {
    this.corse = corse;
  }
  
  public long getId() {
    return id;
  }
  
  public void setId(long id) {
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
