package com.example.proyectomeep.fragmentos;

import android.app.ActivityOptions;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.widget.TextView;

import com.example.proyectomeep.R;
import com.example.proyectomeep.actividades.MapaActivity;
import com.example.proyectomeep.actividades.TaskAdapter;
import com.example.proyectomeep.actividades.TaskViewActivity;
import com.example.proyectomeep.adaptadores.TareaPendienteAdpter;
import com.example.proyectomeep.clases.TareaPendiente;
import com.example.proyectomeep.clases.Usuario;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BienvenidaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BienvenidaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageView imaMap;

    public BienvenidaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BienvenidaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BienvenidaFragment newInstance(String param1, String param2) {
        BienvenidaFragment fragment = new BienvenidaFragment();
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
    final static String urlMostrarTarea = "http://meep.atwebpages.com/services/mostrarTareaUsuario.php";

    RecyclerView recTareas;
    ArrayList<TareaPendiente> lista;

    TareaPendienteAdpter adapter;
    Usuario usuario;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bienvenida, container, false);
        usuario = (Usuario) getActivity().getIntent().getSerializableExtra("usuario");

        recTareas = view.findViewById(R.id.cardTareasP);
        lista = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recTareas.setLayoutManager(manager);
        adapter = new TareaPendienteAdpter(lista);
        recTareas.setAdapter(adapter);

        mostrarTareas();

        return view;
    }

    private void mostrarTareas() {
        AsyncHttpClient ahcTareasPendientes = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("usuarioid", String.valueOf(usuario.getIdUsuario()));


        ahcTareasPendientes.get(urlMostrarTarea, params, new BaseJsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if (statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        lista.clear();
                        for (int i = 0; i < jsonArray.length(); i++){
                            lista.add(new TareaPendiente(jsonArray.getJSONObject(i).getInt("idTarea"),
                                                         jsonArray.getJSONObject(i).getString("Nombre_tarea"),
                                                         jsonArray.getJSONObject(i).getString("Descripcion_tarea"),
                                                         jsonArray.getJSONObject(i).getInt("id_Usuario_Tarea"),
                                                         jsonArray.getJSONObject(i).getInt("id_Proyecto_Tarea"),
                                                         jsonArray.getJSONObject(i).getString("estado"),
                                                         jsonArray.getJSONObject(i).getString("NombreProyec")));
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
}