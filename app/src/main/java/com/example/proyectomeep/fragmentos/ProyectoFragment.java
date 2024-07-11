package com.example.proyectomeep.fragmentos;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;


import com.example.proyectomeep.R;
import com.example.proyectomeep.SQLite.DatabaseHelper;
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
    private DatabaseHelper dbHelper;
    private ImageView menuProyect1;
    private ImageButton btnFavorite;
    private boolean isFavorite;
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
    ArrayList<Proyectos> lista, allProyectos, favoriteProyects;
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
        ScrollView scrollView = view.findViewById(R.id.scrollMain);
        LinearLayout linearLayout = view.findViewById(R.id.botonesL);

        //para desaparecer los botones al scrollear
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrolly = scrollView.getScrollY();
                if(scrolly > 0)
                    linearLayout.setVisibility(View.GONE);
                else
                    linearLayout.setVisibility(View.VISIBLE);
            }
        });

        rvProyecto = view.findViewById(R.id.cardProyceto);
        lista = new ArrayList<>();
        allProyectos = new ArrayList<>();
        favoriteProyects = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvProyecto.setLayoutManager(manager);
        adapter = new ProyectoAdapter(getContext(),lista, fragments);
        rvProyecto.setAdapter(adapter);

        mostrarProyectos();

        ImageView imgJoin = view.findViewById(R.id.imgJoin);
        ImageView imgCProyect = view.findViewById(R.id.imgToken);
        imgJoin.setOnClickListener(this);
        imgCProyect.setOnClickListener(this);

        // Establece onClick para "allProyectos" y "Mis favoritos"
        view.findViewById(R.id.lblAllproyectos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.updateProyects(allProyectos); // Mostrar todos los proyectos
            }
        });

        view.findViewById(R.id.lblMisfav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFavoriteProjects(v); // Mostrar proyectos favoritos
            }
        });
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
                        allProyectos.clear();
                        favoriteProyects.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Proyectos proyecto = new Proyectos(
                                    jsonArray.getJSONObject(i).getInt("idProyecto"),
                                    jsonArray.getJSONObject(i).getString("Estado"),
                                    jsonArray.getJSONObject(i).getString("NombreProyec"),
                                    jsonArray.getJSONObject(i).getString("Descripcion"),
                                    true,
                                    true,
                                    jsonArray.getJSONObject(i).getInt("id_Rol")
                            );
                            lista.add(proyecto);
                            allProyectos.add(proyecto); // Add to all projects list
                            if (proyecto.isFavorite()) {
                                favoriteProyects.add(proyecto); // Add to favorite projects list if it's a favorite
                            }
                        }
                        adapter.notifyDataSetChanged();
                        /*
                        for (int i = 0; i < jsonArray.length(); i++){
                            lista.add(new Proyectos(jsonArray.getJSONObject(i).getInt("idProyecto"),
                                                    jsonArray.getJSONObject(i).getString("Estado"),
                                                    jsonArray.getJSONObject(i).getString("NombreProyec"),
                                                    jsonArray.getJSONObject(i).getString("Descripcion"),
                                                    true,
                                                    true,
                                                    jsonArray.getJSONObject(i).getInt("id_Rol")));
                            adapter.notifyDataSetChanged();
                        }*/
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

    public void showAllProjects(View view) {
        adapter.updateProyects(allProyectos);
    }

    public void showFavoriteProjects(View view) {
        ArrayList<Proyectos> favoriteProyectosList = new ArrayList<>();
        for (Proyectos proyecto : allProyectos) {
            if (proyecto.isFavorite()) {
                favoriteProyectosList.add(proyecto);
            }
        }
        adapter.updateProyects(favoriteProyectosList);
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

