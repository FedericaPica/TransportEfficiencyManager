package com.java.tem.model.programmacorseservice.entity;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ForeignKey;


@Entity
@Table(name = "Corsa")
public class Corsa {
	
	@Id
	private long id;
	private Time orario;
	private long linea_id;
	
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
