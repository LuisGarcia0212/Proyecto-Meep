package com.example.proyectomeep.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomeep.R;
import com.example.proyectomeep.SQLite.MEEP;
import com.example.proyectomeep.clases.Hash;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class RecuperacionActivity extends AppCompatActivity implements View.OnClickListener {
    TextView lblVerificar;
    ImageView imgVolver;
    EditText txtC1, txtC2;
    String email;
    final static String urlContrasena = "http://meep.atwebpages.com/services/actualizarContrasenha.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacion);
        email = getIntent().getStringExtra("email");
        lblVerificar = findViewById(R.id.lblVeri);
        imgVolver = findViewById(R.id.logVolverLogin);
        txtC1 = findViewById(R.id.logTxtContra1);
        txtC2 = findViewById(R.id.logTxtContra2);

        lblVerificar.setOnClickListener(this);
        imgVolver.setOnClickListener(this);
    }

    public void onClick(View v) {
        if(v.getId() == R.id.lblVeri){
            if (verficarContra()){
                return;
            }
            if (!verficarContrasenas()){
                Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }
            iniciarVerificar();
        } else if (v.getId() == R.id.logVolverLogin) {
            volverCodigo();
        }
    }

    private boolean verficarContrasenas() {
        if(txtC1.getText().toString().equals(txtC2.getText().toString()))
            return true;
        return false;
    }

    private boolean verficarContra() {
        if (txtC1.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Llene el campo de contraseña", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (txtC2.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Llene el campo de confirmar contraseña", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void volverCodigo() {
        Intent iCodigo = new Intent(this, RestablecerContrasenhaActivity.class);
        startActivity(iCodigo);
        finish();
    }

    private void iniciarVerificar() {
        Hash hash = new Hash();
        AsyncHttpClient ahcCambiarClave = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String psw;
        psw = hash.StringToHash(txtC1.getText().toString(), "SHA256");
        params.add("email", email);
        params.add("clave", psw);

        ahcCambiarClave.post(urlContrasena, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if (statusCode == 200){
                    int retVal = 0;
                    try {
                        retVal = rawJsonResponse.length() == 0 ? 0 : Integer.parseInt(rawJsonResponse);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException(e);
                    }
                    if (retVal == 1){
                        Toast.makeText(getApplicationContext(), "Se restablecio correctamente su contraseña", Toast.LENGTH_SHORT).show();
                        volverLogin();
                    }else {
                        Toast.makeText(getApplicationContext(), "Error al restablecer contraseña", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {

            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    public void volverLogin(){
        Intent iBienvenida = new Intent(this, InicionSesionMeepActivity.class);
        startActivity(iBienvenida);
        finish();
    }
}