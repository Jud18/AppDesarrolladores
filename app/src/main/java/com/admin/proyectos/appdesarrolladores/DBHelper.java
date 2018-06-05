package com.admin.proyectos.appdesarrolladores;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "AppDesarrollo.db";
    public static final String PETICION_TABLE_NAME = "peticiones";
    public static final String COMPONENTE_TABLE_NAME = "componentes";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table peticiones" +
                        "(folio string primary key, nombre string, empresa string, representante string, desarrollador string, estatus boolean, tecnologias string,extras string)"
        );

        db.execSQL(
                "create table componentes" +
                        "(folio string, id string, nombre string,tecnologia string,min string, max string,estimado string," +
                        "PRIMARY KEY (folio, id)," +
                        "FOREIGN KEY (folio) REFERENCES peticiones(folio)" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS peticiones");
        db.execSQL("DROP TABLE IF EXISTS componentes");
        onCreate(db);
    }

    public boolean insertPeticion(JSONObject data) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String folio= data.getString("folio");
            String nom  = data.getString("nombre");
            String emp  = data.getString("empresaNombre");
            String rep  = data.getString("repEmpresa");
            String des  = data.getString("desarrollador");
            String est  = data.getString("estaTerminado");
            String tec  = data.getString("tecnologias");
            String ext  = data.getString("extras");

            db.execSQL("INSERT OR REPLACE INTO peticiones VALUES" +
                    "('"+folio+"','"+nom+"','"+emp+"',\'"+rep+"','"+des+"','"+est+"','"+tec+"','"+ext+"')");

            return true;
        }
        catch (JSONException e){
            return false;
        }
    }

    public boolean insertComponente(String folio, JSONObject componente) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String id   = componente.getString("id");
            String nom  = componente.getString("nombre");
            String tec  = componente.getString("tecnologia");
            String min  = componente.getString("min");
            String max  = componente.getString("max");
            String est  = componente.getString("estimado");

            db.execSQL("INSERT OR REPLACE INTO componentes VALUES" +
                    "('"+folio+"','"+id+"','"+nom+"','"+tec+"','"+min+"','"+max+"','"+est+"')");

            return true;
        }
        catch (JSONException e){
            return false;
        }
    }

    public Cursor getPeticion(int folio){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from peticiones where folio="+folio+"", null );
        return res;
    }

    public Cursor getComponente(int folio) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from componentes where folio="+folio+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, COMPONENTE_TABLE_NAME);
        return numRows;
    }

    public ListPeticionItem getPeticionActiva() throws JSONException{
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from peticiones where estatus='true'", null );
        res.moveToFirst();

        String folio = res.getString(0);

        Log.e("FOLIO: ", folio);
        JSONObject data = new JSONObject();

        data.put("folio",   folio);
        data.put("nombre",  res.getString(1));
        data.put("empresaNombre", res.getString(2));
        data.put("repEmpresa", res.getString(3));
        data.put("desarrollador", res.getString(4));
        data.put("estaTerminado",     res.getString(5));
        data.put("tecnologias", new JSONArray(res.getString(6)));
        data.put("extras",      new JSONArray(res.getString(7)));

        JSONArray componentes = new JSONArray();

        Cursor com = db.rawQuery("select * from componentes where folio="+folio+"",null);
        com.moveToFirst();
        JSONObject comp;

        while(com.isAfterLast() == false){
            comp = new JSONObject();
            comp.put("id", com.getString(0));
            comp.put("nombre", com.getString(1));
            comp.put("tecnologia", com.getString(2));
            comp.put("min", com.getString(3));
            comp.put("max", com.getString(4));
            comp.put("estimado", com.getString(5));
            componentes.put(comp);
            com.moveToNext();
        }

        return new ListPeticionItem(data, componentes);
    }

    public ArrayList<ListPeticionItem> getFolioPeticion(String folio) {
        ArrayList<ListPeticionItem> array_list = new ArrayList<ListPeticionItem>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from peticiones where folio="+folio+"", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            //array_list.add(new ListPeticionItem(res.))
            //array_list.add(res.getString(res.getColumnIndex(COMPONENTE_TABLE_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}
