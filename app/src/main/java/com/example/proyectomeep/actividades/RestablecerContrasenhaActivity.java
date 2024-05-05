package com.example.proyectomeep.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.proyectomeep.R;

public class RestablecerContrasenhaActivity extends AppCompatActivity implements View.OnClickListener {
    TextView lblRecuperar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer_contrasenha);
        lblRecuperar = findViewById(R.id.logLblRecuperar1);
        lblRecuperar.setOnClickListener(this);
    }

    public void onClick(View v) {
        if(v.getId() == R.id.logLblRecuperar1){
            iniciarRecuperar();
        }
    }

    private void iniciarRecuperar() {
        Intent iBienvenida = new Intent(this, CodigoRecuperaActivity.class);
        startActivity(iBienvenida);
        finish();
    }
}