package com.admin.proyectos.appdesarrolladores;

public class Componente {

    private String folio;
    private String id;
    private String nombre;
    private String tecnologia;
    private String min;
    private String max;
    private String estimado;

    public Componente(String folio, String id, String nom, String tec, String min, String max, String est){
        this.folio = folio;
        this.id = id;
        this.nombre = nom;
        this.tecnologia = tec;
        this.min = min;
        this.max = max;
        this.estimado = est;
    }

    public String getFolio() {
        return folio;
    }
    public String getId(){
        return id;
    }
    public String getNombre(){
        return nombre;
    }
    public String getTecnologia(){
        return tecnologia;
    }
    public String getMinTime(){
        return min;
    }
    public String getMaxTime(){
        return max;
    }
    public String getEstimado(){
        return estimado;
    }

}
