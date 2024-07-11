package com.example.proyectomeep.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.proyectomeep.R;
import com.example.proyectomeep.adaptadores.MiembroAdapter;
import com.example.proyectomeep.clases.Miembro;
import com.example.proyectomeep.clases.Proyectos;
import com.example.proyectomeep.clases.Usuario;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MiembrosActivity extends AppCompatActivity implements View.OnClickListener{

    //BD
    final static String urlMostrarMiembros = "http://meep.atwebpages.com/services/mostrarMiembros.php";

    Usuario usuario;
    ImageView btnCerrar;
    RecyclerView recMiembros;
    ArrayList<Miembro> lista;
    MiembroAdapter adapter = null;
    private int Boton;
    int idProyecto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miembros);
        Intent intent = getIntent();
        idProyecto = intent.getIntExtra("idProyecto", -1);
        recMiembros = findViewById(R.id.cardMiembro);
        lista = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recMiembros.setLayoutManager(manager);
        adapter = new MiembroAdapter(lista);
        recMiembros.setAdapter(adapter);

        mostrarMiembros();
        Boton = getIntent().getIntExtra("idBoton", 0);
        btnCerrar = findViewById(R.id.btnCLose);
        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        btnCerrar.setOnClickListener(this);
    }

    private void mostrarMiembros() {
        AsyncHttpClient ahcMostraMiembros = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("proyectoid", String.valueOf(idProyecto));

        ahcMostraMiembros.get(urlMostrarMiembros, params, new BaseJsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        lista.clear();
                        for (int i = 0; i < jsonArray.length(); i++){
                            lista.add(new Miembro(jsonArray.getJSONObject(i).getString("NombreProyec"),
                                                    jsonArray.getJSONObject(i).getString( "Foto"),
                                                    jsonArray.getJSONObject(i).getString("NombresCompletos"),
                                                    jsonArray.getJSONObject(i).getInt("id_Rol")));
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCLose){
            volver();
        }
    }

    private void volver() {
        Intent iBienvenida = new Intent(getApplicationContext(), BienvenidaActivity.class);
        iBienvenida.putExtra("usuario", usuario);
        iBienvenida.putExtra("idBoton", 4);
        startActivity(iBienvenida);
        finish();
    }

}