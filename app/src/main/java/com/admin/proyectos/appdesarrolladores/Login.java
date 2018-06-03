package com.admin.proyectos.appdesarrolladores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

public class Login extends BaseVolleyActivity implements View.OnClickListener{

    Button btnLogin;
    EditText txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    private void makeRequest(){
        String url = "https://adminproyectosapi.azurewebsites.net/api/Todo/" + txtEmail.getText() + "/Usuario";
        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                //txtEmail.setText("Response is: "+ response);
                onConnectionFinished();
                changeActivity(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //txtEmail.setText("That didn't work!");
                onConnectionFailed(volleyError.toString());
            }
        });
        addToQueue(request);
    }

    public void changeActivity(String response){
        boolean res = Boolean.parseBoolean(response);

        if(res){
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Nombre de usuario incorrecto", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        //Intent intent = new Intent(this, Home.class);
        //startActivity(intent);
        makeRequest();
    }
}
