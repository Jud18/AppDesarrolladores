package com.admin.proyectos.appdesarrolladores;

public class Componente {

    private String id;
    private String nombre;
    private String tecnologia;
    private String minTime;
    private String maxTime;
    private String estimado;

    public Componente(String id, String nom, String tec, String min, String max, String est){
        this.id = id;
        this.nombre = nom;
        this.tecnologia = tec;
        this.minTime = min;
        this.maxTime = max;
        this.estimado = est;
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
        return minTime;
    }
    public String getMaxTime(){
        return maxTime;
    }
    public String getEstimado(){
        return estimado;
    }

}
