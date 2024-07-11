package com.example.proyectomeep.fragmentos;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomeep.R;
import com.example.proyectomeep.adaptadores.MiembroAdapter;
import com.example.proyectomeep.clases.Menu;
import com.example.proyectomeep.clases.Miembro;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.Base64;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProyectsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProyectsFragment extends Fragment implements View.OnClickListener, Menu {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProyectsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProyectsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProyectsFragment newInstance(String param1, String param2) {
        ProyectsFragment fragment = new ProyectsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    final static String urlDetalleProyecto = "http://meep.atwebpages.com/services/detalleProyecto.php";
    final static String urlTareasProyecto = "http://meep.atwebpages.com/services/consultarTareas.php";

    Fragment[] fragments;
    TextView txtEstado, txtNombreProyecto, txtAdministrador;
    CircleImageView imgAdmin;
    ImageView agregarM, imgVolver;
    int idProyecto;
    List<String[]> lista;

    AlertDialog a3;

    EditText editPendiente, editFinalizadas, editTotales;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_proyects, container, false);

        fragments = new Fragment[6];
        fragments[0] = new BienvenidaFragment();
        fragments[1] = new ForoFragment();
        fragments[2] = new ProyectoFragment();
        fragments[3] = new CrearPFragment();
        fragments[4] = new ProyectsFragment();
        fragments[5] = new TareasFragment();

        Button btnForo = view.findViewById(R.id.logBtnForo);
        Button btnTarea = view.findViewById(R.id.logBtnViTareas);


        //Datos del proyecto
        txtEstado = view.findViewById(R.id.lblEstado);
        txtNombreProyecto = view.findViewById(R.id.lblNomProyecto);
        txtAdministrador = view.findViewById(R.id.lblUserAdmin);
        imgAdmin = view.findViewById(R.id.liderProyecto);
        agregarM = view.findViewById(R.id.agregarMiembro);
        imgVolver = view.findViewById(R.id.logVolverMenu);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        idProyecto = sharedPreferences.getInt("idProyectoClicked", -1);
        lista = new ArrayList<>();

        //para las tareas del proyecto
        editPendiente = view.findViewById(R.id.editTareasRestantes);
        editFinalizadas = view.findViewById(R.id.editTareasRealizadas);
        editTotales = view.findViewById(R.id.editTareasTotal);
        asignarConteoTareas();

        cargarDetallesDelProyecto();

        agregarM.setOnClickListener(this);
        btnForo.setOnClickListener(this);
        btnTarea.setOnClickListener(this);
        imgVolver.setOnClickListener(this);
        return view;
    }

    private void asignarConteoTareas() {
        AsyncHttpClient ahcTareasProyecto = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.add("proyectoid", String.valueOf(idProyecto));

        ahcTareasProyecto.get(urlTareasProyecto, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if (statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        editTotales.setText(jsonArray.getJSONObject(0).getString("total_tareas"));
                        editPendiente.setText(jsonArray.getJSONObject(0).getString("tareas_pendientes"));
                        editFinalizadas.setText(jsonArray.getJSONObject(0).getString("tareas_finalizadas"));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
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

    private void cargarDetallesDelProyecto() {
        AsyncHttpClient ahcDetalleProyecto = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.add("proyectoid", String.valueOf(idProyecto));

        ahcDetalleProyecto.post(urlDetalleProyecto, params, new BaseJsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if (statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        lista.clear();
                        for (int i = 0; i < jsonArray.length(); i++){
                            String[] detalle = {jsonArray.getJSONObject(i).getString("Estado"),
                                    jsonArray.getJSONObject(i).getString("NombreProyec"),
                                    jsonArray.getJSONObject(i).getString("NombresCompletos"),
                                    jsonArray.getJSONObject(i).getString("Nombre_Rol"),
                                    jsonArray.getJSONObject(i).getString("Foto")};
                            lista.add(detalle);
                        }

                        // Usando Stream para filtrar y asignar
                        Optional<String> imOptional = lista.stream()
                                .filter(elemento -> "Administrador".equals(elemento[3]))
                                .map(elemento -> elemento[4])
                                .findFirst();
                        if (imOptional.isPresent()) {
                            String imagen = imOptional.get();
                            byte[] imageByte = Base64.decode(imagen, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                            imgAdmin.setImageBitmap(bitmap);
                        } else {
                            // Manejo cuando no hay administrador
                            System.out.println("No se encontró el administrador.");
                        }
                        lista.stream()
                                .filter(elemento -> "Administrador".equals(elemento[3]))
                                .findFirst()
                                .ifPresent(administrador -> {
                                    txtAdministrador.setText(administrador[2]);
                                    txtNombreProyecto.setText(administrador[1]);
                                    txtEstado.setText(administrador[0]);
                                });

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
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

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.logBtnForo){
            ingresarForo();
        } else if (v.getId() == R.id.logBtnViTareas) {
            ingresarTareas();
        } else if (v.getId() == R.id.agregarMiembro) {
            abrirDialogM();
        } else if (v.getId() == R.id.logVolverMenu) {
            volverMenu();
        }
    }

    private void volverMenu() {
        int boton = 2;
        onClickMenu(boton);
    }

    RecyclerView recMiembros;
    ArrayList<Miembro> listaMi;
    MiembroAdapter adapter = null;

    private void abrirDialogM() {
        AlertDialog.Builder d = new AlertDialog.Builder(getContext(), R.style.CustomDialogTheme);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_miembro_nuevo, null);
        d.setView(dialogView);
        recMiembros = dialogView.findViewById(R.id.cardMiembro);
        listaMi = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recMiembros.setLayoutManager(manager);
        adapter = new MiembroAdapter(listaMi);
        recMiembros.setAdapter(adapter);

        mostrarMiembros();

        a3 = d.create();
        a3.show();
    }

    private void mostrarMiembros() {

    }

    private void ingresarTareas() {
        int btnBoton = 5;
        onClickMenu(btnBoton);
    }

    private void ingresarForo() {
        int btnBoton = 1;
        onClickMenu(btnBoton);
    }

    @Override
    public void onClickMenu(int idBoton) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.menuRelaArea, fragments[idBoton]);
        ft.commit();
    }
}