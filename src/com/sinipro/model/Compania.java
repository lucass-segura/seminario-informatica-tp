package com.sinipro.model;

public class Compania {
    private int    id;
    private String nombre;

    public Compania(int id, String nombre) {
        this.id     = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}