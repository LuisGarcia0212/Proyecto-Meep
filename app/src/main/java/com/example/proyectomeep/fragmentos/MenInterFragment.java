package com.example.proyectomeep.fragmentos;

import android.content.Context;
import android.content.Intent;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomeep.R;
import com.example.proyectomeep.actividades.BienvenidaActivity;
import com.example.proyectomeep.adaptadores.ChatAdapter;
import com.example.proyectomeep.clases.Chat;
import com.example.proyectomeep.clases.Menu;
import com.example.proyectomeep.clases.Usuario;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.Base64;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenInterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenInterFragment extends Fragment implements View.OnClickListener, Menu {

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

    private int volver;
    final static String urlMostraChat = "http://meep.atwebpages.com/services/mostrarChatUsuario.php";
    final static String urlMandarMensaje = "http://meep.atwebpages.com/services/generarMensaje.php";
    final static String urlBuscarUsuario2 = "http://meep.atwebpages.com/services/buscarUsuario.php";
    Fragment[] fragments;
    EditText editChat;
    ImageView btnEnviar;
    Usuario usuario2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_men_inter, container, false);
        usuario = (Usuario) getActivity().getIntent().getSerializableExtra("usuario");
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        idUser2 = sharedPreferences.getInt("idMiembroClicked", -1);
        volver = sharedPreferences.getInt("fragementoVolver", -1);
        ImageView imgBack = view.findViewById(R.id.logBackMenu);
        imgUsuario = view.findViewById(R.id.imgUsuario2);
        txtNombreUsuario = view.findViewById(R.id.lblCabezalUsuario2);
        editChat = view.findViewById(R.id.lblchat);
        btnEnviar = view.findViewById(R.id.btnEnviarMensaje);
        usuario2 = new Usuario();

        fragments = new Fragment[6];

        fragments[0] = new BienvenidaFragment();
        fragments[1] = new MessageListFragment();
        fragments[2] = new ProyectoFragment();
        fragments[3] = new ConfigFragment();
        fragments[4] = new ForoFragment();
        fragments[5] = new MenInterFragment();

        recChats = view.findViewById(R.id.cardChats);
        lista = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recChats.setLayoutManager(manager);
        adapter = new ChatAdapter(lista, usuario);
        recChats.setAdapter(adapter);

        mostrarChatUsuario();
        
        imgBack.setOnClickListener(this);
        btnEnviar.setOnClickListener(this);

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
                        System.out.println("el valoor del json: "+jsonArray.length());
                        if (jsonArray.length() == 0){
                            crearNuevoChat();
                            return;
                        }else {
                            System.out.println("Entro al lleno");
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
                                recChats.scrollToPosition(lista.size() - 1);
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

    public void mandarMensaje(String mensaje, int iUs1, int iUs2){// Obtener la fecha y hora actual
        Date currentDate = new Date();

        // Definir el formato para la fecha y la hora
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        // Formatear la fecha y la hora
        String formattedDate = dateFormatter.format(currentDate);
        String formattedTime = timeFormatter.format(currentDate);
        System.out.println("la fecha es: "+formattedDate);
        System.out.println("la hora es: "+formattedTime);

        AsyncHttpClient ahcMandarMensaje = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.add("idUsu1", String.valueOf(iUs1));
        params.add("idUsu2", String.valueOf(iUs2));
        params.add("mensaje", mensaje);
        params.add("fecha",formattedDate);
        params.add("hora", formattedTime);

        ahcMandarMensaje.post(urlMandarMensaje, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if (statusCode == 200){
                    try {
                        int result = Integer.parseInt(rawJsonResponse.trim());
                        if (result == 1) {
                            Chat nuevoMensaje = new Chat(0, iUs1, usuario.getUsuario(), usuario.getFoto(),
                                    iUs2, txtNombreUsuario.getText().toString(), "", mensaje, formattedDate, formattedTime);
                            lista.add(nuevoMensaje);
                            adapter.notifyDataSetChanged();
                            recChats.scrollToPosition(lista.size() - 1);
                        } else {
                            // Manejar otros casos según sea necesario
                            Toast.makeText(getActivity().getApplicationContext(), "Respuesta inesperada del servidor: " + rawJsonResponse, Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        // Manejar el caso donde la respuesta no es un entero válido
                        Log.e("Error", "Respuesta no válida del servidor: " + rawJsonResponse);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getActivity().getApplicationContext(), "Error: " + statusCode, Toast.LENGTH_SHORT).show();
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

    private void crearNuevoChat() {
        AsyncHttpClient ahcBuscarCliente = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.add("idUsuario", String.valueOf(idUser2));

        ahcBuscarCliente.get(urlBuscarUsuario2, params, new BaseJsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if (statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        usuario2.setIdUsuario(jsonArray.getJSONObject(0).getInt("idUsuario"));
                        usuario2.setNombresC(jsonArray.getJSONObject(0).getString("NombresCompletos"));
                        usuario2.setUsuario(jsonArray.getJSONObject(0).getString("Username"));
                        usuario2.setFoto(jsonArray.getJSONObject(0).getString("Foto"));

                        txtNombreUsuario.setText(usuario2.getUsuario());
                        String img = usuario2.getFoto();
                        byte[] imageByte = Base64.decode(img, Base64.DEFAULT);
                        Bitmap  bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
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
        }else if (v.getId() == R.id.btnEnviarMensaje){
            if(editChat.getText().toString().isEmpty()){
                return;
            }else {
                mandarMensaje(editChat.getText().toString(), usuario.getIdUsuario(), idUser2);
                editChat.setText("");
                editChat.setSelection(0);
                return;
            }
        }
    }

    private void volverBienvenida() {
        int iBoton = volver;
        onClickMenu(iBoton);
    }


    @Override
    public void onClickMenu(int idBoton) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.menuRelaArea, fragments[idBoton]);
        ft.commit();
    }
}