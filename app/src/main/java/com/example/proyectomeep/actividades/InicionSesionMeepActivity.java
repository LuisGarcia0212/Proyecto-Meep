package com.example.proyectomeep.actividades;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomeep.R;
import com.example.proyectomeep.SQLite.MEEP;
import com.example.proyectomeep.clases.Hash;
import com.example.proyectomeep.clases.Usuario;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.Base64;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class InicionSesionMeepActivity extends AppCompatActivity implements View.OnClickListener{
    //BD
    private String urlIniciarSesion = "http://meep.atwebpages.com/services/iniciarSesion.php";
    Spinner spinner;
    TextView lblRegistro,lblRecuperacion,lblRestablecer ;

    EditText txtUser, txtPsw;

    CheckBox chkRecordar;

    Button btnIniciarSesion;

    ImageView btnfb;
    private CallbackManager callbackManager;

     //beto



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        AppEventsLogger.activateApp(getApplication());
        setContentView(R.layout.activity_inicion_sesion_meep);
        callbackManager = CallbackManager.Factory.create();

        lblRegistro = findViewById(R.id.logLblRegistro1);
        txtUser = findViewById(R.id.logTxtUser);
        txtPsw = findViewById(R.id.logTxtClave);
        btnIniciarSesion = findViewById(R.id.logBtnIniciar);
        lblRestablecer = findViewById(R.id.logLblRestablecer1);
        chkRecordar = findViewById(R.id.logChkRecordar);
        btnfb = findViewById(R.id.FB);
        btnIniciarSesion.setOnClickListener(this);
        lblRegistro.setOnClickListener(this);
        lblRestablecer.setOnClickListener(this);

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        //handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        btnfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(InicionSesionMeepActivity.this, Arrays.asList("public_profile"));
            }
        });

        validarRecordarSesion();
    }

//    private void handleFacebookAccessToken(AccessToken token) {
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.put("token", token.getToken());

//        client.post(urlIniciarSesion, params, new BaseJsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
//                try {
//                    JsonArray jsonArray = new JSONArray(rawJsonResponse);
//                    if (jsonArray.length() > 0) {
                        // Usuario encontrado en la base de datos
//                        int id = jsonArray.getJSONObject(0).getInt("idUsuario");
//                        String user = jsonArray.getJSONObject(0).getString("Username");
//                        String password = jsonArray.getJSONObject(0).getString("Contraseña");

                        // Iniciar sesión con los datos obtenidos
//                        iniciarSesion(user, password, true);
//                    } else {
                        // Usuario no encontrado en la base de datos
//                        Toast.makeText(getApplicationContext(), "Usuario no registrado", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    Toast.makeText(getApplicationContext(), "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
//                }
//            }

//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
//                Toast.makeText(getApplicationContext(), "Error: " + statusCode, Toast.LENGTH_SHORT).show();
//            }

//            @Override
//            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
//                return null;
//            }
//        });
//    }

    private void validarRecordarSesion() {
        MEEP mp = new MEEP(this);
        if (mp.recordarSesion())
            iniciarSesion(mp.getValue("usuario"), mp.getValue("clave"), true);
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
            iniciarSesion(txtUser.getText().toString().trim(), txtPsw.getText().toString(), false);
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

    private void iniciarSesion(String user, String psw, boolean recordar) {
        MEEP mp = new MEEP(this);
        Hash hash = new Hash();
        AsyncHttpClient ahcIniciarSesion = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        psw = recordar ? psw : hash.StringToHash(psw, "SHA256");


      params.add("usuario",user);
      params.add("clave",psw);

        ahcIniciarSesion.post(urlIniciarSesion, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if (statusCode == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        if(jsonArray.length() > 0){
                            int id = jsonArray.getJSONObject(0).getInt("idUsuario");
                            if (id == -1) {
                                Toast.makeText(getApplicationContext(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                            } else {
                                Usuario usuario = new Usuario();
                                usuario.setIdUsuario(id);
                                usuario.setNombresC(jsonArray.getJSONObject(0).getString("NombresCompletos"));
                                usuario.setUsuario(jsonArray.getJSONObject(0).getString("Username"));
                                usuario.setClave(jsonArray.getJSONObject(0).getString("Contraseña"));
                                usuario.setEmail(jsonArray.getJSONObject(0).getString("Email"));
                                usuario.setTelefono(jsonArray.getJSONObject(0).getString("Telefono"));
                                usuario.setDireccion(jsonArray.getJSONObject(0).getString("Direccion"));
                                usuario.setDescripcion(jsonArray.getJSONObject(0).getString("Descripcion"));
                                Uri imagenU = saveImageToTempFile(jsonArray.getJSONObject(0).getString("Foto"));
                                usuario.setFoto(imagenU.toString());
                                if (chkRecordar.isChecked()) {
                                    mp.agregarUsuario(usuario.getIdUsuario(), usuario.getUsuario(), usuario.getClave());
                                }
                                Intent iBienvenida = new Intent(getApplicationContext(), BienvenidaActivity.class);
                                iBienvenida.putExtra("usuario", usuario);
                                startActivity(iBienvenida);
                                finish();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Error al procesar la respuesta del JSON.", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "Error al procesar la respuesta del servidor.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error en el servidor: " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(), "Error: " + statusCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                try {
                    return new JSONObject(rawJsonData);
                } catch (JSONException e) {
                    Log.e("JSON Exception", "Error al convertir la respuesta a JSON: " + e.getMessage());
                    return null;
                }

            }
        });
    }


    private Uri saveImageToTempFile(String base64Image) throws IOException {
        byte[] imageBytes = Base64.decode(base64Image, Base64.DEFAULT);
        File tempFile = File.createTempFile("tempImage", ".png", getCacheDir());
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(imageBytes);
        fos.close();
        return Uri.fromFile(tempFile);
    }



    private void ingresarRestablecer() {
        Intent iRestablecer = new Intent(this, RestablecerContrasenhaActivity.class);
        startActivity(iRestablecer);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    }
