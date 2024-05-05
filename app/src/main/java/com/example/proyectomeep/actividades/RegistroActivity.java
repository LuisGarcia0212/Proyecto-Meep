package com.example.proyectomeep.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.proyectomeep.R;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnRegistro = findViewById(R.id.logBtnRegistrar);
        btnRegistro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.logBtnRegistrar){
            Intent iSesion = new Intent(this, InicionSesionMeepActivity.class);
            startActivity(iSesion);
            finish();
        }
    }
}