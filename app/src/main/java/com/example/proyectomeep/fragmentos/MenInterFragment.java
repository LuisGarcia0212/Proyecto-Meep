package com.example.proyectomeep.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyectomeep.R;
import com.example.proyectomeep.actividades.BienvenidaActivity;
import com.example.proyectomeep.adaptadores.ChatAdapter;
import com.example.proyectomeep.clases.Chat;
import com.example.proyectomeep.clases.Usuario;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.Base64;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenInterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenInterFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenInterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenInterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenInterFragment newInstance(String param1, String param2) {
        MenInterFragment fragment = new MenInterFragment();
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

    RecyclerView recChats;
    ArrayList<Chat> lista;
    ChatAdapter adapter = null;
    CircleImageView imgUsuario;
    TextView txtNombreUsuario;
    Usuario usuario;
    private int idUser2;

    final static String urlMostraChat = "http://meep.atwebpages.com/services/mostrarChatUsuario.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_men_inter, container, false);
        usuario = (Usuario) getActivity().getIntent().getSerializableExtra("usuario");
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        idUser2 = sharedPreferences.getInt("idMiembroClicked", -1);
        System.out.println("el valor del usuario 2 es: "+idUser2);
        ImageView imgBack = view.findViewById(R.id.logBackMenu);
        imgUsuario = view.findViewById(R.id.imgUsuario2);
        txtNombreUsuario = view.findViewById(R.id.lblCabezalUsuario2);

        recChats = view.findViewById(R.id.cardChats);
        lista = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recChats.setLayoutManager(manager);
        adapter = new ChatAdapter(lista, usuario);
        recChats.setAdapter(adapter);

        mostrarChatUsuario();
        
        imgBack.setOnClickListener(this);

        return view;
    }

    private void mostrarChatUsuario() {
        AsyncHttpClient ahcMostrarChat = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.add("usu1", String.valueOf(usuario.getIdUsuario()));
        params.add("usu2", String.valueOf(idUser2));

        ahcMostrarChat.get(urlMostraChat, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if (statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        lista.clear();
                        for (int i = 0; i < jsonArray.length(); i++){
                            lista.add(new Chat(jsonArray.getJSONObject(i).getInt("idMensajeria"),
                                               jsonArray.getJSONObject(i).getInt("idUsuarioE"),
                                               jsonArray.getJSONObject(i).getString("usuario_emisor"),
                                               jsonArray.getJSONObject(i).getString("foto_emisor"),
                                               jsonArray.getJSONObject(i).getInt("idUsuarioR"),
                                               jsonArray.getJSONObject(i).getString("usuario_receptor"),
                                               jsonArray.getJSONObject(i).getString("foto_receptor"),
                                               jsonArray.getJSONObject(i).getString("mensaje"),
                                               jsonArray.getJSONObject(i).getString("fecha"),
                                               jsonArray.getJSONObject(i).getString("hora")));
                            adapter.notifyDataSetChanged();
                        }
                        String img;
                        Chat chat = lista.stream()
                                .findFirst()
                                .orElse(null);
                        txtNombreUsuario.setText(chat.getUserReceptor());
                        img = chat.getFoto_receptor();
                        byte[] imageByte = Base64.decode(img, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                        imgUsuario.setImageBitmap(bitmap);

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
        if(v.getId() == R.id.logBackMenu){
            volverBienvenida();
        }
    }

    private void volverBienvenida() {
        Intent iBienvenida = new Intent(getActivity(), BienvenidaActivity.class);
        iBienvenida.putExtra("usuario", usuario);
        iBienvenida.putExtra("idBoton", 0);
        startActivity(iBienvenida);
    }


}