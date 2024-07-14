package com.example.proyectomeep.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyectomeep.R;

public class RecuperacionActivity extends AppCompatActivity implements View.OnClickListener {
    TextView lblVerificar;
    ImageView imgVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacion);
        lblVerificar = findViewById(R.id.lblVeri);
        imgVolver = findViewById(R.id.logVolverLogin);
        lblVerificar.setOnClickListener(this);
        imgVolver.setOnClickListener(this);
    }

    public void onClick(View v) {
        if(v.getId() == R.id.lblVeri){
            iniciarVerificar();
        } else if (v.getId() == R.id.logVolverLogin) {
            volverCodigo();
        }
    }

    private void volverCodigo() {
        Intent iCodigo = new Intent(this, RestablecerContrasenhaActivity.class);
        startActivity(iCodigo);
        finish();
    }

    private void iniciarVerificar() {
        Intent iBienvenida = new Intent(this, InicionSesionMeepActivity.class);
        startActivity(iBienvenida);
        finish();
    }
}