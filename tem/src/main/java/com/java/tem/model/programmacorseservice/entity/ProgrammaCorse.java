package com.java.tem.model.programmacorseservice.entity;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.java.tem.model.accountservice.entity.Utente;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "programmacorse")
public class ProgrammaCorse {
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;

	  private Date inizioValidita;
	  private Date fineValidita;

	  @OneToMany(mappedBy = "programma")
	  @Cascade({CascadeType.REMOVE})
	  private Set<Corsa> listaCorse;
	  
	  @ManyToOne
	  @JoinColumn(name = "azienda_id", nullable = false)
	  private Utente azienda;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getInizioValidita() {
		return inizioValidita;
	}

	public void setInizioValidita(Date inizioValidita) {
		this.inizioValidita = inizioValidita;
	}

	public Date getFineValidita() {
		return fineValidita;
	}

	public void setFineValidita(Date fineValidita) {
		this.fineValidita = fineValidita;
	}

	public Set<Corsa> getListaCorse() {
		return listaCorse;
	}

	public void setListaCorse(Set<Corsa> listaCorse) {
		this.listaCorse = listaCorse;
	}

	public Utente getAzienda() {
		return azienda;
	}

	public void setAzienda(Utente azienda) {
		this.azienda = azienda;
	}
	  

}
