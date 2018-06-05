package com.admin.proyectos.appdesarrolladores;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Home extends BaseVolleyActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //SQLiteDatabase mydatabase = openOrCreateDatabase("appdesarrollo",MODE_PRIVATE,null);
        mydb = new DBHelper(this);
        makeRequest();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);


        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#6cbcdc"));
        tabLayout.setTabTextColors(Color.parseColor("#8788a9"), Color.parseColor("#FFFFFF"));

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragPeticiones(), "REQUESTS");
        adapter.addFragment(new FragSelect(), "WORK");
        adapter.addFragment(new FragPeticiones(), "MORE");
        viewPager.setAdapter(adapter);
    }

    private void makeRequest(){

        SharedPreferences preferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String user = preferences.getString("userKey", "");

        String url = "https://adminproyectosapi.azurewebsites.net/api/Todo/" + user + "/Peticion";

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject data = response.getJSONObject(0);
                    JSONArray comp = data.getJSONArray("componentes");

                    insertData(data, comp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onConnectionFinished();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onConnectionFailed("Error: " + volleyError.toString());
            }
        });
        addToQueue(request);
    }

    private void insertData(JSONObject data, JSONArray comp) throws JSONException{

        if(mydb.insertPeticion(data)){
            //Log.e("DATABASE", "INSERT");
            for(int k=0; k<comp.length(); k++){
                mydb.insertComponente(data.getString("folio"), comp.getJSONObject(k));
                //Log.e("DATABASE", "INSERT COMP");
            }
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
        else{
            Log.e("DATABASE", "ERROR");
        }
    }

}