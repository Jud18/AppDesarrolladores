package com.admin.proyectos.appdesarrolladores;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FragSelect extends BaseVolleyFragment implements AdapterView.OnItemClickListener {

    TextView txtFolio;
    TextView txtName;
    TextView txtEmp;
    TextView txtRemp;
    ListView listTec;
    ListView listExt;
    ListView listComp;

    DBHelper mydb;
    ListPeticionItem peticion;

    public FragSelect() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mydb = new DBHelper(this.getActivity());
        getPeticion();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_select, container, false);

        txtFolio = (TextView) view.findViewById(R.id.txtFolio);
        txtName = (TextView) view.findViewById(R.id.txtNom);
        txtEmp = (TextView) view.findViewById(R.id.txtNomEmp);
        txtRemp = (TextView) view.findViewById(R.id.txtRepEmp);
        listTec = (ListView) view.findViewById(R.id.listTec);
        listExt = (ListView) view.findViewById(R.id.listExt);
        listComp = (ListView) view.findViewById(R.id.listComp);


        fillData();
        return view;
    }

    public void getPeticion(){
        try {
            peticion = mydb.getPeticionActiva();

        }catch (JSONException e){
            Log.e("JSON", e.getMessage());
        }
    }

    public void fillData(){
        txtFolio.setText(peticion.getFolio());
        txtName.setText(peticion.getNombre());
        txtEmp.setText(peticion.getNomEmpresa());
        txtRemp.setText(peticion.getRepEmpresa());

        ArrayList<String> tecno = new Gson().fromJson(peticion.getTecnologias().toString(), new TypeToken<List<String>>(){}.getType());
        ArrayAdapter<String> texAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, tecno);
        listTec.setAdapter(texAdapter);

        ArrayList<String> ext = new Gson().fromJson(peticion.getExtras().toString(), new TypeToken<List<String>>(){}.getType());
        ArrayAdapter<String> extAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, ext);
        listExt.setAdapter(extAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
