package com.java.tem.model.accountservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name = "Dettaglio_Utente")
public class DettaglioUtente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "denominazione", nullable = false, length = 20)
	private String denominazione;
	
	@Column(name = "partitaIVA", nullable = false, length = 11)
	private String partitaIVA;

	@Column(name = "telefono", nullable = false, length = 10)
	private String telefono;
	
	@Column(name = "fax", nullable = false, length = 10)
	private String fax;
	
	@Column(name = "indirizzo", nullable = false, length = 25)
	private String indirizzo;
	
	@Column(name = "cap", nullable = false, length = 10)
	private String cap;
	
	@Column(name = "citta", nullable = false, length = 30)
	private String citta;
	
	@OneToOne(mappedBy = "dettaglio")
	private Utente utente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getPartitaIVA() {
		return partitaIVA;
	}

	public void setPartitaIVA(String partitaIVA) {
		this.partitaIVA = partitaIVA;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}
}
