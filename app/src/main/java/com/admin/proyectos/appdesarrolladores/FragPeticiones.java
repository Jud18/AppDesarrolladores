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

    DBHelper mydb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_peticiones, container, false);
        listView = (ListView) view.findViewById(R.id.contact_view);
        mydb = new DBHelper(this.getActivity());
        makeList();
        return view;
    }

    public void makeList(){

        try {
            ListPeticionItem peticiones = mydb.getPeticionActiva();

            contactPeticion = new ArrayList<ListPeticionItem>();

            contactPeticion.add(peticiones);
            listView.setAdapter(new ListviewPeticionAdapter(getActivity(), contactPeticion));

            // ListView on item selected listener.
            listView.setOnItemClickListener(this);
        }catch (JSONException e){
            Log.e("JSON", e.getMessage());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), contactPeticion.get(position).getNombre().toString(), Toast.LENGTH_SHORT).show();

    }
}