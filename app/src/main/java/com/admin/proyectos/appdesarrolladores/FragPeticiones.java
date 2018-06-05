package com.admin.proyectos.appdesarrolladores;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragPeticiones extends BaseVolleyFragment implements AdapterView.OnItemClickListener{

    ListView listView;
    ArrayList<ListPeticionItem> contactPeticion;

    public FragPeticiones() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_peticiones, container, false);

        makeRequest();
        listView = (ListView) view.findViewById(R.id.contact_view);

        return view;
    }


    private void makeRequest(){

        SharedPreferences preferences = this.getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String user = preferences.getString("userKey", "");
        //Log.e("ERROR", "User: " + user);
        String url = "https://adminproyectosapi.azurewebsites.net/api/Todo/" + user + "/Peticion";
        //Log.e("ERROR", "URL: " + url);
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject obj = response.getJSONObject(0);
                    JSONArray comp = obj.getJSONArray("componentes");

                    makeList(obj, comp);
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

    public void makeList(JSONObject datos, JSONArray comp){
        ListPeticionItem peticiones = new ListPeticionItem(datos, comp);
        contactPeticion = new ArrayList<ListPeticionItem>();

        contactPeticion.add(peticiones);
        listView.setAdapter(new ListviewPeticionAdapter(getActivity(), contactPeticion));

        // ListView on item selected listener.
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), contactPeticion.get(position).getNombre().toString(), Toast.LENGTH_SHORT).show();
    }
}