package com.example.proyectomeep.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;

import com.example.proyectomeep.SQLite.MEEP;
import com.example.veterinaria.clases.Hash;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomeep.R;
import com.example.proyectomeep.clases.Usuario;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.Base64;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class CuentaActivity extends AppCompatActivity implements View.OnClickListener {

    //BD
    final static String urleditarUsuario = "http://meep.atwebpages.com/services/editarUsuario.php";
    Usuario usuario;
    ImageView backCuenta, imgCuenta, ediNom, ediUsu, ediTele, ediDire, ediClave, ediEma;
    EditText editNombres, editUsuario, editTelefono, editDireccion, editClave, editEmail;
    Button btnGuardar;

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

        String imagen = usuario.getFoto();
        byte[] imageByte = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        imgCuenta.setImageBitmap(bitmap);
        backCuenta.setOnClickListener(this);

        ediNom.setOnClickListener(this);
        ediUsu.setOnClickListener(this);
        ediTele.setOnClickListener(this);
        ediDire.setOnClickListener(this);
        ediClave.setOnClickListener(this);
        ediEma.setOnClickListener(this);

        btnGuardar.setOnClickListener(this);
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
            editarDatos(editNombres.getText().toString(), editUsuario.getText().toString(), editClave.getText().toString(), editEmail.getText().toString(),
                    editTelefono.getText().toString(), editDireccion.getText().toString());
        }

    }

    private void editarDatos(String nombres, String usua, String clave, String email, String telefono, String direccion) {
        AsyncHttpClient ahceditarUsuario = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        Hash hash = new Hash();
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
            params.add("foto", "-");

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
                            editNombres.setInputType(InputType.TYPE_NULL);
                            editUsuario.setInputType(InputType.TYPE_NULL);
                            editEmail.setInputType(InputType.TYPE_NULL);
                            editTelefono.setInputType(InputType.TYPE_NULL);
                            editDireccion.setInputType(InputType.TYPE_NULL);
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
        startActivity(iBienvenida);
        finish();
    }


}