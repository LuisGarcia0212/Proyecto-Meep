package com.example.proyectomeep.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

import com.example.proyectomeep.R;

import org.json.JSONException;
import org.json.JSONObject;
public class RestablecerContrasenhaActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtEmail;
    private String urlVerificarUsuario = "http://meep.atwebpages.com/services/VerificarU.php";
    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer_contrasenha);

        // Referenciar los elementos de la interfaz
        edtEmail = findViewById(R.id.logTxtEmail);
        ImageView btnVolver = findViewById(R.id.logVolverLogin);
        findViewById(R.id.logLblRecuperar1).setOnClickListener(this);
        btnVolver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logLblRecuperar1) {
            String email = edtEmail.getText().toString().trim();
            if (!email.isEmpty()) {
                // Verificar si el correo electrónico existe en la base de datos usando AsyncHttpClient
                verificarEmailEnBaseDeDatos(email);
            } else {
                Toast.makeText(this, "Por favor ingresa tu correo electrónico.", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.logVolverLogin) {
            volverLogin();
        }
    }

    private void verificarEmailEnBaseDeDatos(final String email) {
        RequestParams params = new RequestParams();
        params.put("email", email);

        asyncHttpClient.post(urlVerificarUsuario, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    String response = new String(responseBody);
                    try {
                        JSONObject json = new JSONObject(response);
                        boolean existe = json.getBoolean("existe");
                        if (existe) {
                            String codigo = RandomStringGenerator.generateRandomCode(5); // Generar código de 5 caracteres
                            enviarCorreoRecuperacion(email, codigo);
                            Toast.makeText(RestablecerContrasenhaActivity.this, "Se ha enviado un correo con instrucciones para restablecer tu contraseña.", Toast.LENGTH_SHORT).show();
                            iniciarRecuperar();
                        } else {
                            Toast.makeText(RestablecerContrasenhaActivity.this, "El correo electrónico no está registrado.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(RestablecerContrasenhaActivity.this, "Error en el formato de la respuesta del servidor.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RestablecerContrasenhaActivity.this, "Respuesta nula del servidor.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(RestablecerContrasenhaActivity.this, "Error al conectar con el servidor.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void iniciarRecuperar() {
        Intent iBienvenida = new Intent(this, CodigoRecuperaActivity.class);
        startActivity(iBienvenida);
        finish();
    }

    private void volverLogin() {
        Intent iSesion = new Intent(getApplicationContext(), InicionSesionMeepActivity.class);
        startActivity(iSesion);
        finish();
    }

    private void enviarCorreoRecuperacion(String email, String codigo) {
        String subject = "Recuperación de contraseña";
        String body = "Estimado usuario,\n\n"
                + "Recibiste este correo porque has solicitado recuperar tu contraseña. "
                + "Tu código de verificación es: " + codigo;

        // Llamar a la clase EmailSender para enviar el correo
        EmailSender.sendEmail(email, subject, body);
    }
}
