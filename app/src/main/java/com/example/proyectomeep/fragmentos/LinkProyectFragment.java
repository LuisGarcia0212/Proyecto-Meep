package com.example.proyectomeep.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectomeep.R;
import com.example.proyectomeep.clases.Menu;
import com.example.proyectomeep.clases.Usuario;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LinkProyectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LinkProyectFragment extends Fragment implements View.OnClickListener, Menu {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LinkProyectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LinkProyectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LinkProyectFragment newInstance(String param1, String param2) {
        LinkProyectFragment fragment = new LinkProyectFragment();
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
    final static String urlAgregarParticipante = "http://meep.atwebpages.com/services/agregarParticipante.php";

    Fragment[] fragments;

    EditText editNombre, editToken;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_link_proyect, container, false);
        fragments = new Fragment[1];
        fragments[0] = new ProyectoFragment();
        Button btnUnirse = view.findViewById(R.id.lblEnter);

        editNombre = view.findViewById(R.id.editName);
        editToken = view.findViewById(R.id.editCode);

        btnUnirse.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.lblEnter){
            unirseProyecto(editNombre.getText().toString(), editToken.getText().toString());
        }
    }

    private void unirseProyecto(String nombreP, String tokenP) {
        Usuario usuario = (Usuario) getActivity().getIntent().getSerializableExtra("usuario");
        AsyncHttpClient ahcUnirseProyecto = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        if (validarDatos()){
            params.add("usuarioid", String.valueOf(usuario.getIdUsuario()));
            params.add("nombre_proyecto", nombreP);
            params.add("token_proyecto", tokenP);

            ahcUnirseProyecto.post(urlAgregarParticipante, params, new BaseJsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                    if(statusCode == 200){
                        if (rawJsonResponse.contains("Duplicate entry")) {
                            // El servidor indica que ya existe una entrada duplicada
                            Toast.makeText(getActivity().getApplicationContext(), "Ya estás unido a este proyecto", Toast.LENGTH_SHORT).show();
                        } else {
                            // Otra respuesta exitosa del servidor
                            // Puedes procesar la respuesta según sea necesario
                            // Aquí asumo que la respuesta contiene el mensaje de éxito
                            Toast.makeText(getActivity().getApplicationContext(), "Se unió al proyecto con exito", Toast.LENGTH_SHORT).show();
                            int btnMenu = 0;
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
        }
    }

    private boolean validarDatos() {
        if (editNombre.getText().toString().isEmpty() || editToken.getText().toString().isEmpty())
            return false;
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