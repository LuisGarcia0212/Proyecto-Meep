package com.example.proyectomeep.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import com.example.proyectomeep.R;

public class CodigoRecuperaActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtCodigo;
    TextView lblVerificar;
    ImageView imgVolver;
    String email;
    private String urlVerificarCodigo = "http://meep.atwebpages.com/services/verificacionC.php";
    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_recupera);

        edtCodigo = findViewById(R.id.logTxtCode); // Aquí asumo que tienes un EditText en tu layout para ingresar el código
        lblVerificar = findViewById(R.id.lblVeri);
        imgVolver = findViewById(R.id.logVolverLogin);

        lblVerificar.setOnClickListener(this);
        imgVolver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.lblVeri) {
            String codigo = edtCodigo.getText().toString().trim();
            if (!codigo.isEmpty()) {
                // Verificar el código ingresado en la base de datos usando AsyncHttpClient
                verificarCodigoEnBaseDeDatos(codigo);
            } else {
                Toast.makeText(this, "Por favor ingresa el código de verificación.", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.logVolverLogin) {
            volverRecuperar();
        }
    }

    private void verificarCodigoEnBaseDeDatos(final String codigo) {
        // Obtener el email del intent anterior (si lo pasaste)
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("codigo", codigo);
        System.out.println(email);
        asyncHttpClient.post(urlVerificarCodigo, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    String response = new String(responseBody);
                    Log.d("DEBUG", "Respuesta del servidor: " + response); // Agrega esto para ver la respuesta del servidor en el Logcat
                    try {
                        JSONObject json = new JSONObject(response);
                        boolean codigoValido = json.getBoolean("valido");
                        if (codigoValido) {
                            // El código es válido, proceder con la recuperación de contraseña
                            Toast.makeText(CodigoRecuperaActivity.this, "Código verificado correctamente.", Toast.LENGTH_SHORT).show();
                            iniciarVerificar(); // Aquí puedes iniciar la actividad de verificación o recuperación
                        } else {
                            // El código no es válido, mostrar mensaje de error
                            Toast.makeText(CodigoRecuperaActivity.this, "El código ingresado no es válido.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(CodigoRecuperaActivity.this, "Error en el formato de la respuesta del servidor.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CodigoRecuperaActivity.this, "Respuesta nula del servidor.", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(CodigoRecuperaActivity.this, "Error al conectar con el servidor.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void volverRecuperar() {
        Intent iRecuperar = new Intent(this, RestablecerContrasenhaActivity.class);
        startActivity(iRecuperar);
        finish();
    }

    private void iniciarVerificar() {
        // Aquí inicias la actividad para la verificación o recuperación de contraseña
        Intent iBienvenida = new Intent(this, RecuperacionActivity.class);
        iBienvenida.putExtra("email", email);
        startActivity(iBienvenida);
        finish();
    }
}
