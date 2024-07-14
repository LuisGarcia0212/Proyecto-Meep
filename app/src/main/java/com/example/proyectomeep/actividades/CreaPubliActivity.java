package com.example.proyectomeep.actividades;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.proyectomeep.R;
import com.example.proyectomeep.fragmentos.ForoFragment;

import android.util.Base64; // Importa la clase Base64 correcta
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CreaPubliActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int COD_SEL_IMAGE = 300; // Definir la constante correctamente

    ImageView imgro, imgUbi, cerrar;
    EditText Lblpublic, lblañadir;
    String base64Image;
    private Uri imageUrl;
    Button publicar;
    Boolean imgE = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_publi);

        imgUbi = findViewById(R.id.imgnew);
        imgro = findViewById(R.id.imgJoin);
        lblañadir = findViewById(R.id.lblaniadar);
        Lblpublic = findViewById(R.id.lblPubliId);
        publicar = findViewById(R.id.logBtnPubli);
        cerrar = findViewById(R.id.backCuenta);

        Uri imagenUri = Uri.parse("content://path/to/image"); // Reemplaza con la URI correcta
        imgUbi.setImageURI(imagenUri);

        imgUbi.setOnClickListener(this);
        imgro.setOnClickListener(this);
        lblañadir.setOnClickListener(this);
        Lblpublic.setOnClickListener(this);
        publicar.setOnClickListener(this);
        cerrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backCuenta) {
            regresar();
        } else if (v.getId() == R.id.imgJoin) { // Asegúrate de que el ID sea correcto
            uploadPhoto();
        } else if (v.getId() == R.id.imgnew) {
            ubicacion();
        }else if (v.getId() == R.id.logBtnPubli) {
            publi();
        }
        // Otros casos para diferentes clics en botones pueden ser manejados aquí
    }

    private void publi() {
        finish();
    }


    private void ubicacion() {
        Intent iForo= new Intent(this, Maps_Activity.class);
        startActivity(iForo);
    }

    private void uploadPhoto() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, COD_SEL_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == COD_SEL_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUrl = data.getData();
            imgro.setImageURI(imageUrl);
            imgE = true;
            try {
                subirFoto(imageUrl);
            } catch (IOException e) {
                e.printStackTrace(); // Cambiado a printStackTrace para un manejo más estándar de excepciones
            }
        }
    }

    private void subirFoto(Uri imageUrl) throws IOException {
        base64Image = convertImageToBase64(imageUrl);
    }

    private String convertImageToBase64(Uri imageUri) throws IOException {
        // Obtener InputStream de la imagen
        InputStream inputStream = getContentResolver().openInputStream(imageUri);
        if (inputStream == null) {
            throw new IOException("Unable to open input stream for URI: " + imageUri);
        }

        // Leer el InputStream en un array de bytes
        byte[] imageBytes = getBytes(inputStream);
        // Convertir el array de bytes a Base64
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private void regresar() {
        finish();
    }
}
