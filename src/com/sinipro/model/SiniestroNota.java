package com.sinipro.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SiniestroNota {
    private int id;
    private int siniestroId;
    private String nota;
    private Date fechaNota;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getSiniestroId() {
        return siniestroId;
    }
    public void setSiniestroId(int siniestroId) {
        this.siniestroId = siniestroId;
    }
    public String getNota() {
        return nota;
    }
    public void setNota(String nota) {
        this.nota = nota;
    }
    public Date getFechaNota() {
        return fechaNota;
    }
    public void setFechaNota(Date fechaNota) {
        this.fechaNota = fechaNota;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(fechaNota) + " - " + nota;
    }
}