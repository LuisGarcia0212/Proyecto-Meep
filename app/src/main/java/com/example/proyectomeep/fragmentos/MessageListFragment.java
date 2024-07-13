package com.example.proyectomeep.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectomeep.R;
import com.example.proyectomeep.adaptadores.MensajeriaAdapter;
import com.example.proyectomeep.clases.MensajeriaInterna;
import com.example.proyectomeep.clases.Menu;
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
 * Use the {@link MessageListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageListFragment extends Fragment implements View.OnClickListener, Menu {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MessageListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageListFragment newInstance(String param1, String param2) {
        MessageListFragment fragment = new MessageListFragment();
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

    Fragment[] fragments;
    final static String urlMostrarChat = "http://meep.atwebpages.com/services/consultaMensajeriaUsuario.php";
    RecyclerView recMensajeria;
    ArrayList<MensajeriaInterna> lista;
    MensajeriaAdapter adapter = null;
    Usuario usuario = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message_list, container, false);

        fragments = new Fragment[6];
        fragments[0] = new BienvenidaFragment();
        fragments[1] = new ForoFragment();
        fragments[2] = new ProyectoFragment();
        fragments[3] = new CrearPFragment();
        fragments[4] = new MessageListFragment();
        fragments[5] = new MenInterFragment();
        recMensajeria = view.findViewById(R.id.cardMensajeria);
        lista = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recMensajeria.setLayoutManager(manager);
        adapter = new MensajeriaAdapter(lista, getContext(), fragments);
        recMensajeria.setAdapter(adapter);
        usuario = (Usuario) getActivity().getIntent().getSerializableExtra("usuario");
        mostraMensajeria();


        return view;
    }

    private void mostraMensajeria() {
        AsyncHttpClient ahcMensajeria = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("usu1", String.valueOf(usuario.getIdUsuario()));

        ahcMensajeria.get(urlMostrarChat, params, new BaseJsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if (statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        lista.clear();
                        for (int i = 0; i < jsonArray.length(); i++){

                            lista.add(new MensajeriaInterna(jsonArray.getJSONObject(i).getInt("idMensajeria"),
                                                            usuario.getIdUsuario(),
                                                            jsonArray.getJSONObject(i).getInt("otro_usuario"),
                                                            jsonArray.getJSONObject(i).getString("Username"),
                                                            jsonArray.getJSONObject(i).getString("ultimo_mensaje"),
                                                            jsonArray.getJSONObject(i).getString("ultima_hora"),
                                                            jsonArray.getJSONObject(i).getString("ultima_fecha"),
                                                            jsonArray.getJSONObject(i).getString("Foto")));
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

    }

    private void mensajeInterno() {
        int btnMenu = 5;
        onClickMenu(btnMenu);
    }

    @Override
    public void onClickMenu(int idBoton) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.menuRelaArea, fragments [idBoton]);
        ft.commit();
    }
}