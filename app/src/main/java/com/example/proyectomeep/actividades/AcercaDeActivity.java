package com.example.proyectomeep.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyectomeep.R;
import com.example.proyectomeep.clases.Usuario;

public class AcercaDeActivity extends AppCompatActivity implements View.OnClickListener{

    Usuario usuario;
    ImageView backCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);
        usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        backCount= findViewById(R.id.backCuenta);

        backCount.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.backCuenta){
            volver();
        }
    }

    private void volver(){
        Intent iBienvenida = new Intent(this, BienvenidaActivity.class);
        iBienvenida.putExtra("usuario", usuario);
        startActivity(iBienvenida);
        finish();
    }

}