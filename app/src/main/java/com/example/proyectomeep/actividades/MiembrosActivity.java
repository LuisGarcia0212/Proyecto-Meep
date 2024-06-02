package com.example.proyectomeep.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.proyectomeep.R;
import com.example.proyectomeep.clases.Usuario;

public class MiembrosActivity extends AppCompatActivity implements View.OnClickListener{

    Usuario usuario;
    ImageView btnCerrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miembros);
        btnCerrar = findViewById(R.id.btnCerrar);
        usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        btnCerrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCerrar){
            volver();
        }
    }

    private void volver() {
        Intent iBienvenida = new Intent(getApplicationContext(), BienvenidaActivity.class);
        iBienvenida.putExtra("usuario", usuario);
        startActivity(iBienvenida);
        finish();
    }

}