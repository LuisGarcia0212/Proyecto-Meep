package com.example.proyectomeep.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyectomeep.R;

public class CodigoRecuperaActivity extends AppCompatActivity implements View.OnClickListener {
    TextView lblVerificar;
    ImageView imgVolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_recupera);
        lblVerificar = findViewById(R.id.lblVeri);
        imgVolver = findViewById(R.id.logVolverLogin);
        lblVerificar.setOnClickListener(this);
        imgVolver.setOnClickListener(this);
    }
    public void onClick(View v) {
        if(v.getId() == R.id.lblVeri){
            iniciarVerificar();
        } else if (v.getId() == R.id.logVolverLogin) {
            volverRecuperar();
        }
    }

    private void volverRecuperar() {
        Intent iRecuperar = new Intent(this, RestablecerContrasenhaActivity.class);
        startActivity(iRecuperar);
        finish();
    }

    private void iniciarVerificar() {
        Intent iBienvenida = new Intent(this, RecuperacionActivity.class);
        startActivity(iBienvenida);
        finish();
    }
}