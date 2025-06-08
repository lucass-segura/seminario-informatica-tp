package com.sinipro.model;

public class Asegurado {
    private int id;
    private String nombre;
    private String dni;
    private String contacto;

    public int getId() { 
        return id; 
    }
    public void setId(int id) { 
        this.id = id; 
    }
    public String getNombre() { 
        return nombre; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    public String getDni() { 
        return dni; 
    }
    public void setDni(String dni) { 
        this.dni = dni; 
    }
    public String getContacto() { 
        return contacto; 
    }
    public void setContacto(String contacto) { 
        this.contacto = contacto; 
    }

    @Override
    public String toString() {
        return this.nombre + " (" + this.dni + ")";
    }
}