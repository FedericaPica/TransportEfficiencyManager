package com.java.tem.aimodule;


import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Time;
import java.time.LocalTime;

@Entity
@Immutable
@Table(name = "generazione_automatica")
public class DatiGenerazione {

    @Id
    private Long id;

    private String linea_corsa;

    private String traffico;

    private int attesi;

    private Long azienda_id;

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

    public String getLinea_corsa() {
        return linea_corsa;
    }

    public void setLinea_corsa(String linea_corsa) {
        this.linea_corsa = linea_corsa;
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

    public Long getAzienda_id() {
        return azienda_id;
    }

    public void setAzienda_id(Long azienda_id) {
        this.azienda_id = azienda_id;
    }

    public String toString() {

        return "[ " + this.linea_corsa + " " + this.traffico + " " + this.attesi + " " + this.orario + " " + this.conducente +
                " " + this.mezzo + " ]";

    }
}
