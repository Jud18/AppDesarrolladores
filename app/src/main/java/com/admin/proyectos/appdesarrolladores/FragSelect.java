package com.admin.proyectos.appdesarrolladores;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FragSelect extends BaseVolleyFragment implements AdapterView.OnItemClickListener {

    TextView txtFolio;
    TextView txtName;
    TextView txtEmp;
    TextView txtRemp;
    ListView listTec;
    ListView listExt;
    ListView listComp;

    Button Estimar;

    Button Update;
    EditText edTime;
    Componente comActual;

    DBHelper mydb;
    ListPeticionItem peticion;
    ArrayList<Componente> com;

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

        Estimar = (Button) view.findViewById(R.id.btnEstimar);
        Estimar.setOnClickListener(estimar_button);

        fillData();
        listComp.setOnItemClickListener(this);

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

        com = new Gson().fromJson(peticion.getComponentes().toString(), new TypeToken<List<Componente>>(){}.getType());
        AdapterComponente comAdapter = new AdapterComponente(this.getActivity(), com);
        listComp.setAdapter(comAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), com.get(position).getNombre(), Toast.LENGTH_SHORT).show();
        comActual = com.get(position);
        showPopup();
    }

    private PopupWindow pw;
    private void showPopup() {
        try {
            LayoutInflater inflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_componente, (ViewGroup) getView().findViewById(R.id.upComp));
            pw = new PopupWindow(layout, 1000, 1500, true);
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
            Update = (Button) layout.findViewById(R.id.btnUpdate);
            Update.setOnClickListener(update_button);

            edTime = (EditText) layout.findViewById(R.id.txtTiempo);
            TextView comp = (TextView) layout.findViewById(R.id.txtComp);
            comp.setText(comActual.getNombre());
            //Close = (Button) layout.findViewById(R.id.close_popup);
            //Close.setOnClickListener(cancel_button);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener update_button = new View.OnClickListener() {
        public void onClick(View v) {
            double time = Double.valueOf(edTime.getText().toString());

            if(time > Double.valueOf(comActual.getMinTime()) && time < Double.valueOf(comActual.getMaxTime()))
                if(mydb.updateTiem(comActual.getFolio(), comActual.getId(), Double.toString(time)))
                    pw.dismiss();
            else
                Toast.makeText(getActivity(), "Ingrese un tiempo valido", Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener estimar_button = new View.OnClickListener() {
        public void onClick(View v) {
            new AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Estimación de tiempos")
                    .setMessage("¿Estas seguro de realizar la estimación de tiempo?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String url = "https://adminproyectosapi.azurewebsites.net/api/Todo/" + peticion.getDesarrollador() + "/Peticion/"+peticion.getFolio();
                            final String parametros = mydb.getTiempos(peticion.getFolio()).toString();

                            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                                    new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getActivity(), "Estimación realizada correctamente.", Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getActivity(), "Estimación realizada correctamente.", Toast.LENGTH_SHORT).show();
                                }
                            }){
                                @Override
                                public String getBodyContentType() {
                                    return "application/json; charset=utf-8";
                                }

                                @Override
                                public byte[] getBody() throws AuthFailureError {
                                    try {
                                        return parametros == null ? null : parametros.getBytes("utf-8");
                                    } catch (UnsupportedEncodingException uee) {
                                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", parametros, "utf-8");
                                        return null;
                                    }
                                }

                            };
                            stringRequest.setShouldCache(false);
                            addToQueue(stringRequest);
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    };


}
