package com.example.proyectomeep.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomeep.R;

import java.util.Locale;

public class InicionSesionMeepActivity extends AppCompatActivity implements View.OnClickListener{
    Spinner spinner;
    TextView lblRegistro,lblRecuperacion,lblRestablecer ;

    EditText txtUser, txtPsw;

    Button btnIniciarSesion;


    public static final String[] languages = {"Selecciona el lenguaje","Español","Ingles"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicion_sesion_meep);

        lblRegistro = findViewById(R.id.logLblRegistro1);
        txtUser = findViewById(R.id.logTxtUser);
        txtPsw = findViewById(R.id.logTxtClave);
        btnIniciarSesion = findViewById(R.id.logBtnIniciar);
        lblRestablecer = findViewById(R.id.logLblRestablecer1);

        btnIniciarSesion.setOnClickListener(this);
        lblRegistro.setOnClickListener(this);
        lblRestablecer.setOnClickListener(this);



        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLang = parent.getItemAtPosition(position).toString();
                if (selectedLang.equals("Español")){
                    setLocal(InicionSesionMeepActivity.this,"es");
                    finish();
                    startActivity(getIntent());
                } else if (selectedLang.equals("Ingles")) {
                    setLocal(InicionSesionMeepActivity.this,"en");
                    finish();
                    startActivity(getIntent());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void setLocal(Activity activity,String langCode){
        Locale locale= new Locale(langCode);
        locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config =resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config,resources.getDisplayMetrics());
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.logBtnIniciar){
            iniciarSesion(txtUser.getText().toString().trim(), txtPsw.getText().toString());
        } else if (v.getId() == R.id.logLblRegistro1) {
            ingresarRegistro();
        }else if (v.getId() == R.id.logLblRestablecer1) {
            ingresarRestablecer();
    }
    }



    private void ingresarRegistro(){
        Intent iRegistro = new Intent(this, RegistroActivity.class);
        startActivity(iRegistro);
        finish();
    }

    private void iniciarSesion(String user, String psw){
        if(user.equals("psyduck") && psw.equals("123456")) {
            Intent iBienvenida = new Intent(this, BienvenidaActivity.class);
            startActivity(iBienvenida);
            finish();
        }else {
            Toast.makeText(this, "Usuario o contraseña incorrecta",
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void ingresarRestablecer() {
        Intent iRestablecer = new Intent(this, RestablecerContrasenhaActivity.class);
        startActivity(iRestablecer);
        finish();
    }
    }
