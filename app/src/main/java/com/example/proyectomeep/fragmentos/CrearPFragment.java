package com.example.proyectomeep.fragmentos;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectomeep.R;
import com.example.proyectomeep.SQLite.MEEP;
import com.example.proyectomeep.actividades.BienvenidaActivity;
import com.example.proyectomeep.actividades.CuentaActivity;
import com.example.proyectomeep.clases.Menu;
import com.example.proyectomeep.clases.Usuario;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Calendar;
import java.util.UUID;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearPFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearPFragment extends Fragment implements View.OnClickListener, Menu {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearPFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearPFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearPFragment newInstance(String param1, String param2) {
        CrearPFragment fragment = new CrearPFragment();
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
    //BD
    private String urlCrearProyecto = "http://meep.atwebpages.com/services/agregarProyecto.php";
    EditText editNombre, editToken, editDescripcion;
    Fragment[] fragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_p, container, false);

        fragments = new Fragment[5];
        fragments[0] = new BienvenidaFragment();
        fragments[1] = new ForoFragment();
        fragments[2] = new ProyectoFragment();
        fragments[3] = new CrearPFragment();
        fragments[4] = new MessageListFragment();

        ImageView imgVolver = view.findViewById(R.id.logVolverMenu);
        Button btnGenerar = view.findViewById(R.id.logbtnCrear);
        Button btnGenTo = view.findViewById(R.id.logBtnGen);

        //valores del proyecto
        editNombre = view.findViewById(R.id.editName);
        editToken = view.findViewById(R.id.editToken);
        editDescripcion = view.findViewById(R.id.editDescripcion);

        btnGenerar.setOnClickListener(this);
        imgVolver.setOnClickListener(this);
        btnGenTo.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.logVolverMenu){
            volver();
        } else if (v.getId() == R.id.logbtnCrear) {
            generarProyecto(editNombre.getText().toString(), editToken.getText().toString(),
                    editDescripcion.getText().toString());
        } else if (v.getId() == R.id.logBtnGen) {
            generarToken();
        }

    }

    private void generarToken() {
        String token = UUID.randomUUID().toString().replace("-", "").substring(0,6);
        editToken.setText(token);
    }

    private void volver() {
        int btnMenu = 2;
        onClickMenu(btnMenu);
    }

    private void generarProyecto(String nombre, String token, String descripcion){
        Usuario usuario = (Usuario) getActivity().getIntent().getSerializableExtra("usuario");
        AsyncHttpClient ahccrearProyecto = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String estadoP = "Activo";

        if(validarDatos()){
            params.add("usuarioid", String.valueOf(usuario.getIdUsuario()));
            params.add("estado", estadoP);
            params.add("nombre_proyecto", nombre);
            params.add("token_proyecto", token);
            params.add("descripcion", descripcion);

            ahccrearProyecto.post(urlCrearProyecto, params, new BaseJsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                    if (statusCode == 200){
                        int retVal = rawJsonResponse.length() == 0 ? 0 : Integer.parseInt(rawJsonResponse);
                        if (retVal == 1){
                            Toast.makeText(getActivity().getApplicationContext(), "Proyecto Creado Correctamente", Toast.LENGTH_SHORT).show();
                            int btnMenu = 2;
                            onClickMenu(btnMenu);
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                    Toast.makeText(getActivity().getApplicationContext(), "Error: "+statusCode, Toast.LENGTH_SHORT).show();
                }

                @Override
                protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                    return null;
                }
            });
        }else{
            Toast.makeText(getActivity().getApplicationContext(), "Llene todos los espacios", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarDatos() {
        if(editNombre.getText().toString().isEmpty() || editToken.getText().toString().isEmpty() ||
                editDescripcion.getText().toString().isEmpty())
            return  false;
        return true;
    }

    @Override
    public void onClickMenu(int idBoton) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.menuRelaArea, fragments [idBoton]);
        ft.commit();
    }
}