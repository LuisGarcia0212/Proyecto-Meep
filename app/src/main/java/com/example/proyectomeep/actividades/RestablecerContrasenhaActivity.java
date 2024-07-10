package com.example.proyectomeep.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyectomeep.R;

public class RestablecerContrasenhaActivity extends AppCompatActivity implements View.OnClickListener {
    TextView lblRecuperar;

    ImageView btnVolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer_contrasenha);
        lblRecuperar = findViewById(R.id.logLblRecuperar1);
        btnVolver = findViewById(R.id.logVolverLogin);

        btnVolver.setOnClickListener(this);
        lblRecuperar.setOnClickListener(this);
    }

    public void onClick(View v) {
        if(v.getId() == R.id.logLblRecuperar1){
            iniciarRecuperar();
        } else if (v.getId() == R.id.logVolverLogin) {
            volverLogin();
        }
    }

    private void volverLogin() {
        Intent iSesion = new Intent(getApplicationContext(), InicionSesionMeepActivity.class);
        startActivity(iSesion);
    }

    private void iniciarRecuperar() {
        Intent iBienvenida = new Intent(this, CodigoRecuperaActivity.class);
        startActivity(iBienvenida);
        finish();
    }
}