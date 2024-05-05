package com.example.proyectomeep.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.proyectomeep.R;

public class ConfigActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView backCuenta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        backCuenta = findViewById(R.id.backCuenta);

        backCuenta.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.backCuenta){
            volver();
        }
    }

    private void volver() {
        Intent iBienvenida = new Intent(this, BienvenidaActivity.class);
        startActivity(iBienvenida);
        finish();
    }
}