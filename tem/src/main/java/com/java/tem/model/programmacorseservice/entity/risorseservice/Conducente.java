package com.java.tem.model.programmacorseservice.entity.risorseservice;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.java.tem.model.programmacorseservice.entity.Corsa;

@Entity
@Table(name = "Conducente")
public class Conducente extends Risorsa {
	
	@Id
	private long id;
	
	private String nome;
	private String cognome;
	private String codiceFiscale;
	
	@ManyToMany(mappedBy = "conducenti")
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
