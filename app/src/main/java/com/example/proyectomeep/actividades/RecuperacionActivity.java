package com.example.proyectomeep.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.proyectomeep.R;

public class RecuperacionActivity extends AppCompatActivity implements View.OnClickListener {
    TextView lblVerificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacion);
        lblVerificar = findViewById(R.id.lblVeri);
        lblVerificar.setOnClickListener(this);
    }

    public void onClick(View v) {
        if(v.getId() == R.id.lblVeri){
            iniciarVerificar();
        }
    }

    private void iniciarVerificar() {
        Intent iBienvenida = new Intent(this, InicionSesionMeepActivity.class);
        startActivity(iBienvenida);
        finish();
    }
}