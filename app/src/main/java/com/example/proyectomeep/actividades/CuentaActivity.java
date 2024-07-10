package com.example.proyectomeep.actividades;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;


import com.example.proyectomeep.SQLite.MEEP;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectomeep.R;
import com.example.proyectomeep.clases.Hash;
import com.example.proyectomeep.clases.Usuario;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.Base64;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class CuentaActivity extends AppCompatActivity implements View.OnClickListener {

    //BD
    final static String urleditarUsuario = "http://meep.atwebpages.com/services/editarUsuario.php";
    Usuario usuario;
    ImageView backCuenta, imgCuenta, ediNom, ediUsu, ediTele, ediDire, ediClave, ediEma, btnImagen;
    EditText editNombres, editUsuario, editTelefono, editDireccion, editClave, editEmail;
    Button btnGuardar;
    CircleImageView perfilI;
    private Uri imageUrl;
    String base64Image;
    Boolean imgE = false;
    private static final int COD_SEL_IMAGE = 300;

    private int Boton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        backCuenta = findViewById(R.id.backCuenta);
        imgCuenta = findViewById(R.id.perfilImage);
        editNombres = findViewById(R.id.editNombres);
        editUsuario = findViewById(R.id.editUsuario);
        editClave = findViewById(R.id.editSenha);
        editEmail = findViewById(R.id.editEmail);
        editTelefono = findViewById(R.id.editTelefono);
        editDireccion = findViewById(R.id.editDireccion);
        btnGuardar = findViewById(R.id.logBtnSave);

        btnImagen = findViewById(R.id.editarFoto);
        perfilI = findViewById(R.id.perfilImage);

        //para editar
        ediNom = findViewById(R.id.ediNombres);
        ediUsu = findViewById(R.id.ediUsuario);
        ediTele = findViewById(R.id.ediTelefono);
        ediDire = findViewById(R.id.ediDireccion);
        ediClave = findViewById(R.id.ediClave);
        ediEma = findViewById(R.id.ediEmail);

        editNombres.setText(usuario.getNombresC());
        editUsuario.setText(usuario.getUsuario());
        editEmail.setText(usuario.getEmail());
        editTelefono.setText(usuario.getTelefono());
        editDireccion.setText(usuario.getDireccion());

        editNombres.setInputType(InputType.TYPE_NULL);
        editUsuario.setInputType(InputType.TYPE_NULL);
        editEmail.setInputType(InputType.TYPE_NULL);
        editTelefono.setInputType(InputType.TYPE_NULL);
        editDireccion.setInputType(InputType.TYPE_NULL);

        String image = usuario.getFoto();
        Uri imagenUri = Uri.parse(image);
        imgCuenta.setImageURI(imagenUri);

        Boton = getIntent().getIntExtra("idBoton", 0);

        backCuenta.setOnClickListener(this);

        ediNom.setOnClickListener(this);
        ediUsu.setOnClickListener(this);
        ediTele.setOnClickListener(this);
        ediDire.setOnClickListener(this);
        ediClave.setOnClickListener(this);
        ediEma.setOnClickListener(this);

        btnGuardar.setOnClickListener(this);

        btnImagen.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.backCuenta){
            volver();
        } else if (v.getId() == R.id.ediNombres) {
            editable(editNombres);
        } else if (v.getId() == R.id.ediUsuario) {
            editable(editUsuario);
        } else if (v.getId() == R.id.ediTelefono) {
            editable(editTelefono);
        } else if (v.getId() == R.id.ediDireccion) {
            editable(editDireccion);
        } else if (v.getId() == R.id.ediClave) {
            editable(editClave);
        } else if (v.getId() == R.id.ediEmail) {
            editable(editEmail);
        } else if (v.getId() == R.id.logBtnSave) {
            try {
                editarDatos(editNombres.getText().toString(), editUsuario.getText().toString(), editClave.getText().toString(), editEmail.getText().toString(),
                        editTelefono.getText().toString(), editDireccion.getText().toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (v.getId() == R.id.editarFoto) {
            uploadPhoto();
        }

    }

    private void uploadPhoto() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, COD_SEL_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageUrl = data.getData();
        imgCuenta.setImageURI(imageUrl);
        imgE = true;
        try {
            subirFoto(imageUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
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


    private void editarDatos(String nombres, String usua, String clave, String email, String telefono, String direccion) throws IOException {
        AsyncHttpClient ahceditarUsuario = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        Hash hash = new Hash();
        editNombres.setInputType(InputType.TYPE_NULL);
        editUsuario.setInputType(InputType.TYPE_NULL);
        editEmail.setInputType(InputType.TYPE_NULL);
        editTelefono.setInputType(InputType.TYPE_NULL);
        editDireccion.setInputType(InputType.TYPE_NULL);
        if(validar()){
            if(editClave.getText().toString().isEmpty()){
                clave = usuario.getClave();
                params.add("clave", clave);
            }else{
                clave = hash.StringToHash(clave, "SHA256");
                params.add("clave", clave);
            }

            params.add("usuarioid", String.valueOf(usuario.getIdUsuario()));
            params.add("nombresusu", nombres);
            params.add("usuario", usua);
            params.add("email", email);
            params.add("telefono", telefono);
            params.add("direccion", direccion);
            params.add("descripcion", "pato");
            if(imgE){
                params.add("foto", base64Image);
                usuario.setFoto(imageUrl.toString());
            }else {
                base64Image = convertImageToBase64(Uri.parse(usuario.getFoto()));
                usuario.setFoto(usuario.getFoto());
                params.add("foto", base64Image);
            }

            usuario.setNombresC(nombres);
            usuario.setUsuario(usua);
            usuario.setClave(clave);
            usuario.setEmail(email);
            usuario.setTelefono(telefono);
            usuario.setDireccion(direccion);
            usuario.setDescripcion("Pato");
            //Eliminamos del recordar Sesion
            MEEP mp = new MEEP(this);
            mp.reiniciarSesion();
            mp.agregarUsuario(usuario.getIdUsuario(), usuario.getUsuario(), usuario.getClave());
            ahceditarUsuario.post(urleditarUsuario, params, new BaseJsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                    if(statusCode == 200){
                        int retVal = (rawJsonResponse.length() == 0 ? 0 : Integer.parseInt(rawJsonResponse));
                        if (retVal == 1){
                            Toast.makeText(getApplicationContext(), "Se edito correctamente", Toast.LENGTH_SHORT).show();
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
        }
    }

    private boolean validar() {
        if (editNombres.getText().toString().isEmpty() || editUsuario.getText().toString().isEmpty()  || editEmail.getText().toString().isEmpty()
                || editTelefono.getText().toString().isEmpty() || editDireccion.getText().toString().isEmpty())
            return false;
        return true;
    }

    @SuppressLint("ResourceAsColor")
    private void editable(EditText edit) {
        edit.setInputType(InputType.TYPE_CLASS_TEXT);
        edit.setBackgroundColor(R.color.white);
    }

    private void volver() {
        Intent iBienvenida = new Intent(this, BienvenidaActivity.class);
        iBienvenida.putExtra("usuario", usuario);
        iBienvenida.putExtra("idBoton", Boton);
        startActivity(iBienvenida);
        finish();
    }


}