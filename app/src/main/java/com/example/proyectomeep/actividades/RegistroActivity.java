package com.example.proyectomeep.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectomeep.R;
import com.example.proyectomeep.clases.Hash;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    //Para la BD
    private String urlAgregarUsuario = "http://meep.atwebpages.com/services/agregarUsuario.php";

    Button btnRegistro;
    ImageView btnfb,btnGG, btnVolver;
    EditText txtNombres, txtUsuario, txtClave, txtClaveConfirmar, txtEmail, txtTelefono, txtDireccion;
    private CallbackManager callbackManager;
    CheckBox chPolitica;

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;


    private String facebookToken;
    private String idToken;// Variable para almacenar el token de Facebook

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Inicializar CallbackManager
        callbackManager = CallbackManager.Factory.create();

        // Para insertar los datos en la BD
        txtNombres = findViewById(R.id.logTxtNombres);
        txtUsuario = findViewById(R.id.logTxtUsuario);
        txtClave = findViewById(R.id.logTxtClave);
        txtClaveConfirmar = findViewById(R.id.logTxtClaveConfirm);
        txtEmail = findViewById(R.id.logTxtEmail);
        txtTelefono = findViewById(R.id.logTxtTelefono);
        txtDireccion = findViewById(R.id.logTxtDirección);
        btnfb = findViewById(R.id.FB);
        btnGG = findViewById(R.id.GG);
        btnRegistro = findViewById(R.id.logBtnRegistrar);
        chPolitica = findViewById(R.id.logChkTerminos);

        btnVolver = findViewById(R.id.logVolverLogin);

        btnVolver.setOnClickListener(this);
        btnRegistro.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("905675094658-ea8d71eggs3uqibroqj7l9d3hhvaqg3d.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Configurar LoginManager para Facebook
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
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
                LoginManager.getInstance().logInWithReadPermissions(RegistroActivity.this, Arrays.asList("public_profile", "email"));
            }
        });

        btnGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });

    }


    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logBtnRegistrar) {
            if (validarCampos())
                return;
            if (validarCamposContrasenha()){
                Toast.makeText(getApplicationContext(), "Las constraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }
            registrarUsuario();
        } else if (v.getId() == R.id.logVolverLogin) {
            volverLogin();
        }
    }

    private boolean validarCampos() {
        if (txtNombres.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Campo nombre vacio", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (txtUsuario.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Campo usuario vacio", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (txtClave.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Campo contraseña vacio", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (txtClaveConfirmar.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Campo confirmar contraseña vacio", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (txtEmail.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Campo email vacio", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (txtTelefono.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Campo teléfono vacio", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (txtDireccion.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Campo dirección vacio", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (!chPolitica.isChecked()){
            Toast.makeText(getApplicationContext(), "Aceptar Términos y Condiciones", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean validarCamposContrasenha() {
        if (txtClave.getText().toString().equals(txtClaveConfirmar.getText().toString()))
            return false;
        return true;
    }

    private void volverLogin() {
        Intent iSesion = new Intent(getApplicationContext(), InicionSesionMeepActivity.class);
        startActivity(iSesion);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String nombre = object.getString("name");
                    String email = object.optString("email"); // email puede no estar disponible
                    String birthday = object.optString("birthday"); // fecha de nacimiento puede no estar disponible
                    JSONObject location = object.optJSONObject("location"); // ubicación puede no estar disponible
                    String direccion = location != null ? location.optString("name") : "";

                    // Llenar los campos con la información de Facebook
                    txtNombres.setText(nombre);
                    txtEmail.setText(email);


                    // Guardar el token en una variable
                    facebookToken = token.getToken();

                    // Bloquear los campos para que no se puedan editar
                    txtNombres.setEnabled(false);
                    txtEmail.setEnabled(false);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email,birthday,location");
        request.setParameters(parameters);
        request.executeAsync();
    }





    private void handleGoogleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                // Mostrar el correo electrónico en el campo correspondiente

                txtEmail.setText(account.getEmail());

                // Mostrar otros datos si son necesarios
                String nombres = account.getDisplayName();
                idToken = account.getIdToken(); // Obtener el token de Google

                // Llenar los campos del formulario con los datos de Google
                txtNombres.setText(nombres);
                // Puedes llenar más campos según los datos que necesites (como teléfono, dirección, etc.)
                txtNombres.setEnabled(false);
                txtEmail.setEnabled(false);

            } else {
                Toast.makeText(this, "No se pudo obtener la cuenta de Google", Toast.LENGTH_SHORT).show();
            }
        } catch (ApiException e) {
            Log.w("GoogleSignIn", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, "Error al iniciar sesión con Google: " + e.getStatusCode(), Toast.LENGTH_SHORT).show();
        }
    }


    private void registrarUsuario() {
        AsyncHttpClient ahcregistrarU = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        Hash hash = new Hash();
        if (validarFormulario()) {
            params.add("NombresCompletos", txtNombres.getText().toString());
            params.add("Username", txtUsuario.getText().toString());
            params.add("Contraseña", hash.StringToHash(txtClave.getText().toString(), "SHA256"));
            params.add("Email", txtEmail.getText().toString());
            params.add("Telefono", txtTelefono.getText().toString());
            params.add("Direccion", txtDireccion.getText().toString());
            params.add("Descripcion", "-");
            params.add("Foto", "-");

            if (facebookToken!=null){
                params.add("token", facebookToken); // Agregar el token de Facebook a los parámetros
            }else {
                params.add("token", "-"); // Agregar el token de Facebook a los parámetros
            }

            if (idToken!=null){
                params.put("token", idToken);; // Agregar el token de Facebook a los parámetros
            }else {
                params.put("token", "-"); // Agregar el token de google  a los parámetros
            }



            ahcregistrarU.post(urlAgregarUsuario, params, new BaseJsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                    if (statusCode == 200) {
                        int retVal = rawJsonResponse.length() == 0 ? 0 : Integer.parseInt(rawJsonResponse);
                        if (retVal == 1) {
                            Toast.makeText(getApplicationContext(), "Usuario Registrado Correctamente", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent iSesion = new Intent(getApplicationContext(), InicionSesionMeepActivity.class);
                            startActivity(iSesion);
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                    Toast.makeText(getApplicationContext(), "Error: " + statusCode, Toast.LENGTH_SHORT).show();
                }

                @Override
                protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                    return null;
                }
            });
        } else {
            Toast.makeText(this, "Complete el Formulario", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarFormulario() {
        if (txtNombres.getText().toString().isEmpty() || txtUsuario.getText().toString().isEmpty() ||
                txtEmail.getText().toString().isEmpty() || txtClave.getText().toString().isEmpty() ||
                txtTelefono.getText().toString().isEmpty() || txtDireccion.getText().toString().isEmpty())
            return false;
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Resultado del inicio de sesión con Google
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        }
    }


}
