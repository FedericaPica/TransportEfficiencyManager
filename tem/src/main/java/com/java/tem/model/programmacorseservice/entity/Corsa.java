package com.java.tem.model.programmacorseservice.entity;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.java.tem.model.programmacorseservice.entity.risorseservice.Conducente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Linea;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Mezzo;

import javax.persistence.JoinColumn;

@Entity
@Table(name = "Corsa")
public class Corsa {
	
	@Id
	private long id;
	private Time orario;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "Conducente_Corsa", 
			   joinColumns = {@JoinColumn(name = "corsa_id")}, 
			   inverseJoinColumns = {@JoinColumn(name = "conducente_id")})
	private Set<Conducente> conducenti = new HashSet<>(); 
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "Mezzo_Corsa", 
			   joinColumns = {@JoinColumn(name = "corsa_id")}, 
			   inverseJoinColumns = {@JoinColumn(name = "mezzo_id")})
	private Set<Mezzo> mezzi = new HashSet<>(); 
	
	@ManyToOne
	@JoinColumn(name = "linea_id", nullable = false)
	private Linea linea;
	
	
	public Set<Mezzo> getMezzi() {
		return mezzi;
	}
	public void setMezzi(Set<Mezzo> mezzi) {
		this.mezzi = mezzi;
	}
	public Set<Conducente> getConducenti() {
		return conducenti;
	}
	public void setConducenti(Set<Conducente> conducenti) {
		this.conducenti = conducenti;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Time getOrario() {
		return orario;
	}
	public void setOrario(Time orario) {
		this.orario = orario;
	}
	
	

	
	
}
