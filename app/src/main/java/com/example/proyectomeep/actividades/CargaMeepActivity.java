package com.example.proyectomeep.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.proyectomeep.R;

public class CargaMeepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_meep);

        Thread tcarga = new Thread(){
            @Override
            public void run(){
                  super.run();
                   try {
                       sleep(4000);
                   }catch (InterruptedException e){
                       throw new RuntimeException(e);
                   }
                   finally {
                       Intent iSesion = new Intent(getApplicationContext(), InicionSesionMeepActivity.class);

                       startActivity(iSesion);

                       finish();
                   }
            }
        };
        tcarga.start();
    }
}