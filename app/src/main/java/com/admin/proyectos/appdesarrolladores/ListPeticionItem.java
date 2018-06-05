package com.admin.proyectos.appdesarrolladores;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListPeticionItem {

    private String folio;
    private String nombre;
    private String nomEmpresa;
    private String repEmpresa;
    private String desarrollador;
    private Boolean estaTerminado;
    private JSONArray tecnologias;
    private JSONArray extras;
    private JSONArray componentes;

    public ListPeticionItem(JSONObject datos, JSONArray comp){
        try {
            this.folio = datos.getString("folio");
            this.nombre = datos.getString("nombre");
            this.nomEmpresa = datos.getString("empresaNombre");
            this.repEmpresa = datos.getString("repEmpresa");
            this.desarrollador = datos.getString("desarrollador");
            this.estaTerminado = datos.getBoolean("estaTerminado");
            this.tecnologias = datos.getJSONArray("tecnologias");
            this.extras = datos.getJSONArray("extras");
            this.componentes = comp;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getFolio(){
        return folio;
    }
    public String getNombre(){
        return nombre;
    }
    public String getNomEmpresa() {
        return nomEmpresa;
    }
    public String getRepEmpresa() {
        return repEmpresa;
    }
    public String getDesarrollador() {
        return desarrollador;
    }
    public JSONArray getTecnologias() {
        return tecnologias;
    }
    public JSONArray getExtras() {
        return extras;
    }
    public JSONArray getComponentes() {
        return componentes;
    }

}
