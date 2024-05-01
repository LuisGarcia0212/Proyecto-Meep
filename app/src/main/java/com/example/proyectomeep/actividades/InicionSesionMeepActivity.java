package com.example.proyectomeep.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.proyectomeep.R;

public class InicionSesionMeepActivity extends AppCompatActivity implements View.OnClickListener{

    TextView lblRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicion_sesion_meep);

        lblRegistro = findViewById(R.id.logLblRegistro1);

        lblRegistro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.logLblRegistro1){
            ingresarRegistro();
        }
    }

    private void ingresarRegistro(){
        Intent iRegistro = new Intent(this, RegistroActivity.class);
        startActivity(iRegistro);
        finish();
    }
}