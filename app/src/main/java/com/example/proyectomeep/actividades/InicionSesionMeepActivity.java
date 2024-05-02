package com.example.proyectomeep.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomeep.R;

public class InicionSesionMeepActivity extends AppCompatActivity implements View.OnClickListener{

    TextView lblRegistro;

    EditText txtUser, txtPsw;

    Button btnIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicion_sesion_meep);

        lblRegistro = findViewById(R.id.logLblRegistro1);
        txtUser = findViewById(R.id.logTxtUser);
        txtPsw = findViewById(R.id.logTxtClave);
        btnIniciarSesion = findViewById(R.id.logBtnIniciar);

        btnIniciarSesion.setOnClickListener(this);
        lblRegistro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.logBtnIniciar){
            iniciarSesion();
        } else if (v.getId() == R.id.logLblRegistro1) {
            ingresarRegistro();
        }
    }

    private void ingresarRegistro(){
        Intent iRegistro = new Intent(this, RegistroActivity.class);
        startActivity(iRegistro);
        finish();
    }

    private void iniciarSesion(){
        Intent iBienvenida = new Intent(this, BienvenidaActivity.class);
        startActivity(iBienvenida);
        finish();
    }
}