package com.example.proyectomeep.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.proyectomeep.R;
import com.example.proyectomeep.actividades.InicionSesionMeepActivity;
import com.example.proyectomeep.adaptadores.ProyectoAdapter;
import com.example.proyectomeep.clases.Menu;
import com.example.proyectomeep.clases.Proyectos;
import com.example.proyectomeep.clases.Usuario;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProyectoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProyectoFragment extends Fragment implements View.OnClickListener, Menu {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProyectoFragment() {
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
    public static ProyectoFragment newInstance(String param1, String param2) {
        ProyectoFragment fragment = new ProyectoFragment();
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
    final static String urlMostraProyecto  = "http://meep.atwebpages.com/services/mostrarProyecto.php";
    Fragment[] fragments;
    RecyclerView rvProyecto;
    ArrayList<Proyectos> lista;
    ProyectoAdapter adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proyecto, container, false);
        fragments = new Fragment[6];
        fragments[0] = new BienvenidaFragment();
        fragments[1] = new ForoFragment();
        fragments[2] = new ProyectoFragment();
        fragments[3] = new CrearPFragment();
        fragments[4] = new ProyectsFragment();
        fragments[5] = new LinkProyectFragment();

        rvProyecto = view.findViewById(R.id.cardProyceto);
        lista = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvProyecto.setLayoutManager(manager);
        adapter = new ProyectoAdapter(lista);
        rvProyecto.setAdapter(adapter);

        mostrarProyectos();

        ImageView imgJoin = view.findViewById(R.id.imgJoin);
        ImageView imgCProyect = view.findViewById(R.id.imgToken);
        imgJoin.setOnClickListener(this);
        imgCProyect.setOnClickListener(this);
        return view;
    }

    private void mostrarProyectos() {
        AsyncHttpClient ahcMostrarProyecto = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        Usuario usuario = (Usuario) getActivity().getIntent().getSerializableExtra("usuario");
        params.add("usuarioid", String.valueOf(usuario.getIdUsuario()));
        ahcMostrarProyecto.get(urlMostraProyecto, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if (statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        lista.clear();
                        for (int i = 0; i < jsonArray.length(); i++){
                            lista.add(new Proyectos(jsonArray.getJSONObject(i).getInt("idProyecto"),
                                                    jsonArray.getJSONObject(i).getString("Estado"),
                                                    jsonArray.getJSONObject(i).getString("NombreProyec"),
                                                    jsonArray.getJSONObject(i).getString("Descripcion"),
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
        if(v.getId() == R.id.imgJoin){
            ingresarUnirse();
        } else if (v.getId() == R.id.imgToken) {
            ingresarCProyecto();
        }
    }

    private void ingresarCProyecto() {
        int btnBoton = 3;
        onClickMenu(btnBoton);
    }

    private void ingresarUnirse() {
        int btnBoton = 5;
        onClickMenu(btnBoton);
    }

    private void ingresarAProy() {
        int idBoton = 4;
        onClickMenu(idBoton);
    }

    @Override
    public void onClickMenu(int idBoton) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.menuRelaArea, fragments[idBoton]);
        ft.commit();
    }
}