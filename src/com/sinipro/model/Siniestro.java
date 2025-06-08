package com.sinipro.model;

import java.util.Date;
import java.util.List;

public class Siniestro {
    private int id;
    private String numeroSiniestro;
    private Date fecha;
    private String estado;
    private String descripcion;
    private Asegurado asegurado;
    private Compania compania;
    private List<SiniestroNota> notas;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNumeroSiniestro() {
        return numeroSiniestro;
    }
    public void setNumeroSiniestro(String numeroSiniestro) {
        this.numeroSiniestro = numeroSiniestro;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Asegurado getAsegurado() {
        return asegurado;
    }
    public void setAsegurado(Asegurado asegurado) {
        this.asegurado = asegurado;
    }
    public Compania getCompania() {
        return compania;
    }
    public void setCompania(Compania compania) {
        this.compania = compania;
    }
    public List<SiniestroNota> getNotas() {
        return notas;
    }
    public void setNotas(List<SiniestroNota> notas) {
        this.notas = notas;
    }
}