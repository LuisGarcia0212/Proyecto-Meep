package com.example.proyectomeep.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyectomeep.R;
import com.example.proyectomeep.fragmentos.BienvenidaFragment;

public class MapaActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        btnAceptar = findViewById(R.id.lblAceptar);

        btnAceptar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.lblAceptar){
            Bienvenida();
        }
    }

    private void Bienvenida() {
        Intent iBienvenidaFrag = new Intent(this, BienvenidaActivity.class);
        startActivity(iBienvenidaFrag);
        finish();
    }
}