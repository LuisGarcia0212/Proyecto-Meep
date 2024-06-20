package com.example.proyectomeep.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectomeep.R;
import com.example.veterinaria.clases.Hash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.nio.channels.AsynchronousChannel;

import cz.msebera.android.httpclient.Header;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    //Para la BD
    private String urlAgregarUsuario = "http://meep.atwebpages.com/services/agregarUsuario.php";

    Button btnRegistro;

    EditText txtNombres, txtUsuario, txtClave, txtEmail, txtTelefono, txtDireccion;

    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //Para insertar los datos en la BD
        txtNombres = findViewById(R.id.logTxtNombres);
        txtUsuario = findViewById(R.id.logTxtUsuario);
        txtClave = findViewById(R.id.logTxtClave);
        txtEmail = findViewById(R.id.logTxtEmail);
        txtTelefono = findViewById(R.id.logTxtTelefono);
        txtDireccion = findViewById(R.id.logTxtDirección);

        btnRegistro = findViewById(R.id.logBtnRegistrar);
        btnRegistro.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.logBtnRegistrar){
            registrarUsuario();
        }
    }

    private void registrarUsuario() {
        AsyncHttpClient ahcregistrarU = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        Hash hash = new Hash();
        if(validarFormulario()){
            params.add("NombresCompletos", txtNombres.getText().toString());
            params.add("Username", txtUsuario.getText().toString());
            params.add("Contraseña", hash.StringToHash(txtClave.getText().toString(), "SHA256"));
            params.add("Email", txtEmail.getText().toString());
            params.add("Telefono", txtTelefono.getText().toString());
            params.add("Direccion", txtDireccion.getText().toString());
            params.add("Descripcion", "-");
            params.add("Foto", "-");

            ahcregistrarU.post(urlAgregarUsuario, params, new BaseJsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                    if (statusCode == 200){
                        int retVal = rawJsonResponse.length() == 0 ? 0 : Integer.parseInt(rawJsonResponse);
                        if (retVal == 1){
                            Toast.makeText(getApplicationContext(), "Usuario Registrado Correctamente", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent iSesion = new Intent(getApplicationContext(), InicionSesionMeepActivity.class);
                            startActivity(iSesion);
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                    Toast.makeText(getApplicationContext(), "Error: "+statusCode, Toast.LENGTH_SHORT).show();
                }

                @Override
                protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                    return null;
                }
            });
        }else{
            Toast.makeText(this, "Complete el Formulario", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarFormulario() {
        if(txtNombres.getText().toString().isEmpty() || txtUsuario.getText().toString().isEmpty() ||
                txtEmail.getText().toString().isEmpty() || txtClave.getText().toString().isEmpty() ||
                txtTelefono.getText().toString().isEmpty() || txtDireccion.getText().toString().isEmpty())
            return false;
        return true;
    }
}