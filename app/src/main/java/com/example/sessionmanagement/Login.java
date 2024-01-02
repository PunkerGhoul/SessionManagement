package com.example.sessionmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private static final String SITIO = "practicaMovil";
    private static final String IP = "192.168.1.7";
    private static final String url = "http://" + IP + "/" + SITIO + "/consulta_login.php";

    EditText editTextPersonName;
    EditText editTextPassword;
    Button buttonLogin;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextPersonName = (EditText) findViewById(R.id.editTextTextPersonName);
        editTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = editTextPersonName.getText().toString();
                String pwd = editTextPassword.getText().toString();
                loginUsuario(url + "?cusuario=" + usuario + "&cclave=" + pwd, usuario, pwd);
            }
        });
    }

    public void loginUsuario(String url, String usuario, String pwd){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, response -> {
            JSONObject jsonObject;
            for (int i = 0; i < response.length(); i++) {
                try { jsonObject = response.getJSONObject(i);
                    if(usuario.equals(jsonObject.getString("nombre")) && pwd.equals(jsonObject.getString("identificacion"))){
                        Intent j = new Intent(Login.this, DataUsuario.class);
                        j.putExtra("identificacion", jsonObject.getString("identificacion"));
                        j.putExtra("nombre", jsonObject.getString("nombre"));
                        j.putExtra("apellido", jsonObject.getString("apellido"));
                        startActivity(j);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, error -> Toast.makeText(getApplicationContext(), "ERROR DE CONEXION", Toast.LENGTH_SHORT).show() );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}