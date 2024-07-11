package com.example.proyectomeep.fragmentos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomeep.R;
import com.example.proyectomeep.actividades.BienvenidaActivity;
import com.example.proyectomeep.actividades.Maps_Activity;
import com.example.proyectomeep.actividades.MiembrosActivity;
import com.example.proyectomeep.actividades.RestablecerContrasenhaActivity;
import com.example.proyectomeep.clases.Menu;
import com.example.proyectomeep.clases.Usuario;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForoFragment extends Fragment implements View.OnClickListener, Menu {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ForoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForoFragment newInstance(String param1, String param2) {
        ForoFragment fragment = new ForoFragment();
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
    AlertDialog a2;
    Usuario usuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_foro, container, false);

        fragments = new Fragment[7];
        fragments[0] = new BienvenidaFragment();
        fragments[1] = new ForoFragment();
        fragments[2] = new ProyectoFragment();
        fragments[3] = new CrearPFragment();
        fragments[4] = new MessageListFragment();
        fragments[5] = new MenInterFragment();
        fragments[6] = new TareasFragment();

        ImageView imgBack = view.findViewById(R.id.barra);
        CircleImageView imgUser = view.findViewById(R.id.user1);
        TextView txtPublicacion = view.findViewById(R.id.lblPublicacion);
        TextView txtTareas = view.findViewById(R.id.lblTareas);
        TextView txtMiembros = view.findViewById(R.id.lblMiembros);
        imgBack.setOnClickListener(this);
        imgUser.setOnClickListener(this);
        txtPublicacion.setOnClickListener(this);
        txtTareas.setOnClickListener(this);
        txtMiembros.setOnClickListener(this);
        ImageView imgMas= view.findViewById(R.id.ubi);
        imgMas.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.barra)
            volver();
        else if (v.getId() == R.id.user1) {
            abrirDialog();
        } else if (v.getId() == R.id.btnEnviarMensaje) {
            a2.dismiss();
            enviarMensaje();
        } else if (v.getId() == R.id.lblPublicacion) {
            ingresarPublicacion();
        } else if (v.getId() == R.id.lblTareas) {
            ingresarTareas();
        } else if (v.getId() == R.id.lblMiembros) {
            ingresarMiembros();
        } else if (v.getId() == R.id.ubi) {
            NuevoForo();
        }
    }

    private void NuevoForo() {
        Intent iForo= new Intent(getActivity(), Maps_Activity.class);
        iForo.putExtra("usuario", usuario);
        startActivity(iForo);
    }

    private void ingresarMiembros() {
        Intent iMiembro = new Intent(getActivity(), MiembrosActivity.class);
        usuario = (Usuario) getActivity().getIntent().getSerializableExtra("usuario");
        iMiembro.putExtra("usuario", usuario);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int idProyectoClicked = sharedPreferences.getInt("idProyectoClicked", -1);
        iMiembro.putExtra("idProyecto", idProyectoClicked);
        startActivity(iMiembro);
        getActivity().finish();
    }

    private void ingresarTareas() {
        int btnMenu = 6;
        onClickMenu(btnMenu);
    }

    private void ingresarPublicacion() {
        int btnMenu = 1;
        onClickMenu(btnMenu);
    }

    private void enviarMensaje() {
        int btnMenu = 5;
        onClickMenu(btnMenu);
    }

    private void abrirDialog() {
        AlertDialog.Builder d = new AlertDialog.Builder(getContext(), R.style.CustomDialogTheme);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_dialog_usuario, null);
        d.setView(dialogView);
        a2 = d.create();
        Button btnEnviar = dialogView.findViewById(R.id.btnEnviarMensaje);
        btnEnviar.setOnClickListener(this);
        a2.show();
    }

    @Override
    public void onClickMenu(int idBoton) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.menuRelaArea, fragments [idBoton]);
        ft.commit();
    }

    private void volver() {
        int btnMenu = 2;
        onClickMenu(btnMenu);
    }
}