package com.example.proyectomeep.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectomeep.R;

public class RestablecerContrasenhaActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtEmail;

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
                String codigo = RandomStringGenerator.generateRandomCode(5); // Generar código de 5 caracteres
                enviarCorreoRecuperacion(email, codigo);
                Toast.makeText(this, "Se ha enviado un correo con instrucciones para restablecer tu contraseña.", Toast.LENGTH_SHORT).show();
                iniciarRecuperar();
            } else {
                Toast.makeText(this, "Por favor ingresa tu correo electrónico.", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.logVolverLogin) {
            volverLogin();
        }
    }

    private void iniciarRecuperar() {
        Intent iBienvenida = new Intent(this, CodigoRecuperaActivity.class);
        startActivity(iBienvenida);
        finish();
    }

    private void volverLogin() {
        Intent iSesion = new Intent(getApplicationContext(), InicionSesionMeepActivity.class);
        startActivity(iSesion);
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
