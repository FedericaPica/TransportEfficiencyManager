package com.java.tem.model.accountservice.entity;

import com.java.tem.model.programmacorseservice.entity.ProgrammaCorse;
import com.java.tem.model.programmacorseservice.entity.daticorsaservice.DatiCorsa;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Conducente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Linea;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Mezzo;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "utente")
public class Utente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(min = 6, max = 45)
  @Column(nullable = false, unique = true, length = 45)
  private String email;

  @Size(min = 6, max = 20)
  @Column(name = "username", nullable = false, length = 20, unique = true)
  private String username;

  @Column(nullable = false, length = 64)
  private String password;

  @ManyToOne
  @JoinColumn(name = "id_profilo", nullable = false)
  private Profilo profilo;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "id_dettaglio", referencedColumnName = "id")
  private DettaglioUtente dettaglio;

  @OneToMany
  @JoinColumn(name = "azienda_id")
  private Set<Conducente> conducenti;

  @OneToMany
  @JoinColumn(name = "azienda_id")
  private Set<Mezzo> mezzi;

  @OneToMany
  @JoinColumn(name = "azienda_id")
  private Set<Linea> linee;

  @OneToMany
  @JoinColumn(name = "azienda_id")
  private Set<DatiCorsa> datiCorse;

  @OneToMany
  @JoinColumn(name = "azienda_id")
  private Set<ProgrammaCorse> programmaCorse;


  public Set<DatiCorsa> getDatiCorse() {
    return datiCorse;
  }

  public void setDatiCorse(Set<DatiCorsa> datiCorse) {
    this.datiCorse = datiCorse;
  }

  public Set<Conducente> getConducenti() {
    return conducenti;
  }

  public void setConducenti(Set<Conducente> conducenti) {
    this.conducenti = conducenti;
  }

  public Set<Mezzo> getMezzi() {
    return mezzi;
  }

  public void setMezzi(Set<Mezzo> mezzi) {
    this.mezzi = mezzi;
  }

  public Set<Linea> getLinee() {
    return linee;
  }

  public void setLinee(Set<Linea> linee) {
    this.linee = linee;
  }

  public Profilo getProfilo() {
    return profilo;
  }

  public void setProfilo(Profilo profilo) {
    this.profilo = profilo;
  }

  public DettaglioUtente getDettaglio() {
    return dettaglio;
  }

  public void setDettaglio(DettaglioUtente dettaglio) {
    this.dettaglio = dettaglio;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


}
