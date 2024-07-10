package com.example.proyectomeep.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.proyectomeep.R;
import com.example.proyectomeep.clases.Usuario;

public class AcercaDeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView infomas,infomas2,infomas3;
    private TextView lblAcercaDe;
    private LinearLayout lblFunda;
    private LinearLayout lblContac;
    private Usuario usuario;
    private ImageView backCount;

    private int Boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);

        // Obtener referencias a las vistas
        infomas = findViewById(R.id.infomas);
        infomas2 = findViewById(R.id.infomas2);
        infomas3 = findViewById(R.id.infomas3);
        lblAcercaDe = findViewById(R.id.lblAcercaDe);
        lblFunda = findViewById(R.id.lblFunda);
        lblContac = findViewById(R.id.lblContac);
        backCount = findViewById(R.id.backCuenta);
        lblAcercaDe.setVisibility(View.GONE);
        lblFunda.setVisibility(View.GONE);
        lblContac.setVisibility(View.GONE);

        Boton = getIntent().getIntExtra("idBoton", 0);


        // Configurar OnClickListener para infomas
        infomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambiar la visibilidad de lblAcercaDe
                if (lblAcercaDe.getVisibility() == View.VISIBLE) {
                    lblAcercaDe.setVisibility(View.GONE); // Ocultar si está visible
                } else {
                    lblAcercaDe.setVisibility(View.VISIBLE); // Mostrar si está oculto
                }
            }
        });

        infomas2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lblFunda.getVisibility() == View.GONE) {
                    lblFunda.setVisibility(View.VISIBLE);
                } else {
                    lblFunda.setVisibility(View.GONE);
                }
            }
        });

        infomas3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lblContac.getVisibility() == View.GONE) {
                    lblContac.setVisibility(View.VISIBLE);
                } else {
                    lblContac.setVisibility(View.GONE);
                }
            }
        });

        // Configurar OnClickListener para backCount
        backCount.setOnClickListener(this);

        // Obtener datos del usuario pasado desde la actividad anterior
        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backCuenta) {
            volver();
        }
    }

    private void volver() {
        Intent iBienvenida = new Intent(this, BienvenidaActivity.class);
        iBienvenida.putExtra("usuario", usuario);
        iBienvenida.putExtra("idBoton", Boton);
        System.out.println("Se esta enviando "+ Boton);
        startActivity(iBienvenida);
        finish();
    }
}

