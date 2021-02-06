package com.java.tem.model.accountservice.entity;
 
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "azienda")
public class Utente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 45)
	private String email;
	
	@Column(name = "username", nullable = false, length = 20)
	private String username;
	
	@Column(nullable = false, length = 64)
	private String password;
	
	@ManyToOne()
	@JoinColumn(name = "id_profilo", nullable = false)
	private Profilo profilo;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_dettaglio", referencedColumnName = "id")
	private DettaglioUtente dettaglio;
	
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
