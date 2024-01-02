package com.example.sessionmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DataUsuario extends AppCompatActivity {


    private static final String SITIO = "practicaMovil";
    private static final String IP = "192.168.1.7";
    private static final String url = "http://" + IP + "/" + SITIO + "/modificar.php";

    EditText editTextTextIdentificacion;
    EditText editTextTextPersonNombre;
    EditText editTextTextPersonApellido;
    EditText editTextTextPerfil;

    Button buttonGuardar;
    RequestQueue requestQueue;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            alertOneButton();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_usuario);
        editTextTextIdentificacion = (EditText) findViewById(R.id.editTextTextIdentificacion);
        editTextTextPersonNombre = (EditText) findViewById(R.id.editTextTextPersonNombre);
        editTextTextPersonApellido = (EditText) findViewById(R.id.editTextTextPersonApellido);
        editTextTextPerfil = (EditText) findViewById(R.id.editTextTextPerfil);
        buttonGuardar = (Button) findViewById(R.id.buttonGuardar);
        editTextTextIdentificacion.setText(getIntent().getStringExtra("identificacion"));
        editTextTextPersonNombre.setText(getIntent().getStringExtra("nombre"));
        editTextTextPersonApellido.setText(getIntent().getStringExtra("apellido"));

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String identificacion = editTextTextIdentificacion.getText().toString();
                String nombre = editTextTextPersonNombre.getText().toString();
                String apellido = editTextTextPersonApellido.getText().toString();
                updateUsuario(url + "?cidentificacion=" + identificacion + "&cnombre=" + nombre + "&capellido=" + apellido, identificacion, nombre, apellido);
            }
        });
    }

    public void updateUsuario(String url, String indentificacion, String nombre, String apellido){
        StringRequest stringRequest = new StringRequest(url, response -> {
            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
        }, error -> {
            Toast.makeText(getApplicationContext(), "ERROR DE CONEXION", Toast.LENGTH_SHORT).show();
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //Método que permite confirmar el cierre o no de sesión
    public void alertOneButton() {
        new AlertDialog.Builder(DataUsuario.this)
                .setTitle("Cerrar Sesíón")
                .setMessage("Está seguro de salir...?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @TargetApi(11) public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @TargetApi(11) public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }
}