package com.example.proyectomeep.fragmentos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.proyectomeep.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfigFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigFragment extends Fragment implements View.OnClickListener {
CheckBox chkNotificaciones, chkSonido;
Spinner cboIdiomas;

Button btnGuardar, btnCerrarSesion;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConfigFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfigFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfigFragment newInstance(String param1, String param2) {
        ConfigFragment fragment = new ConfigFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_config, container, false);
        chkNotificaciones = vista.findViewById(R.id.frgCfgNotificaciones);
        chkSonido = vista.findViewById(R.id.frgCfgSonido);
        cboIdiomas = vista.findViewById(R.id.frgCfgIdiomas);

        btnGuardar = vista.findViewById(R.id.frgCfgGuardar);
        btnCerrarSesion = vista.findViewById(R.id.frgCfgCerrarSesion);

        //Configura los eventos on clic a los botones
        btnGuardar.setOnClickListener(this);
        btnCerrarSesion.setOnClickListener(this);

        //cargar Idiomas al comboBox
        llenarIdiomas();

        //cargar las preferencias
        cargarPreferencias();

        return vista;
    }

    private void llenarIdiomas() {
        String [] idiomas ={"----Elija un idioma----","Español","Ingles","Portugues","Quechua"};
        cboIdiomas.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,idiomas));
    }

    private void cargarPreferencias() {
        SharedPreferences preferences = getActivity().getSharedPreferences("Preferencias", Context.MODE_PRIVATE);
        boolean notificaciones = preferences.getBoolean("Notificaciones",false);
        boolean sonido = preferences.getBoolean("Sonido",false);
        int idioma = preferences.getInt("Idioma",0);

        chkNotificaciones.setChecked(notificaciones);
        chkSonido.setChecked(sonido);
        cboIdiomas.setSelection(idioma);

    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.frgCfgGuardar){
            guardar();
        } else if (v.getId() == R.id.frgCfgCerrarSesion) {
            cerrarsesion();
        }
    }

    private void guardar() {
        if (cboIdiomas.getSelectedItemPosition() != 0) {
        SharedPreferences preferences = getActivity().getSharedPreferences("Preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("Notificaciones", chkNotificaciones.isChecked());
        editor.putBoolean("Sonido", chkSonido.isChecked());

        editor.putInt("Idioma", cboIdiomas.getSelectedItemPosition());
        editor.commit();

        Toast.makeText(getContext(), "Preferencias Guardadas", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getContext(), "Debes elegir un idioma", Toast.LENGTH_SHORT).show();
    }

    private void  cerrarsesion() {
    }

}