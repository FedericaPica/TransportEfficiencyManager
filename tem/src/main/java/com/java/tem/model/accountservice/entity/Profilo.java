package com.java.tem.model.accountservice.entity;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Profilo")
public class Profilo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "profilo")
  private Set<Utente> utenti;

  public Set<Utente> getUtenti() {
    return utenti;
  }

  public void setUtenti(Set<Utente> utenti) {
    this.utenti = utenti;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNomeProfilo() {
    return nomeProfilo;
  }

  public void setNomeProfilo(String nomeProfilo) {
    this.nomeProfilo = nomeProfilo;
  }

  private String nomeProfilo;
}

